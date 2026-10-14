package org.firstinspires.ftc.teamcode.pedroPathing.TeleOP;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.intakeSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.shootingSubsystem;
import com.acmerobotics.dashboard.config.Config;

@TeleOp(name="TeleOp_Final_cu_Turela")
@Config
public class teleOp extends OpMode {
    private Follower follower;
    private intakeSubsystem intake;
    private shootingSubsystem shooter;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        intake = new intakeSubsystem(hardwareMap);
        shooter = new shootingSubsystem(hardwareMap);
    }

    @Override
    public void loop() {
        // Drivetrain: Pedro manual drive, robot-centric.
        // Left stick: forward/back + strafe, right stick X: turn.
        follower.manual(
            -gamepad1.left_stick_y,
             gamepad1.left_stick_x,
             gamepad1.right_stick_x
        );
        follower.update();

        // Shooter: triangle (gamepad1.y) held -> shoot, released -> stop.
        if (gamepad1.y) {
            shooter.setPower(1.0);
        } else {
            shooter.stop();
        }

        // Intake: physical Sony X/Cross button (gamepad1.a) held -> intake, released -> stop.
        if (gamepad1.a) {
            intake.setPower(0.8);
        } else {
            intake.stop();
        }
    }
}