package main;

import java.util.Random;

public class MissionSimulator {
    private static final int MAX_TIME = 720;
    private static final double EVASION_TIME = 15.0;
    private static final double ENERGY_PUSH_THROUGH = 0.05;
    private static final double BASE_PROGRESS_PUSH_THROUGH = 1.5;
    private static final double STORM_RESISTANCE = 0.15;
    private static final double ENERGY_EVADE = 0.15;
    private static final double BASE_PROGRESS_EVADE = 0.8;
    private static final double MINOR_RESISTANCE = 0.05;
    private static final double WEAR_PUSH_THROUGH = 0.002;

    private final DroneState drone;
    private final SensorManager sensor;
    private final AutonomousDecisionEngine engine;
    private final BlackBoxLogger logger;
    private final MarsEnvironment env;

    public MissionSimulator(AutonomousDecisionEngine engine, SensorManager sensor) {
        this.engine = engine;
        this.sensor = sensor;
        drone = new DroneState();
        logger = new BlackBoxLogger();
        this.env = new MarsEnvironment();
    }

    public void runSimulation() {
        System.out.println("Storm expected around second: "+ env.getStormStartSecond());
        for (int s = 0; s < MAX_TIME; s++) {
            drone.setElapsedSecond(s);
            env.updateConditions(s);
            drone.setCurrentTau(env.getCurrentTau());
            double sensedTau = sensor.getReliableVisibility(drone.getCurrentTau());
            Maneuver maneuver = engine.evaluateBestManeuvre(sensedTau, drone.getBattery(), EVASION_TIME);
            applyDecision(maneuver);
            logger.logsDecision(s, drone.getCurrentTau(), drone.getBattery(), maneuver);
            if (drone.isLanded() || drone.getBattery() <= 0 || drone.getDistanceToGoal() <= 0) {
                if (drone.getDistanceToGoal() == 0) {
                    System.out.println("Mission Success! Destination reached");
                } else if (drone.getBattery() <= 0) {
                    System.out.println("Critical Failure");
                } else {
                    System.out.println("Safe Landing");
                }
                break;
            }
        }
        logger.printfFlightStats();
    }

    public void applyDecision(Maneuver maneuver) {
        if (maneuver == Maneuver.PUSH_THROUGH) {
            drone.updateEnergy(ENERGY_PUSH_THROUGH);
            double stormResistance = drone.getCurrentTau() * STORM_RESISTANCE;
            drone.updateDistance(BASE_PROGRESS_PUSH_THROUGH - stormResistance);
            if (drone.getCurrentTau() > 5.0) {
                sensor.applySystemWear(drone.getCurrentTau() * WEAR_PUSH_THROUGH);
            }
        } else if (maneuver == Maneuver.EVADE) {
            drone.updateEnergy(ENERGY_EVADE);
            double minorResistance = drone.getCurrentTau() * MINOR_RESISTANCE;
            drone.updateDistance(BASE_PROGRESS_EVADE - minorResistance);
        } else if (maneuver == Maneuver.HOLD_POSITION) {
            drone.setLanded(true);
            drone.updateDistance(0.0);
        }
    }
}
