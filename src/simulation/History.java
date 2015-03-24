package simulation;

import staticConstantInit.StaticConstantInit;

import java.util.HashMap;

public class History
{
    int simulationTime;
    //<station_name<destination_name,load>>[time]
    public HashMap[] loadsHistory;

    double storage_price = StaticConstantInit.storagePrice;
    double train_price = StaticConstantInit.transportPrice;

    double totalStorageCosts;
    double totalTransportCosts;
    HashMap<String,Object> trainsLaunched = new HashMap<>();

    public double getAvgCosts()
    {
        return (totalStorageCosts+totalTransportCosts)/(StaticConstantInit.gui.getNumberOfIterations());
    }

    public double getAvgStorageCosts()
    {
        return (totalStorageCosts)/(StaticConstantInit.gui.getNumberOfIterations());
    }

    public double getAvgTransportCosts()
    {
        return (totalTransportCosts)/(StaticConstantInit.gui.getNumberOfIterations());
    }

    public HashMap<String, Object> getTrainsLaunched()
    {
        return trainsLaunched;
    }

    public History(int simulationTime){
        this.simulationTime = simulationTime;
        this.loadsHistory = new HashMap[simulationTime];
        for (TrainFactory trainFactory : StaticConstantInit.wayScheme.trainFactories.values())
        {
            trainsLaunched.put(trainFactory.name,0);
        }

    }

    public double getTotalStorageCosts()
    {
        return totalStorageCosts;
    }

    public double getTotalTransportCosts()
    {
        return totalTransportCosts;
    }

    public double getTotalCosts(){
        return totalStorageCosts + totalTransportCosts;
    }

    public void recordLoadsHistory(int time){
        HashMap<String,HashMap<String,Load>> loadsOfStation = new HashMap<>();
        for (Way way : StaticConstantInit.wayScheme.ways.values())
        {
            for (Station station : way.stations)
            {
                HashMap<String,Load> loadOfLoads = new HashMap<>();
                for (Load load : station.loads.values())
                {
                    Load newLoad = new Load(load.destinationStation,load.amount);
                    loadOfLoads.put(newLoad.destinationStation.name,newLoad);
                }
                loadsOfStation.put(station.name,loadOfLoads);
            }
        }
        calculate_storage_costs(time);
        calculate_transport_costs(time);
        loadsHistory[time] = loadsOfStation;
    }

    public double[][] getLoadsHistory(String station_name,String destination_name){
        double[][] result = new double[2][simulationTime];
        for(int time = 0; time < simulationTime; time++){
            HashMap hm1 = loadsHistory[time];
            HashMap hm2 = (HashMap)hm1.get(station_name);
            Load newLoad = (Load)hm2.get(destination_name);
            try
            {
                result[1][time] = newLoad.amount;
                result[0][time] = time;
            }
            catch (Exception e){}

        }
        return result;
    }

    protected void calculate_storage_costs(int time){
        for (Way way : StaticConstantInit.wayScheme.ways.values())
        {
            for (Station station : way.stations)
            {
                totalStorageCosts = totalStorageCosts + station.amountOfLoads()*storage_price;
            }
        }
    }

    protected void calculate_transport_costs(int time){
        for (TrainFactory trainFactory : StaticConstantInit.wayScheme.trainFactories.values())
        {
            if(trainFactory.trainIsLaunched(time)){
                totalTransportCosts = totalTransportCosts + train_price;
                trainsLaunched.put(trainFactory.name,(int)trainsLaunched.get(trainFactory.name) + 1);
            }
        }

    }

}
