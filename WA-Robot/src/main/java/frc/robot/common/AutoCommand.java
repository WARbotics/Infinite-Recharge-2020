package frc.robot.common;


public abstract class AutoCommand{

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
}