package simulation;

public class Load
{
    public Station destinationStation;
    public double amount;
    public Station currentDestinationStation = null;

    public Load(Station destinationStation, double amount)
    {
        this.destinationStation = destinationStation;
        this.amount = amount;
    }
}
