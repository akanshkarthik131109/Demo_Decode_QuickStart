package org.firstinspires.ftc.teamcode.config;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * The general WPILib-style Robot Paradigm
 * <p>
 * Try not to change this as it orchestrates all OpModes
 */
public abstract class Robot extends OpMode {
    /**
     * Cancels all previous commands
     */
    public void reset() {
        Scheduler.reset();
    }

    /**
     * Schedules objects to the scheduler
     */
    public void schedule(Command... commands) {
        Scheduler.schedule(commands);
    }

    @Override
    public void init() { // add init behaviour here...
    }

    @Override
    public void loop() {  // add loop behaviour here...
        // before every loop
        Scheduler.execute(); // super ts method or else break!!
        // after every loop
    }

    public void stop() { // add end behaviour code here...
        reset();
    }
}
