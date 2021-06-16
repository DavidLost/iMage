package org.iMage.iGen.listener;

import org.iMage.iGen.gui.IGenGUI;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Implementation of the KeyListener-interface, controlling the keyingDistanceField in the IGenGUI.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public class FrameKeyListener implements KeyListener {
    
    private final IGenGUI gui;

    /**
     * @param gui is the instance of the IGenGUI-class.
     */
    public FrameKeyListener(IGenGUI gui) {
        this.gui = gui;
    }
    
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { }

    /**
     * Using the keyReleased-method to check validity of the keyingDistanceField, because when keyTyped and
     * keyPressed are triggered, the keyingDistanceField-value is not updated yet.
     * @param e is the KeyEvent.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == gui.keyingDistanceField) {
            float f;
            try {
                f = Float.parseFloat(gui.keyingDistanceField.getText());
                if (f < 0) {
                    gui.keyingDistance = IGenGUI.INVALID_KEYING_DISTANCE;
                    gui.keyingDistanceField.setBackground(Color.RED);
                }
                else {
                    gui.keyingDistance = f;
                    gui.keyingDistanceField.setBackground(Color.WHITE);
                }
            } catch (NumberFormatException nfe) {
                gui.keyingDistance = IGenGUI.INVALID_KEYING_DISTANCE;
                gui.keyingDistanceField.setBackground(Color.RED);
            }
        }
    }
}