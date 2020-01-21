package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Conveyor{
    
private final WPI_TalonSRX motorv;
private final WPI_TalonSRX motorc;
public Conveyor(WPI_TalonSRX motorv, WPI_TalonSRX motorc){
    this.motorv= motorv;
    this.motorc= motorc;
}

public void on(){
    motorv.set(1);
    motorc.set(1);
}
public void off(){
    motorv.set(0);
    motorc.set(0);
}

public void backwards(){
motorv.set(-1);
motorc.set(-1);
}


}
