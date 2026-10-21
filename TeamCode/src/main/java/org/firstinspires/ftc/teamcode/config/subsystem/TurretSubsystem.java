package org.firstinspires.ftc.teamcode.config.subsystem;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.ODO_X_OFFSET;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.ODO_Y_OFFSET;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.TURRET_MAX_ANGLE;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.TURRET_MIN_ANGLE;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.TURRET_TICKS_DEGREES;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.TURRET_kD;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.TURRET_kP;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;


public class TurretSubsystem {

    CRServoImplEx servoL;
    CRServoImplEx servoR;
    DcMotor encoder;
    double targetHeading = 0;
    ElapsedTime time;
    public GoBildaPinpointDriver pinpoint;

    public static double kTurret = 0.7;
    public static double kTurretHeading = 0.13;

    public static boolean USE_LOOKAHEAD = true;

    MecanumSubsystem follower;

    double t0, e0;

    Pose goalPose;

    public TurretSubsystem(HardwareMap hardwareMap, Pose goalPose){
        servoL = hardwareMap.get(CRServoImplEx.class, "servoL");
        servoR = hardwareMap.get(CRServoImplEx.class, "servoR");
        encoder = hardwareMap.get(DcMotor.class, "hammer");
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(ODO_X_OFFSET, ODO_Y_OFFSET, DistanceUnit.MM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        encoder.setMode(STOP_AND_RESET_ENCODER);

        this.goalPose = goalPose;
        
        servoL.setDirection(CRServo.Direction.REVERSE);
        servoR.setDirection(CRServo.Direction.REVERSE);
        servoL.setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        servoR.setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        time = new ElapsedTime();

    }

    public double getEncoderPos(){
        return encoder.getCurrentPosition();
    }

    public double angleWrap(double deg){
        deg %= 360;
        if (deg < 0) deg += 360.0;
        return deg;
    }
    public double getTurretDegrees(){
        return angleWrap(getEncoderPos() * TURRET_TICKS_DEGREES);
    }

    public Pose getFuturePose(MecanumSubsystem follower){
        double xVel = pinpoint.getVelX(DistanceUnit.INCH);
        double yVel = pinpoint.getVelY(DistanceUnit.INCH);
        double headVel = pinpoint.getHeadingVelocity(AngleUnit.DEGREES.getUnnormalized());

        if(Double.isNaN(xVel) || Double.isInfinite(xVel)) xVel = 0.0;
        if(Double.isNaN(yVel) || Double.isInfinite(yVel)) yVel = 0.0;
        if(Double.isNaN(headVel) || Double.isInfinite(headVel)) headVel = 0.0;

        double predX = follower.getPose().getX() + xVel * kTurret;
        double predY = follower.getPose().getY() + yVel * kTurret;
        double predHead = follower.getPose().getHeading() + headVel * kTurretHeading;

        return new Pose(predX, predY, predHead);
    }

    public void setTargetHeading(MecanumSubsystem follower){
        Pose currPose = USE_LOOKAHEAD ? getFuturePose(follower) : follower.getPose();

        double angle = Math.atan2((goalPose.getY() - currPose.getY()), (goalPose.getX() - currPose.getX()));

        targetHeading = 180 + angle - currPose.getHeading();
        targetHeading = Range.clip(targetHeading, TURRET_MIN_ANGLE, TURRET_MAX_ANGLE);
    }

    public void update(){
        double currentDeg = getTurretDegrees();
        double error = targetHeading - currentDeg;
        double currTime = time.milliseconds();

        double power = Range.clip(TURRET_kP * error + TURRET_kD * (error - e0)/(currTime - t0), -1.0, 1.0);
        setPower(power);

        t0 = currTime;
        e0 = error;
    }

    public void setPower(double power){
        servoL.setPower(power);
        servoR.setPower(power);
    }


}
