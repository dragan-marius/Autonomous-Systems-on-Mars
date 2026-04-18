package main;

import static java.lang.Math.max;

public class DroneState {
    public double battery=100.0;
    public double currentTau=0.5;
    public int elapsedSecond=0;
    public boolean isLanded=false;
    public double distanceToGoal=450.0;

    public void updateEnergy(double battery){
        this.battery=this.battery-battery;
        if(this.battery<0) this.battery=0;
    }
    public void updateDistance(double distance){
        this.distanceToGoal-=distance;
        this.distanceToGoal=max(0,this.distanceToGoal);
    }
}
