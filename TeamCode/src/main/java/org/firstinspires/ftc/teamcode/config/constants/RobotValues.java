package org.firstinspires.ftc.teamcode.config.constants;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Config
public class RobotValues {
    public static final String ROBOTTASK = "/robot/task";
    public static final String DEFAULTPHASE = "/robot/default";
    public static final String SPINDEXERPHASE = "/robot/spindexer";
    public static final String GREEN_ORDER_BB_KEY = "greenOrder";
    public static final String ALLIANCE_BB_KEY = "alliance";

    public static double INTAKE_POWER = -1;
    public static double INTAKE_SHOOTING_POWER = -0.4;

    public static PIDFCoefficients SHOOTER_PIDF = new PIDFCoefficients(400,50,50,2.5);
    public static double HOOD_MAX = 1;
    public static double HOOD_MIN = 0; //touching edge
    public static double VELOCITYCOMP_GAIN_FAR = 1.3;
    public static double VELOCITYCOMP_GAIN_CLOSE = 0.4;

    public static double[][] SHOOTER_LOOKUP_TABLE = {
    //Distance, Shooter Vel, Hood Pos, Spindexer Speed, lookuptime
            {0,    1420, 0.05,  3099, 0},
            {23,   1420, 0.05,  2791, 0.3},
            {46,   1440, 0.35,  2500, 0.7},
            {60,   1620, 0.42,   2200, 0.7},
            {70,   1720, 0.5,   2100, 0.8},
            {80,   1840, 0.53, 1800, 1},
            {95,   1960, 0.6,   1460, 1.2},
            {120,  2200, 0.63,  1300, 1.2},
            {1000, 2300, 0.6,  1300, 1.2}
    };

    public static final double[] ballpos = { 0, 0.28, 0.28, 0.611, 0.28, 0.611, 0.611, 0.500 };

    public static double SPIN_TICKS = 537.7;
    public static double GREEN_DETECT_DIST = 6.0;
    public static double GREEN_HUE_MIN = 120.0;
    public static double GREEN_HUE_MAX = 180.0;
    public static int INDEX_DETECTION_WINDOW = 5;

    //1,2,3
    public static double[][] INDEX_FROM_HOME = {
            {-410, -290, -20},  // GPP
            {-650, -430, -260}, // PGP
            {-240, -60, 150}   // PPG
    };

    public static double GREEN_FLASH = 5;

    public static double SPINDEXER_LIMIT_SWITCH_DEGREES = -120;
    public static double SPIN_POWER = 0.6;
    public static double SPINDEXER_ZERO_POWER = 0.2;
    public static double SPINDEXER_INDEX_DETECTING_OFFSET = 20;
    public static double SPINDEXER_ZERO_TIMEOUT = 10;
    public static final double SWEEP_AMPLITUDE = 90;
    public static final double SWEEP_FREQUENCY = 2;

    public static PIDFCoefficients SPINDEXER_PIDF = new PIDFCoefficients(10,0,0,0);

    public static double HAMMER_POWER = 1;
    public static double TRIGGER_HOME = 0;
    public static double TRIGGER_DOWN = 0.642;
    public static double RAMP_ENGAGED = 0.399;
    public static double RAMP_DISENGAGED = 0.591;

    public static final double TURRET_CPR = 8192 * 108.0 / 36.0;
    public static final double TURRET_TICKS_DEGREES = 90.0 / 6144.0;
    public static final double TURRET_ANGLE_OFFSET = 180;
    public static final double TURRET_MIN_ANGLE = 15;
    public static final double TURRET_MAX_ANGLE = 345;
    public static double TURRET_kP = 0.01;
    public static double TURRET_kD = 0.0005;

    public static double LOOKAHEAD_TIME = 0.34; // motion profiling look-ahead time

    public static int ZONE_CLOSE = 1;
    public static int ZONE_FAR = 2;
    public static int ZONE_NONE = 0;
    public static double[][] cornerOffsets = {  {3.307, -7.5},
                                                {3.307, 7.5},
                                                {-7.086, -7.5},
                                                {-7.086, 7.5}};
    public static int RED_TAG_ID = 24;
    public static int BLUE_TAG_ID = 20;
    public static double TARGET_X = -63;
    public static double TARGET_Y = 63;
    public static boolean USING_STATIONARY_CAMERA = true;

    public static final double RELOCALIZE_VEL_THRESHOLD = 0.05;
    public static final double CAMERA_OFFSET = 5.3;
    public static final double CAMERA_PITCH_DEG = 15.0;
    public static boolean CAMERA_USE_MANUAL_EXPOSURE = true;
    public static int CAMERA_EXPOSURE_MS = -10;
    public static int CAMERA_STREAM_WAIT_MS = 1000;
    public static Position CAMERA_POSITION = new Position(DistanceUnit.MM,-128,-104,212,0);
    public static YawPitchRollAngles CAMERA_ORIENTATION = new YawPitchRollAngles(AngleUnit.DEGREES,180,-60, 0,0);


    public static final double TAU = Math.PI * 2;
    public static final double CIRCLE = 1;
    public static final double ANGTORAD = TAU / CIRCLE;
    public static final double TICKS_PER_REVOLUTION = 384.5;
    public static final double GEAR_RATIO = 1.0 * 71.5 / 72.3;
    public static final double WHEEL_RADIUS = 48 / 25.4;
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_RADIUS * TAU;
    public static final double WHEEL_TRACK = 149 / 25.4;
    public static final double WHEEL_BASE = 132 / 25.4;
    public static final double DISTANCE_PER_REVOLUTION = WHEEL_CIRCUMFERENCE * GEAR_RATIO;
    public static final double LATERAL_GAIN = 61.0 / 72.0;
    public static PIDFCoefficients MOTOR_VELOCITY_PIDF = new PIDFCoefficients(10,0,0,4);
    public static double ODO_X_OFFSET = 8*24;
    public static double ODO_Y_OFFSET = -24;
    public static double GAIN_DRIVE_X = 1.1;
    public static double HEADING_GAIN = 0.008;
    public static double MAX_VELOCITY = 2600;

    public static double GATE_ACTIVATE = 0.25;
    public static double GATE_HOME = 0.4;
//
//    public static final DriveBase.Ramp RAMP20 = new DriveBase.Ramp(36, 12, 0.2, 0.15);
//    public static final DriveBase.Ramp RAMP40 = new DriveBase.Ramp(36, 6, 0.4, 0.25);
//    public static final DriveBase.Ramp RAMP60 = new DriveBase.Ramp(12, 6, 0.6, 0.25);
//    public static final DriveBase.Ramp RAMP70 = new DriveBase.Ramp(12, 6, 0.7, 0.25);
//    public static final DriveBase.Ramp RAMP80 = new DriveBase.Ramp(12, 6, 0.8, 0.25);
//    public static final DriveBase.Ramp RAMPPARK = new DriveBase.Ramp(18, 3, 0.7, 0.30);

    public static final LogoFacingDirection LOGO_DIR = LogoFacingDirection.LEFT;
    public static final UsbFacingDirection USB_DIR = UsbFacingDirection.UP;

    public static final IMU.Parameters IMUPARAM = new IMU.Parameters(
            new RevHubOrientationOnRobot(
                    new Orientation(
                            AxesReference.INTRINSIC,
                            AxesOrder.ZYX,
                            AngleUnit.DEGREES,
                            90,
                            0,
                            45,
                            0  // acquisitionTime, not used
                    )
            )
    );
}
