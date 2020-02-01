package frc.robot;


import frc.robot.components.Drivetrain;
import frc.robot.common.PID;


public class autoMove{

    private Drivetrain drive;
    public double speed = 0.0;
    public double time = 0.0;
    public double dist = 0.0;
    public double setPoint = 0.0;
    private PID PID = new PID(0.1, 0.00, 0.00);
    
    public autoMove(Drivetrain drive, double speed, double time, double setPoint){
        this.drive = drive;
        this.setPoint = setPoint;
        
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public void distDiff(double dist){
        this.dist = dist;
    }

    public void setDistance(double setPoint){
        this.setPoint = setPoint;
    }

    public void setTime(double time){
        this.time = time;
    }
    

    

    public void init(){

    }

    public void command(){
        PID.setPoint(setPoint);
        PID.setActual(drive.getDistance());
        
    
        
        drive.drive.arcadeDrive(PID.getRate(), 0);

        
    }

}

    



    

    


