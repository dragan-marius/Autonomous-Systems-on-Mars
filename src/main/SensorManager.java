package main;

public class SensorManager {
    private final Sensor radarSensor;
    private final Sensor opticalSensor;
    private boolean cameraWasDown = false;

    public SensorManager(Sensor opticalSensor, Sensor radarSensor) {
        this.radarSensor = radarSensor;
        this.opticalSensor = opticalSensor;
    }

    public double getReliableVisibility(double tau) {
        opticalSensor.updateTau(tau);
        radarSensor.updateTau(tau);
        if (opticalSensor.isOperational()) {
            if (cameraWasDown) {
                System.out.println("Optical sensor is BACK ONLINE");
                cameraWasDown = false;
            }
            return opticalSensor.getValue();
        } else {
            if (!cameraWasDown) {
                System.out.println("Camera failure: Switching to radar backup");
                cameraWasDown = true;
            }
            return radarSensor.getValue();
        }
    }

    public void applySystemWear(double wear) {
        opticalSensor.updateWear(wear);
        radarSensor.updateWear(wear * 0.5);
    }
}
