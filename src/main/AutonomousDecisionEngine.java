package main;

public class AutonomousDecisionEngine {
    private static final double VISIBILITY_RISK = 3.5;
    private static final double BATTERY_DRAIN = 2.0;
    private static final double MISSION_DELAY = 1.5;
    private static final double CRITICAL_BATTERY_THRESHOLD = 5.0;

    public Maneuver evaluateBestManeuvre(double currentTau, double batteryLevel, double evasionExtraTime) {
        double lowestRiskCost = Double.MAX_VALUE;
        Maneuver bestManeuver = Maneuver.HOLD_POSITION;
        for (Maneuver maneuver : Maneuver.values()) {
            double currentCost = calculateRiskCost(maneuver, currentTau, batteryLevel, evasionExtraTime);
            if (currentCost < lowestRiskCost) {
                lowestRiskCost = currentCost;
                bestManeuver = maneuver;
            }
        }
        return bestManeuver;
    }

    private double calculateRiskCost(Maneuver maneuvrer, double tau, double batteryLevel, double evasionExtraTime) {
        if (batteryLevel < CRITICAL_BATTERY_THRESHOLD && maneuvrer != Maneuver.HOLD_POSITION) {
            return Double.MAX_VALUE;
        }
        double risk = 0;
        if (maneuvrer == Maneuver.PUSH_THROUGH) {
            risk = (tau * VISIBILITY_RISK) + ((100 - batteryLevel) * 0.5);
        } else if (maneuvrer == Maneuver.EVADE) {
            double batteryCost = evasionExtraTime * BATTERY_DRAIN;
            if (batteryLevel - batteryCost < CRITICAL_BATTERY_THRESHOLD) {
                risk = Double.MAX_VALUE;
            } else {
                risk = batteryCost + MISSION_DELAY * 2.0;
            }
        } else if (maneuvrer == Maneuver.HOLD_POSITION) {
            risk = 50.0 + MISSION_DELAY * 10.0;
        } else {
            risk = Double.MAX_VALUE;
        }
        return risk;
    }


}
