package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climber {

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
        
    }
    public void down(){

    }
    public void sendHook(){
        hook.set(DoubleSolenoid.Value.kForward);
    }
}