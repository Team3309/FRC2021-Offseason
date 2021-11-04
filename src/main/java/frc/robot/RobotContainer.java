// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AimAndShoot;
import frc.robot.commands.Climb;
import frc.robot.commands.DriveTeleop;
import frc.robot.commands.Intake;
import frc.robot.commands.Outtake;
import frc.robot.commands.ReverseSerializerRoller;
import frc.robot.commands.autos.TestAuto;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SerializerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.util.FiringSolution;
import friarLib2.hid.LambdaTrigger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

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

  private SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  private final TestAuto testTrajectory = new TestAuto(arm, drive);

  /** 
   * The container for the robot. Contains subsystems, OI devices, and commands. 
   */
  public RobotContainer() {
    autoChooser.setDefaultOption("Test auto", testTrajectory);
    SmartDashboard.putData(autoChooser);

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
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getBumper(Hand.kLeft))
    .whenActive(() -> climber.deploy(), climber);

    /** When the left trigger is pressed, activate the winch motor */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getTriggerAxis(Hand.kLeft) >= .1)
    .whileActiveContinuous(new Climb(climber));

    new LambdaTrigger(() -> OperatorInterface.OperatorController.getAButton())
    .whileActiveContinuous(new Intake(intake, serializer, arm));

    new LambdaTrigger(() -> OperatorInterface.OperatorController.getXButton())
    .whileActiveContinuous(new Outtake(intake, serializer, arm));

    new LambdaTrigger(() -> OperatorInterface.OperatorController.getYButton())
    .whileActiveContinuous(new ReverseSerializerRoller(serializer));

    new LambdaTrigger(() -> OperatorInterface.OperatorController.getPOV() == 180)
    .whileActiveContinuous(
        new AimAndShoot(() -> OperatorInterface.OperatorController.getXButton(), 
        drive, arm, serializer, shooter));

    new LambdaTrigger(() -> OperatorInterface.OperatorController.getPOV() == 0)
    .whileActiveContinuous(
        new AimAndShoot(new FiringSolution(0),
        () -> OperatorInterface.OperatorController.getXButton(), 
        drive, arm, serializer, shooter));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }

  public Command getTeleopInitCommand() {
    System.out.println("Teleop init");
    arm.setArmPositionToCurrentPosition();
    return new InstantCommand(arm::setArmPositionToCurrentPosition, arm);
  }
}
