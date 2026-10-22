## Subsystems folder

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedro/Subsystems/`

**Purpose:**
Home for subsystem classes that encapsulate groups of related hardware (e.g. intake, shooter, turret, sensor wrappers).

**Existing Subsystems:**
- `intakeSubsystem.java` — Encapsulates the intake motor (`motorIntake`). Provides `setPower(double)`, `stop()`, and `reverseIntake()`.
- `shootingSubsystem.java` — Encapsulates the shooter motor (`motorShooter`). Provides `shoot()`, `setPower(double)`, `setVelocity(double)`, and `stop()`.

**Notes for planners:**
- Keep PID/PIDF and FSM usage explicit; mention any such usage before writing it.
- Each new subsystem file needs a matching description file here in DetaliiCod after the change.
