package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climber {
    /*
    This object uses a Talon Tach as a limit switch so that the climber does not go past the top position. 

    Contributed By: Victor Henriksson
    NOTE: This not take into account multipal Talon Tach.
    */
    private WPI_TalonSRX motor;
    private DoubleSolenoid hook;
    private boolean isClimberUp = false; 
    public Climber(WPI_TalonSRX motor, DoubleSolenoid hook){
        this.motor = motor;
        this.hook = hook;
        this.motor.configFactoryDefault();
        this.motor.configForwardLimitSwitchSource(LimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen);
    }

    public void up(){
        motor.set(ControlMode.MotionMagic, .25);
    }
    public void down(){
        motor.set(ControlMode.MotionMagic, .25);
    }
    public void sendHook(){
        hook.set(DoubleSolenoid.Value.kForward);
    }
    public void retrieveHook(){
        hook.set(DoubleSolenoid.Value.kReverse);
    }

}