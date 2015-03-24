package simulation;

import staticConstantInit.StaticConstantInit;

import java.util.HashMap;

public class Train
    {
        public String trainFactoryName;
        public Station nextStation;
        public Station lastStation;
        public Station currentStation;
        HashMap<Object,Station> schedule;
        Station[] route;
        int creatingTime;
        public HashMap<Station,Load> loads = new HashMap<>();

        public Train(String trainFactoryName, Station[] route, HashMap<Object,Station> schedule, int creatingTime){
            this.schedule = schedule;
            this.creatingTime=creatingTime;
            this.trainFactoryName = trainFactoryName;
            this.route = route;
        }

        public void move(int currentTime)  {
            int timeInRoad = currentTime-creatingTime;
            findPosition(timeInRoad);
            uploadingAndUnloading();
        }

        protected void findPosition(int timeInRoad){
            lastStation = findLastStation(timeInRoad);
            nextStation = findNextStation(timeInRoad);
            currentStation = findCurrentStation(timeInRoad);
        }

        protected void uploadingAndUnloading() {
            if(currentStation != null){
                uploadAll();
                unloadAll();
            }
        }

        protected void uploadAll() {
            uploadShortestWayCase();
        }

        protected void unloadAll() {
            for (Load load : loads.values())
            {
                if(load.currentDestinationStation == currentStation){
                    unload(load,load.amount);
                }
            }
        }

        protected void uploadShortestWayCase() {
            for (Load load : currentStation.loads.values())
            {
                if(findCurrentDestination(load) != null) {
                    upload(load, load.amount);
                    load.currentDestinationStation = findCurrentDestination(load);
                }
            }
        }

        protected Station findCurrentDestination(Load load) {
            Station station = null;
            Station[] restRoute = getRestRoute();
            Station[] restShortestRoute = getRestShortestRoute(load);
            for(int i = 0; i < Math.min(restRoute.length,restShortestRoute.length); i++) {
                if(restRoute[i] == restShortestRoute[i] && (restRoute[i].isYard || restRoute[i] == load.destinationStation)){
                    station = restRoute[i];
                }
            }
            return station;
        }

        protected Station[] getRestRoute() {
            int nextStationIndex = 0;
            for(int i = 0; i < route.length; i++)
            {
                try
                {
                    if (route[i] == currentStation)
                    {
                        nextStationIndex = i+1;
                    }
                }
                catch (NullPointerException e){return null;}
            }
            Station[] restWay = new Station[route.length-nextStationIndex];
            for(int i = 0; i < restWay.length; i++) {
                restWay[i] = route[i+nextStationIndex];
            }
            return restWay;
        }

        protected Station[] getRestShortestRoute(Load load) {
            int nextStationIndex = 0;
            Station[] shortestRoute = StaticConstantInit.graphs.findShortestRoute(currentStation.name, load.destinationStation.name);
            for(int i = 0; i < shortestRoute.length; i++)
            {
                if (shortestRoute[i] == currentStation)
                {
                    nextStationIndex = i+1;
                }
            }
            try
            {
                Station[] restWay = new Station[shortestRoute.length - nextStationIndex];
                for(int i = 0; i < restWay.length; i++) {
                    restWay[i] = shortestRoute[i+nextStationIndex];
                }
                return restWay;
            }
            catch (IndexOutOfBoundsException e){return null;}
        }

        protected void upload(Load load, double amount) {
            addLoadToTrain(load, amount);
            removeLoadFromCurrentStation(load,amount);
        }

        protected void unload(Load load, double amount) {
            addLoadToCurrentStation(load,amount);
            removeLoadFromTrain(load,amount);
        }

        protected void addLoadToTrain(Load load, double amount) {
            if(loads.get(load.destinationStation) == null) {
                Load newLoad = new Load(load.destinationStation,load.amount);
                newLoad.currentDestinationStation = findCurrentDestination(load);
                loads.put(newLoad.destinationStation,newLoad);
            }
            else {
                loads.get(load.destinationStation).amount = loads.get(load.destinationStation).amount+amount;
            }
        }

        protected void addLoadToCurrentStation(Load load, double amount) {
            currentStation.addLoad(load,amount);
        }

        protected void removeLoadFromCurrentStation(Load load, double amount) {
            currentStation.removeLoad(load,load.amount);
        }

        protected void removeLoadFromTrain(Load load, double amount) {
            loads.get(load.destinationStation).amount = loads.get(load.destinationStation).amount-amount;
        }

        protected Station findLastStation(int timeInRoad){
            int key = -1;
            for (Object time : schedule.keySet())
            {
                if(key < (int)time && timeInRoad > (int)time) {
                    key = (int)time;
                }
            }
            return schedule.get(key);
        }

        protected Station findNextStation(int timeInRoad){
            int key = 999999;
            for (Object time : schedule.keySet())
            {
                if(key > (int)time && timeInRoad < (int)time) {
                    key = (int)time;
                }
            }
            return schedule.get(key);
        }

        protected Station findCurrentStation(int timeInRoad){
            if (schedule.get(timeInRoad) != null){
                return schedule.get(timeInRoad);
            }
            else{
                return null;
            }
        }

        protected void printTrainStatus(int creatingTime, String trainFactoryName, int timeInRoad) {
            if(creatingTime==this.creatingTime && this.trainFactoryName.equals(trainFactoryName)){
                System.out.println("time in road--"+timeInRoad);
                printLoads();
                if(lastStation != null)
                {
                    System.out.println("last station--"+lastStation.name);
                }
                if(currentStation != null)
                {
                    System.out.println("current station--" + currentStation.name);
                }
                if(nextStation != null)
                {
                    System.out.println("next station--"+nextStation.name);
                }
            }
        }

        public void printLoads() {
            if(loads != null) {
                for (Station station : loads.keySet())
                {
                    System.out.println("In " + trainFactoryName + " to " + station.name + "--" + loads.get(station).amount);
                }
            }
        }

    }

