package org.firstinspires.ftc.teamcode.pedro.TeleOP;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Commands.ClosestFlower;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

@TeleOp(name="TeleOP")
public class teleOP extends OpMode {

    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();

    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        telemetry.addData("Status", "TeleOp Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_trigger + gamepad1.left_trigger;

        if (follower != null) {
            if (gamepad1.dpad_up) {
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

                schedule(follow(follower, line(currentPose, targetPose)));
            } else {
                follower.manual(forward, strafe, turn);
            }

            follower.update();
            Scheduler.execute();

            Pose currentPose = follower.localizer.pose();
            telemetry.addData("X", currentPose.x());
            telemetry.addData("Y", currentPose.y());
            telemetry.addData("Heading", currentPose.heading());
            telemetry.addData("Closest Flower", ClosestFlower.getClosestFlower(currentPose));
            telemetry.addData("Mode", follower.mode());
        }

        telemetry.update();
    }
}
