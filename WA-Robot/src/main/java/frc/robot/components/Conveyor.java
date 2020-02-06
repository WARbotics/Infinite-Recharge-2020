package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.playingwithfusion.TimeOfFlight;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Conveyor{
    /*
    *Decription: This object spins two Talon SRX motors in the same direction to take
    *the power cell to the ball shooter. If need be, there belt can also go backwards.
    *There is a pnumatic hard stop tha can be activated with a button.
    *There is a TimeOfFLight sensor that tells the driver when a ball is present.
    *
    *Contributed: Lily
    */
 
    
    private ConveyorMode mode = ConveyorMode.OFF;
    

    private final WPI_TalonSRX frontConveyorMotor;
    private final WPI_TalonSRX backConveyorMotor;
    private TimeOfFlight ballSensor;
    private double threshold = .25;
    public Conveyor(WPI_TalonSRX frontConveyorMotor, WPI_TalonSRX backConveyorMotor, TimeOfFlight ballSensor, double threshold){
        this.frontConveyorMotor= frontConveyorMotor;
        this.backConveyorMotor= backConveyorMotor;
        this.ballSensor = ballSensor;
        this.threshold = threshold;
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
        frontConveyorMotor.set(1);
        backConveyorMotor.set(1);
        mode = ConveyorMode.FORWARDS;
    

    }
    public void off(){
        frontConveyorMotor.set(0);
        backConveyorMotor.set(0);
        mode = ConveyorMode.OFF;
    }

    public void backwards(){
        frontConveyorMotor.set(-1);
        backConveyorMotor.set(-1);
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

    public boolean isBallPresent(){
        if(ballSensor.getRange() < threshold){
            return true;
        }
    return false;
    
    }

}
