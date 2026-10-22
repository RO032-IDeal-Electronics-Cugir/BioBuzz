package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Localizer;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.ThreeWheelConfig;
import com.pedropathing.revhub.localizers.ThreeWheelLocalizer;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("lf");
        c.frontRightName.set("rf");
        c.backLeftName.set("lr");
        c.backRightName.set("rr");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
    });

    public static ThreeWheelConfig localizerConfig = new ThreeWheelConfig(c -> {
        c.leftEncoderName.set("lf");
        c.rightEncoderName.set("rr");
        c.strafeEncoderName.set("lr");
        c.leftPodY.set(1.0);
        c.rightPodY.set(-1.0);
        c.strafePodX.set(0.0);
        c.forwardTicksToInches.set(0.001);
        c.strafeTicksToInches.set(0.001);
        c.turnTicksToRadians.set(0.001);
        c.leftEncoderDirection.set(1.0);
        c.rightEncoderDirection.set(1.0);
        c.strafeEncoderDirection.set(1.0);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.1);
                Controller secondaryTranslationalForward = Controller.proportional(0.1);
                Controller primaryTranslationalLateral = Controller.proportional(0.1);
                Controller secondaryTranslationalLateral = Controller.proportional(0.1);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.01));
                c.brake.set(Controller.proportionalFeedforward(0.01));

                c.headingFeedback.set(Controller.proportional(1.0));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.0, 0.0));

                c.linearBrakeCoefficients.set(Matrix.diag(0.0, 0.0));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0, 0.0));

                c.maxAchievableForwardVelocity.set(50.0);
                c.maxAchievableStrafeVelocity.set(50.0);
                c.naturalForwardDeceleration.set(100.0);
                c.naturalStrafeDeceleration.set(100.0);

                c.parametricTConstraint.set(0.025);
                c.velocityConstraint.set(0.1);
                c.translationalConstraint.set(0.1);
                c.headingConstraint.set(0.007);
                c.timeoutConstraint.set(100.0);
                c.brakeAggression.set(1.0);
                c.maxBrakingPower.set(0.2);
                c.maxAccelerationConstraint.set(ForesightConfig.Constraint.NONE);
                c.maxVelocityConstraint.set(ForesightConfig.Constraint.NONE);
                c.maxDecelerationConstraint.set(ForesightConfig.Constraint.NONE);
                c.coastDownToVelocity.set(0.0);
                c.headingDriveRatio.set(0.5);
                c.brakeAtEnd.set(true);
                c.translationalDeviationTolerance.set(2.5);
                c.headingDeviationTolerance.set(Math.toRadians(11.25));
                c.cosineScale.set(false);
            }
    );

    public static Follower create(HardwareMap h) {
        Localizer localizer = new ThreeWheelLocalizer(h, localizerConfig);
        Mecanum drivetrain = new Mecanum(h, drivetrainConfig);
        Foresight algorithm = new Foresight(foresightConfig);
        return new Follower(localizer, drivetrain, algorithm);
    }
}
