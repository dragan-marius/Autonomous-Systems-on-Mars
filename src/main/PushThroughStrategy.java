package main;

public class PushThroughStrategy implements  ManeuverStrategy{
    private static final double VISIBILITY_RISK = 3.5;
    private static final double ENERGY_PUSH_THROUGH = 0.05;
    private static final double BASE_PROGRESS_PUSH_THROUGH = 1.5;
    private static final double STORM_RESISTANCE = 0.15;
    private static final double WEAR_PUSH_THROUGH = 0.002;

    @Override
    public String getName() { return "PUSH_THROUGH"; }

    @Override
    public double calculateRisk(double tau, double batteryLevel, double evasionExtraTime) {
        return (tau * VISIBILITY_RISK) + ((100 - batteryLevel) * 0.5);
    }

    @Override
    public void applyEffects(DroneState drone, SensorManager sensor) {
        drone.updateEnergy(ENERGY_PUSH_THROUGH);
        double resistance = drone.getCurrentTau() * STORM_RESISTANCE;
        drone.updateDistance(BASE_PROGRESS_PUSH_THROUGH - resistance);
        if (drone.getCurrentTau() > 5.0) {
            sensor.applySystemWear(drone.getCurrentTau() * WEAR_PUSH_THROUGH);
        }
    }
}
