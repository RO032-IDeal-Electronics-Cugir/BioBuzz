## Subsystems folder

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedro/Subsystems/`

**Purpose:**
Home for subsystem classes that encapsulate groups of related hardware (e.g. intake, transfer, turret, sensor wrappers).

**Existing Subsystems:**
- `IntakeSubsystem.java` — Encapsulates the intake motor (`motorIntake`). Uses `RUN_WITHOUT_ENCODER` for direct PWM power control. Extends `SubsystemBase` for SolversLib command scheduling and requirement management. Provides `setPower(double)` and `stop()`.
- `TransferSubsystem.java` — Encapsulates the transfer motor (`motorTransfer`). Uses `RUN_WITHOUT_ENCODER` for direct PWM power control. Extends `SubsystemBase` for SolversLib command scheduling and requirement management. Provides `setPower(double)` and `stop()`.
- `TurretSubsystem.java` — Encapsulates the turret mechanism driven by an Axon Max MK2 angular Servo (`turretServo`) with $355^\circ$ total range. Extends `SubsystemBase`. Provides field-centric auto-aim targeting (`updateAim(...)`), manual joystick stepping (`manualControl(...)`), zeroing (`centerTurret()`), and directional inversion setters.

**Notes for planners:**
- Keep PID/PIDF and FSM usage explicit; mention any such usage before writing it.
- Each new subsystem file needs a matching description file here in DetaliiCod after the change.
