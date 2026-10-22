package org.firstinspires.ftc.teamcode.pedro.TeleOP;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.pedro.Subsystems.intakeSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Subsystems.shootingSubsystem;

@TeleOp(name="TeleOp_Final_cu_Turela")
public class teleOp extends OpMode {

    private Follower follower;

    private boolean intakeIsOn = false;
    private intakeSubsystem intake;
    private shootingSubsystem shooter;

    private boolean slowMode = false;
    private final double slowModeMultiplier = 0.4;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        intake = new intakeSubsystem(hardwareMap);
        shooter = new shootingSubsystem(hardwareMap);
    }

    @Override
    public void init_loop() {
        if (follower != null) {
            follower.update();
        }
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        if (gamepad1.rightBumperWasPressed()) {
            slowMode = !slowMode;
        }

        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_trigger + gamepad1.left_trigger;

        if (slowMode) {
            forward *= slowModeMultiplier;
            strafe *= slowModeMultiplier;
            turn *= slowModeMultiplier;
        }

        if (follower != null) {
            // Frână pe loc când ții apăsat Left Bumper
            if (gamepad1.left_bumper) {
                follower.manual(0, 0, 0);
            } else {
                follower.manual(forward, strafe, turn);
            }
            follower.update();
            telemetry.addData("Mode", follower.mode());
        }

        if (gamepad1.y) {
            shooter.setPower(1.0);
        }

        if (gamepad1.aWasPressed()) {
            intakeIsOn = !intakeIsOn;
            if (intakeIsOn) {
                intake.setPower(-1.0);
            } else {
                intake.setPower(0.0);
            }
        }

        telemetry.addData("Slow Mode", slowMode);
        telemetry.addData("Shooter Power", gamepad1.y ? "100%" : "30%");
        telemetry.addData("Shooter rpm", shooter.getShooterCurrentRPM());
        telemetry.addData("Shooter target rpm", shooter.getShooterTargetRPM());
        telemetry.update();
    }
}
