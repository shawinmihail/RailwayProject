package gui;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import staticConstantInit.StaticConstantInit;

import javax.swing.*;
import java.awt.*;

public class Chart extends JPanel
{
    protected  XYDataset createDataSet(String stationName, String destinationName) {
        DefaultXYDataset dataSet = new DefaultXYDataset();
        double[][] data1 = StaticConstantInit.simulation.history.getLoadsHistory(stationName,destinationName);
        dataSet.addSeries("station--"+ stationName+" loads to--"+destinationName, data1);
        return dataSet;
    }

    protected void createNewChart(String stationName, String destinationName) {
        this.removeAll();
        JFreeChart chart = ChartFactory.createXYLineChart("station--"+ stationName+" loads to--"+destinationName, "time", "amount of loads", createDataSet(stationName,destinationName), PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        JScrollPane chartScroll = new JScrollPane(chartPanel);
        this.add(chartScroll);
        StaticConstantInit.gui.mainWindow.pack();
    }
}