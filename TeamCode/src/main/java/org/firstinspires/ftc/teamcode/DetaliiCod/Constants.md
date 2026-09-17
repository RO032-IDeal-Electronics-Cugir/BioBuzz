## Constants.java

**Location:** `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/Constants.java`

**Purpose:**
Central configuration for the Pedro Pathing follower. Owns the `FollowerConstants`, `PathConstraints`, and the factory method that builds a `Follower` from the hardware map.

**What it contains:**
- `followerConstants` — a `FollowerConstants` instance used to tune Pedro Pathing behavior globally.
- `pathConstraints` — a `PathConstraints` instance (currently `new PathConstraints(0.99, 100, 1, 1)`).
- `createFollower(HardwareMap hardwareMap)` — static factory that returns a configured `Follower` built from `followerConstants`, `pathConstraints`, and the given hardware map.

**How it is used:**
Other OpModes call `Constants.createFollower(hardwareMap)` to get a follower instance. The tuning OpMode (`Tuning.java`) uses this in `onSelect()` whenever it needs to reinitialize the follower.

**Notes for planners:**
- If PID/FF values or path constraints need to change, start here.
- The `pathConstraints` numbers (0.99, 100, 1, 1) should be understood before editing — they affect how aggressively paths are followed.
- This file is intentionally small and stable; most tuning happens in `Tuning.java` or in the individual OpModes.
