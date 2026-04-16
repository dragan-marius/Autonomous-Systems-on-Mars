package main;

import java.util.Random;
import java.util.Scanner;

public class MissionSimulator {
    private final DroneState drone;
    private final SensorManager sensor;
    private final AutonomousDecisionEngine engine;
    private final BlackBoxLogger logger;
    private Random random=new Random();
    private final int start=random.nextInt(50)+50;
    public MissionSimulator(AutonomousDecisionEngine engine,SensorManager sensor){
        this.engine=engine;
        this.sensor=sensor;
        drone=new DroneState();
        logger=new BlackBoxLogger();

    }
    public void runSimulation(){
        System.out.println(start);
        for(int s=0;s<720;s++){
            drone.elapsedSecond=s;
            if(s>start){
                double change=random.nextDouble()*0.4-0.2;
                drone.currentTau=drone.currentTau+0.02+change;
            }
            double sensedTau=sensor.getReliableVisibility(drone.currentTau);
            Maneuvre maneuvre=engine.evaluateBestManeuvre(sensedTau,drone.battery,900/60.0);
            applyDecision(maneuvre);
            logger.logsDecision(s,drone.currentTau,drone.battery,maneuvre);
            if(drone.isLanded || drone.battery<=0){
                System.out.print("Simulation terminated: ");
                if(drone.battery<=0){
                    System.out.println("Critical Failure");
                }
                else {
                    System.out.println("Safe Landing");
                }
                break;
            }
        }
        logger.printfFlightStat();
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
