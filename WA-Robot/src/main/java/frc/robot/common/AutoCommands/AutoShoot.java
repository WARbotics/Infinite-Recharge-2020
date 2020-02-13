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
    private VisionCamera camera;
    private Shooter shooter;
    private Trajectory trajectory;
    private Conveyor conveyor;
    public AutoShoot(double time, Shooter shooter,Conveyor conveyor, VisionCamera camera, Trajectory trajectory){
        super("AutoShoot", time);
        this.camera = camera;
        this.shooter = shooter;
        this.trajectory = trajectory;
        this.conveyor = conveyor;
    }

    public void init(){

    }
    public void command(){
        shooter.setVelocity(trajectory.getVeloctiy(camera.getDistance()));
        if(shooter.isReady()){
            conveyor.hardStopUp();
            conveyor.on();
        } else {
            conveyor.off();
        }
    }

}