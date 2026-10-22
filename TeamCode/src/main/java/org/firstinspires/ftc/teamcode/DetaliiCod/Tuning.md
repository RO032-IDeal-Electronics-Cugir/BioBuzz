## Tuning.java

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/Tuning.java`

**Purpose:**
Main Pedro Pathing tuning and demo OpMode for the team. It presents a selectable menu (via FTControl Panels) of localization, velocity, acceleration, PIDF, drive, and swerve tuning routines, plus a handful of demo autonomous paths (Line, Triangle, Circle). It also contains the `Drawing` helper that renders the robot, pose history, and paths on the Panels field.

**What it contains:**
- `Tuning` — the outer selectable OpMode (`@TeleOp(name = "Tuning", group = "Pedro Pathing")`). Builds a menu tree with folders: Localization, Automatic, Manual, Tests, Swerve.
- Public static fields: `follower`, `poseHistory`, `telemetryM`, `changes`.
- Static helpers: `drawCurrent()`, `drawCurrentAndHistory()`, `stopRobot()`.
- Inner OpMode classes (all in the same file):
  - LocalizationTest — basic drive + pose telemetry, used to verify localization.
  - ForwardTuner / LateralTuner / TurnTuner — measure ticks-to-inches (or ticks-to-radians) multipliers by driving/turning a known distance/angle.
  - ForwardVelocityTuner / LateralVelocityTuner — measure achieved velocity at full power over a distance, used to tune FollowerConstants velocity vectors.
  - ForwardZeroPowerAccelerationTuner / LateralZeroPowerAccelerationTuner — measure natural deceleration after cutting power at a target velocity, used for braking constants.
  - PredictiveBrakingTuner — runs the robot at various powers, brakes, records velocity vs stopping distance, fits a quadratic curve to model braking behavior.
  - TranslationalTuner / HeadingTuner / DriveTuner — activate individual PIDF axes and let the user push/turn the robot to tune PIDF values.
  - Line — runs forward/back along a line with all PIDFs active, good for pushing the robot and observing correction.
  - CentripetalTuner — runs a curved path to tune centripetal and heading correction.
  - Triangle — a demo autonomous that drives a triangle path.
  - Circle — a demo autonomous that drives a rough circle, facing the center.
  - AnalogMinMaxTuner — collects min/max analog voltages from named encoders while spinning pods (for swerve pod calibration).
  - SwerveOffsetsTest — runs pods in their forward direction to check motor/servo directions (run off the ground).
  - SwerveTurnTest — runs pods in their turning direction to check encoder directions and pod offsets (run off the ground).
  - OffsetsTuner — turns the robot 180 degrees and reports the strafeX/forwardY offsets to apply to the localizer.
- `Drawing` class — renders robot pose, pose history, and paths onto the Panels field using `PanelsField` / `PanelsTelemetry`.

**How it is used:**
- Select "Tuning" on the driver station to open the Panels menu, then pick a sub-OpMode from the folder tree.
- Most tuners are one-shot calibration routines: run them, read the telemetry, apply the reported values to constants/localizer config.
- The demo autos (Triangle, Circle) are for path-following verification, not competition use.
- `Drawing` is used by the tuners to visualize pose on the Panels field.

**Notes for planners:**
- This file is large and mixes many concerns. When adding new tuners or autos, follow the existing inner-class pattern and register them in `Tuning`'s menu builder.
- The team uses PID/PIDF constants and Finite State Machines. When writing new autonomous or teleop logic, prefer those patterns and mention them before implementing.
- Do not edit `Constants.java` or `Tuning.java` without first reading this description and the source, and without agreeing on the change with the programmer.
