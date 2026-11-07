package org.firstinspires.ftc.teamcode.config.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.config.subsystem.ShooterSubsystem;


@TeleOp
@Config
public class getShooterEncoder extends LinearOpMode {
    DcMotorEx shooter;
    @Override
    public void runOpMode(){
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("Shooter Pos", shooter.getCurrentPosition());
            telemetry.addData("Shooter vel", shooter.getVelocity());
            telemetry.update();
        }
    }
}
