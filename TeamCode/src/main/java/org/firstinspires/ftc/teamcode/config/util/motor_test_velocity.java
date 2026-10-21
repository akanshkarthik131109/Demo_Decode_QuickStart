package org.firstinspires.ftc.teamcode.config.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Config
@TeleOp
public class motor_test_velocity extends LinearOpMode {
    public static double vel1 = 0;
    public static double vel2 = 0;
    DcMotorEx motor1;
    DcMotorEx motor2;
    @Override
    public void runOpMode(){
        motor1 = hardwareMap.get(DcMotorEx.class, "motor1");
        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        motor2 = hardwareMap.get(DcMotorEx.class, "motor2");
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);


        waitForStart();
        while(opModeIsActive()){
            motor1.setVelocity(vel1);
            motor2.setVelocity(vel2);

            telemetry.addData("motor1 velocity", motor1.getVelocity());
            telemetry.addData("motor2 velocity", motor2.getVelocity());
            telemetry.update();
        }
    }

}
