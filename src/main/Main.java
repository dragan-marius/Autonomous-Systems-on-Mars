package main;

public class Main{
    public static void main(String args[]){
        Sensor optical=new OpticalVisibilitySensor();
        Sensor radar= new RadarSensor();
        SensorManager sensorManager=new SensorManager(optical,radar);
        AutonomousDecisionEngine engine=new AutonomousDecisionEngine();
        MissionSimulator sim=new MissionSimulator(engine,sensorManager);
        sim.runSimulation();;
    }
}