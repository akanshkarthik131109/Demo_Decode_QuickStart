package org.firstinspires.ftc.teamcode.config.opmodes.test;

import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.SPINDEXER_ZERO_POWER;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.SPIN_POWER;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.subsystem.SpindexerSubsystem;

@Config
@TeleOp(name = "spindexer test", group = "test")
public class spindexerTest extends Robot {
    SpindexerSubsystem spindexer;

    public static double targetDegrees = 200;
    public static double velocity = 800;

    boolean zeroing = false;

    @Override
    public void init(){
        super.reset();
        spindexer = new SpindexerSubsystem(hardwareMap);
    }

    @Override
    public void loop(){
        if(gamepad1.yWasPressed()){
            zeroing = true;
            spindexer.runUsingEncoder();
            spindexer.setPower(SPINDEXER_ZERO_POWER);
        }

        if(zeroing){
            if(spindexer.isLimitSwitchPressed()){
                spindexer.setPower(0);
                spindexer.resetSpindexer();
                zeroing = false;
            }
        } else {
            if(gamepad1.aWasPressed()) spindexer.goDegrees(targetDegrees, SPIN_POWER);
            if(gamepad1.bWasPressed()) spindexer.goHome(SPIN_POWER);
            if(gamepad1.x) spindexer.setVelocity(velocity);
            if(gamepad1.xWasReleased()) spindexer.setVelocity(0);
        }

        telemetry.addData("zeroing", zeroing);
        telemetry.addData("limit switch", spindexer.isLimitSwitchPressed());
        telemetry.addData("ticks", spindexer.getCurrentPosition());
        telemetry.addData("degrees", spindexer.getCurrentDegrees());
        telemetry.update();

        super.loop();
    }

    @Override
    public void stop(){
        spindexer.setPower(0);
    }
}
