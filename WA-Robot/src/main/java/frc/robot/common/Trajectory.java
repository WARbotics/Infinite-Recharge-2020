package frc.robot.common;




public class Trajectory{ 

    private double kTheta = 37.0;
    private double kY = 0;// Height shooting board
    private double kG = -9.8; // Acceleration due to gravity
    public Trajectory(double kY){
        this.kY = kY;
    }

    public void setTheta(double theta){
        this.kTheta = theta;
    }

    public double getVeloctiy(double kX){
        // kx = Distance to the board in the x direction
        try{
            double cosTheta = Math.cos(Math.toRadians(kTheta));
            double sinTheta = Math.sin(Math.toRadians(kTheta));
            double num = .5*(kG)*kX*kX;
            double denum = cosTheta*(kY*cosTheta-sinTheta*kX);
            return Math.sqrt(num/denum);
        }catch(ArithmeticException e){
            throw e; // If the equation was imagernary 
        }
    }
}