package org.iMage.iGen.listener;

import org.iMage.iGen.gui.IGenGUI;
import org.iMage.iGen.utils.ImageUtils;
import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class FrameActionListener implements ActionListener {

    private final IGenGUI gui;

    public FrameActionListener(IGenGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(gui.keyingLoadInputButton)) {
            onLoadInputImage();
        } else if (e.getSource().equals(gui.keyingApplyButton)) {
            onApplyKeying();
        } else if (e.getSource().equals(gui.keyingColorSelectButton)) {
            onGetNewKeyingColor();
        } else if (e.getSource().equals(gui.enhanceRevertButton)) {
            //TODO
        } else if (e.getSource().equals(gui.enhanceApplyButton)) {
            onApplyEnhancement();
        } else if (e.getSource().equals(gui.enhanceSaveButton)) {
            onSaveImage();
        } else if (e.getSource().equals(gui.enhanceSelectImageButton)) {
            onLoadBackgroundImage();
        }
    }

    private void onLoadInputImage() {
        BufferedImage image = ImageUtils.getImageViaFileChooser();
        if (image != null) {
            gui.inputImage = image;
            gui.updateInputImageLabel();
        }
    }

    private void onApplyKeying() {
        if (gui.keyingDistance == IGenGUI.INVALID_KEYING_DISTANCE) {
            JOptionPane.showMessageDialog(gui,
                    "Your keying distance has to be a valid non-negative double value!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ChromaKeying keying = new ChromaKeying(gui.keyingColor, gui.keyingDistance);
        gui.outputImage = ImageUtils.screenImageToBufferedImage(
                keying.process(new BufferedScreenImage(gui.inputImage)));
        gui.updateOutputImageLabel();
    }

    private void onGetNewKeyingColor() {
        Color color = JColorChooser.showDialog(gui,
                "Colorchooser", gui.keyingColorSelectButton.getBackground());
        if (color == null) return;
        gui.keyingColor = color;
        gui.keyingColorSelectButton.setBackground(color);
        if (color.getRed() + color.getGreen() + color.getBlue() > 420) {
            gui.keyingColorSelectButton.setForeground(Color.BLACK);
        }
        else gui.keyingColorSelectButton.setForeground(Color.WHITE);
    }

    private void onApplyEnhancement() {
        if (gui.backgroundImage == null) {
            JOptionPane.showMessageDialog(gui, "You probably first want to select a background-image :)",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Position pos = (Position) gui.enhancePositionComboBox.getSelectedItem();
        BackgroundEnhancement bge = new BackgroundEnhancement(new BufferedScreenImage(gui.backgroundImage), pos);
        gui.outputImage = ImageUtils.screenImageToBufferedImage(
                bge.enhance(new BufferedScreenImage(gui.outputImage)));
        gui.updateOutputImageLabel();
    }

    private void onLoadBackgroundImage() {
        BufferedImage image = ImageUtils.getImageViaFileChooser();
        if (image != null) {
            gui.backgroundImage = image;
        }
    }

    private void onSaveImage() {
        ImageUtils.saveImageAsPngViaFileChooser(gui.outputImage);
    }

}