package org.iMage.iGen.listener;

import org.iMage.iGen.gui.IGenGUI;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Listener implementation for the IGenGUI, with the purpuse to control the change between cardlayouts.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public class FrameItemListener implements ItemListener {

    private final IGenGUI gui;

    /**
     * @param gui is the instance of the IGenGUI-class.
     */
    public FrameItemListener(IGenGUI gui) {
        this.gui = gui;
    }

    /**
     * Checking from which comboBox the change is coming and then changing the layout to the selected menu.
     * @param e is the ItemEvent.
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == gui.keyingModeComboBox) {
            CardLayout layout = (CardLayout) gui.keyingConfigPanel.getLayout();
            layout.show(gui.keyingConfigPanel, (String) e.getItem());
        } else if (e.getSource() == gui.enhanceModeComboBox) {
            CardLayout layout = (CardLayout) gui.enhanceConfigPanel.getLayout();
            layout.show(gui.enhanceConfigPanel, (String) e.getItem());
        }
    }
}
