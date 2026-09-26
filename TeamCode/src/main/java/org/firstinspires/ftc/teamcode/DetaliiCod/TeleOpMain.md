## TeleOpMain.java

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/TeleOP/TeleOpMain.java`

**Purpose:**
Main driver-controlled OpMode for the teleop period of a match.

**What it contains:**
- `TeleOpMain` — `@TeleOp(name = "TeleOp Main", group = "TeleOp")` extending `LinearOpMode`.
- Reads left stick Y (`drive`), left stick X (`strafe`), and right stick X (`turn`) from `gamepad1`.
- Basic telemetry showing initialization state, active running status, and drive/strafe/turn joystick values.

**How it is used:**
- Select "TeleOp Main" on the driver station during the TeleOp period.
- Serves as the primary TeleOp entry point to be expanded with Subsystems, Commands, and FSM-driven mechanism controls.

**Notes for planners:**
- Designed to integrate with team Subsystems, Pedro Pathing follower, and Finite State Machines.
- Any modifications or addition of new controls must be discussed with the programmer beforehand and documented in this file.
