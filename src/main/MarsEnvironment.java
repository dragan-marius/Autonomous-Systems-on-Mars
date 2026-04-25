package main;

import java.util.Random;

public class MarsEnvironment {
    private double currentTau = 0.5;
    private static final double TAU_BASE_INCREASE = 0.02;
    private final Random random = new Random();
    private final int stormStartSecond;

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
