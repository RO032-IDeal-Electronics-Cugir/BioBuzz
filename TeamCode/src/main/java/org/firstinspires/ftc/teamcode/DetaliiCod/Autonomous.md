## Autonomous folder

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedro/Autonomous/`

**Purpose:**
Home for autonomous-period OpModes — the routines that run during the autonomous period of a match.

**Current Contents:**
- **`Auto.java`**: Autonomous OpMode (`AUTO.BIG.BLUE`) using Pedro Pathing Follower, poses, paths (`Score1`, `Grab1`, `Park`), and the Ivy Scheduler.
- **`AutonomousExample.java`**: Baseline FSM-driven autonomous OpMode using Pedro Pathing `Follower` with `BezierLine` paths and telemetry feedback.

**Intended contents:**
Autonomous OpMode classes that use Pedro Pathing (and the team's subsystems/constants) to execute autonomous routes and actions. These should be self-contained OpModes (or menus of them) suitable for selecting on the driver station for the autonomous period.

**Notes for planners:**
- Reuse `Constants.create(hardwareMap)` for follower setup where appropriate.
- Prefer PID/PIDF-based motion and Finite State Machines for sequencing; mention any such approach before implementing.
- Each new autonomous OpMode file needs a matching description file here in DetaliiCod after the change.
