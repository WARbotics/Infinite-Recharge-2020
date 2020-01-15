package main.java.frc.robot.components;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake{

private final WPI_TalonSRX motor;

public Intake(final WPI_TalonSRX motor){
    this.motor= motor;

}
public void on(){
    motor.set(1);
}

public void off(){
    motor.set(0);
}



}






