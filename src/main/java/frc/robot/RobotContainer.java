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
import frc.robot.commands.BrakeFlywheels;
import frc.robot.commands.Climb;
import frc.robot.commands.DriveTeleop;
import frc.robot.commands.Intake;
import frc.robot.commands.LineUpWithTarget;
import frc.robot.commands.MoveArmUpForDefense;
import frc.robot.commands.Outtake;
import frc.robot.commands.ReverseSerializerRoller;
import frc.robot.commands.autos.ScorePreloads;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SerializerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
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
    private final ScorePreloads scorePreloadsTowardsDriverStation = new ScorePreloads(-.25, arm, drive, serializer, shooter);
    private final ScorePreloads scorePreloadsAwayFromDriverStation = new ScorePreloads(.25, arm, drive, serializer, shooter);

    /** 
    * The container for the robot. Contains subsystems, OI devices, and commands. 
    */
    public RobotContainer() {
        autoChooser.setDefaultOption("Move towards driver station", scorePreloadsTowardsDriverStation);
        autoChooser.addOption("Move away from driver station", scorePreloadsAwayFromDriverStation);
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

    /** While the left trigger is pressed, activate the winch motor */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getTriggerAxis(Hand.kLeft) >= .1)
    .whileActiveContinuous(new Climb(climber));

    /** While the A button is pressed, intake powercells */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getAButton())
    .whileActiveContinuous(new Intake(intake, serializer, arm));

    /** While the X button is pressed, reverse the intake and serializer */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getXButton())
    .whileActiveContinuous(new Outtake(intake, serializer, arm));

    /** While the Y button is pressed, reverse the serializer motor */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getYButton())
    .whileActiveContinuous(new ReverseSerializerRoller(serializer));

    /** While d-pad down is pressed, automatically aim and shoot */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getPOV() == 180)
    .whileActiveContinuous(
        new AimAndShoot(() -> OperatorInterface.OperatorController.getBButton(), 
        arm, serializer, shooter));

    /** While d-pad up is pressed, shoot from against driverstation wall */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getPOV() == 0)
    .whileActiveContinuous(
        new AimAndShoot(
            Constants.Shooter.LOB_INTO_GOAL,
            () -> OperatorInterface.OperatorController.getBButton(), 
            arm, serializer, shooter));

    /** While d-pad right is pressed, shoot from initiation line */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getPOV() == 90)
    .whileActiveContinuous(
        new AimAndShoot(
            Constants.Shooter.SHOOT_FROM_STARTING_LINE,
            () -> OperatorInterface.OperatorController.getBButton(), 
            arm, serializer, shooter));

    /** While d-pad left is pressed, shoot from the trench */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getPOV() == 270)
    .whileActiveContinuous(
        new AimAndShoot(
            Constants.Shooter.SHOOT_FROM_TRENCH,
            () -> OperatorInterface.OperatorController.getBButton(), 
            arm, serializer, shooter));

    /** While driver joystick trigger(s) are pressed, line up the robot with the target */
    new LambdaTrigger(() -> OperatorInterface.DriverLeft.getTrigger() || OperatorInterface.DriverRight.getTrigger())
    .whileActiveContinuous(new LineUpWithTarget(drive));

    /** 
     * When right bumper is pressed, zero the arm encoder.
     * 
     * This is in case the arm gearbox slips, which is mostly resolved, but
     * still a good safety net
     */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getBumper(Hand.kRight))
    .whileActiveContinuous(() -> arm.zeroArmPosition(), arm);

    /** While the right trigger is pressed, slow down the flywheels quickly */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getTriggerAxis(Hand.kRight) >= .1)
    .whileActiveContinuous(new BrakeFlywheels(shooter));

    /** 
     * While the start button on the xbox controller is pressed, move 
     * the arm all the way up to block opponents' shots.
     * 
     * Used sucessfully in a Beach Blitz 2021 playoff match
     * TODO: Find out what match that is
     */
    new LambdaTrigger(() -> OperatorInterface.OperatorController.getStartButton())
    .whileActiveContinuous(new MoveArmUpForDefense(arm));
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
      arm.setArmPositionToCurrentPosition();
      return new InstantCommand(arm::setArmPositionToCurrentPosition, arm);
  }
}
