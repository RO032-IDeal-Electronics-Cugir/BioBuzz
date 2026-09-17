## TeleOP folder

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/TeleOP/`

**Purpose:**
Home for teleop-period OpModes — the routines controlled by drivers during the teleop period of a match.

**Intended contents:**
Teleop OpMode classes that read gamepad input and drive the robot, using the team's subsystems, constants, and Pedro Pathing as appropriate. These should be selectable on the driver station for the teleop period.

**Notes for planners:**
- The folder is currently empty — no teleop OpModes exist yet.
- Coordinate with the subsystem design so teleop OpModes delegate to subsystems rather than poking hardware directly.
- Prefer PID/PIDF-based control and Finite State Machines for complex teleop behaviors; mention any such approach before implementing.
- Each new teleop OpMode file needs a matching description file here in DetaliiCod after the change.
