package org.firstinspires.ftc.teamcode.config.opmodes.test;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.command.MecanumCommand;
import org.firstinspires.ftc.teamcode.config.subsystem.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.VisionSubsystem;
import org.firstinspires.ftc.teamcode.config.util.Alliance;

@Config
@TeleOp(name = "drive test", group = "test")
public class driveTest extends Robot {
    MecanumSubsystem drive;
    VisionSubsystem vision;
    MecanumCommand driveCommand;

    public static Alliance a = Alliance.BLUE;

    Pose start = new Pose(0, 0, 0);

    @Override
    public void init(){
        super.reset();
        drive = new MecanumSubsystem(hardwareMap, a, start);
        vision = new VisionSubsystem(hardwareMap, a);
        driveCommand = new MecanumCommand(drive, vision, gamepad1);
        schedule(driveCommand);
    }

    @Override
    public void loop(){
        if(gamepad1.xWasPressed()) schedule(driveCommand.toggleCentric());
        if(gamepad1.yWasPressed()) schedule(driveCommand.hold());
        if(gamepad1.bWasPressed()) schedule(driveCommand.release());

        super.loop();
    }
}
