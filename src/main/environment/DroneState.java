package main.environment;

import static java.lang.Math.max;

/**
 * Represents the physical state and telemetry data of the aircraft.
 * Tracks critical resources such as battery levels, distance to the objective,
 * and the current environmental conditions immediately surrounding the drone.
 */

public class DroneState {
    private double battery = 100.0;
    private double currentTau = 0.5;
    private int elapsedSecond = 0;
    private boolean isLanded = false;
    private double distanceToGoal = 450.0;

    public double getBattery() {
        return battery;
    }

    public double getCurrentTau() {
        return currentTau;
    }

    public void setCurrentTau(double currentTau) {
        this.currentTau = currentTau;
    }

    public int getElapsedSecond() {
        return elapsedSecond;
    }

    public void setElapsedSecond(int elapsedSecond) {
        this.elapsedSecond = elapsedSecond;
    }

    public boolean isLanded() {
        return isLanded;
    }

    public void setLanded(boolean landed) {
        isLanded = landed;
    }

    public double getDistanceToGoal() {
        return distanceToGoal;
    }


    public void updateEnergy(double battery) {
        this.battery = this.battery - battery;
        if (this.battery < 0) this.battery = 0;
    }

    public void updateDistance(double distance) {
        this.distanceToGoal -= distance;
        this.distanceToGoal = max(0, this.distanceToGoal);
    }
}
