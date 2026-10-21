package org.firstinspires.ftc.teamcode.config.subsystem;

import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.SPINDEXER_LIMIT_SWITCH_DEGREES;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.SPIN_TICKS;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.config.constants.RobotValues;


public class SpindexerSubsystem {
    public DcMotorEx spindexer;
    public TouchSensor limitSwitch;
    public SpindexerSubsystem(HardwareMap hardwareMap){
        spindexer = hardwareMap.get(DcMotorEx.class, "spindexer");
        spindexer.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        spindexer.setVelocityPIDFCoefficients(RobotValues.SPINDEXER_PIDF.p, RobotValues.SPINDEXER_PIDF.i, RobotValues.SPINDEXER_PIDF.d, RobotValues.SPINDEXER_PIDF.f);
        limitSwitch = hardwareMap.get(TouchSensor.class, "spindexerSwitch");
    }
    public double calcSpinHome() {
        double currPos = spindexer.getCurrentPosition();
        double targetPos = currPos + SPIN_TICKS/2;
        targetPos -= targetPos % SPIN_TICKS;
        return targetPos - 10-80;
    }

    public void goHome(double power) {
        goPosition(calcSpinHome(), power);
    }

    public void goPosition(double target, double power) {
        spindexer.setTargetPosition((int)target);
        spindexer.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        spindexer.setPower(power);
    }

    public void goDegrees(double targetDeg, double power, int direction) {
        double targetPos = (normalizeAngle(targetDeg) - SPINDEXER_LIMIT_SWITCH_DEGREES) / 360.0 * SPIN_TICKS;
        int currentPos = spindexer.getCurrentPosition();
        while (targetPos < currentPos) targetPos += SPIN_TICKS;
        while (targetPos > currentPos + SPIN_TICKS) targetPos -= SPIN_TICKS;
        if (direction < 0) targetPos -= SPIN_TICKS;
        if (Math.abs(targetPos - currentPos) > 10 || spindexer.getMode() != DcMotorEx.RunMode.RUN_TO_POSITION) {
            goPosition(targetPos, power);
        }
    }

    public void goDegrees(double targetDeg, double power) {
        goDegrees(targetDeg, power, 1);
    }

    public void goContinuousPosition(double ticks, double power) {
        double current = spindexer.getCurrentPosition();
        double wrappedPhase = ((ticks % SPIN_TICKS) + SPIN_TICKS) % SPIN_TICKS;
        double base = current - (((current % SPIN_TICKS) + SPIN_TICKS) % SPIN_TICKS);

        double candidate0 = base + wrappedPhase;
        double candidateUp = candidate0 + SPIN_TICKS;
        double candidateDown = candidate0 - SPIN_TICKS;

        double best = candidate0;
        if (Math.abs(candidateUp - current) < Math.abs(best - current)) best = candidateUp;
        if (Math.abs(candidateDown - current) < Math.abs(best - current)) best = candidateDown;

        goPosition(best, power);
    }

    public void resetEncoder() {
        spindexer.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void resetSpindexer(){
        resetEncoder();
        runUsingEncoder();
    }

    public void runUsingEncoder() {
        spindexer.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void setPower(double power) {
        spindexer.setPower(power);
    }

    public void setMode(DcMotorEx.RunMode mode) {
        spindexer.setMode(mode);
    }

    public void setVelocity(double velocity) {
        spindexer.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        spindexer.setVelocity(velocity);
    }

    public int getCurrentPosition() {
        return spindexer.getCurrentPosition();
    }

    public boolean isLimitSwitchPressed() {
        return limitSwitch.isPressed();
    }

    private int degreesToEncoderTicks(double degrees) {
        double degreesFromLimitSwitch = degrees - SPINDEXER_LIMIT_SWITCH_DEGREES;
        return (int) Math.round((degreesFromLimitSwitch / 360.0) * SPIN_TICKS);
    }

    private double normalizeAngle(double degrees) {
        degrees = degrees % 360;
        if (degrees < 0) degrees += 360;
        return degrees;
    }

    public double getCurrentDegrees() {
        int currentPos = spindexer.getCurrentPosition();
        double degreesFromLimitSwitch = (currentPos / SPIN_TICKS) * 360.0;
        double degreesFromZero = degreesFromLimitSwitch + SPINDEXER_LIMIT_SWITCH_DEGREES;
        return normalizeAngle(degreesFromZero);
    }

    public void goDegree(double targetDegree, double power) {
        targetDegree = normalizeAngle(targetDegree);
        double currentDegree = getCurrentDegrees();

        double cwDiff = normalizeAngle(targetDegree - currentDegree);
        double ccwDiff = 360 - cwDiff;

        if (cwDiff <= ccwDiff) {
            goDegreeRight(targetDegree, power);
        } else {
            goDegreeLeft(targetDegree, power);
        }
    }

    public void goDegreeLeft(double targetDegree, double power) {
        targetDegree = normalizeAngle(targetDegree);
        double currentPos = spindexer.getCurrentPosition();
        double targetTicks = degreesToEncoderTicks(targetDegree);

        double offset = ((targetTicks - currentPos) % SPIN_TICKS + SPIN_TICKS) % SPIN_TICKS;
        if (offset == 0) offset = SPIN_TICKS;

        targetTicks = currentPos + offset;
        goPosition(targetTicks, power);
    }

    public void goDegreeRight(double targetDegree, double power) {
        targetDegree = normalizeAngle(targetDegree);
        double currentPos = spindexer.getCurrentPosition();
        double targetTicks = degreesToEncoderTicks(targetDegree);

        double offset = ((targetTicks - currentPos) % SPIN_TICKS + SPIN_TICKS) % SPIN_TICKS;
        if (offset == 0) offset = SPIN_TICKS;

        targetTicks = currentPos - (SPIN_TICKS - offset);
        goPosition(targetTicks, power);
    }
}
