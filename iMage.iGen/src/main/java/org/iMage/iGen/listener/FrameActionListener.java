package org.iMage.iGen.listener;

import org.iMage.iGen.gui.IGenGUI;
import org.iMage.iGen.utils.ImageUtils;
import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.LumaKeying;
import org.iMage.screengen.TextEnhancement;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImageEnhancement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Listener implementation of the IGenGUI, which contains most logic what happens when buttons are clicked.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public class FrameActionListener implements ActionListener {

    public static final int MAGIC_NUMBER = 420;
    private final IGenGUI gui;

    /**
     * @param gui is the instance of the IGenGUI-class.
     */
    public FrameActionListener(IGenGUI gui) {
        this.gui = gui;
    }

    /**
     * Determine what's the source the ActionEvent comes from.
     * @param e is the ActionEvent.
     */
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

    /**
     * When the loadInputImage-button is clicked, a Filechooser will ask you to select a image.
     * If a valid image was selected, the inputImage is updated and displayed.
     */
    private void onLoadInputImage() {
        BufferedImage image = ImageUtils.getImageViaFileChooser();
        if (image != null) {
            gui.inputImage = image;
            gui.updateInputImageLabel();
        }
    }

    /**
     * When the applyKeying-button is clicked, it is determined which keying-mode is active.
     * Then all keying parameters are beeing approved and finally the keying will be applied and the outputImage
     * will be updated.
     */
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
        assert keying != null;
        updateOutputImage(ImageUtils.screenImageToBufferedImage(
                keying.process(new BufferedScreenImage(gui.inputImage))));
    }

    /**
     * When the loadInputImage-button is clicked, a filechooser will ask you to select a image.
     * If a valid image was selected, the inputImage is updated and displayed.
     */
    private void onGetNewKeyingColor() {
        Color color = JColorChooser.showDialog(gui,
                "Colorchooser", gui.keyingColorSelectButton.getBackground());
        if (color == null) return;
        gui.keyingColor = color;
        gui.keyingColorSelectButton.setBackground(color);
        if (color.getRed() + color.getGreen() + color.getBlue() > MAGIC_NUMBER) {
            gui.keyingColorSelectButton.setForeground(Color.BLACK);
        } else gui.keyingColorSelectButton.setForeground(Color.WHITE);
    }

    /**
     * When the revert-button is clicked, the last keying respectively enhancement will be undone.
     * If there exists no previous action, nothing will happen.
     */
    private void onRevertLastStep() {
        if (gui.outputImageHistory.size() == 0) return;
        gui.outputImage = gui.outputImageHistory.get(gui.outputImageHistory.size() - 1);
        gui.outputImageHistory.remove(gui.outputImageHistory.size() - 1);
        gui.updateOutputImageLabel();
    }

    /**
     * When the applyEnhancement-button is clicked, it is determined which enhancement-mode is active.
     * After checking the parameters the respective enhancement is applied and the outputImage is updated.
     */
    private void onApplyEnhancement() {
        ScreenImageEnhancement enhancement = null;
        if (Objects.equals(gui.enhanceModeComboBox.getSelectedItem(), gui.ENHANCE_MODE_BACKGROUND)) {
            if (gui.backgroundImage == null) {
                JOptionPane.showMessageDialog(gui, "You probably want to select a background-image first :)",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Position pos = (Position) gui.enhancePositionComboBox1.getSelectedItem();
            enhancement = new BackgroundEnhancement(new BufferedScreenImage(gui.backgroundImage), pos);
        } else if (Objects.equals(gui.enhanceModeComboBox.getSelectedItem(), gui.ENHANCE_MODE_TEXT)) {
            Font font = new Font((String) gui.enhanceFontComboBox.getSelectedItem(),
                    Font.PLAIN, (int) gui.enhanceSizeSpinner.getValue());
            Position pos = (Position) gui.enhancePositionComboBox2.getSelectedItem();
            enhancement = new TextEnhancement(gui.enhanceTextField.getText(), font, pos);
        }
        assert enhancement != null;
        updateOutputImage(ImageUtils.screenImageToBufferedImage(
                enhancement.enhance(new BufferedScreenImage(gui.outputImage))));
    }

    /**
     * As soon as the loadBackgroundImage-button is pressed, a filechooser asks for a image.
     * If a valid image is selected, it will be set as background-image.
     */
    private void onLoadBackgroundImage() {
        BufferedImage image = ImageUtils.getImageViaFileChooser();
        if (image != null) {
            gui.backgroundImage = image;
        }
    }

    /**
     * This method is triggerd by the saveImage-button and opens a file-save dialog, which will save the image.
     */
    private void onSaveImage() {
        ImageUtils.saveImageAsPngViaFileChooser(gui.outputImage);
    }

    /**
     * If the outputImage is about to be updated, first the old outputImage is added to the history.
     * Then the actual update happens.
     * @param newImage is the new outputImage. If it is null, no update will happen.
     */
    public void updateOutputImage(BufferedImage newImage) {
        if (newImage == null) return;
        gui.outputImageHistory.add(gui.outputImage);
        gui.outputImage = newImage;
        gui.updateOutputImageLabel();
    }

}