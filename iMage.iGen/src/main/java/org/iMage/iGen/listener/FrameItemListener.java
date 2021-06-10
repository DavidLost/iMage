package org.iMage.iGen.listener;

import org.iMage.iGen.gui.IGenGUI;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FrameItemListener implements ItemListener {

    private final IGenGUI gui;

    public FrameItemListener(IGenGUI gui) {
        this.gui = gui;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout layout = (CardLayout) gui.keyingConfigPanel.getLayout();
        layout.show(gui.keyingConfigPanel, (String) e.getItem());
    }
}
