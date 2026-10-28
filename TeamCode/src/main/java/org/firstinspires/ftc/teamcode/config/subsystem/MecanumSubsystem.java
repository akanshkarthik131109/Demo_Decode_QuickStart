package org.firstinspires.ftc.teamcode.config.subsystem;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.config.util.Alliance;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class MecanumSubsystem {
    Follower f;
    final Alliance a;
    private boolean hold = false, field=true;

    public MecanumSubsystem(HardwareMap hardwareMap, Alliance a, Pose start){
        f = Constants.create(hardwareMap);
        f.setPose(start);
        this.a=a;
    }
    public void startDrive(){f.manual();}
    public void resetDrive(Pose3D pose){
        f.setPose(new Pose(pose.getPosition().x, pose.getPosition().y, pose.getOrientation().getYaw()));
    }

    public void periodic() {
        f.update();
    }
    public void drive(Gamepad g) {
        if (!hold)
            if (field)
                f.manual( -g.left_stick_y, -g.left_stick_x, -g.right_stick_x);
            else
                f.manual(-g.left_stick_y, -g.left_stick_x, -g.right_stick_x);
    }

    public void holdCurrent() { // useful for parking
        f.hold(f.pose());
        hold = true;
    }

    public void releaseHold() {
        hold = false;
    }

    public void teleToggleCentric() {
        field = !field;
    }

    public void setStart(Pose start) {
        f.setPose(start);
    }

    public Pose getPose() {
        return f.pose();
    }

    public Pose isBusy() {
        return f.pose();
    }

//    public double getT() {
//        return f.getCurrentTValue();
//    }

    public Follower getFollower() { return f;}
}
