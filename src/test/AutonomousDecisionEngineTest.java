package test;

import main.AutonomousDecisionEngine;
import main.Maneuver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutonomousDecisionEngineTest {

    private final AutonomousDecisionEngine engine = new AutonomousDecisionEngine();

    @Test
    @DisplayName("Should choose PUSH_THROUGH in ideal conditions")
    public void testClearWeatherFullBattery() {
        Maneuver result = engine.evaluateBestManeuvre(0.5, 100.0, 10.0);
        assertEquals(Maneuver.PUSH_THROUGH, result);
    }

    @Test
    @DisplayName("Should choose EVADE during storm if battery permits")
    public void testHeavyStormEnoughBattery() {
        Maneuver result = engine.evaluateBestManeuvre(12.0, 90.0, 20.0);
        assertEquals(Maneuver.EVADE, result);
    }

    @Test
    @DisplayName("Should choose HOLD_POSITION during storm if battery is low")
    public void testHeavyStormLowBattery() {
        Maneuver result = engine.evaluateBestManeuvre(12.0, 15.0, 20.0);
        assertEquals(Maneuver.HOLD_POSITION, result);
    }

    @Test
    @DisplayName("Should force HOLD_POSITION if battery is below 5% safety threshold")
    public void testCriticalBatterySafetyThreshold() {
        Maneuver result = engine.evaluateBestManeuvre(0.5, 4.0, 10.0);
        assertEquals(Maneuver.HOLD_POSITION, result);
    }

    @Test
    @DisplayName("Should avoid PUSH_THROUGH in extreme visibility conditions")
    public void testExtremeVisibilityRisk() {
        Maneuver result = engine.evaluateBestManeuvre(25.0, 100.0, 30.0);
        assertEquals(Maneuver.EVADE, result);
    }
}