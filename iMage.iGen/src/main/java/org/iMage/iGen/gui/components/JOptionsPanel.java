package org.iMage.iGen.gui.components;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;

/**
 * Concrete Implementation of a JCustomPanel, which uses the BoxLayout.
 * This panel will have a invisible border, adding a given padding to the panel.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public class JOptionsPanel extends JCustomPanel {

    private final int layoutDirection;

    /**
     * @param layoutDirection is the layoutType for the BoxLayout.
     * @param padding contains the values for the spacing to the panel-border.
     */
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

    /**
     * In this specific case, there is also added a glue between all the components,
     * which will cause them to be evenly spaced.
     * @param components are JComponents, which will be added to the panel.
     */
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