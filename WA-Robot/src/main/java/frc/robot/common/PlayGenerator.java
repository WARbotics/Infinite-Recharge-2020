package frc.robot.common;

import java.util.ArrayList;
import frc.robot.common.AutoCommand;

public class PlayGenerator{

    private String name; 
    private ArrayList<AutoCommand> commands = new ArrayList<AutoCommand>();

    public PlayGenerator(String name){
        this.name = name;
    }
}