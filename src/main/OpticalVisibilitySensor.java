package main;

import static java.lang.Math.max;

public class OpticalVisibilitySensor implements Sensor {
    private double lastValue;
    private boolean operational = true;
    private double health = 1.0;

    @Override
    public double getValue() {
        double factor = (1.0 - health) * 2.0;
        double noise = (new java.util.Random().nextGaussian()) * factor;
        return lastValue + noise;
    }

    public void updateTau(double tau) {
        lastValue = tau;
        this.operational = isOperational();
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
        return lastValue <= 10.0 && health > 0.1;
    }

    @Override
    public String getSensorName() {
        return "Optical Camera VIO";
    }

}
