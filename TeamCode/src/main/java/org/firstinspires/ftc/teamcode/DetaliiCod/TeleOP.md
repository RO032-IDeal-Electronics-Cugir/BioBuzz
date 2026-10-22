## TeleOP folder

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedro/TeleOP/`

**Purpose:**
Home for teleop-period OpModes — the routines controlled by drivers during the teleop period of a match.

**Intended contents:**
Teleop OpMode classes that read gamepad input and drive the robot, using the team's subsystems, constants, and Pedro Pathing as appropriate. These should be selectable on the driver station for the teleop period.

**Existing OpModes:**
- `teleOp.java` — Current driver-controlled OpMode (`TeleOp_Final_cu_Turela`) using Pedro Pathing manual drive.
  - Drivetrain: `follower.manual(forward, strafe, turn)` with null checks and `follower.update()` called in `loop()`.
    - Left stick Y: forward/back, Left stick X: strafe, Triggers: turn.
    - Right Bumper toggle: Slow Mode (multiplies drive inputs by 0.4).
    - Left Bumper: hold-to-brake (`follower.manual(0, 0, 0)`).
  - Mechanisms:
    - **Y Button (`gamepad1.y`)**: runs shooter at 100% power (1.0) while held; defaults to 30% power (0.3) idle.
    - **A button (`gamepad1.aWasPressed()`)**: toggles intake on (`setPower(-1.0)`) or off (`setPower(0.0)`).
  - Uses `shootingSubsystem` and `intakeSubsystem`.
- `TeleOpMain.java` — Main teleop OpMode reading gamepad driver inputs (see `TeleOpMain.md`).

**Notes for planners:**
- Coordinate with the subsystem design so teleop OpModes delegate to subsystems rather than poking hardware directly.
- Prefer PID/PIDF-based control and Finite State Machines for complex teleop behaviors.
- Each new teleop OpMode file needs a matching description file here in DetaliiCod after the change.
