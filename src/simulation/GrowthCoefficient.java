package simulation;

import staticConstantInit.StaticConstantInit;

import java.util.HashMap;
import java.util.Map;

public class GrowthCoefficient
{
    public Station stationFrom;
    public Station stationTo;
    public double coefficient;
    public Station[] shortestWay;
    public Station[] destinationsList;
    public Station[] sortStationsList;
    public TrainFactory[] routesList;

    public GrowthCoefficient(Station stationFrom, Station stationTo, double coefficient)
    {
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.coefficient = coefficient;
        stationFrom.growthCoefficients.put(stationTo, this);
    }

    protected void setShortestWay(){
        shortestWay = StaticConstantInit.graphs.findShortestRoute(stationFrom.name,stationTo.name);
    }

    protected void setDestinationsList(){
        Station[] stations = new Station[shortestWay.length];
        int counter = 1;
        for (Station station : shortestWay)
        {
            if(station.isYard){
                stations[counter-1] = station;
                counter++;
            }
        }
        destinationsList = new Station[counter+1];
        destinationsList[counter] = stationTo;
        destinationsList[0] = stationFrom;
        for (int k = 0; k < counter-1; k++) {
            destinationsList[k+1] = stations[k];
        }
    }

    protected void setRoutesListAndSortStationsList(){
        int counter = 0;
        TrainFactory[] routes = new TrainFactory[destinationsList.length];
        Station[] stations = new Station[destinationsList.length];
        Station[] restDestinations = destinationsList;

        for(int i = 0; i < destinationsList.length; i++) {
            HashMap<Object,TrainFactory> result = findBestRoute(restDestinations);
            int endIndex = 0;
            TrainFactory trainFactory = null;
            for(Map.Entry<Object,TrainFactory> entry : result.entrySet()) {
                endIndex = (int)entry.getKey()-1;
                trainFactory = entry.getValue();
            }
            if(endIndex == 0){break;}
            routes[counter] = trainFactory;
            stations[counter] = restDestinations[endIndex];

            Station[] newDestinations = new Station[restDestinations.length - endIndex];
            for(int k = 0; k < restDestinations.length - endIndex; k++){
                newDestinations[k] = restDestinations[k+endIndex];
            }
            restDestinations = newDestinations;
            counter++;
        }

        routesList = new TrainFactory[counter];
        if(counter > 1)
        {
        sortStationsList = new Station[counter-1];
        }
        else sortStationsList = null;
        for(int i = 0; i < counter; i++){
            routesList[i] = routes[i];
            if(i<counter-1)
            {
                sortStationsList[i] = stations[i];
            }
        }
        for (TrainFactory trainFactory : routesList)
        {
            System.out.println(trainFactory.name);
        }
        for (Station station : sortStationsList)
        {
            System.out.println(station.name);
        }


    }

    protected HashMap<Object,TrainFactory> findBestRoute(Station[] restDestinations)
    {
        HashMap<Object,TrainFactory> hm = new HashMap<>();
        for (TrainFactory trainFactory : StaticConstantInit.wayScheme.trainFactories.values())
        {
            int quality = 0;
            int beginning = -1;
            for (int destination_slider = 0; destination_slider < restDestinations.length; destination_slider++)
            {
                Boolean isInOrder = false;
                for (int route_slider = 0; route_slider < trainFactory.route.length; route_slider++)
                {
                    if (trainFactory.route[route_slider].name.equals(restDestinations[destination_slider].name))
                    {
                        if(route_slider > beginning)
                        {
                            beginning = route_slider;
                            quality++;
                            isInOrder = true;
                        }
                    }
                }
                if(!isInOrder){break;}
            }
            hm.put(quality,trainFactory);
        }
        //
        if(restDestinations[0].isYard){
            Station[] stations = new Station[restDestinations.length-1];
            for(int i = 0; i< restDestinations.length-1; i++){
                stations[i] = restDestinations[i+1];
            }
            restDestinations = stations;
            for (TrainFactory trainFactory : StaticConstantInit.wayScheme.trainFactories.values())
            {
                int quality = 1;
                int beginning = -1;
                for (int destination_slider = 0; destination_slider < restDestinations.length; destination_slider++)
                {
                    Boolean isInOrder = false;
                    for (int route_slider = 0; route_slider < trainFactory.route.length; route_slider++)
                    {
                        if (trainFactory.route[route_slider].name.equals(restDestinations[destination_slider].name))
                        {
                            if(route_slider > beginning)
                            {
                                beginning = route_slider;
                                quality++;
                                isInOrder = true;
                            }
                        }
                    }
                    if(!isInOrder){break;}
                }
                hm.put(quality,trainFactory);
            }
        }

        int max = -1;
        for (Object quality : hm.keySet())
        {
            if((int)quality > max){max = (int)quality;}
        }
        HashMap<Object,TrainFactory> result = new HashMap<>();
        result.put(max,hm.get(max));
        return result;
    }

    public void calculateLoadRoute(){
        setShortestWay();
        setDestinationsList();
        if(stationFrom.name.equals("B14") && stationTo.name.equals("G1")){
            setRoutesListAndSortStationsList();
        }
    }

}
