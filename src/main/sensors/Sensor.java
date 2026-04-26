package main.sensors;
/**
 * Hardware contract for all sensory equipment on the aircraft.
 * Allows the SensorManager to poll data agnostically, whether it's an optical camera,
 * a radar, or any future hardware component.
 */
public interface Sensor {
    double getValue();

    boolean isOperational();

    String getSensorName();

    void updateTau(double tau);

    double getHealth();

    void updateWear(double wear);

}
