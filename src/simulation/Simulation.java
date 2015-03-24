package simulation;

import staticConstantInit.StaticConstantInit;

public class Simulation
{
    int simulationTime;
    public History history;

    public void start(int simulationTime) {
        this.simulationTime = simulationTime;
        history = new History(simulationTime);
        for(int time = 0; time<simulationTime;time++){
            loadsGrowth();
            startTrainMoving(time);
            removeArrivedLoads();
            history.recordLoadsHistory(time);
        }
        //printAllLoads();
        printCosts();
    }

    protected void startTrainMoving(int time) {
        for (TrainFactory trainFactory : StaticConstantInit.wayScheme.trainFactories.values())
        {
            trainFactory.start(time);
        }
    }

    protected void loadsGrowth() {
        for (Way way : StaticConstantInit.wayScheme.ways.values())
        {
            for (Station station : way.stations)
            {
                station.loadGrow();
            }
        }
    }

    protected void removeArrivedLoads(){
        for (Way way : StaticConstantInit.wayScheme.ways.values())
        {
            for (Station station : way.stations)
            {
                station.removeArrivedLoads();
            }
        }
    }

    protected void printAllLoads() {
        for (Way way : StaticConstantInit.wayScheme.ways.values())
        {
            for (Station station : way.stations)
            {
                station.printLoads();
            }
        }
    }

    protected void printCosts() {
        System.out.println("Storage -- "+history.totalStorageCosts);
        System.out.println("Transport -- "+history.totalTransportCosts);
        System.out.println("Total -- "+(history.totalTransportCosts + history.totalStorageCosts));
    }

}

