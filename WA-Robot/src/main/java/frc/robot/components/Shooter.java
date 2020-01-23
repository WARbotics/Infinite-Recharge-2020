package frc.robot.components;
import java.lang.Math;
import frc.robot.common.PID;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Shooter {
    /*
    *This shooter gets two WPI_TalonSRX passed inside the constructor, then initiates two mag encoders.
    *This object has methods to get the rpm and the velocity of the mag encoders, and returns error if two mag encoders have different velocity or rpm.
    *With PID Method(Needs to be changed to PID methods), the shooter gets the speed that is required to shot the ball out
    *There is also a getter and setter method to get the state of the encoders.
    */
    private WPI_TalonSRX magEncoderLeft, magEncoderRight;
    private PID PID = new PID(0.30, 0.00, 0.01);
    private int motionErrorLeft = 0;
    private int motionErrorRight = 0;
    private String encoderError = "None";
    private double deadBand = 0.0;
    private Boolean encoderOn = false; 
    //Assume the radius of the shooter wheel is 3.1415926
    private static final double RADIUS = 3.1415926;

    public Shooter(WPI_TalonSRX motorLeft, WPI_TalonSRX motorRight) {
       
        //Set up Mag Encoders
        magEncoderLeft = motorLeft;
        magEncoderRight = motorRight;
        magEncoderLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        magEncoderRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        magEncoderLeft.setInverted(false);
        magEncoderRight.setInverted(true);
        //Sensor Phase true on real robot
        magEncoderLeft.setSensorPhase(true);
        magEncoderRight.setSensorPhase(true);
        magEncoderLeft.setNeutralMode(NeutralMode.Brake);
        magEncoderRight.setNeutralMode(NeutralMode.Brake);
    }

    private double convertToVelocity(double rpm) {
        return (rpm * Math.PI * 2 * RADIUS);
    }

    private double convertToRpm(double velocity) {
        return (velocity / (Math.PI * 2 * RADIUS));
    }
//get the RPM value with the mag encoder sensors on two Talon SRX Shooters
//secure the rpm values of two motors are same
    public double getVelocity() {
        while (magEncoderLeft.getSelectedSensorVelocity(0) != magEncoderRight.getSelectedSensorVelocity(0)) {
            this.encoderError = "Two Velocity Not Same";
        }
        this.encoderError = "None";
        return magEncoderLeft.getSelectedSensorVelocity(0);
    }

    public double getPosition() {
        return magEncoderLeft.getSelectedSensorPosition(0);
    }

    public void runMotor(int val) {
        this.runSpeed(val);
        this.encoderOn = true;
    }

    public void setDeadBand(double deadBand) {
        this.deadBand = deadBand;
    }

    private void runSpeed(int speed) {
        if (Math.abs(speed) <= this.deadBand) {
            speed = 0;
        }
        PID.setPoint(speed);
        PID.setActual(this.getVelocity());
        magEncoderLeft.set(ControlMode.PercentOutput, PID.getRate());
        magEncoderRight.set(ControlMode.PercentOutput, PID.getRate());
    }
    
    /*public void BinaryRunMotor(double speed) {
        double leftLimit = -1.0;
        double rightLimit = 1.0;
        double middle = (leftLimit + rightLimit) / 2;
        magEncoderLeft.set(ControlMode.PercentOutput, middle);
        magEncoderRight.set(ControlMode.PercentOutput, middle);
        while (true) {
            if (leftLimit > rightLimit) {
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
            magEncoderLeft.set(ControlMode.PercentOutput, middle);
            magEncoderRight.set(ControlMode.PercentOutput, middle);
        }
    }*/

    public Boolean encoderOn() {
        return this.encoderOn;
    }

    public String getErrorType() {
        return this.encoderError;
    }
}