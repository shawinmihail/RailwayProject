package simulation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TrainFactory
{

    public Station[] route;
    public String name;
    public int train_id;
    public HashMap<Object,Station> schedule = new HashMap<>();
    public int timeToArrival;
    public HashMap<Object,Train> trains = new HashMap<>();
    public int period = 1;
    public double calculatedPeriod;

    public TrainFactory(String name, int train_id)
    {
        this.name = name;
        this.train_id = train_id;
    }

    public void createRoute(int routeLength) {
        route = new Station[routeLength];
    }

    public void addRoutePoint(Station station, int stationIndex) {
        route[stationIndex] = station;
    }

    public void generateSchedule(HashMap<String,Object> times) {
        try{
        int time = 0;
        schedule.put(time,route[0]);
        for (int i = 1; i < route.length;i++)
        {
            int dTime = (int)times.get(route[i-1].name +route[i].name);
            time = time + dTime;
            schedule.put(time, route[i]);
        }
        timeToArrival = time;
        }
        catch (NullPointerException e){}
    }

    public void start(int currentTime) {
        if(Math.floorMod(currentTime+1,period) == 0 && period != -1){
            createTrain(currentTime);
        }
        moveTrains(currentTime);
        removeTrains(currentTime);
    }

    public void createTrain(int currentTime)
    {
        Train newTrain = new Train(name, route, schedule, currentTime);
        int deathTime = currentTime + timeToArrival;
        trains.put(deathTime,newTrain);
    }

    public void moveTrains(int currentTime) {
        for (Train train : trains.values())
        {
            train.move(currentTime);
        }
    }

    public void removeTrains(int currentTime)
    {
        for (Iterator<Map.Entry<Object, Train>> it = trains.entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry<Object, Train> entry = it.next();
            if (entry.getKey().equals(currentTime))
            {
                it.remove();
            }
        }
    }

    public void setPeriod(int period)
    {
        this.period = period;
    }

    public boolean trainIsLaunched(int currentTime) {
        if(Math.floorMod(currentTime+1,period) == 0 && period != -1){
            return true;
        }
        else{
            return false;
        }
    }

}
