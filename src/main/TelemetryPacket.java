package main;

public class TelemetryPacket {
    private int second;
    private double tau;
    private double battery;
    private Maneuvre maneuvre;

    public TelemetryPacket(int second, double tau, double battery, Maneuvre maneuvre) {
        this.second = second;
        this.battery = battery;
        this.tau = tau;
        this.maneuvre = maneuvre;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public double getTau() {
        return tau;
    }

    public void setTau(double tau) {
        this.tau = tau;
    }

    public double getBattery() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public Maneuvre getManeuvre() {
        return maneuvre;
    }

    public void setManeuvre(Maneuvre maneuvre) {
        this.maneuvre = maneuvre;
    }

    public String toString() {
        return String.format("[T+%03ds] Tau: %.2f | Battery: %.1f | Action: %s", second, tau, battery, maneuvre);
    }
}
