package org.firstinspires.ftc.teamcode.pedro.Commands;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;

public class ClosestFlower {
    private static final PoseFactory p = PoseFactory.degrees();

    // Flower positions defined on the field
    public static final Pose FLOARE_JOS = p.of(94, 13, 270);
    public static final Pose FLOARE_DREAPTA = p.of(128, 94, 0);
    public static final Pose FLOARE_SUS = p.of(47, 129, 90);
    public static final Pose FLOARE_STANGA = p.of(13, 47, 180);

    public enum FlowerLocation {
        JOS, DREAPTA, SUS, STANGA
    }

    /**
     * Determines which flower is closest to the robot's current pose using Euclidean distance.
     * @param currentPose The robot's current pose from the localizer.
     * @return The FlowerLocation enum representing the closest flower.
     */
    public static FlowerLocation getClosestFlower(Pose currentPose) {
        if (currentPose == null) {
            return FlowerLocation.JOS;
        }

        double distJos = Math.hypot(FLOARE_JOS.x() - currentPose.x(), FLOARE_JOS.y() - currentPose.y());
        double distDreapta = Math.hypot(FLOARE_DREAPTA.x() - currentPose.x(), FLOARE_DREAPTA.y() - currentPose.y());
        double distSus = Math.hypot(FLOARE_SUS.x() - currentPose.x(), FLOARE_SUS.y() - currentPose.y());
        double distStanga = Math.hypot(FLOARE_STANGA.x() - currentPose.x(), FLOARE_STANGA.y() - currentPose.y());

        double minDist = Math.min(Math.min(distJos, distDreapta), Math.min(distSus, distStanga));

        if (minDist == distJos) return FlowerLocation.JOS;
        if (minDist == distDreapta) return FlowerLocation.DREAPTA;
        if (minDist == distSus) return FlowerLocation.SUS;
        return FlowerLocation.STANGA;
    }
}
