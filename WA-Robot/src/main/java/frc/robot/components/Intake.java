package frc.robot.components;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


    
public class Intake{
    
    private Boolean intakeRunning = false;
    

    private final WPI_TalonSRX intake;
    public Intake(final WPI_TalonSRX intake){
    this.intake= intake;

}
public Boolean isIntakeRunning(){
    return intakeRunning;
}

public void on(){
    intake.set(1);
    intakeRunning = true;

}

public void off(){
    intake.set(0);
    intakeRunning = false;
}



}






