package org.firstinspires.ftc.teamcode.config.subsystem;

import static org.firstinspires.ftc.teamcode.config.constants.RobotValues.INTAKE_POWER;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem {
    public DcMotorEx intake;
    public IntakeSubsystem(HardwareMap hardwareMap){
        intake = hardwareMap.get(DcMotorEx.class,"intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void setPower(double INTAKE_POWER){intake.setPower(INTAKE_POWER);}

    public void outtake(){
        intake.setPower(-INTAKE_POWER);
    }
}
