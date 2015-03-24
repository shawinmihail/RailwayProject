package gui;

import javax.swing.*;
import java.awt.*;

public class ViewAndControlPanel extends JPanel
{

    ViewPanel viewPanel = new ViewPanel();
    ControlPanel controlPanel = new ControlPanel(new GridLayout(3,0));

    public ViewAndControlPanel(LayoutManager layout)
    {
        super(layout);
        this.add(controlPanel);
        this.add(viewPanel);
    }
}
