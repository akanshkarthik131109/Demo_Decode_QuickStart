package org.firstinspires.ftc.teamcode.config.subsystem;

import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.CAMERA_OFFSET;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.CAMERA_ORIENTATION;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.CAMERA_PITCH_DEG;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.CAMERA_POSITION;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.CAMERA_STREAM_WAIT_MS;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.CAMERA_USE_MANUAL_EXPOSURE;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.RELOCALIZE_VEL_THRESHOLD;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VisionSubsystem {
    private static VisionPortal visionPortal;
    private static AprilTagProcessor aprilTagProc;

    Alliance a;

    public static List<AprilTagDetection> dets;
    private static double fx,fy;

    private static double tagX, tagY;

    private static double theta;
    HardwareMap h;

    public VisionSubsystem(HardwareMap h, Alliance a){
        this.h = h;
        this.a = a;
    }

    public void initCamera() {
        aprilTagProc = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagGameDatabase.getDecodeTagLibrary())
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .setLensIntrinsics(544.64 ,  544.64, 343.603 , 212.709)
                .setCameraPose(CAMERA_POSITION, CAMERA_ORIENTATION)
                .build();

        aprilTagProc.setDecimation(1);

        visionPortal = new VisionPortal.Builder()
                .addProcessor(aprilTagProc)
                .setCamera(h.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .build();

        startDashboardCameraStream();
        applyManualExposure();
    }

    public void stopPortal() {
        if (visionPortal != null) {
            visionPortal.stopStreaming();
            visionPortal.close();
            visionPortal = null;
        }
    }

    public double getAprilGoalError() {
        AprilTagDetection det = getDetection(getTag());
        if (det != null && det.ftcPose != null) {
            return det.ftcPose.bearing;
        }
        return 0;
    }

    public int getTag() { return a.equals(Alliance.RED) ? 24 : 20; }

    public AprilTagDetection getDetection(int wantedId) {
        if (dets == null || dets.isEmpty()) return null;
        for (AprilTagDetection det : dets) {
            AprilTagSingleDetection singleTag = (AprilTagSingleDetection) det;
            if (singleTag.id == wantedId) return det;
        }
        return null;
    }

    public void updateDets() {
        if (aprilTagProc == null) return;

        dets = aprilTagProc.getDetections();
    }

    public Pose3D getAprilTagPositionStationary(double velocity){
        if (aprilTagProc == null) return null;
        if (velocity > RELOCALIZE_VEL_THRESHOLD) return null;

        dets = aprilTagProc.getDetections();
        if (dets == null || dets.isEmpty()) return null;
        for (AprilTagDetection det : dets) {
            AprilTagSingleDetection singleTag = (AprilTagSingleDetection) det;
            if (det == null) continue;
            if (singleTag.metadata == null || det.ftcPose == null) continue;

            return det.robotPose;
        }
        return null;
    }

    public int mapTag(int tagId) {
        if (tagId == 21) return 1;
        if (tagId == 22) return 2;
        if (tagId == 23) return 3;
        return 0;
    }

    public int updateGreenOrderFromTag() {
        List<AprilTagDetection> detections = dets;
        if (detections == null || detections.isEmpty()) return -1;

        for (AprilTagDetection det : detections) {
            AprilTagSingleDetection singleTag = (AprilTagSingleDetection) det;
            if (det == null) continue;
            if(singleTag.id == 24 || singleTag.id == 20) continue;
            int mappedOrder = mapTag(singleTag.id);
            if (mappedOrder == 0) continue;

            return mappedOrder;
        }
        return -2;
    }

    private void startDashboardCameraStream() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        if (visionPortal != null) {
            dashboard.startCameraStream(visionPortal, 30);
        }
    }

    private void applyManualExposure() {
        if (!CAMERA_USE_MANUAL_EXPOSURE || visionPortal == null) return;

        int waitedMs = 0;
        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING
                && waitedMs < CAMERA_STREAM_WAIT_MS) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            waitedMs += 20;
        }

        ExposureControl exposure = visionPortal.getCameraControl(ExposureControl.class);
        if (exposure == null) return;

        exposure.setMode(ExposureControl.Mode.Manual);
        exposure.setExposure(6, TimeUnit.MILLISECONDS);

        // Set Gain.
        GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
        gainControl.setGain(67);
    }

    public List<Double> getAprilTagPosition(double velocity, double currentRobotAngle, double currentCameraAngle) {
        if (aprilTagProc == null) return Collections.emptyList();
        if (velocity > RELOCALIZE_VEL_THRESHOLD) return Collections.emptyList();

        dets = aprilTagProc.getDetections();
        if (dets == null || dets.isEmpty()) return Collections.emptyList();

        double headingRad = Math.toRadians(currentRobotAngle);
        double turretRad = Math.toRadians(AngleUnit.normalizeDegrees(currentCameraAngle));
        double cameraYawRad = headingRad + turretRad;

        for (AprilTagDetection det : dets) {
            if (det == null) continue;
            AprilTagSingleDetection singleTag = (AprilTagSingleDetection) det;
            if (singleTag.metadata == null || det.ftcPose == null) continue;

            tagX = singleTag.metadata.fieldPosition.get(0);
            tagY = singleTag.metadata.fieldPosition.get(1);

            double bearingRad = Math.toRadians(det.ftcPose.bearing);
            double elevationRad = Math.toRadians(det.ftcPose.elevation + CAMERA_PITCH_DEG);

            double hrange = det.ftcPose.range * Math.cos(elevationRad);

            theta = cameraYawRad + bearingRad;

            fx = tagX - Math.cos(theta) * hrange;
            fy = tagY - Math.sin(theta) * hrange;

            double offsetX = Math.cos(cameraYawRad) * CAMERA_OFFSET;
            double offsetY = Math.sin(cameraYawRad) * CAMERA_OFFSET;

            fx -= offsetX;
            fy -= offsetY;
            List<Double> l = new ArrayList<>();
            l.add(fx);
            l.add(fy);
            return l;
        }
        return Collections.emptyList();
    }

    public void periodic(){
        updateDets();
    }
    public CameraName getCamera(){
        return h.get(WebcamName.class, "Webcam 1");
    }
}
