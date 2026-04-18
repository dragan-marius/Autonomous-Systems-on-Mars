package main;

import java.util.Random;
import java.util.Scanner;

public class MissionSimulator {
    private static final int MAX_TIME= 720;
    private static final double TAU_INCREASE=0.02;
    private static final double EVASION_TIME=15.0;
    private static final double ENERGY_PUSH_THROUGH=0.05;
    private static final double BASE_PROGRESS_PUSH_THROUGH=1.5;
    private static final double STORM_RESISTANCE=0.15;
    private static final double ENERGY_EVADE=0.15;
    private static final double BASE_PROGRESS_EVADE=0.8;
    private static final double MINOR_RESISTANCE=0.05;
    private static final double WEAR_PUSH_THROUGH=0.002;

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
        for(int s=0;s<MAX_TIME;s++){
            drone.elapsedSecond=s;
            if(s>start){
                double change=random.nextDouble()*0.4-0.2;
                drone.currentTau=drone.currentTau+TAU_INCREASE+change;
            }
            double sensedTau=sensor.getReliableVisibility(drone.currentTau);
            Maneuvre maneuvre=engine.evaluateBestManeuvre(sensedTau,drone.battery,EVASION_TIME);
            applyDecision(maneuvre);
            logger.logsDecision(s,drone.currentTau,drone.battery,maneuvre);
            if(drone.isLanded || drone.battery<=0 || drone.distanceToGoal<=0){
                if(drone.distanceToGoal==0) {
                    System.out.println("Mission Success! Destination reached");
                } else if(drone.battery<=0){
                    System.out.println("Critical Failure");
                } else {
                    System.out.println("Safe Landing");
                }
                break;
            }
        }
        logger.printfFlightStat();
    }
    public void applyDecision(Maneuvre maneuvre){
        if(maneuvre==Maneuvre.PUSH_THROUGH) {
            drone.updateEnergy(ENERGY_PUSH_THROUGH);
            double stormResistance=drone.currentTau*STORM_RESISTANCE;
            drone.updateDistance(BASE_PROGRESS_PUSH_THROUGH-stormResistance);
            if(drone.currentTau>5.0){
                sensor.apllySystemWear(drone.currentTau*WEAR_PUSH_THROUGH);
            }
        } else if (maneuvre==Maneuvre.EVADE){
            drone.updateEnergy(ENERGY_EVADE);
            double minorResistance=drone.currentTau*MINOR_RESISTANCE;
            drone.updateDistance(BASE_PROGRESS_EVADE-minorResistance);
        } else if(maneuvre==Maneuvre.HOLD_POSITION){
            drone.isLanded=true;
            drone.updateDistance(0.0);
        }
    }
}
