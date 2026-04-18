package main;

public class RadarSensor implements Sensor{
    private double sensedTau;
    public double getValue(){
        return sensedTau;
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
