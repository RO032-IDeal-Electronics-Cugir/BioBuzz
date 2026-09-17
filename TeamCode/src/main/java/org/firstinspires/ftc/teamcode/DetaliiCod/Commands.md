## Commands folder

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/Commands/`

**Purpose:**
Home for command-layer code that composes subsystems into more complex functions. Commands sit on top of Subsystems and are used by Autonomous and TeleOP OpModes to run higher-level behavior without duplicating subsystem logic.

**Intended contents:**
Command classes (and any supporting command infrastructure) that take subsystem instances and expose higher-level operations — for example "drive to pose", "intake from zone", "shoot sequence", or multi-step autonomous routines built from subsystem primitives.

**Notes for planners:**
- The folder is currently empty — no command classes exist yet.
- Commands should be built **from** the existing Subsystems, not from raw hardware.
- Prefer PID/PIDF-based motion and Finite State Machines for sequencing; mention any such approach before implementing.
- Each new command file needs a matching description file here in DetaliiCod after the change.
