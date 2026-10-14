## TeleOP folder

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/TeleOP/`

**Purpose:**
Home for teleop-period OpModes — the routines controlled by drivers during the teleop period of a match.

**Intended contents:**
Teleop OpMode classes that read gamepad input and drive the robot, using the team's subsystems, constants, and Pedro Pathing as appropriate. These should be selectable on the driver station for the teleop period.

**Existing OpModes:**
- `teleOp.java` — Current driver-controlled OpMode using Pedro Pathing manual drive.
  - Drivetrain: `follower.manual(...)` with `follower.update()` every loop.
    - Left stick Y: forward/back, left stick X: strafe, right stick X: turn (robot-centric).
  - Mechanisms bound to face buttons while held:
    - **Triangle (`gamepad1.y`)**: shooter runs while held, stops on release.
    - **Sony X/Cross (`gamepad1.a`)**: intake runs while held, stops on release.
  - Both mechanisms use the existing `shootingSubsystem` and `intakeSubsystem`.
  - Button mapping note: per FTC PS-style controller aliases, the physical Sony X button is mapped to `gamepad1.a`; `gamepad1.x` is Square.
- `TeleOpMain.java` — Main teleop OpMode reading gamepad driver inputs (see `TeleOpMain.md`).

**Notes for planners:**
- Coordinate with the subsystem design so teleop OpModes delegate to subsystems rather than poking hardware directly.
- Prefer PID/PIDF-based control and Finite State Machines for complex teleop behaviors; mention any such approach before implementing.
- Each new teleop OpMode file needs a matching description file here in DetaliiCod after the change.
