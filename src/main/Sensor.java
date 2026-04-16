package main;

public interface Sensor {
    double getValue();
    boolean isOperational();
    String getSensorName();
    public void update(double tau);
}
