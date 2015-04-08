package simulation;

import db.*;
import db.executors.*;
import db.responses.*;
import java.util.HashMap;


public class WayScheme
{
    public HashMap<Object,Way> ways = new HashMap<>();
    //<firstName,intersection>
    public HashMap<Object,Intersection> intersections = new HashMap<>();
    //<name,factory>
    public HashMap<Object, TrainFactory> trainFactories = new HashMap<>();
    public HashMap<String,Object> times = new HashMap<>();

    public WayScheme()
    {
        takeWays();
        takeIntersections();
        takeGrowthCoefficients();
        takeTrainsFactories();
    }

    public void takeTrainsFactories() {
        ExecutorTrainsList executorTrainsList = new ExecutorTrainsList(DbProxy.connection);
        TrainsListResponseMessage trainsListResponseMessage = new TrainsListResponseMessage(executorTrainsList.responseMaps);

        try
        {
            for (HashMap trainMap : trainsListResponseMessage.responseMaps)
            {
                String train_name = (String) trainMap.get("name");
                int train_id = (int) trainMap.get("train_id");
                TrainFactory newTrainFactory = new TrainFactory(train_name, train_id);
                trainFactories.put(newTrainFactory.name, newTrainFactory);

                ExecutorRoute executorRoute = new ExecutorRoute(DbProxy.connection, train_name);
                RouteResponseMessage routeResponseMessage = new RouteResponseMessage(executorRoute.responseMaps);

                newTrainFactory.createRoute(routeResponseMessage.responseMaps.length);
                for (HashMap routeMap : routeResponseMessage.responseMaps)
                {
                    String station_name = (String) routeMap.get("station_name");
                    int station_index = (int) routeMap.get("station_index");
                    newTrainFactory.addRoutePoint(findStation(station_name), station_index);
                }
            }
        }
        catch (NullPointerException e){}
        takeTimes();
        generateSchedule();
    }

    protected void takeTimes() {
        ExecutorTimesList executorTimesList = new ExecutorTimesList(DbProxy.connection);
        TimesListResponseMessage timesListResponseMessage = new TimesListResponseMessage(executorTimesList.responseMaps);

        for (HashMap timeMap : timesListResponseMessage.responseMaps)
        {
            String first_station_name = (String)timeMap.get("first_station_name");
            String second_station_name = (String)timeMap.get("second_station_name");
            int time = (int)timeMap.get("time");
            times.put(first_station_name+second_station_name,time);
            times.put(second_station_name+first_station_name,time);
        }
    }

    protected void generateSchedule() {
        for (TrainFactory trainFactory : trainFactories.values())
        {
            trainFactory.generateSchedule(times);
        }
    }

    protected void takeGrowthCoefficients() {
        ExecutorLoadsGrowthList executorLoadsGrowthList = new ExecutorLoadsGrowthList(DbProxy.connection);
        LoadsGrowthListResponseMessage loadsGrowthListResponseMessage = new LoadsGrowthListResponseMessage(executorLoadsGrowthList.responseMaps);

        try
        {
            for (HashMap growthCoefficientMap : loadsGrowthListResponseMessage.responseMaps)
            {
                String stationFrom_name = (String) growthCoefficientMap.get("stationFrom_name");
                String stationTo_name = (String) growthCoefficientMap.get("stationTo_name");
                double amount = (int) growthCoefficientMap.get("amount");
                Station stationTo = findStation(stationTo_name);
                Station stationFrom = findStation(stationFrom_name);
                GrowthCoefficient growthCoefficient = new GrowthCoefficient(stationFrom, stationTo, amount);
                stationFrom.growthCoefficients.put(stationTo, growthCoefficient);
            }
        }
        catch (NullPointerException e){}
}

    public void calculateLoadsRoutes(){
        for (Way way : ways.values())
        {
            for (Station station : way.stations)
            {
                for (GrowthCoefficient growthCoefficient : station.growthCoefficients.values())
                {
                    growthCoefficient.calculateLoadRoute();
                }

            }

        }

    }

    protected void takeIntersections() {
        ExecutorIntersectionsList executorIntersectionsList = new ExecutorIntersectionsList(DbProxy.connection);
        IntersectionsListResponseMessage intersectionsListResponseMessage = new IntersectionsListResponseMessage(executorIntersectionsList.responseMaps);

        for (HashMap responseIntersection : intersectionsListResponseMessage.responseMaps)
        {
            int intId = (int)responseIntersection.get("intersection_id");
            String firstName = (String) responseIntersection.get("first_station_name");
            String secondName = (String) responseIntersection.get("second_station_name");
            Intersection newIntersection1 = new Intersection(this,intId,firstName,secondName);
            Intersection newIntersection2 = new Intersection(this,intId,secondName,firstName);
            intersections.put(newIntersection1.firstStation.name, newIntersection1);
            intersections.put(newIntersection2.firstStation.name,newIntersection2);
        }
        setIntersections();
    }

    protected void setIntersections(){
        for (Way way : ways.values())
        {
            for (Station station : way.stations)
            {
                for (Intersection intersection : intersections.values()) {
                    if(intersection.firstStation == station) {
                        station.intersections.put(intersection.intersectionId, intersection);
                        station.isYard = true;
                        if(intersection.firstStation.name.equals(station.name)){
                            station.loads = findStation(intersection.secondStation.name).loads;
                        }
                        else {
                            station.loads = findStation(intersection.firstStation.name).loads;
                        }
                    }
                }
            }
        }
    }

    protected void takeWays(){
        ExecutorWaysList executorWaysList = new ExecutorWaysList(DbProxy.connection);
        WaysListResponseMessage responseWaysList = new WaysListResponseMessage(executorWaysList.responseMaps);

        for (HashMap responseWay : responseWaysList.responseMaps)
        {
            int wayId = (int)responseWay.get("way_id");
            String wayName = (String)responseWay.get("way_name");
            Way newWay = new Way(wayId,wayName);
            ways.put(wayName,newWay);
            ExecutorStations executorStations = new ExecutorStations(DbProxy.connection,(String)responseWay.get("way_name"));
            StationsResponseMessage responseStations = new StationsResponseMessage(executorStations.responseMaps);

            newWay.setWay(responseStations.responseMaps.length);
            for (HashMap responseStation : responseStations.responseMaps)
            {
                int stationId = (int)responseStation.get("station_id");
                int stationIndex = (int)responseStation.get("station_index");
                String stationName = (String)responseStation.get("station_name");
                Station newStation = new Station(wayName,stationId,stationIndex,stationName);
                newWay.addStation(newStation);
            }
        }
    }

    public Station findStation(String station_name) {

        Station newStation = null;
        for (Way way : ways.values())
        {
            for (Station station : way.stations)
            {
                if(station.name.equals(station_name)) {
                    newStation = station;
                }
            }
        }
        return newStation;
    }

}
