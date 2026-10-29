## TurretSubsystem

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedro/Subsystems/TurretSubsystem.java`

**Purpose:**
Subsystem controlling an automated turret mechanism using an angular servo (`Servo`) with configurable physical range (`SERVO_RANGE_RADIANS = 300°`), zero offset (`ZERO_OFFSET_RADIANS`), and mechanical boundaries (`MIN_SAFE_ANGLE = -135°`, `MAX_SAFE_ANGLE = +135°`).

**Key Features:**
- **Continuous Field Auto-Aim:** Computes relative target angle between `(robotX, robotY, robotHeading)` and `(targetX, targetY)` or `redGoalPose` `(127, 129)` using 6-step calculation logic.
- **Physical Clamping:** Protects wires by clipping local target angles within safe boundaries (`-135°` to `+135°`).
- **Manual Control:** Provides `manualControl(double joystickInput)` for driver override.
- **Utilities:** Provides `centerTurret()`, `setInvertServo(boolean)`, `getTargetAngleDeg()`, and `getLastServoPos()`.
