package org.firstinspires.ftc.teamcode.pedro.Autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import static com.pedropathing.api.Paths.*;

import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "AUTO.BIG.BLUE", group = "Pedro Pathing")
public class Auto extends OpMode {

    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();

    private final Pose startPose = p.of(59, 9, 90);
    private final Pose first = p.of(59, 54, 180);
    private final Pose second = p.of(110, 96, 0);
    // other poses...
    private final Pose controlPose = p.of(26, 126, 45);


    private Path frst() {
        return line(startPose, first).linear(startPose, first);
    }
    private Path scnd() {
        return curve(first, controlPose, second).linear(first, second);
    }

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
    }

    @Override
    public void start() {
        schedule(follow(follower, frst()));
        schedule(follow(follower, scnd()));
        // using the same park path from the last page
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}
