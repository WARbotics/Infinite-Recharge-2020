package frc.robots.components;

import frc.robots.components.Drivetrain;

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

    public AutoTurn(WPI_TalonSRX leftLeadTalonSRX, WPI_VictorSPX leftFollowSPX, WPI_TalonSRX rightLeadSRX,
    WPI_TalonSRX rightFollowSPX, String name, double time) {
        super(name, time);
        ahrsDevice = new AHRS(SPI.Port.kMXP);
        robotDrive = new Drivetrain(leftLeadTalonSRX, leftFollowSPX, rightLeadSRX,
        rightFollowSPX);
    }

    public void robotRotating (double speed, double angle) {
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