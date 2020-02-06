package frc.robot.common.AutoCommands;

import frc.robot.components.Drivetrain;
import frc.robot.common.AutoCommand;
import frc.robot.common.PID;


public class AutoMove extends AutoCommand{
    /*
     * This is an auto command that initiats the move funstion in automous. Uses a
     * PID so that as the robot gest closer to the distance(setPoint), it slows
     * down.
     *
     * Contributed by: Lily
     *
     *
     *
     *
     */
    private Drivetrain drive;
    public double speed = 0.0;
    public double setPoint = 0.0;
    private PID PID = new PID(0.1, 0.00, 0.00);
    
    public AutoMove(Drivetrain drive, double time, double setPoint){
        super("AutoMove", time);
        this.drive = drive;
        this.setPoint = setPoint;
        
    }

    public void init(){

    }

    public void command(){
        PID.setPoint(setPoint);
        PID.setActual(drive.getDistance());
        drive.drive.arcadeDrive(PID.getRate(), 0);

        
    }

}

    



    

    


