package frc.robot.components;
import java.lang.Math;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Shooter {
    
    private WPI_TalonSRX leftShooter, rightShooter;
    //Assume the radius is 3.5;
    private static final double RADIUS = 3.5;
    private Boolean isRunning = false;
    private int timeOutMs = 30;
    private int loopIDX = 0;
    private double f = 2;
    private double p = 0.0;
    private double i = 0.0;
    private double d = 0.0;
    private double setVelocity = leftShooter.getClosedLoopTarget();
    private double syncThreshold = .25;//Figure this out
    private boolean readyToShoot = false; 
    public Shooter(WPI_TalonSRX motorLeft, WPI_TalonSRX motorRight) {
       
        //Set up Mag Encoders
        leftShooter = motorLeft;
        rightShooter = motorRight;
        
        


        leftShooter.configFactoryDefault();
        rightShooter.configFactoryDefault();
        //leftShooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, loopIDX, timeOutMs);
        //rightShooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, loopIDX, timeOutMs);
        //invert
        leftShooter.setInverted(false);
        rightShooter.setInverted(true);
        //motor Phase
        leftShooter.setSensorPhase(false);
        rightShooter.setSensorPhase(false);



        leftShooter.configNominalOutputForward(0, timeOutMs);

        leftShooter.configNominalOutputReverse(0, timeOutMs);

        leftShooter.configPeakOutputForward(1, timeOutMs);

        leftShooter.configPeakOutputReverse(-1, timeOutMs);


        rightShooter.configNominalOutputForward(0, timeOutMs);

        rightShooter.configNominalOutputReverse(0, timeOutMs);

        rightShooter.configPeakOutputForward(1, timeOutMs);

        rightShooter.configPeakOutputReverse(-1, timeOutMs);

        // config P,I,D,F values

        leftShooter.config_kF(loopIDX, f, timeOutMs);

        leftShooter.config_kP(loopIDX, p, timeOutMs);

        leftShooter.config_kI(loopIDX, i, timeOutMs);

        leftShooter.config_kD(loopIDX, d, timeOutMs);



    }

    private double convertToVelocity(double rpm) {
        return (rpm * Math.PI * 2 * RADIUS);
    }

    private double convertToRpm(double velocity) {
        return (velocity / (Math.PI * 2 * RADIUS));
    }
    public boolean isReady(){
        if(motorInSync()){
            return readyToShoot;
        }
        return readyToShoot;
    }
    public boolean isRunning(){
        return isRunning;
    }
    public void setVelocity(double velocity){
        setVelocity = velocity; 
        rightShooter.set(ControlMode.Velocity, velocity);
        leftShooter.set(ControlMode.Velocity, velocity);
        isRunning = true; 
        
    }
    private Boolean motorInSync(){
        double velocityDifference = Math.abs((leftShooter.getClosedLoopTarget()-leftShooter.getErrorDerivative()) - (rightShooter.getClosedLoopTarget()-rightShooter.getErrorDerivative()));
        if(velocityDifference < syncThreshold){
            readyToShoot = true; 
            return true;
        }
        readyToShoot = false;
        return false;
    }
    public void off(){
        isRunning = false; 
        setVelocity = 0.0;
        rightShooter.set(ControlMode.PercentOutput, 0);
        leftShooter.set(ControlMode.PercentOutput, 0);
    }
/*
//get the RPM value with the mag encoder sensors on two Talon SRX Shooters
//secure the rpm values of two motors are same
    public double getVelocity() {
        while (leftShooter.getSelectedSensorVelocity(0) != rightShooter.getSelectedSensorVelocity(0)) {
            encoderError = "Two Velocity Not Same";
        }
        encoderError = "None";
        return leftShooter.getSelectedSensorVelocity(0);
    }

    public double getRpm() {
        while (leftShooter.getSelectedSensorPosition(0) != rightShooter.getSelectedSensorPosition(0)) {
            encoderError = "Two Velocity Not Same";
        }
        encoderError = "None";
        return leftShooter.getSelectedSensorPosition(0);
    }

    public void runMotor(double val) {
        if (runSpeed(val)) {
            encoderOn = true;
            return;
        } else {
            encoderError = "Unable to Set the Speed";
        }
    }

    public void resetEncoders() {
        leftShooter.setSelectedSensorPosition(0, 0, 0);
        rightShooter.setSelectedSensorPosition(0, 0, 0);
    }

    public Boolean runSpeed(double speed) {
       
    }

    public double getEncoder() {
        return leftShooter.getSelectedSensorPosition(0) / SHOOTER_CONSTANT;
    }

    public Boolean encoderOn() {
        return encoderOn;
    }

    public String getErrorType() {
        return encoderError;
    }

    public void setMotionMagicSetpoint(double setpoint, int cruiseVelocity, double secsToMaxSpeed) {
        leftShooter.configMotionCruiseVelocity(cruiseVelocity, 0);
        rightShooter.configMotionCruiseVelocity(cruiseVelocity, 0);
        //Assume setpoint limit is 70;
        if (setpoint > 70){
          secsToMaxSpeed = 0.6; 
        }
  
        if ((this.getEncoder() > 50) && setpoint > this.getEncoder()){
          //3 second acceleration at top
          leftShooter.configMotionAcceleration((int) ((MAX_VAL)/secsToMaxSpeed), 0);
          rightShooter.configMotionAcceleration((int) ((MAX_VAL)/secsToMaxSpeed), 0); 
        } else {
          //regular acceleration
          leftShooter.configMotionAcceleration((int)(MAX_VAL / secsToMaxSpeed), 0);
          rightShooter.configMotionAcceleration((int)(MAX_VAL / secsToMaxSpeed), 0);
        }
  
        this.runMotionMagic(setpoint * SHOOTER_CONSTANT);
    }

    public void runMotionMagic(double val){
        leftShooter.set(ControlMode.MotionMagic, val);
        rightShooter.set(ControlMode.MotionMagic, val);
        motionErrorLeft = leftShooter.getClosedLoopError(0);
        motionErrorRight = rightShooter.getClosedLoopError(0);
    }
    */
}