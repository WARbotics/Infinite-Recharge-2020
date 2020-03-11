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
    //sets up initial values
    private Drivetrain drive;
    public double speed = 0.0;
    public double setPoint = 0.0;
    private PID PID = new PID(0.25, 0.00, 0.00);

    //initialize the variables and pass to abstract class (setPoint is desired speed)
    public AutoMove(Drivetrain drive, double time, double setPoint){
        super("AutoMove", time);
        this.drive = drive;
        this.setPoint = setPoint;
    }

    public void init(){

    }

    //drive the robot in Arcade Drive mode with PID to calculate its optimal rate
    //the command will be called for several times until the desired speed (setPoint) is achieved
    public void command(){
        PID.setPoint(setPoint);
        PID.setActual(drive.getDistance());
        drive.drive.arcadeDrive(PID.getRate(), 0);
    }
}

    



    

    


