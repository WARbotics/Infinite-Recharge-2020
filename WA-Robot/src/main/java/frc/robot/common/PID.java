package frc.robot.common;

public class PID {
    private double P, I, D, dt;
    private double setPoint = 0;
    private double integral, previous_error;
    private double rate;
    private double error = 0;

    /**
     * 
     * @param P Constant for proportional term
     * @param I Constant for integral term
     * @param D Constant for derivative term
     * @param dt Delta time, or refresh rate. Typically 1/60.
     */
    public PID(double P, double I, double D, double dt) {
        this.P = P;
        this.I = I;
        this.D = D;
        this.dt = dt;
    }

    public void setPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public void setActual(double actual) {
        //https://en.wikipedia.org/wiki/PID_controller#Pseudocode
        this.error = (setPoint - actual);
        this.integral += (error * dt);
        double derivative = (error - this.previous_error) / dt;
        this.previous_error = this.error;
        this.rate = (P * error) + (I * this.integral) + (D * derivative);
    }
    public double getError(){
        return this.error;
    }
    public double getRate() {
        return this.rate;
    }
}