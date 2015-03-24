package simulation;

public class Way
{

    public int wayId;
    public String name;
    public Station[] stations;

    public Way(int wayId,String name)
    {
        this.wayId = wayId;
        this.name = name;
    }

    public void setWay(int wayLength){
        stations = new Station[wayLength];
    }

    public void addStation(Station station) {
        stations[station.stationIndex] = station;
    }

}
