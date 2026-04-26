package main.engine;

import main.environment.DroneState;
import main.sensors.SensorManager;

/**
 * Contract for all flight maneuvers using Strategy Pattern
 * This ensure the Open/Closed Principles: new flight strategies can be added
 * in the future without modifying the core AutonomousDecisionEngine
 */
public interface ManeuverStrategy {
    String getName();

    /**
     *Calculate the dynamic risk score for this specific maneuver.
     * Lower score means a safer/better choice
     */
    double calculateRisk(double tau, double batteryLevel, double evasionExtraTime);

    /**
     *Applies the physical consequences of the maneuver to the drone's state
     * (e.g, energy consumption, distance traveled, hardware wear)
     */
    void applyEffects(DroneState drone, SensorManager sensor);
}
