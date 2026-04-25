package main.engine;

import java.util.List;

public class AutonomousDecisionEngine {
    private static final double CRITICAL_BATTERY_THRESHOLD = 5.0;
    private final List<ManeuverStrategy> strategyList;
    public AutonomousDecisionEngine(List<ManeuverStrategy> strategies){
        this.strategyList = strategies;
    }

    public ManeuverStrategy evaluateBestManeuver(double currentTau, double batteryLevel, double evasionExtraTime) {
        ManeuverStrategy best = null;
        double minRisk = Double.MAX_VALUE;

        for (ManeuverStrategy strategy : strategyList) {
            if (batteryLevel < CRITICAL_BATTERY_THRESHOLD && !strategy.getName().equals("HOLD_POSITION")) {
                continue;
            }

            double risk = strategy.calculateRisk(currentTau, batteryLevel, evasionExtraTime);
            if (risk < minRisk) {
                minRisk = risk;
                best = strategy;
            }
        }
        return (best != null) ? best : strategyList.get(2);
    }



}
