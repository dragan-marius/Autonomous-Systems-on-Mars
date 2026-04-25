package main.engine;

import main.environment.DroneState;
import main.sensors.SensorManager;

public interface ManeuverStrategy {
    String getName();
    double calculateRisk(double tau, double batteryLevel, double evasionExtraTime);
    void applyEffects(DroneState drone, SensorManager sensor);
}
