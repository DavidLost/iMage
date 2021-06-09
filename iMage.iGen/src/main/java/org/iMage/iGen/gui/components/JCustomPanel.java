package org.iMage.iGen.gui.components;

import javax.swing.*;

public abstract class JCustomPanel extends JPanel {

    public void addAll(JComponent... components) {
        for (JComponent component : components) {
            add(component);
        }
    }
}