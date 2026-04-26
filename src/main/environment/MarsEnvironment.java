package main.environment;

import java.util.Random;
/**
 * Simulates the unpredictable and harsh Martian weather conditions.
 * Uses randomized progression to model the onset and severity of dust storms,
 * dynamically altering the atmospheric opacity (Tau) over time.
 */
public class MarsEnvironment {
    private double currentTau = 0.5;
    private static final double TAU_BASE_INCREASE = 0.1;
    private final Random random = new Random();
    private final int stormStartSecond;

    /**
     * Updates the environmental conditions for the current time step.
     * Simulates the gradual thickening of the atmosphere as a storm approaches.
     */
    public MarsEnvironment() {
        this.stormStartSecond = random.nextInt(50) + 50;
    }

    public void updateConditions(int elapsedSeconds) {
        if (elapsedSeconds > stormStartSecond) {
            double noise = random.nextDouble() * 0.4 - 0.2;
            currentTau = currentTau + TAU_BASE_INCREASE + noise;
        }
        if (currentTau < 0.5) currentTau = 0.5;
    }

    public double getCurrentTau() {
        return currentTau;
    }

    public int getStormStartSecond() {
        return stormStartSecond;
    }
}
