## Commands folder

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedro/Commands/`

**Purpose:**
Home for command-layer code that composes subsystems into more complex functions. Commands sit on top of Subsystems and are used by Autonomous and TeleOP OpModes to run higher-level behavior without duplicating subsystem logic.

**Current Contents:**
- **`ClosestFlower.java`**: Utility/command class that stores flower target poses (`FLOARE_JOS`, `FLOARE_DREAPTA`, `FLOARE_SUS`, `FLOARE_STANGA`) and computes via Euclidean distance (`Math.hypot`) which flower is closest to the robot's current pose (`getClosestFlower(Pose)`).

**Notes for planners:**
- Commands should be built **from** the existing Subsystems, not from raw hardware.
- Prefer PID/PIDF-based motion and Finite State Machines for sequencing; mention any such approach before implementing.
- Each new command file needs a matching description file here in DetaliiCod after the change.
