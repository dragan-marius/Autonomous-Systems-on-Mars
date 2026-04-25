package main;

import main.engine.AutonomousDecisionEngine;
import main.engine.EvadeStrategy;
import main.engine.HoldPositionStrategy;
import main.engine.PushThroughStrategy;
import main.sensors.OpticalVisibilitySensor;
import main.sensors.RadarSensor;
import main.sensors.Sensor;
import main.sensors.SensorManager;

public class Main {
    static void main(String[] args) {
        Sensor optical = new OpticalVisibilitySensor();
        Sensor radar = new RadarSensor();
        SensorManager sensorManager = new SensorManager(optical, radar);
        var strategies = java.util.List.of(
                new PushThroughStrategy(),
                new EvadeStrategy(),
                new HoldPositionStrategy()
        );
        AutonomousDecisionEngine engine = new AutonomousDecisionEngine(strategies);
        MissionSimulator sim = new MissionSimulator(engine, sensorManager);
        sim.runSimulation();
    }
}