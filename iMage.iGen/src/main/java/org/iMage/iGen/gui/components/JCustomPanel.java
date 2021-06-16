package org.iMage.iGen.gui.components;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Abstract helper-class, which provides a base-structure for custom panel-implementations.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public abstract class JCustomPanel extends JPanel {

    /**
     * Method, which can add multiple JComponents to the panel at once.
     * @param components are JComponents, which will be added to the panel.
     */
    public void addAll(JComponent... components) {
        for (JComponent component : components) {
            add(component);
        }
    }
}