package main;

import java.util.ArrayList;
import java.util.List;

public class BlackBoxLogger {
    private List<String> logs=new ArrayList<>();
    public void logDecision(int second,double tau,Maneuvre maneuvre,double battery){
        String entry=String.format("[T+%ds] Weather: Tau %.2f | Battery : %.1f | Action: %s",second,tau,battery,maneuvre);
        logs.add(entry);
        System.out.println(entry);
    }
}
