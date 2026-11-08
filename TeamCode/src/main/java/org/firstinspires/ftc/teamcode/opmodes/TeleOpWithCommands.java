package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.goalPose;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.command.IntakeCommand;
import org.firstinspires.ftc.teamcode.config.command.MecanumCommand;
import org.firstinspires.ftc.teamcode.config.command.ShooterCommand;
import org.firstinspires.ftc.teamcode.config.command.TurretCommand;
import org.firstinspires.ftc.teamcode.config.subsystem.HammerSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.SpindexerSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.TurretSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.VisionSubsystem;
import org.firstinspires.ftc.teamcode.config.util.Alliance;

public class TeleOpWithCommands extends Robot {
    HammerSubsystem hammer;
    IntakeSubsystem intake;
    MecanumSubsystem drive;
    ShooterSubsystem shooter;
    SpindexerSubsystem spindexer;
    TurretSubsystem turret;
    VisionSubsystem vision;

    Pose start = new Pose(0, 0, 0);

    public static Alliance a = Alliance.BLUE;

    @Override
    public void init(){
        super.reset();
        shooter = new ShooterSubsystem(hardwareMap, goalPose);
        drive = new MecanumSubsystem(hardwareMap, a, start);
        turret = new TurretSubsystem(hardwareMap, drive, goalPose);
        vision = new VisionSubsystem(hardwareMap, a);
        hammer = new HammerSubsystem(hardwareMap);
        intake = new IntakeSubsystem(hardwareMap);
        spindexer = new SpindexerSubsystem(hardwareMap);
        schedule(new TurretCommand(turret, drive));
        schedule(new MecanumCommand(drive, vision, gamepad1));
        schedule(new ShooterCommand(shooter, drive, hammer));
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop(){

        if(gamepad1.a){
            shooter.isShooting = true;
        }else{
            shooter.isShooting = false;
        }

        if(gamepad1.b){
            schedule(new IntakeCommand(intake, spindexer, hammer, true, false));
        }else{
            schedule(new IntakeCommand(intake, spindexer, hammer, false, false));
        }

        super.schedule(new TurretCommand(turret, drive));
        super.schedule(new MecanumCommand(drive, vision, gamepad1));

        telemetry.addData("Pose", drive.getPose());
        telemetry.addData("Servo Power", turret.getPower());
        telemetry.addData("Absolute Angle", turret.getAbsoluteTarget(drive));
        telemetry.addData("Ideal Turret Angle", turret.angleWrap(-turret.getAbsoluteTarget(drive) + Math.toDegrees(drive.getPose().heading())));
        telemetry.addData("Current Turret Angle", turret.getTurretDegrees());
        telemetry.addData("Theoretical Power", turret.power);
        telemetry.update();

        super.loop();
    }
}