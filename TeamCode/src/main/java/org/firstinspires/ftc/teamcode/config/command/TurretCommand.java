package org.firstinspires.ftc.teamcode.config.command;

import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.ConflictBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.config.subsystem.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.TurretSubsystem;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TurretCommand implements Command{

    MecanumSubsystem follower;

    TurretSubsystem turret;

    public TurretCommand(TurretSubsystem turret, MecanumSubsystem follower){
        this.turret = turret;
        this.follower = follower;
    }

    @Override
    public Set<Object> requirements() {
        Set<Object> reqs = new HashSet<>();
        reqs.add(turret);
        reqs.add(follower.getFollower());
        return reqs;
    }

    @Override
    public void start(){
        turret.setTargetHeading(follower);
    }

    @Override
    public void execute(){
        turret.setTargetHeading(follower);
        turret.update();
    }

    @Override
    public void end(EndCondition endCondition) {

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
