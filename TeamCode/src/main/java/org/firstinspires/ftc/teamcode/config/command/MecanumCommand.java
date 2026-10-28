package org.firstinspires.ftc.teamcode.config.command;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.CommandBuilder;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.ConflictBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.pedropathing.ivy.commands.Commands;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.config.subsystem.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystem.VisionSubsystem;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MecanumCommand implements Command {
    MecanumSubsystem mecanumSubsystem;
    VisionSubsystem visionSubsystem;
    Gamepad gpad;
    public MecanumCommand(MecanumSubsystem mecanumSubsystem, VisionSubsystem visionSubsystem, Gamepad gpad){
        this.mecanumSubsystem = mecanumSubsystem;
        this.visionSubsystem = visionSubsystem;
        this.gpad = gpad;
    }

    @Override
    public Set<Object> requirements() {
        Set<Object> reqs = new HashSet<>();
        reqs.add(mecanumSubsystem.getFollower());
        reqs.add(visionSubsystem.getCamera());
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
    public void start(){
        mecanumSubsystem.startDrive();
    }

    @Override
    public boolean done() {
        return false;
    }

    @Override
    public void execute() {
        mecanumSubsystem.periodic();
        mecanumSubsystem.drive(gpad);
    }

    @Override
    public void end(EndCondition endCondition) {

    }

    public CommandBuilder reset(){
        Pose3D p = visionSubsystem.getAprilTagPositionStationary(mecanumSubsystem.getFollower().tangentialVelocity());
        return Commands.instant(() -> mecanumSubsystem.resetDrive(p));
    }

    public CommandBuilder toggleCentric(){
        return Commands.instant(mecanumSubsystem::teleToggleCentric);
    }

    public CommandBuilder hold(){
        return Commands.instant(mecanumSubsystem::holdCurrent);
    }

    public CommandBuilder release(){
        return Commands.instant(mecanumSubsystem::releaseHold);
    }
}
