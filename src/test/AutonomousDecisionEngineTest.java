package test;

import main.engine.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutonomousDecisionEngineTest {

    private final AutonomousDecisionEngine engine = new AutonomousDecisionEngine(Arrays.asList(
            new PushThroughStrategy(),
            new EvadeStrategy(),
            new HoldPositionStrategy()
    ));

    @Test
    @DisplayName("Should choose PUSH_THROUGH in ideal conditions")
    public void testClearWeatherFullBattery() {
        ManeuverStrategy result = engine.evaluateBestManeuver(0.5, 100.0, 10.0);
        assertEquals("PUSH_THROUGH", result.getName());
    }

    @Test
    @DisplayName("Should choose EVADE during storm if battery permits")
    public void testHeavyStormEnoughBattery() {
        ManeuverStrategy result = engine.evaluateBestManeuver(12.0, 90.0, 20.0);
        assertEquals("EVADE", result.getName());
    }

    @Test
    @DisplayName("Should choose HOLD_POSITION during storm if battery is low")
    public void testHeavyStormLowBattery() {
        ManeuverStrategy result = engine.evaluateBestManeuver(12.0, 15.0, 20.0);
        assertEquals("HOLD_POSITION", result.getName());
    }

    @Test
    @DisplayName("Should force HOLD_POSITION if battery is below 5% safety threshold")
    public void testCriticalBatterySafetyThreshold() {
        ManeuverStrategy result = engine.evaluateBestManeuver(0.5, 4.0, 10.0);
        assertEquals("HOLD_POSITION", result.getName());
    }

    @Test
    @DisplayName("Should avoid PUSH_THROUGH in extreme visibility conditions")
    public void testExtremeVisibilityRisk() {
        ManeuverStrategy result = engine.evaluateBestManeuver(25.0, 100.0, 30.0);
        assertEquals("EVADE", result.getName());
    }
}