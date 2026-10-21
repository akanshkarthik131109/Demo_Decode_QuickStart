package org.firstinspires.ftc.teamcode.config.opmodes.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.command.VisionCommand;
import org.firstinspires.ftc.teamcode.config.subsystem.VisionSubsystem;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Config
@TeleOp(name = "vision test", group = "test")
public class visionTest extends Robot {
    VisionSubsystem vision;

    public static Alliance a = Alliance.BLUE;

    @Override
    public void init(){
        super.reset();
        vision = new VisionSubsystem(hardwareMap, a);
        schedule(new VisionCommand(vision));
    }

    @Override
    public void loop(){
        super.loop();
        vision.periodic();

        telemetry.addData("goal tag", vision.getTag());
        telemetry.addData("bearing", vision.getAprilGoalError());
        telemetry.addData("motif", vision.updateGreenOrderFromTag());

        if(VisionSubsystem.dets != null){
            for(AprilTagDetection det : VisionSubsystem.dets){
                telemetry.addData("tag " + det.id + " range", det.ftcPose.range);
            }
        }

        Pose3D pos = vision.getAprilTagPositionStationary(0);
        telemetry.addData("robot x", pos.getPosition().x);
        telemetry.addData("robot y", pos.getPosition().y);
        telemetry.update();
    }

    @Override
    public void stop(){
        vision.stopPortal();
        super.stop();
    }
}
