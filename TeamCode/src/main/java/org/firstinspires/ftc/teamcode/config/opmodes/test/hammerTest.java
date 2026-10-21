package org.firstinspires.ftc.teamcode.config.opmodes.test;

import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.HAMMER_POWER;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.RAMP_DISENGAGED;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.RAMP_ENGAGED;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.TRIGGER_DOWN;
import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.TRIGGER_HOME;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.subsystem.HammerSubsystem;

@Config
@TeleOp(name = "hammer test", group = "test")
public class hammerTest extends Robot {
    HammerSubsystem hammer;

    boolean engaged = false;

    @Override
    public void init(){
        super.reset();
        hammer = new HammerSubsystem(hardwareMap);
    }

    @Override
    public void loop(){
        if(gamepad1.aWasPressed()){
            engaged = !engaged;
            hammer.triggerPosition(engaged ? TRIGGER_DOWN : TRIGGER_HOME);
            hammer.rampPosition(engaged ? RAMP_ENGAGED : RAMP_DISENGAGED);
        }

        hammer.setPower(gamepad1.right_bumper ? HAMMER_POWER : 0);

        super.loop();
    }

    @Override
    public void stop(){
        hammer.setPower(0);
    }
}
