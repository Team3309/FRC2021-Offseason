package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.AimAndShoot;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.SerializerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import friarLib2.commands.RunForTime;

public class ScorePreloads extends SequentialCommandGroup {
    public ScorePreloads (ArmSubsystem arm, DriveSubsystem drive, SerializerSubsystem serializer, ShooterSubsystem shooter) {
        addCommands(
            new RunForTime(
                new AimAndShoot(
                    Constants.Shooter.SHOOT_FROM_STARTING_LINE, 
                    () -> true, 
                    arm, serializer, shooter), 
                10),
            new DriveForward(.25, 2, drive)
        );
    }
}