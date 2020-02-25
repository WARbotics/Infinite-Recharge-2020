package frc.robot.common;

public class PID {
    private double P, I, D;
    private double setPoint = 0;
    private double integral, previous_error;
    private double rate;
    private double error = 0;

    public PID(double P, double I, double D) {
        this.P = P;
        this.I = I;
        this.D = D;
    }

    public void setPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public void setActual(double actual) {
        this.error = (setPoint - actual);
        this.integral += (error * I);
        double derivative = (error - this.previous_error) / .02;
        this.rate = (P * error) + (I * this.integral) + (D * derivative);
    }
    public double getError(){
        return this.error;
    }
    public double getRate() {
        return this.rate;
    }
}