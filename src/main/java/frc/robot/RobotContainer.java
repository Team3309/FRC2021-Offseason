// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.commands.Climb;
import frc.robot.commands.DriveTeleop;
import frc.robot.commands.Intake;
import frc.robot.commands.autos.FollowTrajectory;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SerializerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import friarLib2.hid.LambdaTrigger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ArmSubsystem arm = new ArmSubsystem();
  private final ClimberSubsystem climber = new ClimberSubsystem();
  private final DriveSubsystem drive = new DriveSubsystem();
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final SerializerSubsystem serializer = new SerializerSubsystem();
  private final ShooterSubsystem shooter = new ShooterSubsystem();

  /** 
   * The container for the robot. Contains subsystems, OI devices, and commands. 
   */
  public RobotContainer() {
    configureDefaultCommands();
    configureButtonBindings();
  }

  private void configureDefaultCommands () {
    drive.setDefaultCommand(new DriveTeleop(drive));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    /** Deploy the climber when the left bumper is pressed */
    new JoystickButton(OperatorInterface.OperatorController, XboxController.Button.kBumperLeft.value)
    .whenPressed(() -> climber.deploy(), climber);

    /** When the left trigger is pressed, activate the winch motor */
    new LambdaTrigger(() -> {return OperatorInterface.OperatorController.getTriggerAxis(Hand.kLeft) >= .1;})
    .whileActiveContinuous(new Climb(climber));

    new LambdaTrigger(() -> OperatorInterface.OperatorController.getAButton())
    .whileActiveContinuous(new Intake(intake, serializer, arm));

    //new LambdaTrigger(() -> OperatorInterface.DriverLeft.getTrigger() || OperatorInterface.DriverRight.getTrigger())
    //.whenActive(new FollowTrajectory(drive, "Unnamed_0.wpilib.json"));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;//m_autoCommand;
  }
}
