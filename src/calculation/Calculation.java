package calculation;

import simulation.GrowthCoefficient;
import simulation.Station;
import simulation.TrainFactory;
import simulation.Way;
import staticConstantInit.StaticConstantInit;

import java.util.Arrays;

public class Calculation
{

    double storage_price = StaticConstantInit.storagePrice;
    double train_price = StaticConstantInit.transportPrice;

    public double calculateSimplePeriod(Station[] route){
        double x = 0;
        for (Station station : route)
        {
            for (GrowthCoefficient growthCoefficient : station.growthCoefficients.values())
            {
                x = x + growthCoefficient.coefficient*storage_price;
            }

        }
        double period = Math.sqrt(2*train_price/x);
        return period;
    }

    public void setCalculatedPeriods(){
        for (TrainFactory trainFactory : StaticConstantInit.wayScheme.trainFactories.values())
        {
            //trainFactory.calculatedPeriod = calculateComplicatedPeriod(trainFactory.route);
            trainFactory.calculatedPeriod = calculateSimplePeriod(trainFactory.route);
            trainFactory.setPeriod((int)Math.round(trainFactory.calculatedPeriod));
        }

    }

    public double calculateComplicatedPeriod(Station[] route){
        double x = 0;
        for (Station station : route)
        {
            for (GrowthCoefficient growthCoefficient : station.growthCoefficients.values())
            {
                x = x + growthCoefficient.coefficient*storage_price;
            }
        }
        for (Way way : StaticConstantInit.wayScheme.ways.values())
        {
            for (Station station : way.stations)
            {
                for (GrowthCoefficient growthCoefficient : station.growthCoefficients.values())
                {
                    for (Station sort_station : growthCoefficient.sortStationsMap.values())
                    {
                        Arrays.asList(route).contains(sort_station);
                        x = x + growthCoefficient.coefficient * storage_price;
                    }

                }
            }
        }
        double period = Math.sqrt(2*train_price/x);
        return period;
    }

}
