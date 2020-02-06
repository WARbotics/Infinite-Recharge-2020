package frc.robot.common.AutoCommands;

import frc.robot.common.FollowVision;
import frc.robot.common.AutoCommand;
import frc.robot.components.Drivetrain;
import frc.robot.common.PID;

import java.lang.*;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class AutoTurn extends AutoCommand{
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
    private PID PID = new PID(0.10, 0.00, 0.0);

    private double angle = 0.0;
    private double ROBOT_RADIUS = 3.1415926;
    public AutoTurn(Drivetrain drive, double time) {
        super("AutoTurn", time);
        ahrsDevice = new AHRS(SPI.Port.kMXP);
        this.drive = drive;
    }


    public void init() {

    }

    public void command() {
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