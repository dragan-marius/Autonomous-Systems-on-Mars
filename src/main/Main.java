package main;

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