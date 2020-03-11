package frc.robot.components;

import java.lang.Math;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;


public class Shooter {

    private WPI_TalonSRX leftShooter, rightShooter;
    private Boolean isRunning = false;
    private double syncThreshold = 100;// Figure this out
    private double leftSpeed = 0.0; 
    private double rightSpeed = 0.0;
    private double currentRPMgoal = 0.0;
    private double kError = 2;
    //FPID 
    private double kF = 0; // Velocity Closed Loop: kF is multiplied by target velocity and added to
                           // output.
    private double kP = .25;
    private double kI = 0;
    private double kD = 0;
    private double wheelRadius = .0762;
    



    public Shooter(WPI_TalonSRX motorLeft, WPI_TalonSRX motorRight, double syncThreshold) {

        // Set up Mag Encoders
        leftShooter = motorLeft;
        rightShooter = motorRight;


        leftShooter.configFactoryDefault();
        rightShooter.configFactoryDefault();

        leftShooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30); 
        rightShooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);

        leftShooter.setSensorPhase(true);
        rightShooter.setSensorPhase(true);

        leftShooter.configNominalOutputForward(0, 30);
        leftShooter.configNominalOutputReverse(0, 30);
        leftShooter.configPeakOutputForward(1, 30);
        leftShooter.configPeakOutputReverse(-1, 30);

        rightShooter.configNominalOutputForward(0, 30);
        rightShooter.configNominalOutputReverse(0, 30);
        rightShooter.configPeakOutputForward(1, 30);
        rightShooter.configPeakOutputReverse(-1, 30);

        leftShooter.config_kF(0, kF, 30);
        leftShooter.config_kP(0, kP, 30);
        leftShooter.config_kI(0, kI, 30);
        leftShooter.config_kD(0, kD, 30);

        rightShooter.config_kF(0, kF, 30);
        rightShooter.config_kP(0, kP, 30);
        rightShooter.config_kI(0, kI, 30);
        rightShooter.config_kD(0, kD, 30);
        // invert
        leftShooter.setInverted(false);
        rightShooter.setInverted(true);

        this.syncThreshold = syncThreshold;
    }

    public double convertVelocity(double velocity){
        // Convert Velocity to rpm
        return (velocity*100*4096*.001)/2*Math.PI*wheelRadius;
    }
    public double convertRPM(double RPM){
        return ((RPM * (2*Math.PI))*wheelRadius)/(100*4096*.001);
        // return (((double) getRawVelocity() / ticksPerRotation) * distancePerRotation)
        // / 0.1;
    }

    public void setVelocity(double velocity){
        leftShooter.set(ControlMode.Velocity, convertVelocity(velocity));
        System.out.println("Goal:"+convertVelocity(velocity)+" Current: "+ leftShooter
                .getSelectedSensorVelocity(0));
        leftSpeed = convertRPM(leftShooter.getSelectedSensorVelocity(0));
        rightShooter.set(ControlMode.Velocity, -convertVelocity(velocity));
        rightSpeed = convertRPM(rightShooter.getSelectedSensorVelocity(0));
        currentRPMgoal = convertVelocity(velocity);
    }
    public double[] getVelocity(){
        // return (((double) getRawVelocity() / ticksPerRotation) * distancePerRotation)
        // / 0.1;
        //2 * radius * Math.PI;

        double left = (leftShooter.getSelectedSensorVelocity(0)/4096)*(2*0.0762*Math.PI);
        double right = (rightShooter.getSelectedSensorVelocity(0)/4096)* (2*0.0762*Math.PI);
        double[] temp = {left, right};
        return temp;
    }
    public boolean isReady() {
        return motorInSync();
    }

    public boolean isRunning() {
        return isRunning;
    }

    private Boolean motorInSync() {
        double velocityDifference = Math.abs(leftShooter.getSelectedSensorVelocity(0) + rightShooter.getSelectedSensorVelocity(0));
        if (velocityDifference < syncThreshold && (leftSpeed > 0 && rightSpeed < 0)) {
            // add a statement checking to see if they met the goal
            return true;
        }
        return false;
    }

    public void off() {
        isRunning = false;
        rightShooter.set(ControlMode.PercentOutput, 0);
        leftShooter.set(ControlMode.PercentOutput, 0);
    }
    public void lowGoal(){
        rightShooter.set(ControlMode.PercentOutput, -.5);
        leftShooter.set(ControlMode.PercentOutput,-.5);
    }

}