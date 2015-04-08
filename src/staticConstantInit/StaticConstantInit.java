package staticConstantInit;

import db.DbProxy;
import gui.Graphs;
import gui.Gui;
import simulation.Simulation;
import simulation.WayScheme;

public class StaticConstantInit
{

    public static DbProxy dbProxy;
    public static WayScheme wayScheme;
    public static Graphs graphs;
    public static Simulation simulation;
    public static Gui gui;

    public static double storagePrice;
    public static double transportPrice;

    public StaticConstantInit() {
        dbProxy = new DbProxy();
        wayScheme = new WayScheme();
        graphs = new Graphs();
        wayScheme.calculateLoadsRoutes();
        simulation = new Simulation();
        gui = new Gui();
    }

    public static void setStoragePrice(double storagePrice)
    {
        StaticConstantInit.storagePrice = storagePrice;
    }

    public static void setTransportPrice(double transportPrice)
    {
        StaticConstantInit.transportPrice = transportPrice;
    }
}
