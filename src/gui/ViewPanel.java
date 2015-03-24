package gui;

import org.jfree.chart.ChartPanel;
import simulation.TrainFactory;
import staticConstantInit.StaticConstantInit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ViewPanel extends JTabbedPane
{

    JTable resultsTable;

    public ViewPanel() {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        createResultsPane();
    }

    protected void createResultsPane() {
        JPanel resultPane = new JPanel(new BorderLayout());
        resultPane.setBorder(new EmptyBorder(10,10,10,10));
        resultPane.add(createResultsTableScroll(), BorderLayout.CENTER);
        this.addTab("Results", resultPane);
    }

    protected JScrollPane createResultsTableScroll() {
        createResultsTable();
        JScrollPane routeTableScroll = new JScrollPane(resultsTable);
        resultsTable.setFillsViewportHeight(true);
        return routeTableScroll;
    }

    protected void createResultsTable(){
        String[] columnNames = {"Param","Value"};
        Object[][] data = new Object[99][2];
        data[0][0] = "number of iterations";
        data[1][0] = "total costs";
        data[2][0] = "storage costs";
        data[3][0] = "train costs";
        data[4][0] = "avg total costs";
        data[5][0] = "avg storage costs";
        data[6][0] = "avg train costs";
        data[7][0] = "trains was launched";
        resultsTable = new JTable(data,columnNames);

        resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent event)
            {
                try
                {
                }
                catch (NullPointerException e){}
            }
        });
    }

    protected void refreshResultsTable() {
        clearRouteTablePeriods();
        String numberOfIterations = null;
        double totalCosts = 0;
        double storageCosts = 0;
        double transportCosts = 0;
        double avgTotalCosts = 0;
        double avgStorageCosts = 0;
        double avgTrainCosts = 0;
        int trainsWasLaunched = 0;
        try
        {
            numberOfIterations = StaticConstantInit.gui.mainWindow.viewAndControlPanel.controlPanel.iterationsNumberField.getText();
            totalCosts = StaticConstantInit.simulation.history.getTotalCosts();
            transportCosts = StaticConstantInit.simulation.history.getTotalTransportCosts();
            storageCosts = StaticConstantInit.simulation.history.getTotalStorageCosts();
        }
        catch (Exception e){};
        resultsTable.getModel().setValueAt(numberOfIterations,0,1);
        resultsTable.getModel().setValueAt(totalCosts,1,1);
        resultsTable.getModel().setValueAt(storageCosts,2,1);
        resultsTable.getModel().setValueAt(transportCosts,3,1);
    }

    protected void clearRouteTablePeriods() {
        int rowCount = resultsTable.getRowCount();
        for(int i = 0; i < rowCount; i++){
            resultsTable.getModel().setValueAt(null,i,1);
        }
    }

}
