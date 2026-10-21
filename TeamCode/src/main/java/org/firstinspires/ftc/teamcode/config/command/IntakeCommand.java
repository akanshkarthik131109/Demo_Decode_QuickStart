package org.firstinspires.ftc.teamcode.config.command;

import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.INTAKE_POWER;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.RAMP_DISENGAGED;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.SPIN_POWER;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.TRIGGER_HOME;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.ConflictBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.config.subsystem.HammerSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.SpindexerSubsystem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class IntakeCommand implements Command {
    IntakeSubsystem intake;
    SpindexerSubsystem spindexer;
    HammerSubsystem hammer;
    boolean outtake = false;

    public IntakeCommand(IntakeSubsystem intake, SpindexerSubsystem spindexer, HammerSubsystem hammer, Boolean outtake){
        this.intake = intake;
        this.spindexer = spindexer;
        this.hammer = hammer;
        this.outtake = outtake;
    }

    @Override
    public Set<Object> requirements() {
        Set<Object> reqs = new HashSet<>();
        reqs.add(intake.intake);
        reqs.add(spindexer.spindexer);
        reqs.add(spindexer.limitSwitch);
        reqs.add(hammer.hammer);
        reqs.add(hammer.trigger);
        reqs.add(hammer.ramp);
        return reqs;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public InterruptedBehavior interruptedBehavior() {
        return null;
    }

    @Override
    public ConflictBehavior conflictBehavior() {
        return null;
    }

    @Override
    public BlockedBehavior blockedBehavior() {
        return null;
    }

    @Override
    public void start() {
        intake.setPower(0);
        hammer.triggerPosition(TRIGGER_HOME);
        hammer.rampPosition(RAMP_DISENGAGED);
        hammer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hammer.setPower(0);
    }

    @Override
    public boolean done() {
        return false;
    }

    @Override
    public void execute() {
        spindexer.goDegrees(200,SPIN_POWER);
        intake.setPower(outtake ? -INTAKE_POWER : INTAKE_POWER);
    }


    @Override
    public void end(EndCondition endCondition) {
        intake.setPower(0);
    }
}
