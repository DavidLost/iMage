package org.iMage.iGen.gui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JOptionsPanel extends JCustomPanel {

    private final int layoutDirection;

    public JOptionsPanel(int layoutDirection, Insets padding) {
        this.layoutDirection = layoutDirection;
        BoxLayout layout = new BoxLayout(this, layoutDirection);
        setLayout(layout);
        setBorder(new EmptyBorder(padding));
    }

    public JOptionsPanel(int layoutDirection) {
        this(layoutDirection, new Insets(0, 0, 0, 0));
    }

    public JOptionsPanel(Insets padding) {
        this(BoxLayout.X_AXIS, padding);
    }

    public JOptionsPanel() {
        this(BoxLayout.X_AXIS, new Insets(0, 0, 0, 0));
    }

    public void addAll(JComponent... components) {
        for (JComponent component : components) {
            add(component);
            if (component != components[components.length - 1]) {
                switch (layoutDirection) {
                    case BoxLayout.X_AXIS, BoxLayout.LINE_AXIS -> add(Box.createHorizontalGlue());
                    case BoxLayout.Y_AXIS, BoxLayout.PAGE_AXIS -> add(Box.createVerticalGlue());
                }
            }
        }
    }

}