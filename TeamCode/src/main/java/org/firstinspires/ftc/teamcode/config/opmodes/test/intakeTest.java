package org.firstinspires.ftc.teamcode.config.opmodes.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.command.IntakeCommand;
import org.firstinspires.ftc.teamcode.config.subsystem.HammerSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.SpindexerSubsystem;

@Config
@TeleOp(name = "intake test", group = "test")
public class intakeTest extends Robot {
    IntakeSubsystem intake;
    SpindexerSubsystem spindexer;
    HammerSubsystem hammer;

    @Override
    public void init(){
        super.reset();
        intake = new IntakeSubsystem(hardwareMap);
        spindexer = new SpindexerSubsystem(hardwareMap);
        hammer = new HammerSubsystem(hardwareMap);
    }

    @Override
    public void loop(){
        if(gamepad1.aWasPressed()) schedule(new IntakeCommand(intake, spindexer, hammer, false));
        if(gamepad1.bWasPressed()) schedule(new IntakeCommand(intake, spindexer, hammer, true));

        super.loop();
    }
}
