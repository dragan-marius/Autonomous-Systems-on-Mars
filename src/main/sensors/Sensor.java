package main.sensors;

public interface Sensor {
    double getValue();

    boolean isOperational();

    String getSensorName();

    void updateTau(double tau);

    double getHealth();

    void updateWear(double wear);

}
