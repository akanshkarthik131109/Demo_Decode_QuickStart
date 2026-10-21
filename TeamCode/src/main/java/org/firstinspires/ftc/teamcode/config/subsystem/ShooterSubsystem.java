package org.firstinspires.ftc.teamcode.config.subsystem;

import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.SHOOTER_LOOKUP_TABLE;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.util.InterpLUT;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.SHOOTER_PIDF;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.VELOCITYCOMP_GAIN_CLOSE;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.VELOCITYCOMP_GAIN_FAR;

import com.ThermalEquilibrium.homeostasis.Filters.FilterAlgorithms.KalmanFilter;

public class ShooterSubsystem {
    public DcMotorEx shooter;
    public Servo hood;

    InterpLUT shooterLUT;
    InterpLUT hoodLUT;

    PIDFController pidf;

    KalmanFilter kf;

    public static double Q = 0;
    public static double R = 0;
    public static int N = 0;

    Pose goalPose;
    public boolean isShooting = false;



    public ShooterSubsystem(HardwareMap hardwareMap, Pose goalPose){
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        hood = hardwareMap.get(Servo.class, "hood");

        this.goalPose = goalPose;

        pidf = new PIDFController(SHOOTER_PIDF.p, SHOOTER_PIDF.i, SHOOTER_PIDF.d, SHOOTER_PIDF.f);

        kf = new KalmanFilter(Q, R, N);

        shooterLUT = new InterpLUT();
        hoodLUT = new InterpLUT();

        shooterLUT.add(SHOOTER_LOOKUP_TABLE[0][0], SHOOTER_LOOKUP_TABLE[0][1]);
        shooterLUT.add(SHOOTER_LOOKUP_TABLE[1][0], SHOOTER_LOOKUP_TABLE[1][1]);
        shooterLUT.add(SHOOTER_LOOKUP_TABLE[2][0], SHOOTER_LOOKUP_TABLE[2][1]);
        shooterLUT.add(SHOOTER_LOOKUP_TABLE[3][0], SHOOTER_LOOKUP_TABLE[3][1]);
        shooterLUT.add(SHOOTER_LOOKUP_TABLE[4][0], SHOOTER_LOOKUP_TABLE[4][1]);
        shooterLUT.add(SHOOTER_LOOKUP_TABLE[5][0], SHOOTER_LOOKUP_TABLE[5][1]);
        shooterLUT.add(SHOOTER_LOOKUP_TABLE[6][0], SHOOTER_LOOKUP_TABLE[6][1]);
        shooterLUT.add(SHOOTER_LOOKUP_TABLE[7][0], SHOOTER_LOOKUP_TABLE[7][1]);
        shooterLUT.add(SHOOTER_LOOKUP_TABLE[8][0], SHOOTER_LOOKUP_TABLE[8][1]);


        hoodLUT.add(SHOOTER_LOOKUP_TABLE[0][0], SHOOTER_LOOKUP_TABLE[0][2]);
        hoodLUT.add(SHOOTER_LOOKUP_TABLE[1][0], SHOOTER_LOOKUP_TABLE[1][2]);
        hoodLUT.add(SHOOTER_LOOKUP_TABLE[2][0], SHOOTER_LOOKUP_TABLE[2][2]);
        hoodLUT.add(SHOOTER_LOOKUP_TABLE[3][0], SHOOTER_LOOKUP_TABLE[3][2]);
        hoodLUT.add(SHOOTER_LOOKUP_TABLE[4][0], SHOOTER_LOOKUP_TABLE[4][2]);
        hoodLUT.add(SHOOTER_LOOKUP_TABLE[5][0], SHOOTER_LOOKUP_TABLE[5][2]);
        hoodLUT.add(SHOOTER_LOOKUP_TABLE[6][0], SHOOTER_LOOKUP_TABLE[6][2]);
        hoodLUT.add(SHOOTER_LOOKUP_TABLE[7][0], SHOOTER_LOOKUP_TABLE[7][2]);
        hoodLUT.add(SHOOTER_LOOKUP_TABLE[8][0], SHOOTER_LOOKUP_TABLE[8][2]);
    }

    public double getVelocityUnfiltered() {
        return -((((shooter.getVelocity())) / 28) * 60);
    }

    public double getEstimatedVelocity(){
        return kf.estimate(getVelocityUnfiltered());
    }

    public void setWheelVel(double targetVel){
        double power = pidf.calculate(getEstimatedVelocity(), targetVel);
        shooter.setPower(power);
    }
    

    public void setHoodAngle(double target){
        hood.setPosition(target);
    }

    public double getDistance(Pose currentPose){
        return currentPose.distanceFrom(goalPose);
    }

    public void shoot(Pose currentPose){
        double distance = getDistance(currentPose);
        double shooterTarget = shooterLUT.get(distance);
        setWheelVel(shooterTarget);

        double currentVel = getEstimatedVelocity();
        double gain = (distance > 90) ? VELOCITYCOMP_GAIN_FAR : VELOCITYCOMP_GAIN_CLOSE;
        double hoodPos = hoodLUT.get(distance);
        hoodPos = hoodPos - ((shooterTarget - currentVel) / shooterTarget) * gain;

        setHoodAngle(hoodPos);
    }

    public void setShooting(boolean isShooting){this.isShooting = isShooting;}
}
