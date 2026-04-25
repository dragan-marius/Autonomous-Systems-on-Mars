package main;

public class HoldPositionStrategy implements ManeuverStrategy {
    private static final double MISSION_DELAY = 1.5;

    @Override
    public String getName() { return "HOLD_POSITION"; }

    @Override
    public double calculateRisk(double tau, double batteryLevel, double evasionExtraTime) {
        return 50.0 + MISSION_DELAY * 10.0;
    }

    @Override
    public void applyEffects(DroneState drone, SensorManager sensor) {
        drone.setLanded(true);
        drone.updateDistance(0.0);
    }
}
