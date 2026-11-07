package org.firstinspires.ftc.teamcode.config.subsystem;

import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.SHOOTER_LOOKUP_TABLE;

import com.pedropathing.controllers.PIDController;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.SHOOTER_PIDF;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.VELOCITYCOMP_GAIN_CLOSE;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.VELOCITYCOMP_GAIN_FAR;

import java.util.ArrayList;
import java.util.Arrays;

public class ShooterSubsystem {
    public DcMotorEx shooter;
    public Servo hood;
    PIDController pidf;


    Pose goalPose;
    public boolean isShooting = false;



    public ShooterSubsystem(HardwareMap hardwareMap, Pose goalPose){
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        hood = hardwareMap.get(Servo.class, "hood");
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        this.goalPose = goalPose;

        pidf = new PIDController(SHOOTER_PIDF.p, SHOOTER_PIDF.i, SHOOTER_PIDF.d);
    }

    public double[] interpolateDistance(double distance){
        int i = 0;
        while(i < SHOOTER_LOOKUP_TABLE.length && distance > SHOOTER_LOOKUP_TABLE[i][0]) {i++;}

        double d0 = SHOOTER_LOOKUP_TABLE[i][0];
        double d1 = SHOOTER_LOOKUP_TABLE[i + 1][0];

        double shooterVel = Range.scale(
                distance, d0, d1,
                SHOOTER_LOOKUP_TABLE[i][1],
                SHOOTER_LOOKUP_TABLE[i + 1][1]
        );

        double hoodPos = Range.scale(
                distance, d0, d1,
                SHOOTER_LOOKUP_TABLE[i][2],
                SHOOTER_LOOKUP_TABLE[i + 1][2]
        );

        double spindexerVel = Range.scale(
                distance, d0, d1,
                SHOOTER_LOOKUP_TABLE[i][3],
                SHOOTER_LOOKUP_TABLE[i + 1][3]
        );

        double lookupTime = Range.scale(
                distance, d0, d1,
                SHOOTER_LOOKUP_TABLE[i][4],
                SHOOTER_LOOKUP_TABLE[i + 1][4]
        );

        return new double[]{shooterVel, hoodPos, spindexerVel, lookupTime, i, d0, d1};
    }

    public void bangbang(double targetVel){
        if(getVelocity() < targetVel){
            shooter.setPower(0.8);
        }else{
            shooter.setPower(0);
        }
    }

    public double getVelocity() {
        return shooter.getVelocity();
    }


    public void setWheelVel(double targetVel){
        double vel = pidf.calculate(targetVel, getVelocity());
        shooter.setVelocity(vel);
    }


    public void setHoodAngle(double target){
        hood.setPosition(target);
    }

    public double getDistance(Pose currentPose){
        return currentPose.distance(goalPose);
    }

    public void shoot(Pose currentPose){
        double distance = getDistance(currentPose);
        double shooterTarget = interpolateDistance(distance)[0];
        bangbang(shooterTarget);

        double currentVel = getVelocity();
        double gain = (distance > 90) ? VELOCITYCOMP_GAIN_FAR : VELOCITYCOMP_GAIN_CLOSE;
        double hoodPos = interpolateDistance(distance)[1];
        hoodPos = hoodPos - ((shooterTarget - currentVel) / shooterTarget) * gain;

        setHoodAngle(hoodPos);
    }

    public void setShooting(boolean isShooting){this.isShooting = isShooting;}
}
