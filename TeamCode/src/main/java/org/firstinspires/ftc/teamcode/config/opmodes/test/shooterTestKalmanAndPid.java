package org.firstinspires.ftc.teamcode.config.opmodes.test;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.command.ShooterCommand;
import org.firstinspires.ftc.teamcode.config.constants.RobotValues;
import org.firstinspires.ftc.teamcode.config.subsystem.HammerSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.config.util.Alliance;


@Config
@TeleOp(name = "shooterTestKalmanAndPid", group = "test")
public class shooterTestKalmanAndPid extends Robot {

    MecanumSubsystem drive;
    ShooterSubsystem shooter;
    Pose goalPose = new Pose(128, 128);

    HammerSubsystem hammer;

    public static Alliance a = Alliance.BLUE;

    Pose start = new Pose(0, 0, 0);

    public static double targetVel = 0;

    @Override
    public void init(){
        super.reset();

        shooter = new ShooterSubsystem(hardwareMap, goalPose);
        drive = new MecanumSubsystem(hardwareMap, a, start);
        hammer = new HammerSubsystem(hardwareMap);
    }

    @Override
    public void loop(){
        if(gamepad1.b){
            shooter.isShooting = !shooter.isShooting;
        }
        

        if(gamepad1.y) targetVel += 500;
        if(gamepad1.a) targetVel -= 500;
        schedule(instant(() -> shooter.setWheelVel(targetVel)));



        drive.drive(gamepad1);

        super.loop();


        telemetry.addData("Velocity Unfiltered", shooter.getVelocityUnfiltered());
        telemetry.addData("KF Estimate", shooter.getEstimatedVelocity());
        telemetry.addData("Target Velocity", targetVel);
        telemetry.update();
    }
}
