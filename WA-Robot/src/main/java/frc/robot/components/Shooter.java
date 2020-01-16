package frc.robot.components;

import java.lang.Math.*;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.DigitalInput;

public class Shooter {

    private WPI_TalonSRX shooterLeft, shooterRight;
    private TalonSRX magEncoderLeft, magEncoderRight;
    private Double motionErrorLeft = 0.0;
    private Double motionErrorRight = 0.0;
    private String encoderError = "None";
    //Assume the radius is 3.5;
    private static final double RADIUS = 3.5;
    //Assume the maximum velocity is 9.8;
    private static final double maximumVelocity = 9.8;
    private static final int MAX_ACC = 0x3f3f3f3f;
    private static final int MAX_VAL = 0x3f3f3f3f;
    private static final double SHOOTER_CONSTANT = 23.57;

    public Shooter(WPI_TalonSRX shooterLeft, WPI_TalonSRX shooterRight) {
        this.shooterLeft = shooterLeft;
        this.shooterRight = shooterRight;
       
        //Set up Mag Encoders
        magEncoderLeft = new TalonSRX(this.shooterLeft);
        magEncoderRight = new TalonSRX(this.shooterRight);
        magEncoderLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        magEncoderRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        magEncoderLeft.setInverted(false);
        magEncoderRight.setInverted(true);
        //Sensor Phase true on real robot
        magEncoderLeft.setSensorPhase(true);
        magEncoderRight.setSensorPhase(true);
        magEncoderLeft.setNeutralMode(NeutralMode.Brake);
        magEncoderRight.setNeutralMode(NeutralMode.Brake);
        //For Motion Magic Velocity and Acceleration
        //Assume the maximum acceleration and cruise velocity to be 0x3f3f3f3f
        magEncoderLeft.configMotionAcceleration(MAX_ACC, 0);
        magEncoderRight.configMotionAcceleration(MAX_ACC, 0);
        magEncoderLeft.configMotionCruiseVelocity(MAX_VAL, 0);
        magEncoderRight.configMotionCruiseVelocity(MAX_VAL, 0);
        //Set Up Soft Limits (Does not need soft limit temporarily)
        magEncoderLeft.configForwardSoftLimitEnable(false, 0);
        magEncoderRight.configForwardSoftLimitEnable(false, 0);
        magEncoderLeft.configReverseSoftLimitEnable(false, 0);
        magEncoderRight.configReverseSoftLimitEnable(false, 0);
    }

    private double convertToVelocity(doubel rpm) {
        return (rpm * Math.PI * 2 * RADIUS);
    }

    private double convertToRpm(double velocity) {
        return (velocity / (Math.PI * 2 * RADIUS));
    }
//get the RPM value with the mag encoder sensors on two Talon SRX Shooters
//secure the rpm values of two motors are same
    public double getVelocity() {
        while (magEncoderLeft.getSelectedSensorVelocity(0) != magEncoderRight.getSelectedSensorVelocity(0)) {
            encoderError = "Two Velocity Not Same";
        }
        encoderError = "None";
        return magEncoderLeft.getSelectedSensorVelocity(0);
    }

    public double getRpm() {
        while (magEncoderLeft.getSelectedSensorPosition(0) != magEncoderRight.getSelectedSensorPosition(0)) {
            encoderError = "Two Velocity Not Same";
        }
        encoderError = "None";
        return magEncoderLeft.getSelectedSensorPosition(0);
    }

    public void runMotor(double val) {
        this.setSpeed(val);
    }

    public void resetEncoders() {
        magEncoderLeft.setSelectedSensorPosition(0, 0, 0);
        magEncoderRight.setSelectedSensorPosition(0, 0, 0);
    }

    public Boolean runSpeed(double speed) {
        double leftLimit = -1.0;
        double rightLimit = 1.0;
        double middle = (leftLimit + rightLimit) / 2;
        magEncoderLeft.set(middle);
        magEncoderRight.set(middle);
        while (true) {
            if (left > right) {
                return false;
            }
            double diff = speed - this.getVelocity();
            //if expected speed is much bigger than real speed;
            if (diff > 0.01) {
                leftLimit = middle - 0.01;
                middle = (leftLimit + rightLimit) / 2;
            } 
            //if expected speed is much less than real speed;
            else if (diff < -0.01) {
                rightLimit = middle + 0.01;
                middle = (leftLimit + rightLimit) / 2;
            }
            else if (Math.abs(diff) < 0.01) {
                return true;
            }
            magEncoderLeft.set(middle);
            magEncoderRight.set(middle);
        } 
    }

    public void setMotionMagicSetpoint(double setpoint, int cruiseVelocity, double secsToMaxSpeed) {
        magEncoderLeft.configMotionCruiseVelocity(cruiseVelocity, 0);
        magEncoderRight.configMotionCruiseVelocity(cruiseVelocity, 0);
        //Assume setpoint limit is 70;
        if (setpoint > 70){
          secsToMaxSpeed = 0.6; 
        }
  
        if ((this.getEncoder() > 50) && setpoint > this.getEncoder()){
          //3 second acceleration at top
          magEncoderLeft.configMotionAcceleration((int) ((MAX_VAL)/secsToMaxSpeed), 0);
          magEncoderRight.configMotionAcceleration((int) ((MAX_VAL)/secsToMaxSpeed), 0); 
        } else {
          //regular acceleration
          magEncoderLeft.configMotionAcceleration((int)(MAX_VAL / secsToMaxSpeed), 0);
          magEncoderRight.configMotionAcceleration((int)(MAX_VAL / secsToMaxSpeed), 0);
        }
  
        runElevatorMotionMagic(setpoint * SHOOTER_CONSTANT);
    }

    public void runMotionMagic(double val){
        magEncoderLeft.set(ControlMode.MotionMagic, val);
        magEncoderRight.set(ControlMode.MotionMagic, val);
        motionErrorLeft = magEncoderLeft.getClosedLoopError(0);
        motionErrorRight = magEncoderRight.getClosedLoopError(0);
    }

}