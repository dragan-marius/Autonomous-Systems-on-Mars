package main;

public class MissionSimulator {
    private final DroneState drone;
    private final SensorManager sensor;
    private final AutonomousDecisionEngine engine;
    private final BlackBoxLogger logger;
    public MissionSimulator(AutonomousDecisionEngine engine,SensorManager sensor){
        this.engine=engine;
        this.sensor=sensor;
        drone=new DroneState();
        logger=new BlackBoxLogger();

    }
    public void runSimulation(){
        for(int s=0;s<720;s++){
            drone.elapsedSecond=s;
            if(s>100){
                drone.currentTau=drone.currentTau+0.02;
            }
            double sensedTau=sensor.getReliableVisibility(drone.currentTau);
            Maneuvre maneuvre=engine.evaluateBestManeuvre(sensedTau,drone.battery,900/60.0);
            applyDecision(maneuvre);
            logger.logDecision(s,drone.currentTau,maneuvre,drone.battery);
            if(drone.isLanded || drone.battery<=0){
                System.out.print("Simulation terminated: ");
                if(drone.battery<=0){
                    System.out.println("Critical Failure");
                }
                else {
                    System.out.println("Safe Landing");
                }
            }
        }
    }
    public void applyDecision(Maneuvre maneuvre){
        if(maneuvre==Maneuvre.PUSH_THROUGH) {
            drone.updateEnergy(0.05);
        } else if (maneuvre==Maneuvre.EVADE){
            drone.updateEnergy(0.15);
        } else if(maneuvre==Maneuvre.HOLD_POSITION){
            drone.isLanded=true;
        }
    }
}
