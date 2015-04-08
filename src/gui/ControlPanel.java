package gui;

import calculation.Calculation;
import simulation.*;
import staticConstantInit.StaticConstantInit;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel
{

    String firstWayName = null;
    String firstStationName = null;

    JTable routeTable;
    JTable wayTable;
    JTable stationTable;
    JTable infoTable;
    JTextField iterationsNumberField;
    JTextField storagePriceField;
    JTextField transportPriceField;

    public ControlPanel(LayoutManager layout) {
        super(layout);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        createWaysAndStationsPanel();
        createRouteTablePanel();
        createSimulationPanel();
    }

    protected void createRouteTablePanel(){
        JPanel routeTablePanel = new JPanel(new BorderLayout());
        routeTablePanel.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3),new TitledBorder("Routes")));
        routeTablePanel.add(createRouteTableScroll(),BorderLayout.CENTER);
        this.add(routeTablePanel);
    }

    protected void createWaysAndStationsPanel() {
        JPanel waysAndStationsPanel = new JPanel(new GridLayout(0,3));
        waysAndStationsPanel.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3),new TitledBorder("Ways&Stations")));
        waysAndStationsPanel.add(createWaysTableScroll());
        waysAndStationsPanel.add(createStationsTableScroll(this.firstWayName));
        waysAndStationsPanel.add(createInfoTableScroll(this.firstStationName));
        this.add(waysAndStationsPanel);
    }

    protected JScrollPane createRouteTableScroll() {
        createRouteTable();
        JScrollPane routeTableScroll = new JScrollPane(routeTable);
        routeTable.setFillsViewportHeight(true);
        return routeTableScroll;
    }

    protected void createRouteTable(){
        String[] columnNames = {"Route","Period"};
        Object[][] routeNames = new Object[99][2];
        int i = 0;
        for (TrainFactory trainFactory : StaticConstantInit.wayScheme.trainFactories.values())
        {
            routeNames[i][0] = trainFactory.name;
            routeNames[i][1] = trainFactory.period;
            i++;
        }
        routeTable = new JTable(routeNames,columnNames);

        routeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent event)
            {
                try
                {
                    String routeName = routeTable.getValueAt(routeTable.getSelectedRow(), 0).toString();
                    StaticConstantInit.graphs.setNormalStyleWaysGraph();
                    StaticConstantInit.graphs.distinguishRoute(routeName);
                }
                catch (NullPointerException e){StaticConstantInit.graphs.setNormalStyleWaysGraph();}
            }
        });
    }

    protected void refreshRouteTable() {
        clearRouteTablePeriods();
        int i = 0;
        for (TrainFactory trainFactory : StaticConstantInit.wayScheme.trainFactories.values())
        {
            routeTable.getModel().setValueAt(trainFactory.period, i, 1);
            i++;
        }
    }

    protected void clearRouteTablePeriods() {
        int rowCount = routeTable.getRowCount();
        for(int i = 0; i < rowCount; i++){
            routeTable.getModel().setValueAt(null,i,1);
        }
    }

    protected JScrollPane createWaysTableScroll() {
        createWaysTable();
        JScrollPane wayTableScroll = new JScrollPane(wayTable);
        wayTable.setFillsViewportHeight(true);
        this.add(wayTableScroll);
        return wayTableScroll;
    }

    protected void createWaysTable(){
        String[] columnNames = {"Way"};
        Object[][] data = new Object[99][1];
        int i = 0;
        for (Way way : StaticConstantInit.wayScheme.ways.values())
        {
            if(this.firstWayName == null){this.firstWayName = way.name;}
            data[i][0] = way.name;
            i++;
        }
        wayTable = new JTable(data,columnNames);

        wayTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                try
                {
                    clearStationsTable();
                    String wayName = wayTable.getValueAt(wayTable.getSelectedRow(), 0).toString();
                    int i = 0;
                    for (Station station : StaticConstantInit.wayScheme.ways.get(wayName).stations)
                    {
                        stationTable.getModel().setValueAt(station.name, i, 0);
                        i++;
                    }
                }
                catch (Exception e){}
            }
        });
    }

    protected JScrollPane createStationsTableScroll(String wayName) {
        createStationsTable(wayName);
        JScrollPane stationTableScroll = new JScrollPane(stationTable);
        stationTable.setFillsViewportHeight(true);
        this.add(stationTableScroll);
        return stationTableScroll;
    }

    protected void createStationsTable(String wayName) {
        String[] columnNames = {"Station"};
        Object[][] data = new Object[99][1];
        int i = 0;
        firstStationName = null;
        for (Station station : StaticConstantInit.wayScheme.ways.get(wayName).stations)
        {
            if(firstStationName == null) {firstStationName = station.name;}
            data[i][0] = station.name;
            i++;
        }
        stationTable = new JTable(data,columnNames);

        stationTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                try
                {
                    clearInfoTable();
                    String stationName = stationTable.getValueAt(stationTable.getSelectedRow(), 0).toString();
                    int i = 0;
                    if(StaticConstantInit.wayScheme.findStation(stationName).growthCoefficients.keySet().size() != 0)
                    {
                        for (Station station : StaticConstantInit.wayScheme.findStation(stationName).growthCoefficients.keySet())
                        {
                            String destination = station.name;
                            double amount = (double) StaticConstantInit.wayScheme.findStation(stationName).growthCoefficients.get(station).coefficient;
                            infoTable.getModel().setValueAt("loads to" + destination + " - " + amount, i, 0);
                            i++;
                        }
                    }
                    else {
                        infoTable.getModel().setValueAt("No loads", 0, 0);
                    }
                }
                catch (Exception e) {}
            }
        });
    }

    protected void clearStationsTable(){
        int rowCount = stationTable.getRowCount();
        for(int i = 0; i < rowCount; i++){
            stationTable.getModel().setValueAt(null,i,0);
        }
    }

    protected JScrollPane createInfoTableScroll(String stationName) {
        createInfoTable(stationName);
        JScrollPane stationTableScroll = new JScrollPane(infoTable);
        infoTable.setFillsViewportHeight(true);
        this.add(stationTableScroll);
        return stationTableScroll;
    }

    protected void createInfoTable(String stationName) {
        String[] columnNames = {"Info"};
        Object[][] data = new Object[99][1];
        int i = 0;
        for (Station station : StaticConstantInit.wayScheme.findStation(stationName).growthCoefficients.keySet())
        {
            String destination = station.name;
            double amount = (double)StaticConstantInit.wayScheme.findStation(stationName).growthCoefficients.get(station).coefficient;
            data[i][0] = "loads to" + destination + " - " + amount;
            i++;
        }
        infoTable = new JTable(data,columnNames);

        infoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                try
                {
                    String stationName = infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString();
                }
                catch (Exception e){}
            }
        });
    }

    protected void clearInfoTable(){
        int rowCount = infoTable.getRowCount();
        for(int i = 0; i < rowCount; i++){
            infoTable.getModel().setValueAt(null,i,0);
        }
    }

    protected void createSimulationPanel(){
        SpringLayout layout = new SpringLayout();
        JPanel simulationPanel = new JPanel(layout);
        simulationPanel.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), new TitledBorder("Simulation")));

        iterationsNumberField = createIterationsNumberField();
        JLabel iterationsNumberLabel = new JLabel("Number of iterations");
        simulationPanel.add(iterationsNumberLabel);
        simulationPanel.add(iterationsNumberField);
        layout.putConstraint(SpringLayout.WEST, iterationsNumberLabel, 5, SpringLayout.WEST, simulationPanel);
        layout.putConstraint(SpringLayout.NORTH, iterationsNumberLabel, 5, SpringLayout.NORTH, simulationPanel);
        layout.putConstraint(SpringLayout.WEST, iterationsNumberField, 5, SpringLayout.EAST, iterationsNumberLabel);
        layout.putConstraint(SpringLayout.NORTH, iterationsNumberField, 5, SpringLayout.NORTH, simulationPanel);

        storagePriceField = createStoragePriceField();
        JLabel storagePriceLabel = new JLabel("Storage price");
        simulationPanel.add(storagePriceLabel);
        simulationPanel.add(storagePriceField);
        layout.putConstraint(SpringLayout.WEST, storagePriceLabel, 10, SpringLayout.EAST, iterationsNumberField);
        layout.putConstraint(SpringLayout.NORTH, storagePriceLabel, 5, SpringLayout.NORTH, simulationPanel);
        layout.putConstraint(SpringLayout.WEST, storagePriceField, 5, SpringLayout.EAST, storagePriceLabel);
        layout.putConstraint(SpringLayout.NORTH, storagePriceField, 5, SpringLayout.NORTH, simulationPanel);

        transportPriceField = createTransportPriceField();
        JLabel transportPriceLabel = new JLabel("Storage price");
        simulationPanel.add(transportPriceLabel);
        simulationPanel.add(transportPriceField);
        layout.putConstraint(SpringLayout.WEST, transportPriceLabel, 10, SpringLayout.EAST, storagePriceField);
        layout.putConstraint(SpringLayout.NORTH, transportPriceLabel, 5, SpringLayout.NORTH, simulationPanel);
        layout.putConstraint(SpringLayout.WEST, transportPriceField, 5, SpringLayout.EAST, transportPriceLabel);
        layout.putConstraint(SpringLayout.NORTH, transportPriceField, 5, SpringLayout.NORTH, simulationPanel);

        JButton startButton = createStartButton();
        simulationPanel.add(startButton);
        layout.putConstraint(SpringLayout.WEST, startButton, 5, SpringLayout.WEST, simulationPanel);
        layout.putConstraint(SpringLayout.SOUTH, startButton, -5, SpringLayout.SOUTH, simulationPanel);

        JButton calculateButton = createCalculationButton();
        simulationPanel.add(calculateButton);
        layout.putConstraint(SpringLayout.WEST, calculateButton, 10, SpringLayout.EAST, startButton);
        layout.putConstraint(SpringLayout.SOUTH, calculateButton, -5, SpringLayout.SOUTH, simulationPanel);

        this.add(simulationPanel);
    }

    protected JTextField createIterationsNumberField() {
        JTextField iterationsNumberField = new JTextField();
        iterationsNumberField.setColumns(3);
        iterationsNumberField.setText("500");
        return iterationsNumberField;
    }

    protected JTextField createStoragePriceField() {
        JTextField storagePriceField = new JTextField();
        storagePriceField.setColumns(3);
        storagePriceField.setText("0.1");
        return storagePriceField;
    }

    protected JTextField createTransportPriceField() {
        JTextField transportPriceField = new JTextField();
        transportPriceField.setColumns(3);
        transportPriceField.setText("3256");
        return transportPriceField;
    }

    protected JButton createStartButton() {
        JButton startButton = new JButton("start");
        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent start)
            {
                StaticConstantInit.wayScheme = new WayScheme();
                int columnCount = routeTable.getModel().getRowCount();
                for(int i = 0; i < columnCount; i++)
                {
                    if (routeTable.getModel().getValueAt(i, 0) != null)
                    {
                        String name = routeTable.getModel().getValueAt(i, 0).toString();
                        int period = Integer.parseInt(routeTable.getModel().getValueAt(i, 1).toString());
                        StaticConstantInit.wayScheme.trainFactories.get(name).setPeriod(period);
                    }
                    else {break;}
                }
                int iterationsNumber = Integer.parseInt(iterationsNumberField.getText());
                double storagePrice = Double.parseDouble(storagePriceField.getText());
                double transportPrice = Double.parseDouble(transportPriceField.getText());
                StaticConstantInit.setStoragePrice(storagePrice);
                StaticConstantInit.setTransportPrice(transportPrice);
                StaticConstantInit.simulation.start(iterationsNumber);
                showResults();
            }
        });
        return startButton;
    }

    protected void showResults() {
        StaticConstantInit.gui.mainWindow.viewAndControlPanel.viewPanel.refreshResultsTable();
    }

    protected JButton createCalculationButton() {
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                double storagePrice = Double.parseDouble(storagePriceField.getText());
                double transportPrice = Double.parseDouble(transportPriceField.getText());
                StaticConstantInit.setStoragePrice(storagePrice);
                StaticConstantInit.setTransportPrice(transportPrice);
                new Calculation().setCalculatedPeriods();
                refreshRouteTable();
            }
        });
        return calculateButton;
    }

}
