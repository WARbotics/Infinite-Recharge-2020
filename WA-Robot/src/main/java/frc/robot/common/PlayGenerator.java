package frc.robot.common;

import java.util.ArrayList;
import frc.robot.common.AutoCommand;
import edu.wpi.first.wpilibj.Timer;

public class PlayGenerator{
    /*
        This objects allows the team to easily create plays taht made up commands that have a set amount time.
        Plus, the commands will in a smaller form factor making the AutonomousPeridoc method less crowded.

        Contributed by: Victor Henriksson
    */
    private static ArrayList<String> plays = new ArrayList<String>();
    private int iteration = 0; 
    private String name; 
    private Timer timer = new Timer();
    private ArrayList<AutoCommand> commands = new ArrayList<AutoCommand>();
    //once the command is passed in, the command is stored in a queue
    public PlayGenerator(String name){
        plays.add(name);
        this.name = name;
    }

    public boolean run(){
        // Runs through each command for its set amount of time
        if(iteration == commands.size()){
            return true;
        }else{
            if(timer.get() >= commands.get(iteration).getTime()){
                iteration++;
            }else{
                commands.get(iteration).command();
            }
            return false;
        }
    }

    public String getName(){
        return name;
    }

    //getter method for the queue for commands
    public static ArrayList<String> getPlays(){
        return plays;
    }

    //add commands and their run time in a queue
    public void addPlay(AutoCommand command){
        commands.add(command);
    }
}