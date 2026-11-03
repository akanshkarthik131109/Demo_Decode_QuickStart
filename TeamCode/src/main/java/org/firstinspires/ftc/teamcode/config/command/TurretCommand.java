package org.firstinspires.ftc.teamcode.config.command;

import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.ConflictBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.config.subsystem.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.TurretSubsystem;

import java.util.HashSet;
import java.util.Set;

public class TurretCommand implements Command{

    MecanumSubsystem drive;

    TurretSubsystem turret;

    public TurretCommand(TurretSubsystem turret, MecanumSubsystem drive){
        this.turret = turret;
        this.drive = drive;
    }

    @Override
    public Set<Object> requirements() {
        Set<Object> reqs = new HashSet<>();
        reqs.add(turret);
        return reqs;
    }

    @Override
    public void start(){
        turret.setTargetHeading(drive);
    }

    @Override
    public void execute(){
        turret.setTargetHeading(drive);
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
        return InterruptedBehavior.END;
    }

    @Override
    public ConflictBehavior conflictBehavior() {
        return ConflictBehavior.OVERRIDE;
    }

    @Override
    public BlockedBehavior blockedBehavior() {
        return BlockedBehavior.CANCEL;
    }

}
