## TeleOP folder

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedro/TeleOP/`

**Purpose:**
Home for teleop-period OpModes — the routines controlled by drivers during the teleop period of a match.

**Intended contents:**
Teleop OpMode classes that read gamepad input and drive the robot, using the team's subsystems, constants, and Pedro Pathing as appropriate. These should be selectable on the driver station for the teleop period.

**Existing OpModes:**
- `teleOP.java` — Current driver-controlled OpMode (`TeleOP`) using Pedro Pathing manual drive, Ivy Scheduler, and SolversLib `GamepadEx`.
  - Drivetrain: `follower.manual(forward, strafe, turn)` with null checks and `follower.update()` called in `loop()`.
    - Left stick Y (`forward`), Left stick X (`strafe`), Triggers (`turn`).
  - Position Initializing: Sets `follower.setPose(new Pose(110, 96, 0))` in `init()`.
  - **Turret Auto-Aim (`gamepad1.b` / `CIRCLE`)**: Toggles continuous auto-aim mode on/off. When active, continuously executes `turret.updateAim(...)` in every `loop()` iteration to keep the turret aimed at target `(144.0, 144.0)` as the robot moves.
  - **Intake & Transfer Controls (`handleDriver1Controls()`)**:
    - **A Button (`driver1.wasJustPressed(GamepadKeys.Button.A)`)**: Toggles both `IntakeSubsystem` and `TransferSubsystem` on (power `0.75`) and off (`stop()`).
  - **Dpad Up (`gamepad1.dpad_up`)**: Automatically evaluates the closest flower via `ClosestFlower.getClosestFlower(...)` and schedules hold/navigation (`schedule(hold(follower, targetPose))`) to drive to that flower.
  - Position Tracking: Retrieves real-time position using `follower.localizer.pose()` and displays X, Y, Heading, Closest Flower, Intake Status, and Turret Target Angle / Servo Position on telemetry.
  - Uses Pedro Pathing `Follower` via `Constants.create(hardwareMap)`.

**Notes for planners:**
- Coordinate with the subsystem design so teleop OpModes delegate to subsystems rather than poking hardware directly.
- Prefer PID/PIDF-based control and Finite State Machines for complex teleop behaviors.
- Each new teleop OpMode file needs a matching description file here in DetaliiCod after the change.
