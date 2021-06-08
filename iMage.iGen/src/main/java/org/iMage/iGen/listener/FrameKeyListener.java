package org.iMage.iGen.listener;

import org.iMage.iGen.gui.IGenGUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FrameKeyListener implements KeyListener {
    
    private final IGenGUI gui;
    
    public FrameKeyListener(IGenGUI gui) {
        this.gui = gui;
    }
    
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { }

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