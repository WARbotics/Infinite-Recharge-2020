package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.playingwithfusion.TimeOfFlight;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Sendable;

public class Conveyor{
    /*
    *Decription: This object spins two Talon SRX motors in the same direction to take
    *the power cell to the ball shooter. If need be, there belt can also go backwards.
    *
    *Contributed: Lily
    *
    *
    */
 
    
    private ConveyorMode mode = ConveyorMode.OFF;
    

    private final WPI_TalonSRX frontConveyor;
    private final WPI_TalonSRX backConveyor;
    private final TimeOfFlight ballSensor;
    public Conveyor(WPI_TalonSRX frontConveyor, WPI_TalonSRX backConveyor, TimeOfFlight ballSensor){
    this.frontConveyor= frontConveyor;
    this.backConveyor= backConveyor;
    this.ballSensor = ballSensor;
     } 
    
    DoubleSolenoid hardStop = new DoubleSolenoid(1, 2);
    
    
    public enum ConveyorMode {
        FORWARDS, BACKWARDS, OFF
    }

    public ConveyorMode getConveyorMode(){
        return mode;
    }

    public void setConveyorMode(ConveyorMode mode){
        this.mode = mode;
    }

    public void on(){
        frontConveyor.set(1);
        backConveyor.set(1);
        mode = ConveyorMode.FORWARDS;
    

    }
    public void off(){
        frontConveyor.set(0);
        backConveyor.set(0);
        mode = ConveyorMode.OFF;
    }

    public void backwards(){
        frontConveyor.set(-1);
        backConveyor.set(-1);
        mode = ConveyorMode.BACKWARDS;
    
    }

    public void hardStopUp(){
        hardStop.set(DoubleSolenoid.Value.kForward);
    }

    public void hardStopDown(){
        hardStop.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void ballDist(){
        ballSensor.getRange();
    }

}
