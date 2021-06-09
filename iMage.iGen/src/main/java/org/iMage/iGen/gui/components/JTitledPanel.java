package org.iMage.iGen.gui.components;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class JTitledPanel extends JCustomPanel {

    public JTitledPanel(String title, int layoutDirection, JComponent... components) {
        BoxLayout layout = new BoxLayout(this, layoutDirection);
        setLayout(layout);
        setBorder(new TitledBorder(title));
        addAll(components);
    }

    public JTitledPanel(String title, int layoutDirection) {
        this(title, layoutDirection, new JComponent[0]);
    }

    public JTitledPanel(String title, JComponent... components) {
        this(title, BoxLayout.X_AXIS, components);
    }

}