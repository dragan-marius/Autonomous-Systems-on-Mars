package main.sensors;

import static java.lang.Math.max;

public class RadarSensor implements Sensor {
    private double sensedTau;
    private double health = 1.0;

    public double getValue() {
        double factor = (1.0 - health) * 0.5;
        double noise = (new java.util.Random().nextGaussian()) * factor;
        return sensedTau + noise;
    }

    public void updateTau(double tau) {
        this.sensedTau = tau;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public void updateWear(double wear) {
        this.health -= wear;
        this.health = max(0, this.health);
    }

    @Override
    public boolean isOperational() {
        return true;
    }

    public String getSensorName() {
        return "X-Band Radar";
    }
}
