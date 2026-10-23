## shootCommand

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedro/Commands/shootCommand.java`

**Purpose:**
A command group (`SequentialCommandGroup`) that coordinates the `shootingSubsystem` to execute a shooting sequence using FSM-style states (spin-up, wait for target RPM via velocity control, maintain firing state, and stop).

**Key Features:**
- Takes `shootingSubsystem` and `targetRpm` as parameters.
- Uses velocity control (ticks per second calculated from RPM and CPR) with encoder feedback.
- Uses `WaitUntilCommand` to ensure the shooter has spun up to speed before proceeding.
- Uses `WaitCommand` for the firing window.
- Automatically claims subsystem requirements to prevent command conflicts.
