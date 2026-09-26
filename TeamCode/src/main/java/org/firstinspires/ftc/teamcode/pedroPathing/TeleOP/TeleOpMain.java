package org.firstinspires.ftc.teamcode.pedroPathing.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp Main", group = "TeleOp")
public class TeleOpMain extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            telemetry.addData("Status", "Running");
            telemetry.addData("Drive", drive);
            telemetry.addData("Strafe", strafe);
            telemetry.addData("Turn", turn);
            telemetry.update();
        }
    }
}
