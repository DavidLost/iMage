package org.iMage.iGen.listener;

import org.iMage.iGen.gui.IGenGUI;
import org.iMage.iGen.utils.ImageUtils;
import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.LumaKeying;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class FrameActionListener implements ActionListener {

    public static final int MAGIC_CONSTANT = 420;
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
            onRevertLastStep();
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
        Keying keying = null;
        if (Objects.equals(gui.keyingModeComboBox.getSelectedItem(), gui.KEYING_MODE_CHROMA)) {
            if (gui.keyingDistance == IGenGUI.INVALID_KEYING_DISTANCE) {
                JOptionPane.showMessageDialog(gui,
                        "Your keying distance has to be a valid non-negative double value!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            keying = new ChromaKeying(gui.keyingColor, gui.keyingDistance);
        } else if (Objects.equals(gui.keyingModeComboBox.getSelectedItem(), gui.KEYING_MODE_LUMA)) {
            float min = gui.keyingBrightnessMinSlider.getValue() / 100f;
            float max = gui.keyingBrightnessMaxSlider.getValue() / 100f;
            if (min > max) {
                JOptionPane.showMessageDialog(gui,
                        "Your min. brightness should not be greater thant you max. brightness!",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            keying = new LumaKeying(min, max);
        }
        if (gui.outputImageState != IGenGUI.OUTPUTIMAGE_STATE_KEYED) {
            gui.lastOutputImage = gui.outputImage;
        }
        assert keying != null;
        gui.outputImage = ImageUtils.screenImageToBufferedImage(
                keying.process(new BufferedScreenImage(gui.inputImage)));
        gui.updateOutputImageLabel();
        gui.outputImageState = IGenGUI.OUTPUTIMAGE_STATE_KEYED;
    }

    private void onGetNewKeyingColor() {
        Color color = JColorChooser.showDialog(gui,
                "Colorchooser", gui.keyingColorSelectButton.getBackground());
        if (color == null) return;
        gui.keyingColor = color;
        gui.keyingColorSelectButton.setBackground(color);
        if (color.getRed() + color.getGreen() + color.getBlue() > MAGIC_CONSTANT) {
            gui.keyingColorSelectButton.setForeground(Color.BLACK);
        }
        else gui.keyingColorSelectButton.setForeground(Color.WHITE);
    }

    private void onRevertLastStep() {
        if (gui.outputImageState == IGenGUI.OUTPUTIMAGE_STATE_DEFAULT) return;
        gui.outputImage = gui.lastOutputImage;
        gui.updateOutputImageLabel();
        if (gui.outputImageState == IGenGUI.OUTPUTIMAGE_STATE_KEYED) {
            gui.outputImageState = IGenGUI.OUTPUTIMAGE_STATE_DEFAULT;
        } else if (gui.outputImageState == IGenGUI.OUTPUTIMAGE_STATE_ENHANCED) {
            gui.lastOutputImage = ImageUtils.getBlankImage(IGenGUI.IMAGE_MAX_WIDTH, IGenGUI.IMAGE_MAX_HEIGHT, Color.WHITE);
            gui.outputImageState = IGenGUI.OUTPUTIMAGE_STATE_KEYED;
        }
    }

    private void onApplyEnhancement() {
        if (gui.backgroundImage == null) {
            JOptionPane.showMessageDialog(gui, "You probably want to select a background-image first :)",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Position pos = (Position) gui.enhancePositionComboBox.getSelectedItem();
        BackgroundEnhancement bge = new BackgroundEnhancement(new BufferedScreenImage(gui.backgroundImage), pos);
        if (gui.outputImageState != IGenGUI.OUTPUTIMAGE_STATE_ENHANCED) {
            gui.lastOutputImage = gui.outputImage;
        }
        gui.outputImage = ImageUtils.screenImageToBufferedImage(
                bge.enhance(new BufferedScreenImage(gui.outputImage)));
        gui.updateOutputImageLabel();
        gui.outputImageState = IGenGUI.OUTPUTIMAGE_STATE_ENHANCED;
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