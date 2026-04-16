package main;

import java.util.*;

public class BlackBoxLogger {
    private NavigableMap<Integer,TelemetryPacket> logs;
    private Map<Maneuvre,Integer> maneuvreStats;
    public BlackBoxLogger(){
        this.logs=new TreeMap<>();
        this.maneuvreStats=new EnumMap<>(Maneuvre.class);
        for(Maneuvre m : Maneuvre.values()){
            maneuvreStats.put(m,0);
        }
    }
    public void logsDecision(int second,double tau,double battery,Maneuvre maneuvre){
        TelemetryPacket packet=new TelemetryPacket(second,tau,battery,maneuvre);
        logs.put(second,packet);
        System.out.println(packet);
        Integer cnt=maneuvreStats.get(maneuvre);
        cnt++;
        maneuvreStats.put(maneuvre,cnt);
    }
    public List<TelemetryPacket> getLogsBetween(int start,int end){
        return new ArrayList<>(logs.subMap(start,true,end,true).values());
    }
    public void printfFlightStat(){
        for(Map.Entry<Maneuvre,Integer> m:maneuvreStats.entrySet()){
            System.out.println("Maneuver "+ m.getKey()+" was called "+m.getValue()+" times");
        }
    }
}
