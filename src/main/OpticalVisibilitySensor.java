package main;

public class OpticalVisibilitySensor implements Sensor{
    private double lastValue;
    private boolean operational=true;
    @Override
    public double getValue() {
        return lastValue;
    }
    public void update(double tau){
        lastValue=tau;
        this.operational=isOperational();
    }

    @Override
    public boolean isOperational() {
        if(lastValue<=10.0)
            return true;
        return false;
    }

    @Override
    public String getSensorName() {
        return "Optical Camera VIO";
    }

}
