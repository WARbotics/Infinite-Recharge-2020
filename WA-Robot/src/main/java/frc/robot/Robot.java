/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.components.Drivetrain;
import frc.robot.components.OI;
import frc.robot.components.OI.DriveMode;
import frc.robot.components.Intake;
import frc.robot.components.Conveyor;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
  */

  
  private Intake intake;
  private Drivetrain drive;
  private OI input;

  private Conveyor conveyor;


  public void robotInit() {
    WPI_TalonSRX intakeMotor = new WPI_TalonSRX(5);

    NetworkTable table;
    NetworkTableEntry targetX;
    NetworkTableEntry targetY;
  
    WPI_TalonSRX intakeMotor = new WPI_TalonSRX(5);
    WPI_TalonSRX leftLeader = new WPI_TalonSRX(0);
    WPI_VictorSPX leftFollower = new WPI_VictorSPX(1);
    WPI_TalonSRX rightLeader = new WPI_TalonSRX(1);
    WPI_TalonSRX rightFollower = new WPI_TalonSRX(2);
    WPI_TalonSRX frontConveyor = new WPI_TalonSRX(4);
    WPI_TalonSRX backConveyor = new WPI_TalonSRX(3);

    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);
    drive = new Drivetrain(leftLeader, leftFollower, rightLeader, rightFollower);
    

    Joystick drive = new Joystick(0);
    Joystick operator = new Joystick(1);
    input = new OI(drive, operator);
    intake = new Intake(intakeMotor);
    conveyor = new Conveyor(frontConveyor, backConveyor);
    
  }

  @Override
  public void autonomousInit() {
    
  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {

  }

  @Override
  public void teleopPeriodic() {

    double driveY = -input.driver.getRawAxis(1);
    double zRotation = input.driver.getRawAxis(2);
    double rightDriveY = input.driver.getRawAxis(3);
    

    if (input.getDriveMode() == DriveMode.SPEED) {
      // Speed
    } else if (input.getDriveMode() == DriveMode.PRECISION) {
      // Double check that they are the right controls
      // Precision
      drive.drive.tankDrive(driveY * .70, -rightDriveY * .70);
      // make turning senetive but forward about .50
    } else {
      // Default
      if (input.driver.getRawButton(5)) {
          drive.curveDrive(-driveY, zRotation, true);
      }else {
          drive.curveDrive(-driveY, zRotation, false);
        }
    }

    // Set driver modes
    if (input.driver.getRawButton(1)) {
      // Set Speed Mode
      input.setDriveMode(DriveMode.SPEED);
    } else if (input.driver.getRawButton(2)) {
      // Precision
      input.setDriveMode(DriveMode.PRECISION);
    } else if (input.driver.getRawButton(3)) {
      // Default
      input.setDriveMode(DriveMode.DEFAULT);
    }
    
    
    
    
    
    
      if(input.driver.getRawButton(1)){
        intake.on();
        conveyor.on();
    } else{
      
        intake.off();
        conveyor.off();
      

    }

    if(input.driver.getRawButton(2)){
      conveyor.backwards();
    }else{
      conveyor.off();
    }

  
  
  }


  @Override
  public void testInit() {

  }

  @Override
  public void testPeriodic() {
    
  }

}
