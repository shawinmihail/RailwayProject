package gui;

import staticConstantInit.StaticConstantInit;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    JPanel mainPanel = new JPanel(new GridLayout(0,2));
    GraphPanel graphPanel = new GraphPanel(new BorderLayout());
    ViewAndControlPanel viewAndControlPanel = new ViewAndControlPanel(new GridLayout(2,0));

   public MainWindow() {

       this.getContentPane().add(mainPanel);
       this.setLocationByPlatform(true);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       mainPanel.setPreferredSize(new Dimension(1000,700));
       mainPanel.add(viewAndControlPanel);
       mainPanel.add(graphPanel);
    }

}
