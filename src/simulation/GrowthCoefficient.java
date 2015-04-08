package simulation;

import staticConstantInit.StaticConstantInit;

import java.util.HashMap;

public class GrowthCoefficient
{
    public Station stationFrom;
    public Station stationTo;
    public double coefficient;
    public Station[] shortestWay;
    public HashMap<String, Station> sortStationsMap = new HashMap<>();

    public GrowthCoefficient(Station stationFrom, Station stationTo, double coefficient)
    {
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.coefficient = coefficient;
    }

    protected void setShortestWay(){
        shortestWay = StaticConstantInit.graphs.findShortestRoute(stationFrom.name,stationTo.name);
    }

    protected void setDestinationsList(){
        sortStationsMap = new HashMap();
        for (Station station : shortestWay)
        {
            if(station.isYard){
                sortStationsMap.put(station.name,station);
            }
        }
    }

    public void calculateLoadRoute(){
        setShortestWay();
        setDestinationsList();
    }

}
