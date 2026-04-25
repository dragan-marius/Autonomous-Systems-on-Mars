package main;

public class EvadeStrategy implements  ManeuverStrategy{
    private static final double BATTERY_DRAIN = 2.0;
    private static final double MISSION_DELAY = 1.5;
    private static final double ENERGY_EVADE = 0.15;
    private static final double BASE_PROGRESS_EVADE = 0.8;
    private static final double MINOR_RESISTANCE = 0.05;

    @Override
    public String getName() { return "EVADE"; }

    @Override
    public double calculateRisk(double tau, double batteryLevel, double evasionExtraTime) {
        double batteryCost = evasionExtraTime * BATTERY_DRAIN;
        if (batteryLevel - batteryCost < 5.0) return Double.MAX_VALUE;
        return batteryCost + MISSION_DELAY * 2.0;
    }

    @Override
    public void applyEffects(DroneState drone, SensorManager sensor) {
        drone.updateEnergy(ENERGY_EVADE);
        double resistance = drone.getCurrentTau() * MINOR_RESISTANCE;
        drone.updateDistance(BASE_PROGRESS_EVADE - resistance);
    }
}
