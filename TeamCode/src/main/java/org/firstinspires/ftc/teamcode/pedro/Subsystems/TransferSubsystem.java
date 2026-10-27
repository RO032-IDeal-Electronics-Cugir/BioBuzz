package org.firstinspires.ftc.teamcode.pedro.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class TransferSubsystem extends SubsystemBase {

    private final DcMotorEx transferMotor;

    /**
     * Constructor pentru subsistemul de admisie.
     * @param hardwareMap Obiectul hardwareMap de la OpMode.
     */
    public TransferSubsystem(HardwareMap hardwareMap) {
        transferMotor = hardwareMap.get(DcMotorEx.class, "motorTransfer");

        //transferMotor.setDirection(DcMotor.Direction.REVERSE);
        transferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        transferMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        transferMotor.setPower(0);
    }

    /**
     * Setează puterea motorului de admisie.
     * O valoare pozitivă va rula motorul într-o direcție (ex: admisie),
     * o valoare negativă în direcția opusă (ex: evacuare).
     *
     * @param power Puterea de setat, între -1.0 și 1.0.
     */
    public void setPower(double power) {
        transferMotor.setPower(power);

    }

    /**
     * Oprește motorul de admisie.
     */
    public void stop() {
        transferMotor.setPower(0);
    }


}
