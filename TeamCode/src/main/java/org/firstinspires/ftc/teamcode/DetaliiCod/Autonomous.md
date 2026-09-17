## Autonomous folder

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/Autonomous/`

**Purpose:**
Home for autonomous-period OpModes — the routines that run during the autonomous period of a match.

**Intended contents:**
Autonomous OpMode classes that use Pedro Pathing (and the team's subsystems/constants) to execute autonomous routes and actions. These should be self-contained OpModes (or menus of them) suitable for selecting on the driver station for the autonomous period.

**Notes for planners:**
- The folder is currently empty — no autonomous OpModes exist yet.
- Reuse `Constants.createFollower(hardwareMap)` for follower setup where appropriate.
- Prefer PID/PIDF-based motion and Finite State Machines for sequencing; mention any such approach before implementing.
- Each new autonomous OpMode file needs a matching description file here in DetaliiCod after the change.
