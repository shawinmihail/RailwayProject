package gui;

import staticConstantInit.StaticConstantInit;

import javax.swing.*;

public class Gui
{

    public static MainWindow mainWindow = new MainWindow();

    public Gui() {
        new ViewGuiThread().start();
    }

    private static void createAndShowGUI() {

        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    public static void launch() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    class ViewGuiThread extends Thread
    {
        @Override
        public void run()
        {StaticConstantInit.gui.launch();}
    }

    public int getNumberOfIterations(){
        return Integer.parseInt(mainWindow.viewAndControlPanel.controlPanel.iterationsNumberField.getText());
    }

}
