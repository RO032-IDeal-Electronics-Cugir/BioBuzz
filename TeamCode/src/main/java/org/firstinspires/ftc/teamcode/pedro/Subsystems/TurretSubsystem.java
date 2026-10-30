package org.firstinspires.ftc.teamcode.pedro.Subsystems;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    private final Servo turretServo;

    // SPECIFICAȚII FIZICE HARDWARE
    public static double SERVO_RANGE_RADIANS = Math.toRadians(355.0); // Cursa totală a servoului (de ex. Axon 300 grade)

    // ADJUST ME: Dacă turela nu privește exact în față la poziția 0.5 a servoului, ajustează aici în grade
    public static double ZERO_OFFSET_RADIANS = Math.toRadians(-5);

    // Limite fizice de siguranță (față de centrul robotului)
    public static double MIN_SAFE_ANGLE = Math.toRadians(-350.0);
    public static double MAX_SAFE_ANGLE = Math.toRadians(350.0);

    public static double MANUAL_STEP_RADIANS = Math.toRadians(2.5);

    private final Pose redGoalPose = new Pose(127, 129, 0);

    private double lastTargetDeg = 0.0;
    private double lastServoPos = 0.5;

    public TurretSubsystem(HardwareMap hardwareMap) {
        this(hardwareMap, "turretServo");
    }

    public TurretSubsystem(HardwareMap hardwareMap, String servoName) {
        turretServo = hardwareMap.get(Servo.class, servoName);
        turretServo.setDirection(Servo.Direction.FORWARD);
    }

    public void update(Pose robotPose) {
        if (robotPose == null) return;
        update(robotPose.x(), robotPose.y(), robotPose.heading(), redGoalPose.x(), redGoalPose.y());
    }

    public void updateAim(Pose robotPose, double targetX, double targetY) {
        if (robotPose == null) return;
        update(robotPose.x(), robotPose.y(), robotPose.heading(), targetX, targetY);
    }

    public static double TURRET_OFFSET_X = -7.87;
    public static double TURRET_OFFSET_Y = 4.33;

    public void update(double robotX, double robotY, double robotHeading, double targetX, double targetY) {
        // 1. Calculate actual field position of the side-mounted turret
        double cosH = Math.cos(robotHeading);
        double sinH = Math.sin(robotHeading);

        double turretX = robotX + (TURRET_OFFSET_X * cosH - TURRET_OFFSET_Y * sinH);
        double turretY = robotY + (TURRET_OFFSET_X * sinH + TURRET_OFFSET_Y * cosH);

        // 2. Vector from TURRET to target on field
        double deltaX = targetX - turretX;
        double deltaY = targetY - turretY;

        // 3. Absolute field angle from the turret
        double targetFieldAngle = Math.atan2(deltaY, deltaX);

        // 4. Convert to local robot frame
        double localTargetAngle = targetFieldAngle - robotHeading;

        // 5. Normalize [-PI, PI]
        localTargetAngle = normalizeRadians(localTargetAngle);

        // 6. Send to servo
        setTargetAngle(localTargetAngle);
    }

    public void manualControl(double joystickInput) {
        double currentAngleRad = Math.toRadians(lastTargetDeg);
        double newAngleRad = currentAngleRad + (joystickInput * MANUAL_STEP_RADIANS);
        setTargetAngle(newAngleRad);
    }

    public void centerTurret() {
        setTargetAngle(0.0);
    }

    private void setTargetAngle(double targetAngleRadians) {
        // Limitare în unghiurile de siguranță ale turelei
        double clampedAngle = Range.clip(targetAngleRadians, MIN_SAFE_ANGLE, MAX_SAFE_ANGLE);
        this.lastTargetDeg = Math.toDegrees(clampedAngle);

        // Mapare din unghi (-RANGE/2 ... +RANGE/2) în semnal servo (0.0 ... 1.0)
        // Poziția 0.5 = Centru (0 radiani)
        double servoPosition = 0.5 + ((clampedAngle + ZERO_OFFSET_RADIANS) / SERVO_RANGE_RADIANS);

        this.lastServoPos = Range.clip(servoPosition, 0.0, 1.0);
        turretServo.setPosition(lastServoPos);
    }

    public void setInvertServo(boolean invert) {
        turretServo.setDirection(invert ? Servo.Direction.REVERSE : Servo.Direction.FORWARD);
    }

    public double getTargetAngleDeg() {
        return lastTargetDeg;
    }

    public double getLastServoPos() {
        return lastServoPos;
    }

    private double normalizeRadians(double radians) {
        return Math.atan2(Math.sin(radians), Math.cos(radians));
    }
}