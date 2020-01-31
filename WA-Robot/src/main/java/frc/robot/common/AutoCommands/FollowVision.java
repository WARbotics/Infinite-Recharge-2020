package frc.robot.common.AutoCommands;

import frc.robot.common.AutoCommand;
import frc.robot.components.Drivetrain;
import frc.robot.components.VisionCamera;
import frc.robot.common.PID;
public class FollowVision extends AutoCommand{
    /**
     * An Example of an AutoCommand using the example vision code from the Chamaleon
     * vision documentation 
     * Link:
     * https://chameleon-vision.readthedocs.io/en/latest/getting-started/robot-code-example.html
     * 
     * Contributed By: Victor Henriksson
     */
    private Drivetrain drive;
    private VisionCamera vision; 
    double rotationError;
    double distanceError;
    double KpRot = -0.1;
    double KpDist = -0.1;
    double angleTolerance = 5;// Deadzone for the angle control loop
    double distanceTolerance = 5;// Deadzone for the distance control loop
    double constantForce = 0.05;
    double rotationAjust;
    double distanceAjust;
    public FollowVision(Drivetrain drive, VisionCamera vision, double time){
        super("FollowVision", time);
        this.drive = drive;
        this.vision = vision;
    }
    public void init(){

    }
    public void command(){
        if(vision.isConnected()){
            // Example code from Chameleon
            rotationError = vision.getYaw();
            rotationAjust = 0;
            distanceAjust = 0;
            if (rotationError > angleTolerance)
                rotationAjust = KpRot * rotationError + constantForce;
            else if (rotationError < angleTolerance)
                rotationAjust = KpRot * rotationError - constantForce;
            /*
             * Proportional (to targetY) control loop for distance Deadzone of
             * distanceTolerance Constant power is added to the direction the control loop
             * wants to turn (to overcome friction)
             */
            if (distanceError > distanceTolerance)
                distanceAjust = KpDist * distanceError + constantForce;
            else if (distanceError < distanceTolerance)
                distanceAjust = KpDist * distanceError - constantForce;

            // Output the power signals to a arcade drivetrain
            drive.drive.arcadeDrive(distanceAjust, rotationAjust);
        }
    }

}