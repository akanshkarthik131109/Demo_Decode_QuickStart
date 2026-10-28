package org.firstinspires.ftc.teamcode.config;

import com.pedropathing.follower.Follower;
import com.pedropathing.utils.Timer;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.command.VisionCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.config.subsystem.VisionSubsystem;
import org.firstinspires.ftc.teamcode.config.util.Alliance;

import java.util.List;

public class RobotContainer {
    public Alliance a;
    public final Follower f;

    private final List<LynxModule> hubs;
    private final Timer loop = new Timer();
    public double loops = 0, lastLoop = 0, loopTime = 0;

    // subsystems
    VisionSubsystem visionSubsystem;

    // commands
    VisionCommand visionCommand;


    public RobotContainer(HardwareMap h, Alliance a) {
        this.a = a;

        visionSubsystem = new VisionSubsystem(h,a);

        visionCommand = new VisionCommand(visionSubsystem);

        f = Constants.create(h);
        hubs = h.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        loop.reset();

        periodic();
    }

    public void periodic() {
        f.update();
        visionSubsystem.periodic(); // call the subsystem explicitly here is fine.
    }

    public double getLoopTimeMs() {
        return loopTime;
    }

    public double getLoopTimeHz() {
        return 1000 / loopTime;
    }

}