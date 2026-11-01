package org.firstinspires.ftc.teamcode.config.opmodes.test;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.command.MecanumCommand;
import org.firstinspires.ftc.teamcode.config.command.TurretCommand;
import org.firstinspires.ftc.teamcode.config.subsystem.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.TurretSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.VisionSubsystem;
import org.firstinspires.ftc.teamcode.config.util.Alliance;


@Config
@TeleOp(name = "turret test", group = "test")
public class turretTest extends Robot {
    MecanumSubsystem drive;
    TurretSubsystem turret;

    VisionSubsystem vision;
    Pose goalPose = new Pose(128, 128);


    public static Alliance a = Alliance.BLUE;


    Pose start = new Pose(0, 0, 0);

    @Override
    public void init(){
        super.reset();
        drive = new MecanumSubsystem(hardwareMap, a, start);
        turret = new TurretSubsystem(hardwareMap, drive, goalPose);
        vision = new VisionSubsystem(hardwareMap, a);
        schedule(new TurretCommand(turret, drive));
        schedule(new MecanumCommand(drive, vision, gamepad1));
    }

    @Override
    public void loop(){
        super.loop();
        super.schedule(new TurretCommand(turret, drive));
        super.schedule(new MecanumCommand(drive, vision, gamepad1));

        telemetry.addData("Pose", drive.getPose());
        telemetry.update();
    }

}
