package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Conveyor{
 
    
    private ConveyorMode mode = ConveyorMode.OFF;

    private final WPI_TalonSRX frontConveyor;
    private final WPI_TalonSRX backConveyor;
    public Conveyor(WPI_TalonSRX frontConveyor, WPI_TalonSRX backConveyor){
    this.frontConveyor= frontConveyor;
    this.backConveyor= backConveyor;
}



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


}
