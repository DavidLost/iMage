package org.iMage.iGen.gui.components;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

/**
 * Concrete Implementation of a JCustomPanel, which uses the BoxLayout.
 * This panel will have a visible border, also containing the given title.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public class JTitledPanel extends JCustomPanel {

    /**
     *
     * @param title is the title of the panel.
     * @param layoutDirection is the direction of the BoxLayout.
     * @param components are JComponents, which will be directly added to the panel.
     */
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