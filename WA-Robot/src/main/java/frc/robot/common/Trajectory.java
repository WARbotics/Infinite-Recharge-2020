package frc.robot.common;




public class Trajectory{ 
    /*
        This object calculates the trajectory for a fixed angle shooter.
        Note: The equation becomes imagengery if the kX < [13.271, 0] and the kY and kX are in meter 

        Contributed by: Victor Henriksson
    */
    private double kTheta = 37.0; // Degree
    private double kY = 0;// Height shooting board
    private double kG = -9.8; // Acceleration due to gravity
    private double correctionFactor = 0;
    public Trajectory(double kY, double correctionFactor){
        this.kY = kY;
        this.correctionFactor = correctionFactor;
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
            return Math.sqrt(num/denum) + correctionFactor; // Returns m/s
        }catch(ArithmeticException e){
            throw e; // If the equation was imagernary 
        }
    }
}