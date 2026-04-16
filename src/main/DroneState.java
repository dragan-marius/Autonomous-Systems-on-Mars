package main;

public class DroneState {
    public double battery=100.0;
    public double currentTau=0.5;
    public int elapsedSecond=0;
    public boolean isLanded=false;

    public void updateEnergy(double battery){
        this.battery=this.battery-battery;
        if(this.battery<0) this.battery=0;
    }
}
