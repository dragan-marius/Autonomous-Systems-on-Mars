package main.engine;

import java.util.List;

/**
 * The "Brain" of the aircraft
 * Evaluates available flight strategies using a Risk-Based Cost Function to ensure
 * optimal autonomous operation during communication blackouts.
 */
public class AutonomousDecisionEngine {
    private static final double CRITICAL_BATTERY_THRESHOLD = 5.0;
    private final List<ManeuverStrategy> strategyList;
    public AutonomousDecisionEngine(List<ManeuverStrategy> strategies){
        this.strategyList = strategies;
    }
    /**
     * Iterates through all known strategies and selects the one with the lowest risk.
     */

    public ManeuverStrategy evaluateBestManeuver(double currentTau, double batteryLevel, double evasionExtraTime) {
        ManeuverStrategy best = null;
        double minRisk = Double.MAX_VALUE;

        for (ManeuverStrategy strategy : strategyList) {
            // CRITICAL FAIL-SAFE: If battery is critically low, strictly enforce holding position
            // to prevent total loss of the aircraft. Ignore all other high-drain maneuvers.
            if (batteryLevel < CRITICAL_BATTERY_THRESHOLD && !strategy.getName().equals("HOLD_POSITION")) {
                continue;
            }
            // Calculate the dynamic cost for the current strategy based on environmental data
            double risk = strategy.calculateRisk(currentTau, batteryLevel, evasionExtraTime);
            if (risk < minRisk) {
                minRisk = risk;
                best = strategy;
            }
        }
        // Fallback to the safest option (Hold Position) if no valid maneuver is found
        if (best == null) best = strategyList.get(2);
        return best;
    }



}
