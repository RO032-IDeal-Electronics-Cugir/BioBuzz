package org.firstinspires.ftc.teamcode.pedro.Commands;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;
import org.firstinspires.ftc.teamcode.pedro.Subsystems.shootingSubsystem;

public class shootCommand extends SequentialCommandGroup {
    private static final double SHOOTER_MOTOR_CPR = shootingSubsystem.SHOOTER_MOTOR_CPR;

    public shootCommand(shootingSubsystem shooter, double targetRpm) {
        addCommands(
            // 1. Set target RPM and apply velocity PIDF via motor encoder velocity
            new InstantCommand(() -> {
                shooter.setShooterTargetRPM(targetRpm);
                double ticksPerSec = (targetRpm / 60.0) * SHOOTER_MOTOR_CPR;
                shooter.setVelocity(ticksPerSec);
            }),
            // 2. Wait until the shooter reaches the target RPM (within 50 RPM tolerance)
            new WaitUntilCommand(() -> Math.abs(shooter.getShooterCurrentRPM() - targetRpm) < 50.0),
            // 3. Maintain firing state for a short duration
            new WaitCommand(500),
            // 4. Stop shooter
            new InstantCommand(() -> shooter.setPower(0.0))
        );
        addRequirements(shooter);
    }
}
