package frc.robot.common.AutoCommands;

import frc.robot.common.AutoCommand;
import frc.robot.components.Drivetrain;
import frc.robot.components.VisionCamera;
import frc.robot.common.PID;
public class FollowVision extends AutoCommand{

    private Drivetrain drive;
    private VisionCamera vision; 
    private double rotationError; 
    private double angleTolerance = 5;// Deadzone for the angle control loop
    private PID visionLoop = new PID(.05,0.0, 0.0);
    public FollowVision(Drivetrain drive, VisionCamera vision, double time){
        super("FollowVision", time);
        this.drive = drive;
        this.vision = vision;
    }
    public void init(){

    }
    public void command(){
        if(vision.isConnected()){
            rotationError = vision.getYaw();

        }
    }

}