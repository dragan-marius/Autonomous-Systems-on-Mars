package main;

public class TelemetryPacket {
    private int second;
    private double tau;
    private double battery;
    private Maneuver maneuver;

    public TelemetryPacket(int second, double tau, double battery, Maneuver maneuver) {
        this.second = second;
        this.battery = battery;
        this.tau = tau;
        this.maneuver = maneuver;
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

    public Maneuver getManeuvre() {
        return maneuver;
    }

    public void setManeuvre(Maneuver maneuver) {
        this.maneuver = maneuver;
    }

    public String toString() {
        return String.format("[T+%03ds] Tau: %.2f | Battery: %.1f | Action: %s", second, tau, battery, maneuver);
    }
}
