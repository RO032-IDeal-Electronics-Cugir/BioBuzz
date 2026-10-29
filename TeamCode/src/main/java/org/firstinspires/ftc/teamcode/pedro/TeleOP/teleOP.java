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
    private final PoseFactory p = PoseFactory.degrees();

    // --- Turela cu Axon ---
    private TurretSubsystem turret;
    private boolean turretAutoAimActive = false; // Comutator pornit/oprit pe butonul B / Cerc

    @Override
    public void init() {
        Scheduler.reset();
        intakeIsOn = false;
        driver1 = new GamepadEx(gamepad1);
        intake = new IntakeSubsystem(hardwareMap);
        transfer = new TransferSubsystem(hardwareMap);
        follower = Constants.create(hardwareMap);

        // Inițializare Turelă
        turret = new TurretSubsystem(hardwareMap, "turretServo");

        // IMPORTANT: Dacă turela se rotește în sens invers față de direcția în care ar trebui să compenseze,
        // schimbă această valoare pe true:
        turret.setInvertServo(false);

        turret.centerTurret(); // Aliniază turela drept în față la INIT

        // Poziția inițială a robotului pe teren
        follower.setPose(new Pose(110, 96, 0));

        telemetry.addData("Status", "TeleOp Initialized");
        telemetry.addData("Turret Target (deg)", turret.getTargetAngleDeg());
        telemetry.addData("Turret Servo Pos (0-1)", turret.getLastServoPos());
        telemetry.update();
    }

    @Override
    public void loop() {
        driver1.readButtons();

        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_trigger + gamepad1.left_trigger;

        // Comutator pornit/oprit Auto-Aim pe butonul B (Cerc)
        if (driver1.wasJustPressed(GamepadKeys.Button.B) || driver1.wasJustPressed(GamepadKeys.Button.CIRCLE)) {
            turretAutoAimActive = !turretAutoAimActive;
        }

        // --- CONTROL TURELĂ (EXECUTAT CONTINUU ÎN LOOP) ---
        if (follower != null) {
            Pose currentPose = follower.localizer.pose();

            if (turretAutoAimActive) {
                // Actualizează continua poziție a turelei către ținta implicită (redGoalPose)
                turret.update(currentPose);

                // Notă: Dacă dorești o altă coordonată specifică (ex: X=144, Y=144), folosește:
                // turret.updateAim(currentPose, 144.0, 144.0);
            } else {
                // Când Auto-Aim este OPRIT, controlezi turela manual din Stick-ul Dreapta (X)
                final double TURRET_DEADZONE = 0.1;
                double manualInput = -driver1.getRightX();

                if (Math.abs(manualInput) > TURRET_DEADZONE) {
                    turret.manualControl(manualInput);
                }
            }

            // Comandă aliniere rapidă pe D-PAD UP
            if (driver1.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
                ClosestFlower.FlowerLocation closest = ClosestFlower.getClosestFlower(currentPose);
                Pose targetPose = ClosestFlower.FLOARE_JOS;

                switch (closest) {
                    case JOS:
                        targetPose = ClosestFlower.FLOARE_JOS;
                        break;
                    case DREAPTA:
                        targetPose = ClosestFlower.FLOARE_DREAPTA;
                        break;
                    case SUS:
                        targetPose = ClosestFlower.FLOARE_SUS;
                        break;
                    case STANGA:
                        targetPose = ClosestFlower.FLOARE_STANGA;
                        break;
                }

                Scheduler.reset();
                schedule(hold(follower, targetPose));
            }

            if (!driver1.getButton(GamepadKeys.Button.DPAD_UP)) {
                Scheduler.reset();
                follower.manual(forward, strafe, turn);
            }

            handleDriver1Controls();

            Scheduler.execute();
            follower.update();

            // Telemetrie pentru verificare în timp real
            Pose updatedPose = follower.localizer.pose();
            telemetry.addData("X", updatedPose.x());
            telemetry.addData("Y", updatedPose.y());
            telemetry.addData("Heading (deg)", Math.toDegrees(updatedPose.heading()));
            telemetry.addData("Turret Auto-Aim", turretAutoAimActive ? "ACTIV" : "MANUAL");
            telemetry.addData("Turret Angle Relative (deg)", turret.getTargetAngleDeg());
            telemetry.addData("Turret Servo Pos (0-1)", turret.getLastServoPos());
            telemetry.addData("Intake On", intakeIsOn);
            telemetry.addData("Mode", follower.mode());
        }

        telemetry.update();
    }

    private void handleDriver1Controls() {
        // Toggle Intake & Transfer pe butonul A
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