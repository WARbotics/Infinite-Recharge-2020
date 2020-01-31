package frc.robot.components; 

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionCamera{
    /*
     * This object acts as a wrapper for the vision processing pipeline that is done
     * by the Chameleon vision on a Raspberry pi.
     * 
     * Contributed by: Victor Henriksson
     */
    private NetworkTable table;
    private String tableName;
    private String cameraName;
    private Boolean isConnected = false;
    public VisionCamera(String tableName, String cameraName){
        this.tableName = tableName;
        this.cameraName = cameraName;
    }

    public void connect(){
        table = NetworkTableInstance.getDefault().getTable(this.tableName).getSubTable(this.cameraName);
        isConnected = true; 
    }
    
    public Boolean isConnected(){
        return isConnected;
    }
    
    public double getDistance(){
        double[] defaultValue = {0.0, 0.0, 0.0};
        return table.getEntry("targetPose").getDoubleArray(defaultValue)[0];
    }
    public double getPitch(){
        return table.getEntry("targetPitch").getDouble(0.0);
    }
    public double getYaw(){
        return table.getEntry("targetYaw").getDouble(0.0); 
    }



}