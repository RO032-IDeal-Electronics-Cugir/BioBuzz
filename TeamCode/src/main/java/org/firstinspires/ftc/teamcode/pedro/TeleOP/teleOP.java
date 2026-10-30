package org.firstinspires.ftc.teamcode.pedro.TeleOP;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.pedro.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Commands.ClosestFlower;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.pedro.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Subsystems.TransferSubsystem;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.hold;

@TeleOp(name="TeleOP")
public class teleOP extends OpMode {

    private Follower follower;
    private GamepadEx driver1;
    private IntakeSubsystem intake;
    private TransferSubsystem transfer;
    public static Boolean intakeIsOn = false;

    // --- Subsystem Turelă cu Encoder Absolut ---
    private TurretSubsystem turret;
    private boolean turretAutoAimActive = false;

    @Override
    public void init() {
        Scheduler.reset();
        intakeIsOn = false;
        driver1 = new GamepadEx(gamepad1);
        intake = new IntakeSubsystem(hardwareMap);
        transfer = new TransferSubsystem(hardwareMap);
        follower = Constants.create(hardwareMap);

        // Inițializare Subsystem Turelă (CRServo + Encoder Analogic)
        turret = new TurretSubsystem(hardwareMap, "turretServo", "turretEncoder");
        turret.setInvertServo(true);
        turret.centerTurret();
        turret.stop();
        // Poziția inițială a robotului pe teren
        follower.setPose(new Pose(110, 96, 0));

        telemetry.addData("Status", "TeleOp Initialized");
        telemetry.addData("Turret Current Encoder (deg)", turret.getCurrentAngleDeg());
        telemetry.update();
    }

    @Override
    public void loop() {
        driver1.readButtons();

        // 1. ACTUALIZARE ODOMETRIE (Trebuie rulat la începutul loop-ului)
        follower.update();
        Scheduler.execute();

        Pose currentPose = follower.localizer.pose();

        // 2. INPUT-URI DRIFT & MANEVRABILITATE
        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_trigger + gamepad1.left_trigger;

        // Comutator Auto-Aim (Buton B / CIRCLE)
        if (driver1.wasJustPressed(GamepadKeys.Button.B) || driver1.wasJustPressed(GamepadKeys.Button.CIRCLE)) {
            turretAutoAimActive = !turretAutoAimActive;
        }

        // 3. LOGICĂ CONTROL TURELĂ (Buclă Închisă)
        if (turretAutoAimActive) {
            // Actualizează continuu poziția target recalculată din odometrie + offset
            turret.update(currentPose);
        } else {
            // Control Manual din Joystick Dreapta (X)
            final double TURRET_DEADZONE = 0.1;
            double manualInput = -driver1.getRightX();

            if (Math.abs(manualInput) > TURRET_DEADZONE) {
                turret.manualControl(manualInput);
            } else {
                // Menține poziția curentă setată (Closed-Loop hold)
                turret.setTargetAngle(Math.toRadians(turret.getTargetAngleDeg()));
            }
        }

        // 4. CONTROL COMANDAT & CONDUCERE MANUALĂ
        if (driver1.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
            ClosestFlower.FlowerLocation closest = ClosestFlower.getClosestFlower(currentPose);
            Pose targetPose = ClosestFlower.FLOARE_JOS;

            switch (closest) {
                case JOS:     targetPose = ClosestFlower.FLOARE_JOS; break;
                case DREAPTA: targetPose = ClosestFlower.FLOARE_DREAPTA; break;
                case SUS:     targetPose = ClosestFlower.FLOARE_SUS; break;
                case STANGA:  targetPose = ClosestFlower.FLOARE_STANGA; break;
            }

            Scheduler.reset();
            schedule(hold(follower, targetPose));
        }

        if (!driver1.getButton(GamepadKeys.Button.DPAD_UP)) {
            follower.manual(forward, strafe, turn);
        }

        handleDriver1Controls();

        // 5. TELEMETRIE ÎN TIMP REAL PENTRU TURING PID & CONTROL
        telemetry.addData("Robot X", currentPose.x());
        telemetry.addData("Robot Y", currentPose.y());
        telemetry.addData("Robot Heading (deg)", Math.toDegrees(currentPose.heading()));
        telemetry.addData("--- TURELĂ ---", "");
        telemetry.addData("Turret Mode", turretAutoAimActive ? "AUTO-AIM (ODOMETRIE)" : "MANUAL");
        telemetry.addData("Turret Target Angle (deg)", turret.getTargetAngleDeg());
        telemetry.addData("Turret Current Encoder (deg)", turret.getCurrentAngleDeg());
        telemetry.addData("Turret Motor Power", turret.getLastPower());
        telemetry.addData("Intake Running", intakeIsOn);
        telemetry.update();
    }

    private void handleDriver1Controls() {
        if (driver1.wasJustPressed(GamepadKeys.Button.A)) {
            intakeIsOn = !intakeIsOn;
        }

        if (intakeIsOn) {
            intake.setPower(0.75);
            transfer.setPower(0.75);
        } else {
            intake.stop();
            transfer.stop();
        }
    }
}