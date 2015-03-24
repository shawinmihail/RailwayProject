package calculation;

import simulation.Station;
import simulation.TrainFactory;
import staticConstantInit.StaticConstantInit;

public class Calculation
{

    double storage_price = StaticConstantInit.storagePrice;
    double train_price = StaticConstantInit.transportPrice;

    public double calculatePeriod(Station[] route){
        double x = 0;
        for (Station station : route)
        {
            for (Object coefficient : station.growthCoefficient.values())
            {
                x = x + (double)coefficient*storage_price;
            }

        }
        double period = Math.sqrt(2*train_price/x);
        return period;
    }

    public void setCalculatedPeriods(){
        for (TrainFactory trainFactory : StaticConstantInit.wayScheme.trainFactories.values())
        {
            trainFactory.calculatedPeriod = calculatePeriod(trainFactory.route);
            trainFactory.setPeriod((int)Math.round(trainFactory.calculatedPeriod));
        }

    }

}
