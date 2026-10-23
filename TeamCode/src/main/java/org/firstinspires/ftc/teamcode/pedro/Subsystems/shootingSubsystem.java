package org.firstinspires.ftc.teamcode.pedro.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class shootingSubsystem extends SubsystemBase {
    public static final double SHOOTER_MOTOR_CPR = 28.0;
    private double currentTargetRPM = 0.0;

    private final DcMotorEx shooterMotor;

    public shootingSubsystem(HardwareMap hardwareMap) {
        shooterMotor = hardwareMap.get(DcMotorEx.class, "motorShooter");
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor.setPower(0);
    }

    public void setPower(double power) {
        shooterMotor.setPower(power);
    }
    public void setVelocity(double angularRate) {
        shooterMotor.setVelocity(angularRate);
    }

    public double getRawVelocity() {
        return shooterMotor.getVelocity();
    }

    private double ticksPerSecondToRpm(double ticksPerSecond) {
        // Use absolute value to prevent negative RPM readings due to encoder polarity / wiring
        return Math.abs((ticksPerSecond / SHOOTER_MOTOR_CPR) * 60.0);
    }

    public double getShooterCurrentRPM() {
        return ticksPerSecondToRpm(shooterMotor.getVelocity());
    }

    public void setShooterTargetRPM(double rpm) {
        this.currentTargetRPM = rpm;
    }

    public double getShooterTargetRPM() {
        return this.currentTargetRPM;
    }

    public void stop() {
        shooterMotor.setPower(0);
    }
}
