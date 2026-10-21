package org.firstinspires.ftc.teamcode.config.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Config
@TeleOp
public class motor_test_power extends LinearOpMode {
    public static double power1 = 0;
    public static double power2 = 0;
    DcMotor motor1;
    DcMotor motor2;
    @Override
    public void runOpMode(){
        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);


        waitForStart();
        while(opModeIsActive()){
            motor1.setPower(power1);
            motor2.setPower(power2);

            telemetry.addData("motor1 power", motor1.getPower());
            telemetry.addData("motor2 power", motor2.getPower());
            telemetry.update();
        }
    }

}
