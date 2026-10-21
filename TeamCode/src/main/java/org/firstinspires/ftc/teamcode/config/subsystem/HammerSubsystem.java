package org.firstinspires.ftc.teamcode.config.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HammerSubsystem {
    public DcMotor hammer;
    public Servo trigger;
    public Servo ramp;

    public HammerSubsystem(HardwareMap hardwareMap){
        hammer = hardwareMap.get(DcMotor.class,"hammer");
        trigger = hardwareMap.get(Servo.class, "trigger");
        hammer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ramp = hardwareMap.get(Servo.class, "ramp");
    }

    public void setPower(double power){hammer.setPower(power);}
    public void setMode(DcMotor.RunMode runmode){hammer.setMode(runmode);}
    public void triggerPosition(double pos){trigger.setPosition(pos);}
    public void rampPosition(double pos){ramp.setPosition(pos);}
}
