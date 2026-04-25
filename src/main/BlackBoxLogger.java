package main;

import java.util.*;

public class BlackBoxLogger {
    private final NavigableMap<Integer, TelemetryPacket> logs;
    private final Map<Maneuver, Integer> maneuvreStats;

    public BlackBoxLogger() {
        this.logs = new TreeMap<>();
        this.maneuvreStats = new EnumMap<>(Maneuver.class);
        for (Maneuver m : Maneuver.values()) {
            maneuvreStats.put(m, 0);
        }
    }

    public void logsDecision(int second, double tau, double battery, Maneuver maneuver) {
        TelemetryPacket packet = new TelemetryPacket(second, tau, battery, maneuver);
        logs.put(second, packet);
        System.out.println(packet);
        Integer cnt = maneuvreStats.get(maneuver);
        cnt++;
        maneuvreStats.put(maneuver, cnt);
    }

    public List<TelemetryPacket> getLogsBetween(int start, int end) {
        return new ArrayList<>(logs.subMap(start, true, end, true).values());
    }

    public void printfFlightStats() {
        for (Map.Entry<Maneuver, Integer> m : maneuvreStats.entrySet()) {
            System.out.println("Maneuver " + m.getKey() + " was called " + m.getValue() + " times");
        }
    }
}
