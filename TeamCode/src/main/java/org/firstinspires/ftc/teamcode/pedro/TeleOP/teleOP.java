package org.firstinspires.ftc.teamcode.pedro.TeleOP;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

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

    @Override
    public void init() {
        Scheduler.reset();
        intakeIsOn = false;
        driver1 = new GamepadEx(gamepad1);
        intake = new IntakeSubsystem(hardwareMap);
        transfer = new TransferSubsystem(hardwareMap);
        follower = Constants.create(hardwareMap);

        // Set initial pose so localizer has a valid starting position and heading
        follower.setPose(new Pose(0, 0, 0));

        telemetry.addData("Status", "TeleOp Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        driver1.readButtons();

        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_trigger + gamepad1.left_trigger;

        if (follower != null) {
            // 1. Pornește comanda O SINGURĂ DATĂ la apăsarea DPAD_UP
            if (driver1.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
                Pose currentPose = follower.localizer.pose();
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

                Scheduler.reset(); // Curăță comenzile vechi
                schedule(hold(follower, targetPose));
            }

            // 2. Când nu ții apăsat DPAD_UP, oprește comanda automată și treci pe manual
            if (!driver1.getButton(GamepadKeys.Button.DPAD_UP)) {
                Scheduler.reset(); // Oprește comanda 'hold' din Scheduler
                follower.manual(forward, strafe, turn);
            }

            handleDriver1Controls();

            // Ordinea corectă: întâi executăm comenzile din Scheduler, apoi facem update la follower
            Scheduler.execute();
            follower.update();

            Pose currentPose = follower.localizer.pose();
            telemetry.addData("X", currentPose.x());
            telemetry.addData("Y", currentPose.y());
            telemetry.addData("Heading (deg)", Math.toDegrees(currentPose.heading()));
            telemetry.addData("Closest Flower", ClosestFlower.getClosestFlower(currentPose));
            telemetry.addData("Intake On", intakeIsOn);
            telemetry.addData("Mode", follower.mode());
        }

        telemetry.update();
    }

    private void handleDriver1Controls() {
        final double STICK_DEADZONE = 0.1;
        double joystickPower = driver1.getRightY() * 0.9;

        if (driver1.wasJustPressed(GamepadKeys.Button.A)) {
            intakeIsOn = !intakeIsOn;
        }

        if (Math.abs(joystickPower) > STICK_DEADZONE) {
            intakeIsOn = false;
            intake.setPower(-joystickPower);
            transfer.setPower(-joystickPower);
        } else if (intakeIsOn) {
            intake.setPower(0.75);
            transfer.setPower(0.75);
        } else {
             intake.stop();
            transfer.stop();
        }
    }
}
