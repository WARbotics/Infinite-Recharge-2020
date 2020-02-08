package frc.robot.components;


    import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;


    
public class Intake{
    /*
    * Description: This object uses a single Talon SRX that spins a motor to bring 
    *in a powercell to the conveyor belt.
    *
    *Contrubuted By: Lily
    *
    */
    private Boolean intakeRunning = false;
    

    private WPI_VictorSPX intake;
    public Intake(WPI_VictorSPX intake){
        this.intake = intake;

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






