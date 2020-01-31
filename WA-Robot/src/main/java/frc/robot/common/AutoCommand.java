package frc.robot.common;


public abstract class AutoCommand{
    /*
       This class acts as base for what the robot Autonomous commands should look like.

       Cotributed By: Victor Henriksson
    */
    private String name; 
    private double time;

    public AutoCommand(String name, double time){
        this.name = name;
        this.time = time;
    }
    public abstract void init();

    public abstract void command();

    public double getTime(){
        return time;
    } 
    public String getName(){
        return name;
    }
}