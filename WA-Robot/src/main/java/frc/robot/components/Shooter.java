package frc.robot.components;

import java.lang.Math.*;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.DigitalInput;

public class Shooter {

    private WPI_TalonSRX shooterLeft, shooterRight;
    private TalonSRX magEncoderLeft, magEncoderRight;
    //Assume the radius is 3.5;
    private static final double RADIUS = 3.5;
    //Assume the maximum velocity is 9.8;
    private static final double maximumVelocity = 9.8;

    public Shooter(WPI_TalonSRX shooterLeft, WPI_TalonSRX shooterRight) {
        this.shooterLeft = shooterLeft;
        this.shooterRight = shooterRight;
       
        magEncoderLeft = new TalonSRX(ElectricalConstants.this.shooterLeft);
        magEncoderRight = new TalonSRX(ElectricalConstants.this.shooterRight);
        magEncoderLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        magEncoderRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        magEncoderLeft.setInverted(false);
        magEncoderRight.setInverted(true);
        magEncoderLeft.setSensorPhase(true);
        magEncoderRight.setSensorPhase(true);
        magEncoderLeft.setNeutralMode(NeutralMode.Brake);
        magEncoderRight.setNeutralMode(NeutralMode.Brake);
    }

    private double convertToVelocity(doubel rpm) {
        return (rpm * Math.PI * 2 * RADIUS);
    }

    private double convertToRpm(double velocity) {
        return (velocity / (Math.PI * 2 * RADIUS));
    }

    public void getRpm() {

    }

    public Boolean reachesRequiredVelocity(double velocity) {
        return (this.converToVelocity() == velocity);
    }

    public void setSpeed(double speed) {
        this.shooterLeft.setSpeed(speed);
        this.shooterRight.setSpeed(-speed);
    }
}