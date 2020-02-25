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
    public Shooter(WPI_TalonSRX motorLeft, WPI_TalonSRX motorRight, double syncThreshold) {

        // Set up Mag Encoders
        leftShooter = motorLeft;
        rightShooter = motorRight;

        leftShooter.configFactoryDefault();
        rightShooter.configFactoryDefault();
         leftShooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        rightShooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
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

    public void setVelocity(double velocity) {
        shooterLeftPID.setPoint(velocity);
        shooterLeftPID.setActual(leftShooter.getSelectedSensorVelocity());
        shooterRightPID.setPoint(velocity);
        shooterRightPID.setActual(rightShooter.getSelectedSensorVelocity());
        rightShooter.set(ControlMode.Velocity, shooterRightPID.getRate());
        leftShooter.set(ControlMode.Velocity, shooterLeftPID.getRate());
        isRunning = true;

    }

    private Boolean motorInSync() {
        double velocityDifference = Math.abs(shooterLeftPID.getError() - shooterRightPID.getError());
        if (velocityDifference < syncThreshold) {
            return true;
        }
        return false;
    }

    public void off() {
        isRunning = false;
        rightShooter.set(ControlMode.PercentOutput, 0);
        leftShooter.set(ControlMode.PercentOutput, 0);
    }

}