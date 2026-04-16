package main;

public class SensorManager {
    private Sensor radarSensor;
    private Sensor opticalSensor;
    public SensorManager(Sensor opticalSensor,Sensor radarSensor){
        this.radarSensor=radarSensor;
        this.opticalSensor=opticalSensor;
    }
    public double getReliableVisibility(double tau){
        opticalSensor.update(tau);
        radarSensor.update(tau);
        if(opticalSensor.isOperational()) {
            return opticalSensor.getValue();
        } else {
            System.out.println("Camera failure: Switching to radar backup");
            return radarSensor.getValue();
        }
    }
}
