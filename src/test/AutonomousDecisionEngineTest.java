package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.*;

public class AutonomousDecisionEngineTest {
    AutonomousDecisionEngine engine= new AutonomousDecisionEngine();
    @Test
    public void testClearWeatherFullBatteryShoulPushThrough(){
        Maneuvre result=engine.evaluateBestManeuvre(0.5,100.0,10.0);
        assertEquals(Maneuvre.PUSH_THROUGH,result);
    }
    @Test
    public void testHeavyStormEnughBattery_ShouldEvade(){
        Maneuvre result=engine.evaluateBestManeuvre(12.0,90.0,20.0);
        assertEquals(Maneuvre.EVADE,result);
    }
    @Test
    public void testHeavyStormLowBattery_ShouldHoldPosition(){
        Maneuvre result=engine.evaluateBestManeuvre(12.0,15.0,20.0);
        assertEquals(Maneuvre.HOLD_POSITION,result);
    }
}
