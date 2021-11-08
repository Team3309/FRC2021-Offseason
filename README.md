Code for our 2021 offseason robot!

# Button bindings
The driver uses two large joysticks and the operator uses an xbox controller.

**Left joystick (port 0)**:
 - Up/down: drivetrain throttle
 - Trigger: line up with target (using vision system)
 
**Right joystick (port 1)**:
 - Left/right: drivetrain rotation
 - Trigger: line up with target (using vision system)

**Xbox controller (port 2)**:
 - Left bumper: deploy climber
 - Left trigger (only after climber has been deployed): activate climber winch
 - Right bumper: zero arm encoder
 - Right trigger: brake flywheels
 - Up d-pad: TBD
 - Down d-pad: Automatically aim
 - Left d-pad: TBD
 - Right d-pad: TBD
 - A: intake
 - B (when flywheels are running): shoot powercells
 - X: outtake
 - Y: reverse serializer roller
 
# Startup
When powering on the robot, ensure that the arm is level with the floor and the robot is facing in the same direction it will be when autonomous mode starts.

*IMPORTANT: if the gyroscope is not zeroed correctly, the robot will move out-of-control and possibly destroy itself in autonomous!*
