/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.components.Drivetrain;
import frc.robot.components.OI;
import frc.robot.components.OI.DriveMode;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.Intake;
import frc.robot.components.Conveyor;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.common.PlayGenerator;
import frc.robot.common.AutoCommands.AutoMove;
import frc.robot.common.AutoCommands.AutoTurn;

import java.lang.Math;
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
  private static final double cpr = 360; // am-3132
  private static final double wheelDiameter = 6; // 6 inch wheel
  private static final String kDefaultAuto = "Default";
  private static final String kFowardAuto = "Foward Auto";
  private static final String kLeftAuto = "Left Auto";
  private static final String kRightAuto = "Right Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private PlayGenerator fowardAuto = new PlayGenerator("fowardAuto");

  @Override
  public void robotInit() {
  
    WPI_TalonSRX intakeMotor = new WPI_TalonSRX(5);

    //Drivetrain
    WPI_TalonSRX leftLeader = new WPI_TalonSRX(0);
    WPI_VictorSPX leftFollower = new WPI_VictorSPX(1);
    WPI_TalonSRX rightLeader = new WPI_TalonSRX(1);
    WPI_TalonSRX rightFollower = new WPI_TalonSRX(2);
    Encoder leftEncoder = new Encoder(0,1);
    Encoder rightEncoder = new Encoder(2,3);
    leftEncoder.setDistancePerPulse(Math.PI * wheelDiameter / cpr);
    rightEncoder.setDistancePerPulse(Math.PI * wheelDiameter / cpr);
    //WPI_TalonSRX frontConveyor = new WPI_TalonSRX(4);
    //WPI_TalonSRX backConveyor = new WPI_TalonSRX(3);
    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);
    drive = new Drivetrain(leftLeader, leftFollower, rightLeader, rightFollower, leftEncoder, rightEncoder);

    Joystick drive = new Joystick(0);
    Joystick operator = new Joystick(1);
    input = new OI(drive, operator);
    intake = new Intake(intakeMotor);
    //conveyor = new Conveyor(frontConveyor, backConveyor);
    // Auto
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("Foward Auto", kFowardAuto);
    m_chooser.addOption("Left Auto", kLeftAuto);
    m_chooser.addOption("Right Auto", kRightAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
    switch(m_autoSelected){
      case kDefaultAuto:
        
        break;
      case kFowardAuto:
        fowardAuto.addPlay((new AutoMove(drive, 1.5, 1.0)));
  
        break;
      case kLeftAuto:
        break;
      case kRightAuto:
        break;
      
  }

  @Override
  public void teleopInit() {

  }

  @Override
  public boolean isTurning() {
      return Math.abs(ahrsDevice.getRate()) >= 7.5;
  }

  @Override
  public void teleopPeriodic() {

    double driveY = -input.driver.getRawAxis(1);
    double zRotation = input.driver.getRawAxis(2);
    double rightDriveY = input.driver.getRawAxis(3);
    SmartDashboard.putString("Drivemode", input.getDriveMode().name());
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
        //conveyor.on();
    } else{
      
        intake.off();
        //conveyor.off();

    }
    /*
    if(input.driver.getRawButton(2)){
      conveyor.backwards();
    }else{
      conveyor.off();
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
