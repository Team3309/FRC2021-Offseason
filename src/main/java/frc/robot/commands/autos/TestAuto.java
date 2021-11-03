package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ExitStartingConfiguration;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class TestAuto extends SequentialCommandGroup {
    public TestAuto (ArmSubsystem arm, DriveSubsystem drive) {
        addCommands(
            new ExitStartingConfiguration(arm),
            new FollowTrajectory(drive, "Unnamed.wpilib.json", true),
            new FollowTrajectory(drive, "Unnamed_0.wpilib.json", false)
        );
    }
}
