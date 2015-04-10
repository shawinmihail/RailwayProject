package simulation;

import java.util.HashMap;
import java.util.Map;

public class Station
{

    public String wayName;
    public int stationId;
    public int stationIndex;
    public String name;
    public HashMap<Object,Intersection> intersections = new HashMap<>();
    public HashMap<Station,Load> loads = new HashMap<>();
    public HashMap<Station,GrowthCoefficient> growthCoefficients = new HashMap<>();
    public boolean isYard = false;

    public Station(String wayName, int stationId,int stationIndex, String name)
    {
        this.wayName = wayName;
        this.stationId = stationId;
        this.stationIndex = stationIndex;
        this.name = name;
    }

    public void addLoad(Load load, double amount) {

        if(loads.get(load.destinationStation) == null) {
            Load newLoad = new Load(load.destinationStation,load.amount);
            newLoad.currentDestinationStation = null;
            loads.put(newLoad.destinationStation,newLoad);
        }
        else {
            loads.get(load.destinationStation).amount = loads.get(load.destinationStation).amount+amount;
        }
    }

    public void removeLoad(Load load, double amount) {
        loads.get(load.destinationStation).amount = loads.get(load.destinationStation).amount-amount;
    }

    public void printLoads() {
        if(loads != null) {
            for (Station station : loads.keySet())
            {
                System.out.println("In " + this.name + " to " + station.name + "--" + loads.get(station).amount);
            }
        }
    }

    public  void loadGrow() {
        for (GrowthCoefficient growthCoefficient : growthCoefficients.values())
        {
            Load newLoad = new Load(growthCoefficient.stationTo,growthCoefficient.coefficient);
            addLoad(newLoad,newLoad.amount);
        }
    }

    public void removeArrivedLoads() {
        for (Station station : loads.keySet())
        {
            if(station.name.equals(this.name)) {
                loads.get(station).amount = 0;
            }
        }

    }

    public double amountOfLoads(){
        double amount = 0;
        for (Load load : loads.values())
        {
            amount = amount + load.amount;
        }
        return amount;
    }

}
