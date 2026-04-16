package main;

public class RadarSensor implements Sensor{
    private double sensedTau;
    public double getValue(){
        return 0.5;
    }
    public void update(double tau){
        this.sensedTau=tau;
    }

    @Override
    public boolean isOperational() {
        return true;
    }
    public String getSensorName(){
        return "X-Band Radar";
    }
}
