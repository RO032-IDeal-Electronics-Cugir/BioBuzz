## Subsystems folder

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/Subsystems/`

**Purpose:**
Home for subsystem classes that encapsulate groups of related hardware (e.g. drivetrain, intake, shooter, turret, carousel, sensor wrappers).

**Intended contents:**
One class per subsystem. Each subsystem should own its own hardware references, its own PID/PIDF constants (formula-based on current and target position, not raw DcMotor position control), and its own state logic where appropriate. Subsystems are meant to be used by Commands to build more complex functions.

**Notes for planners:**
- The folder is currently empty — no subsystem classes exist yet.
- Before adding a subsystem, agree on its scope and which hardware it owns.
- Keep PID/PIDF and FSM usage explicit; mention any such usage before writing it.
- Each new subsystem file needs a matching description file here in DetaliiCod after the change.
