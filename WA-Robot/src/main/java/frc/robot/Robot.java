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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

  
  
  private Drivetrain drive;
  private OI input;
  WPI_TalonSRX leftLeader;
  WPI_VictorSPX leftFollower;
  WPI_TalonSRX rightLeader;
  WPI_TalonSRX rightFollower;
  @Override
  public void robotInit() {
    
    leftLeader = new WPI_TalonSRX(0);
    leftFollower = new WPI_VictorSPX(1);
    rightLeader = new WPI_TalonSRX(1);
    rightFollower = new WPI_TalonSRX(2);

    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);
    drive = new Drivetrain(leftLeader, leftFollower, rightLeader, rightFollower);
    

    Joystick drive = new Joystick(0);
    Joystick operator = new Joystick(1);
    input = new OI(drive, operator);
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

    SmartDashboard.putString("Drivemode", input.getDriveMode().name());
    /*
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
    */
    if(input.driver.getRawButton(1)){
      leftLeader.set(1);
    }else if(input.driver.getRawButton(2)){
      leftFollower.set(1);
    }else if(input.driver.getRawButton(3)){
      rightLeader.set(1);
    }else if(input.driver.getRawButton(4)){
      rightFollower.set(-1);
    }else{
      leftLeader.set(0);
      leftFollower.set(0);
      rightLeader.set(0);
      rightFollower.set(0);
    }
    if(input.driver.getRawButton(8)){
      leftFollower.set(1);
      rightLeader.set(1);
      rightFollower.set(-1);
    }else{
      rightFollower.set(0);
      rightLeader.set(0);
      leftFollower.set(0);
    }



  }

  @Override
  public void testInit() {

  }

  @Override
  public void testPeriodic() {
    
  }

}
