package main;

import main.engine.AutonomousDecisionEngine;
import main.engine.ManeuverStrategy;
import main.environment.DroneState;
import main.environment.Maneuver;
import main.environment.MarsEnvironment;
import main.sensors.SensorManager;
import main.telemetry.BlackBoxLogger;
/**
 * Time-stepped simulation environment.
 * Orchestrates the flow of time, Martian weather changes, sensor reads, and drone actuations.
 */
public class MissionSimulator {
    private static final int MAX_TIME = 720;
    private static final double EVASION_TIME = 15.0;

    private final DroneState drone;
    private final SensorManager sensor;
    private final AutonomousDecisionEngine engine;
    private final BlackBoxLogger logger;
    private final MarsEnvironment env;

    /**
     * Executes the main mission loop until destination is reached, battery dies, or time expires.
     */
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
            // 1. Sense: Read environment through degrading hardware
            double sensedTau = sensor.getReliableVisibility(drone.getCurrentTau());
            // 2. Think: Engine evaluates the lowest risk maneuver
            ManeuverStrategy strategy = engine.evaluateBestManeuver(sensedTau, drone.getBattery(), EVASION_TIME);
            // 3. Act: Apply physics and energy drain to the drone
            strategy.applyEffects(drone, sensor);
            logger.logsDecision(s, drone.getCurrentTau(), drone.getBattery(), Maneuver.valueOf(strategy.getName()));
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

}
