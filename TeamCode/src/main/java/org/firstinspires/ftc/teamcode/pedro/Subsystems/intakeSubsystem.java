package org.firstinspires.ftc.teamcode.pedro.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class intakeSubsystem extends SubsystemBase {
    private final DcMotor intakeMotor;

    public intakeSubsystem(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "motorIntake");
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeMotor.setPower(0);
    }

    public void setPower(double power) {
        intakeMotor.setPower(power);
    }

    public void stop() {
        intakeMotor.setPower(0);
    }

    public void reverseIntake() {
        intakeMotor.setPower(-0.8);
    }
}
