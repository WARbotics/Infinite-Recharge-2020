package main.java.frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Conveyor{
    
private final WPI_TalonSRX motorv;
public Conveyor(final WPI_TalonSRX motorv){
    this.motorv= motorv;
}

public void on(){
    motorv.set(1);
}
public void off(){
    motorv.set(0);
}

}