package org.firstinspires.ftc.teamcode.config.command;

import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.ConflictBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.constants.RobotValues;
import org.firstinspires.ftc.teamcode.config.subsystem.HammerSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.SpindexerSubsystem;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ShooterCommand implements Command {

    ShooterSubsystem shooter;
    double distance;
    Pose currentPose;
    MecanumSubsystem follower;
    IntakeSubsystem intake;
    SpindexerSubsystem spindexer;

    HammerSubsystem hammer;

    public ShooterCommand(ShooterSubsystem shooter, MecanumSubsystem follower, HammerSubsystem hammer){
        this.shooter = shooter;
        this.follower = follower;
        this.hammer = hammer;
    }

    @Override
    public Set<Object> requirements() {
        Set<Object> reqs = new HashSet<>();
        reqs.add(shooter.shooter);
        reqs.add(follower.getFollower());
        reqs.add(hammer.hammer);
        reqs.add(hammer.trigger);
        reqs.add(hammer.ramp);
        return reqs;
    }

    @Override
    public void start(){}

    @Override
    public void execute(){
        currentPose = follower.getPose();
        if(shooter.isShooting) {
            hammer.setPower(RobotValues.HAMMER_POWER);
            hammer.rampPosition(RobotValues.RAMP_ENGAGED);
            hammer.triggerPosition(RobotValues.TRIGGER_DOWN);
        }else{
            hammer.setPower(0);
            hammer.rampPosition(RobotValues.RAMP_DISENGAGED);
            hammer.triggerPosition(RobotValues.TRIGGER_HOME);
        }
        shooter.shoot(currentPose);
        shooter.setShooting(true);
    }

    @Override
    public void end(EndCondition endCondition) {
        shooter.setShooting(false);
    }

    @Override
    public boolean done(){
        return false;
    }

    /*-------------------------------------------------------------------------------------------*/



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

}
