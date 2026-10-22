## Constants.java

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedro/Constants.java`

**Purpose:**
Central configuration for the Pedro Pathing follower. Owns the drivetrain configuration (`MecanumConfig`), localizer configuration (`ThreeWheelConfig`), foresight pathing configuration (`ForesightConfig`), and the factory method `create(HardwareMap)` that builds and returns a configured `Follower`.

**What it contains:**
- `drivetrainConfig` — a `MecanumConfig` instance defining drive motor names (`lf`, `rf`, `lr`, `rr`) and directions.
- `localizerConfig` — a `ThreeWheelConfig` instance defining encoder motor names (`lf`, `rr`, `lr`), offsets, and ticks-to-inches conversions.
- `foresightConfig` — a `ForesightConfig` instance defining controllers (`forwardTranslational`, `strafeTranslational`, `headingFeedback`, `coast`, `brake`), brake coefficients, max velocities/decelerations, constraints, and tolerances.
- `create(HardwareMap h)` — static factory method that instantiates `ThreeWheelLocalizer`, `Mecanum`, and `Foresight`, and returns a fully initialized `Follower` instance.

**How it is used:**
OpModes (such as `teleOp.java`) call `Constants.create(hardwareMap)` during `init()` to initialize their `Follower` instance.

**Notes for planners:**
- Adjust motor names or encoder directions in `drivetrainConfig` and `localizerConfig` according to the physical robot setup.
- Most auto-tuning routines (e.g. `ForesightTuner`) output updated `ForesightConfig` code that can be pasted directly into `foresightConfig`.
