package org.firstinspires.ftc.teamcode.pedro.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class TransferSubsystem extends SubsystemBase {

    private final DcMotorEx transferMotor;

    /**
     * Constructor pentru subsistemul de transfer.
     * @param hardwareMap Obiectul hardwareMap de la OpMode.
     */
    public TransferSubsystem(HardwareMap hardwareMap) {
        transferMotor = hardwareMap.get(DcMotorEx.class, "motorTransfer");

        transferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        transferMotor.setPower(0);
    }

    /**
     * Setează puterea motorului de transfer.
     * @param power Puterea de setat, între -1.0 și 1.0.
     */
    public void setPower(double power) {
        transferMotor.setPower(power);
    }

    /**
     * Oprește motorul de transfer.
     */
    public void stop() {
        transferMotor.setPower(0);
    }
}
