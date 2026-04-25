package main;

public interface ManeuverStrategy {
    String getName();
    double calculateRisk(double tau, double batteryLevel, double evasionExtraTime);
    void applyEffects(DroneState drone, SensorManager sensor);
}
