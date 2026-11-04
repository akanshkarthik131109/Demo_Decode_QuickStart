package org.firstinspires.ftc.teamcode.config.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;


@Config
@TeleOp
public class motor_and_servo_test extends LinearOpMode {
    public static double velShooter = 0;
    public static double powerTransfer = 0;
    DcMotorEx shooter1;
    DcMotorEx shooter2;

    DcMotorEx transfer;
    Servo deflector;

    Gamepad lastGamepad;

    public static double deflectorPosition = 0.5;
    @Override
    public void runOpMode(){
        shooter1 = hardwareMap.get(DcMotorEx.class, "shooter1");
        shooter1.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        shooter2 = hardwareMap.get(DcMotorEx.class, "shooter2");
        shooter2.setDirection(DcMotorSimple.Direction.REVERSE);
        shooter2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        deflector = hardwareMap.get(Servo.class, "deflector");

        transfer = hardwareMap.get(DcMotorEx.class, "transfer");
        transfer.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
        while(opModeIsActive()){
            shooter1.setVelocity(velShooter);
            shooter2.setVelocity(velShooter);

            transfer.setPower(powerTransfer);

            if(gamepad1.dpad_up) deflectorPosition += 0.005;
            else if(gamepad1.dpad_down) deflectorPosition -= 0.005;

            if(gamepad1.rightBumperWasPressed()) velShooter += 100;
            else if(gamepad1.leftBumperWasPressed()) velShooter -= 100;

            if(gamepad1.yWasPressed()) powerTransfer += 0.05;
            else if(gamepad1.aWasPressed()) powerTransfer -= 0.05;

            lastGamepad = gamepad1;
            deflector.setPosition(deflectorPosition);
            telemetry.addData("shooter1 velocity", shooter1.getVelocity());
            telemetry.addData("shooter2 velocity", shooter2.getVelocity());
            telemetry.addData("Servo Position", deflector.getPosition());
            telemetry.addData("transferVel", transfer.getPower());
            telemetry.update();
        }
    }

}
