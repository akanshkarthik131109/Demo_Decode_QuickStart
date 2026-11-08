package org.firstinspires.ftc.teamcode.opmodes.test;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.math.Pose;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.command.MecanumCommand;
import org.firstinspires.ftc.teamcode.config.command.ShooterCommand;
import org.firstinspires.ftc.teamcode.config.subsystem.HammerSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.VisionSubsystem;
import org.firstinspires.ftc.teamcode.config.util.Alliance;

import java.util.Arrays;


@Config
@TeleOp(name = "shooter test", group = "test")
public class shooterTest extends Robot {

    MecanumSubsystem drive;
    ShooterSubsystem shooter;
    Pose goalPose = new Pose(128, 128);

    HammerSubsystem hammer;

    public static Alliance a = Alliance.RED;
    VisionSubsystem vision;

    Pose start = new Pose(138, 36, Math.toRadians(180));

    @Override
    public void init(){
        super.reset();
        vision = new VisionSubsystem(hardwareMap, a);
        shooter = new ShooterSubsystem(hardwareMap, goalPose);
        drive = new MecanumSubsystem(hardwareMap, a, start);
        hammer = new HammerSubsystem(hardwareMap);
        schedule(new ShooterCommand(shooter, drive, hammer));
    }

    @Override
    public void loop(){
        if(gamepad1.a){
            shooter.isShooting = !shooter.isShooting;
        }
        super.schedule(new MecanumCommand(drive, vision, gamepad1));

        drive.drive(gamepad1);

        double[] interp = shooter.interpolateDistance(shooter.getDistance(drive.getPose()));

        telemetry.addData("Distance", shooter.getDistance(drive.getPose()));
        telemetry.addData("Hood Angle", shooter.hood.getPosition());
        telemetry.addData("Interp Arrat", Arrays.toString(interp));
        telemetry.addData("Shooter velocity", shooter.shooter.getVelocity());
        telemetry.addData("Pose", drive.getPose());
        telemetry.update();

        super.loop();
    }

}
