package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class shootingSubsystem {

    private final DcMotor shooterMotor;

    public shootingSubsystem(HardwareMap hardwareMap) {
        shooterMotor =hardwareMap.get(DcMotorEx.class, "motorShooter");
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor.setPower(0);

    }

    public void setPower(double power) {
        shooterMotor.setPower(power);
    }

    public void stop() {
        shooterMotor.setPower(0);
    }




}
