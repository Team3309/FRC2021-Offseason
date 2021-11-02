package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;

public class TestAuto extends SequentialCommandGroup {
    public TestAuto (DriveSubsystem drive) {
        addCommands(
            new FollowTrajectory(drive, "Unnamed.wpilib.json", true),
            new FollowTrajectory(drive, "Unnamed_0.wpilib.json", false)
        );
    }
}
