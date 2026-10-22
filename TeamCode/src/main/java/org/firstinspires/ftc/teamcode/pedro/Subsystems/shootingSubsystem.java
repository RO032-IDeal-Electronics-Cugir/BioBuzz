package org.firstinspires.ftc.teamcode.pedro.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class shootingSubsystem {
    public static final double SHOOTER_MOTOR_CPR = 28.0;
    private double currentTargetRPM = 0.0;

    private final DcMotorEx shooterMotor;

    public shootingSubsystem(HardwareMap hardwareMap) {
        shooterMotor = hardwareMap.get(DcMotorEx.class, "motorShooter");
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor.setPower(0);
    }

    public void shoot() {
        shooterMotor.setPower(1.0);
    }

    public void setPower(double power) {
        shooterMotor.setPower(power);
    }

    public void setVelocity(double angularRate) {
        shooterMotor.setVelocity(angularRate);
    }

    private double ticksPerSecondToRpm(double ticksPerSecond) {
        return (ticksPerSecond / SHOOTER_MOTOR_CPR) * 60.0;
    }
    public double getShooterCurrentRPM() { return ticksPerSecondToRpm(shooterMotor.getVelocity()); }
    public void setShooterTargetRPM(double rpm) { this.currentTargetRPM = rpm; }
    public double getShooterTargetRPM() { return this.currentTargetRPM; }

    public void stop() {
        shooterMotor.setPower(0);
    }
}
