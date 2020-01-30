/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.components.Drivetrain;
import frc.robot.components.OI;
import frc.robot.components.OI.DriveMode;
//import frc.robot.components.Shooter;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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

  
  
  //private Drivetrain drive;
  private OI input;
  //private Shooter shooter;
  WPI_TalonSRX leftShooter;
  WPI_TalonSRX rigntShooter;
  //DoubleSolenoid yeet;
  @Override
  public void robotInit() {
    /*
    WPI_TalonSRX leftLeader = new WPI_TalonSRX(0);
    WPI_VictorSPX leftFollower = new WPI_VictorSPX(1);
    WPI_TalonSRX rightLeader = new WPI_TalonSRX(1);
    WPI_TalonSRX rightFollower = new WPI_TalonSRX(2);
    */
    leftShooter = new WPI_TalonSRX(2);
    rigntShooter = new WPI_TalonSRX(0);
    //yeet = new DoubleSolenoid(0,1);
    /*
    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);
    drive = new Drivetrain(leftLeader, leftFollower, rightLeader, rightFollower);
    */
    //shooter = new Shooter(leftShooter, rigntShooter);


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

    // Set driver modes
    /*
    if (input.driver.getRawButton(1)) {
      // Set Speed Mode
      input.setDriveMode(DriveMode.SPEED);      
    } else if (input.driver.getRawButton(2)) {
      // Precision
      input.setDriveMode(DriveMode.PRECISION);
    } else if (input.driver.getRawButton(3)) {
      // Default
      input.setDriveMode(DriveMode.DEFAULT);
    } else if (input.driver.getRawButton(4)) {
      shooter.spinToRPM(300);
    }
    */
    if(input.driver.getRawButton(1)){
      leftShooter.set(.5);
    }else{
      leftShooter.set(0);
    }
    if(input.driver.getRawButton(2)){
      leftShooter.set(-.5);
    }else{
      leftShooter.set(0);
    }
    /*
    if(input.driver.getRawButton(1)){
      shooter.setVelocity(25);
      if(shooter.isReady()){
        System.out.println("FIRE!");
      }
    }else{
      shooter.off();
    }
    
     if (input.driver.getRawButton(4)) {
      rigntShooter.set(ControlMode.PercentOutput, .60);
     }else{
      rigntShooter.set(ControlMode.PercentOutput, 0);
     }
     if(input.driver.getRawButton(1)){
      yeet.set(DoubleSolenoid.Value.kForward);
     }
     if(input.driver.getRawButton(2)){
      yeet.set(DoubleSolenoid.Value.kReverse);
     }
     */


  }

  @Override
  public void testInit() {

  }

  @Override
  public void testPeriodic() {
    
  }

}
