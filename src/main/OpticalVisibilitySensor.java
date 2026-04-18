package main;

import static java.lang.Math.max;

public class OpticalVisibilitySensor implements Sensor{
    private double lastValue;
    private boolean operational=true;
    private double health=1.0;
    @Override
    public double getValue() {
        return lastValue;
    }
    public void updateTau(double tau){
        lastValue=tau;
        this.operational=isOperational();
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public void updateWear(double wear) {
        this.health-=wear;
        this.health=max(0,this.health);

    }

    @Override
    public boolean isOperational() {
        if(lastValue<=10.0 && health>0.1)
            return true;
        return false;
    }

    @Override
    public String getSensorName() {
        return "Optical Camera VIO";
    }

}
