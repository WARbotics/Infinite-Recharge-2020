package frc.robot.common.AutoCommands;

import frc.robot.common.AutoCommand;
import frc.robot.components.Drivetrain;

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
    static private AHRS ahrsDevice;
    static private Drivetrain robotDrive;
    private double angle = 0.0;
    private double speed = 0.0;
    private double time = 0.0;

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
    }

    public void setTime(double time) {
        this.time = time;
    }
    
    public void init() {

    }

    public void command() {
        double angleDiff = angle - ahrsDevice.getAngle();
        double rotation = angleDiff * 0.05;
    
        if (rotation > 0.3) {
            rotation = 0.3;
        }
        else if (rotation < -0.3) {
            rotation = -0.3;
        }

        SmartDashboard.putNumber("Turn Input for straight driving", rotation);
        robotDrive.curveDrive(speed, rotation, true);
    }   
}