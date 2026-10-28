package org.firstinspires.ftc.teamcode.config.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.subsystem.HammerSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.SpindexerSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.TurretSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.VisionSubsystem;

public class TeleOpWithCommands extends Robot {
    HammerSubsystem hammer;
    IntakeSubsystem intake;
    MecanumSubsystem drive;
    ShooterSubsystem shooter;
    SpindexerSubsystem spindexer;
    TurretSubsystem turret;
    VisionSubsystem vision;

    @Override
    public void init(){

    }

    @Override
    public void loop(){
        super.loop();


    }
}
