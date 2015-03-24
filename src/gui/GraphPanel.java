package gui;

import staticConstantInit.StaticConstantInit;

import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel
{
    public GraphPanel(LayoutManager layout)
    {
        super(layout);
        this.getBaselineResizeBehavior();
        this.add(StaticConstantInit.graphs.view);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

}
