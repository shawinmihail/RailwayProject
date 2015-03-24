package simulation;

public class Intersection
{
    public int intersectionId;
    public Station firstStation;
    public Station secondStation;

    public Intersection(WayScheme wayScheme, int intersectionId, String firstName, String secondName) {
        this.intersectionId = intersectionId;

        for (Way way : wayScheme.ways.values())
        {
            for (Station station : way.stations)
            {
                if(firstName.equals(station.name)) {
                    firstStation = station;
                }
                else if(secondName.equals(station.name)) {
                    secondStation = station;
                }
            }
        }
    }

}
