package main.telemetry;

import main.environment.Maneuver;
/**
 * An immutable data structure representing a single snapshot of the aircraft's
 * status at a specific second in time.
 */
public class TelemetryPacket {
    private final int second;
    private final double tau;
    private final double battery;
    private final Maneuver maneuver;

    public TelemetryPacket(int second, double tau, double battery, Maneuver maneuver) {
        this.second = second;
        this.battery = battery;
        this.tau = tau;
        this.maneuver = maneuver;
    }

    public int getSecond() {
        return second;
    }

    public double getTau() {
        return tau;
    }

    public double getBattery() {
        return battery;
    }

    public Maneuver getManeuvre() {
        return maneuver;
    }


    public String toString() {
        return String.format("[T+%03ds] Tau: %.2f | Battery: %.1f | Action: %s", second, tau, battery, maneuver);
    }
}
