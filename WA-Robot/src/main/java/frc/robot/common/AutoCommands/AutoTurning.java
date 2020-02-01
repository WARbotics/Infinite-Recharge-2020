package frc.robot.common.AutoCommands;

import frc.robot.common.AutoCommand;
import frc.robot.components.Drivetrain;

import java.lang.*;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

private PID PID = new PID(0.10, 0.00, 0.0);
private double distance = 0.0;
private double angle = 0.0;
private double speed = 0.0;
private double time = 0.0;
final static private double ROBOT_RADIUS = 3.1415926;

public class AutoTurn extends AutoCommand{
    /*
    *This object receives all the motors for drivetrain and initiates NavX Micro (for angle) and drivetrain(four motors)
    *For the rotating method, it receives the desired angle and the desired speed
    *Then, the angleDiff calculates the "error" between the current angle and the desired angle
    *Rotating 5% of angleDiff for numerous times, the robot will finally achieve the desired angle
    *With the PID build within drivetrain, the robot achieves the desired goal.
    *Contributed by Bowen Tan
    */
    static private AHRS ahrsDevice;
    static private Drivetrain robotDrive;
    
    public AutoTurn(Drivetrain drive, double time, double angle) {
        super("AutoTurn", time);
        ahrsDevice = new AHRS(SPI.Port.kMXP);
        robotDrive = drive;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.distance = angle * 2 * ROBOT_RADIUS * Math.PI;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public void init() {

    }

    public void command() {
        double realAngle = ahrsDrive.pidGet();
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
            double angle = realAngle - angleDiff;
            double dist = angle * 2 * Math.PI * ROBOT_RADIUS / 360;
            double actualDist = realAngle * 2 * Math.PI * ROBOT_RADIUS / 360;
            PID.setPoint(dist);
            PID.setActual(actualDist);
            drive.drive.tankDrive(PID.getRate() * (-1), PID.getRate(), false);
        } 
        else if (goRight) {
            double angle = realAngle + angleDiff;
            double dist = angle * 2 * Math.PI * ROBOT_RADIUS / 360;
            double actualDist = realAngle * 2 * Math.PI * ROBOT_RADIUS / 360;
            PID.setPoint(dist);
            PID.setActual(actualDist);
            PID.setPoint(dist);
            PID.setActual(actualDist);
            drive.drive.tankDrive(PID.getRate(), PID.getRate() * (-1), false);
        }
    }
}