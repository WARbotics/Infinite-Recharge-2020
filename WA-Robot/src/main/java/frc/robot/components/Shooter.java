package frc.robot.components;

import java.lang.Math;
import frc.robot.common.PID;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;


public class Shooter {

    private WPI_TalonSRX leftShooter, rightShooter;
    private Boolean isRunning = false;
    private double syncThreshold = 1;// Figure this out
    private PID shooterLeftPID = new PID(.2, 0.0, 0.0);
    private PID shooterRightPID = new PID(.2, 0.0, 0.0);
    private double leftSpeed = 0.0; 
    prviate double rightSpeed = 0.0;
    public Shooter(WPI_TalonSRX motorLeft, WPI_TalonSRX motorRight, double syncThreshold) {

        // Set up Mag Encoders
        leftShooter = motorLeft;
        rightShooter = motorRight;

        leftShooter.configFactoryDefault();
        rightShooter.configFactoryDefault();
        leftShooter.setSensorPhase(true);
        rightShooter.setSensorPhase(true);

        // invert
        leftShooter.setInverted(false);
        rightShooter.setInverted(true);

        this.syncThreshold = syncThreshold;
    }
    public boolean isReady() {
        return motorInSync();
    }

    public boolean isRunning() {
        return isRunning;
    }
    public double getShooterVelocity(int rpm){
        // (kMaxRPM / 600) * (kSensorUnitsPerRotation / kGearRatio)
        return (double)(rpm)*(4096/600);
    }
    public double getShooterRPM(double velocity, double wheelRadius){
        // Remember it is in meters
        double period =  (2.0*Math.PI*wheelRadius)/velocity; // 2PI(r)/V
        return (1/period)/1000; // (1/T/1000) convert seconds to millsecond thus unit/ms
    }
    public void setVelocity(double velocity) {

        shooterLeftPID.setPoint(velocity);
        shooterLeftPID.setActual(getShooterVelocity(leftShooter.getSelectedSensorVelocity()));
        leftSpeed = getShooterVelocity(leftShooter.getSelectedSensorVelocity());
        shooterRightPID.setPoint(velocity);
        shooterRightPID.setActual(getShooterVelocity(rightShooter.getSelectedSensorVelocity()));
        rightSpeed = getShooterVelocity(rightShooter.getSelectedSensorVelocity());
        System.out.println(shooterRightPID.getRate());
        rightShooter.set(ControlMode.Velocity, getShooterRPM(shooterRightPID.getRate(),0.0762));
        leftShooter.set(ControlMode.Velocity, getShooterRPM(shooterLeftPID.getRate(), 0.0762));
        // set velocity unit/100ms so we need to convert our velocity to their units/100ms
        isRunning = true;

    }

    private Boolean motorInSync() {
        double velocityDifference = Math.abs(shooterLeftPID.getError() - shooterRightPID.getError());
        if (velocityDifference < syncThreshold && (leftSpeed > 0 && rightSpeed > 0)) {
            return true;
        }
        return false;
    }

    public void off() {
        isRunning = false;
        rightShooter.set(ControlMode.PercentOutput, 0);
        leftShooter.set(ControlMode.PercentOutput, 0);
    }
    public void sendIt(){
        rightShooter.set(ControlMode.PercentOutput, 1);
        leftShooter.set(ControlMode.PercentOutput,1);
    }

}