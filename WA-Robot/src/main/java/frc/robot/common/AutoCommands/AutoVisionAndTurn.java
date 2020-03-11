package frc.robot.common.AutoCommands;

import frc.robot.common.AutoCommand;
import frc.robot.components.Drivetrain;
import frc.robot.components.VisionCamera;
import frc.robot.common.PID;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class AutoVisionAndTurn extends AutoCommand{
    /*
    *This object receives all the motors for drivetrain and initiates NavX Micro (for angle) and drivetrain(four motors)
    *For the rotating method, it receives the desired angle and the desired speed
    *Then, the angleDiff calculates the "error" between the current angle and the desired angle
    *Rotating 5% of angleDiff for numerous times, the robot will finally achieve the desired angle
    *With the PID build within drivetrain, the robot achieves the desired goal.
    *Contributed by Bowen Tan
    */
    private AHRS ahrsDevice;
    private Drivetrain drive;
    private VisionCamera vision;
    private PID PID = new PID(0.10, 0.00, 0.0);

    double KpRot = -0.1;
    double KpDist = -0.1;
    double angleTolerance = 5;// Deadzone for the angle control loop
    double distanceTolerance = 5;// Deadzone for the distance control loop
    double constantForce = 0.05;
    double rotationAjust, rotationError;
    double distanceAjust;

    private double angle = 0.0;
    //Assume the robot radius is PI;
    private double ROBOT_RADIUS = 3.1415926;

    public AutoVisionAndTurn(Drivetrain drive, VisionCamera vision, double time) {
        super("VisionAndTurn", time);
        ahrsDevice = new AHRS(SPI.Port.kMXP);
        this.drive = drive;
        this.vision = vision;
    }

    public void init() {

    }

    private double visionProcess() {
        if (!vision.isConnected()) {
            return 0.0;
        }
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
        // Output the power signals to a arcade drivetrain
        this.angle = rotationAjust;
        return angle;
    }

    public void command() {
        this.visionProcess();
        double realAngle = ahrsDevice.pidGet();
        if (realAngle < 0) {
            realAngle += 360;
        }
        //records whether the robot should turn right or left every time
        Boolean goLeft = false;
        Boolean goRight = false;
        //records the angle the robot has to travel without any process on the angle
        double angleDiff = Math.abs(angle - realAngle);
        //If the robot has a shorter distance to travel
        if (angleDiff > 180) {
            //Choose the shorter way(angle)
            angleDiff = 360 - angleDiff;
            //Should the robot turn right or left
            if (angle < realAngle) {
                goRight = true;
            } else {
                goLeft = true;
            }
        } else {
            if (angle < realAngle) {
                goLeft = true;
            } else {
                goRight = true;
            }
        }
        
        SmartDashboard.putNumber("Turn Input for straight driving", PID.getRate());
        if (goLeft) {
        //if expected angle is bigger than the real angle (0<=real<=90 && 270<=actual<=360)
        //make them both 90<=both<=270 (both at opposite sides of the circle)
            if (angle > realAngle) {
                realAngle += 180;
                angle -= 180;
            }
            double dist = angle * 2 * Math.PI * ROBOT_RADIUS / 360;
            double actualDist = realAngle * 2 * Math.PI * ROBOT_RADIUS / 360;
            PID.setPoint(dist);
            PID.setActual(actualDist);
            drive.drive.tankDrive(PID.getRate() * (-1), PID.getRate(), false);
        } 
        //if expected angle is smaller than the real angle (270<=real<=360 && 0<=actual<=90)
        //make them both 90<=both<=270 (both at opposite sides of the circle)
        else if (goRight) {
            if (angle < realAngle) {
                realAngle -= 180;
                angle += 180;
            }
            double dist = angle * 2 * Math.PI * ROBOT_RADIUS / 360;
            double actualDist = realAngle * 2 * Math.PI * ROBOT_RADIUS / 360;
            PID.setPoint(dist);
            PID.setActual(actualDist);
            drive.drive.tankDrive(PID.getRate(), PID.getRate() * (-1), false);
        }
    }
    public boolean isTurning(){
        return Math.abs(ahrsDevice.getRate()) >= 7.5;
    }
}