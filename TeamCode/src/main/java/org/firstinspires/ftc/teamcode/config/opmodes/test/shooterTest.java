package org.firstinspires.ftc.teamcode.config.opmodes.test;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.math.Pose;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.command.ShooterCommand;
import org.firstinspires.ftc.teamcode.config.subsystem.HammerSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.config.util.Alliance;


@Config
@TeleOp(name = "shooter test", group = "test")
public class shooterTest extends Robot {

    MecanumSubsystem drive;
    ShooterSubsystem shooter;
    Pose goalPose = new Pose(128, 128);

    HammerSubsystem hammer;

    public static Alliance a = Alliance.BLUE;

    Pose start = new Pose(0, 0, 0);

    @Override
    public void init(){
        super.reset();

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


        drive.drive(gamepad1);

        super.loop();
    }

}
