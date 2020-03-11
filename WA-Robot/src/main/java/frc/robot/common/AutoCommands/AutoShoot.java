package frc.robot.common.AutoCommands;

import frc.robot.common.AutoCommand;
import frc.robot.common.Trajectory;
import frc.robot.components.Conveyor;
import frc.robot.components.Shooter;
import frc.robot.components.VisionCamera;

public class AutoShoot extends AutoCommand {

    /**
     * This auto command use the trajectory and camera in order to get the shooter to the right speed autonomously. 
     * 
     * NOTE: Hard stop code needs to be added
     * 
     * Contributed by: Victor Henriksson
    **/
    //initializes objects
    private VisionCamera camera;
    private Shooter shooter;
    private Trajectory trajectory;
    private Conveyor conveyor;
    //initializes variables and pass into the abstract class
    public AutoShoot(double time, Shooter shooter,Conveyor conveyor, VisionCamera camera, Trajectory trajectory){
        super("AutoShoot", time);
        this.camera = camera;
        this.shooter = shooter;
        this.trajectory = trajectory;
        this.conveyor = conveyor;
    }

    public void init(){

    }
    //set the Shooter object to velocity calculated by Trajectory object
    //with distance obtained through Camera object.
    public void command(){
        //set the shooter velocity
        shooter.setVelocity(trajectory.getVeloctiy(camera.getDistance()));
        //once the shooter motors reach the desired velocity and left speed == right speed
        if(shooter.isReady()){
            //retrive the hardstop for conveyor
            conveyor.hardStopUp();
            conveyor.on();
        } 
        //the shooter motors are not ready
        else {
            conveyor.off();
        }
    }
}