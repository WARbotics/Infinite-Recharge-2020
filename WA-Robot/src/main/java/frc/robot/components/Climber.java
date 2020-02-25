package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
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
    private WPI_VictorSPX motor;
    private DoubleSolenoid hook;
    public WPI_VictorSPX winch;
    private boolean isClimberUp = false; 
    public Climber(WPI_VictorSPX motor, DoubleSolenoid hook, WPI_VictorSPX winch){
        this.motor = motor;
        this.hook = hook;
        this.winch = winch;
    }

    public void up(){
        motor.set(ControlMode.PercentOutput, 1);
    }
    public void down(){
        motor.set(ControlMode.PercentOutput, -1);
    }
    public void off(){
        motor.set(ControlMode.PercentOutput, 0);
    }
    public void sendHook(){
        hook.set(DoubleSolenoid.Value.kForward);
    }
    public void retrieveHook(){
        hook.set(DoubleSolenoid.Value.kReverse);
    }
    public void winchOn(){
        winch.set(ControlMode.PercentOutput, 1);
    }
    public void winchOff(){
        winch.set(ControlMode.PercentOutput, 0);
    }

}