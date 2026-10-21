package org.firstinspires.ftc.teamcode.config.subsystem;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierPoint;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class MecanumSubsystem {
    Follower f;
    final Alliance a;
    private boolean hold = false, field=true;

    public MecanumSubsystem(HardwareMap hardwareMap, Alliance a, Pose start){
        f = Constants.createFollower(hardwareMap);
        f.setStartingPose(start);
        this.a=a;
    }
    public void startDrive(){f.startTeleOpDrive();}
    public void resetDrive(Pose3D pose){
        f.setPose(new Pose(pose.getPosition().x, pose.getPosition().y, pose.getOrientation().getYaw()));
    }

    public void periodic() {
        f.update();
    }
    public void drive(Gamepad g) {
        if (!hold)
            if (field)
                f.setTeleOpDrive(-g.left_stick_y, -g.left_stick_x, -g.right_stick_x, false, a == Alliance.BLUE ? Math.toRadians(180) : 0);
            else
                f.setTeleOpDrive(-g.left_stick_y, -g.left_stick_x, -g.right_stick_x, true);
    }

    public void holdCurrent() { // useful for parking
        f.holdPoint(new BezierPoint(f.getPose()), f.getHeading(), true);
        hold = true;
    }

    public void releaseHold() {
        hold = false;
    }

    public void teleToggleCentric() {
        field = !field;
    }

    public void setStart(Pose start) {
        f.setStartingPose(start);
    }

    public Pose getPose() {
        return f.getPose();
    }

    public Pose isBusy() {
        return f.getPose();
    }

    public double getT() {
        return f.getCurrentTValue();
    }

    public Follower getFollower() { return f;}
}
