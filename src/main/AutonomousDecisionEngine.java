package main;

public class AutonomousDecisionEngine {
    private static final double VISIBILITY_RISK = 3.5;
    private static final double BATTERY_DRAIN = 2.0;
    private static final double MISSION_DELAY = 1.5;

    public Maneuvre evaluateBestManeuvre(double currentTau, double batteryLevel, double evasionExtraTime) {
        double lowestRiskCost = Double.MAX_VALUE;
        Maneuvre bestManeuvre = Maneuvre.HOLD_POSITION;
        for (Maneuvre maneuvre : Maneuvre.values()) {
            double currentCost = calculateRiskCost(maneuvre, currentTau, batteryLevel, evasionExtraTime);
            if (currentCost < lowestRiskCost) {
                lowestRiskCost = currentCost;
                bestManeuvre = maneuvre;
            }
        }
        return bestManeuvre;
    }

    private double calculateRiskCost(Maneuvre maneuvrer, double tau, double batteryLevel, double evasionExtraTime) {
        double risk = 0;
        if (maneuvrer == Maneuvre.PUSH_THROUGH) {
            risk = (tau * VISIBILITY_RISK) + ((100 - batteryLevel) * 0.5);
        } else if (maneuvrer == Maneuvre.EVADE) {
            double batteryCost = evasionExtraTime * BATTERY_DRAIN;
            if (batteryLevel - batteryCost < 5.0) {
                risk = Double.MAX_VALUE;
            } else {
                risk = batteryCost + MISSION_DELAY * 2.0;
            }
        } else if (maneuvrer == Maneuvre.HOLD_POSITION) {
            risk = 50.0 + MISSION_DELAY * 10.0;
        } else {
            risk = Double.MAX_VALUE;
        }
        return risk;
    }


}
