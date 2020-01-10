package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.common.PID;

public class Drivetrain {

    private WPI_TalonSRX _leftLeader;
    private WPI_VictorSPX _leftFollower;

    private WPI_TalonSRX _rightLeader;
    private WPI_VictorSPX _rightFollower;

    private SpeedControllerGroup left, right;
    public DifferentialDrive drive;

    private double deadBand = 0.0;
    private PID PID = new PID(0.30, 0.00, 0.01);
    private double speed = 0;
    private double rotation = 0;

    public Drivetrain(WPI_TalonSRX leftLeadTalonSRX, WPI_VictorSPX leftFollowSPX, WPI_TalonSRX rightLeadSRX,
            WPI_VictorSPX rightFollowSPX) {
        this._leftLeader = leftLeadTalonSRX;
        this._leftFollower = leftFollowSPX;
        this.left = new SpeedControllerGroup(_leftLeader, _leftFollower);

        this._rightLeader = rightLeadSRX;
        this._rightFollower = rightFollowSPX;
        this.right = new SpeedControllerGroup(_rightLeader, _rightFollower);
        this.drive = new DifferentialDrive(left, right);
    }

    public void setDeadBand(double deadband) {
        this.deadBand = deadBand;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRotation() {
        return rotation;
    }

    public void curveDrive(double speed, double rotation, boolean isQuickTurn) {
        if (Math.abs(speed) <= this.deadBand) {
            speed = 0;
        }
        if (Math.abs(rotation) <= this.deadBand) {
            rotation = 0;
        }
        this.speed = speed;
        this.rotation = rotation;
        PID.setActual(this.speed);
        drive.curvatureDrive(PID.getRate(), this.rotation, isQuickTurn);
    }
}