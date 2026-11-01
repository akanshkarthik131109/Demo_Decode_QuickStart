package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("lf");
        c.frontRightName.set("rf");
        c.backLeftName.set("lb");
        c.backRightName.set("rb");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.manualBrakeMode.set(true);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(6.955828929510642);
        c.yPodOffset.set(1.4691791384238897);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.28036852056957223);
                Controller secondaryTranslationalForward = Controller.proportional(0.10358867676561283);
                Controller primaryTranslationalLateral = Controller.proportional(0.8565510186413613);
                Controller secondaryTranslationalLateral = Controller.proportional(0.31647271392324045);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.01901754880536574));
                c.brake.set(Controller.proportionalFeedforward(0.01616491648456088));

                c.headingFeedback.set(Controller.proportional(6.930940205826235));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.052784493077175275, 0.007728746773544796));

                c.linearBrakeCoefficients.set(Matrix.diag(0.08967677297782625, 0.03426883070903397));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0018741123943544996, 0.0025134831583406457));

                c.maxAchievableForwardVelocity.set(62.98242189225059);
                c.maxAchievableStrafeVelocity.set(38.792177741233914);
                c.naturalForwardDeceleration.set(57.91372210760057);
                c.naturalStrafeDeceleration.set(101.16424969517666);
            }
    );
}