package org.firstinspires.ftc.teamcode.pedro.Subsystems;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    private final CRServo turretServo;
    private final AnalogInput encoder;

    // --- REGLAJ PID PENTRU CRSERVO ---
    public static double kP = 0.45;  // Redus de la 1.5 pentru a elimina oscilațiile
    public static double kD = 0.02;  // Amortizează mișcarea la apropierea de țintă
    public static double MIN_POWER = 0.04;
    public static double ALLOWED_ERROR_RAD = Math.toRadians(1.5); // Deadband de 1.5 grade

    public static double ENCODER_VOLTAGE_MAX = 3.3;
    public static double ZERO_OFFSET_RADIANS = Math.toRadians(-5);
    public static double MANUAL_STEP_RADIANS = Math.toRadians(2.5);

    public static double TURRET_OFFSET_X = -7.87;
    public static double TURRET_OFFSET_Y = 4.33;

    private final Pose redGoalPose = new Pose(127, 129, 0);

    private double targetAngleRad = 0.0;
    private double lastPower = 0.0;
    private double lastError = 0.0;

    public TurretSubsystem(HardwareMap hardwareMap) {
        this(hardwareMap, "turretServo", "turretEncoder");
    }

    public TurretSubsystem(HardwareMap hardwareMap, String servoName, String encoderName) {
        turretServo = hardwareMap.get(CRServo.class, servoName);
        encoder = hardwareMap.get(AnalogInput.class, encoderName);
        turretServo.setDirection(CRServo.Direction.FORWARD);

        stop(); // Forțează oprirea fizică în INIT
    }

    public void update(Pose robotPose) {
        if (robotPose == null) return;
        update(robotPose.x(), robotPose.y(), robotPose.heading(), redGoalPose.x(), redGoalPose.y());
    }

    public void updateAim(Pose robotPose, double targetX, double targetY) {
        if (robotPose == null) return;
        update(robotPose.x(), robotPose.y(), robotPose.heading(), targetX, targetY);
    }

    public void update(double robotX, double robotY, double robotHeading, double targetX, double targetY) {
        double cosH = Math.cos(robotHeading);
        double sinH = Math.sin(robotHeading);

        double turretX = robotX + (TURRET_OFFSET_X * cosH - TURRET_OFFSET_Y * sinH);
        double turretY = robotY + (TURRET_OFFSET_X * sinH + TURRET_OFFSET_Y * cosH);

        double deltaX = targetX - turretX;
        double deltaY = targetY - turretY;

        double targetFieldAngle = Math.atan2(deltaY, deltaX);
        double localTargetAngle = targetFieldAngle - robotHeading;

        setTargetAngle(normalizeRadians(localTargetAngle));
    }

    public void manualControl(double joystickInput) {
        double newAngleRad = targetAngleRad + (joystickInput * MANUAL_STEP_RADIANS);
        setTargetAngle(normalizeRadians(newAngleRad));
    }

    public void centerTurret() {
        this.targetAngleRad = 0.0;
    }

    public void stop() {
        lastPower = 0.0;
        lastError = 0.0;
        turretServo.setPower(0.0);
    }

    public void setTargetAngle(double targetAngleRadians) {
        this.targetAngleRad = normalizeRadians(targetAngleRadians);

        double currentAngleRad = getCurrentAngleRad();
        double error = normalizeRadians(this.targetAngleRad - currentAngleRad);

        // Oprire directă în zona de toleranță (Deadband)
        if (Math.abs(error) < ALLOWED_ERROR_RAD) {
            stop();
            return;
        }

        // Calcul PD (Proporțional - Derivativ)
        double errorDerivative = error - lastError;
        double power = (error * kP) + (errorDerivative * kD);

        if (Math.abs(power) > 0) {
            power += Math.signum(power) * MIN_POWER;
        }

        lastError = error;
        lastPower = Range.clip(power, -1.0, 1.0);
        turretServo.setPower(lastPower);
    }

    public double getCurrentAngleRad() {
        double voltage = encoder.getVoltage();
        double rawAngle = (voltage / ENCODER_VOLTAGE_MAX) * 2.0 * Math.PI - Math.PI;
        return normalizeRadians(rawAngle - ZERO_OFFSET_RADIANS);
    }

    public void setInvertServo(boolean invert) {
        turretServo.setDirection(invert ? CRServo.Direction.REVERSE : CRServo.Direction.FORWARD);
    }

    public double getTargetAngleDeg() {
        return Math.toDegrees(targetAngleRad);
    }

    public double getCurrentAngleDeg() {
        return Math.toDegrees(getCurrentAngleRad());
    }

    public double getLastPower() {
        return lastPower;
    }

    private double normalizeRadians(double radians) {
        return Math.atan2(Math.sin(radians), Math.cos(radians));
    }
}