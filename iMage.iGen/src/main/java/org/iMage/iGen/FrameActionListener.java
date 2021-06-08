package org.iMage.iGen;

import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FrameActionListener implements ActionListener {

    Main main;

    FrameActionListener(Main main) {
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(main.keyingLoadInputButton)) {
            onLoadInputImage();
        } else if (e.getSource().equals(main.keyingApplyButton)) {
            onApplyKeying();
        } else if (e.getSource().equals(main.keyingColorSelectButton)) {
            onGetNewKeyingColor();
        } else if (e.getSource().equals(main.enhanceRevertButton)) {
            //TODO
        } else if (e.getSource().equals(main.enhanceApplyButton)) {
            onApplyEnhancement();
        } else if (e.getSource().equals(main.enhanceSaveButton)) {
            //TODO
        } else if (e.getSource().equals(main.enhanceSelectImageButton)) {
            onLoadBackgroundImage();
        }
    }

    private void onLoadInputImage() {
        BufferedImage image = getImageFromFileChooser();
        if (image != null) {
            main.inputImage = image;
            main.updateInputImageLabel();
        }
    }

    private void onApplyKeying() {
        if (main.keyingDistance == Main.INVALID_KEYING_DISTANCE) {
            JOptionPane.showMessageDialog(main,
                    "Your keying distance has to be a valid non-negative double value!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ChromaKeying keying = new ChromaKeying(main.keyingColor, main.keyingDistance);
        main.outputImage = GraphicUtils.screenImageToBufferedImage(keying.process(new BufferedScreenImage(main.inputImage)));
        main.updateOutputImageLabel();
    }

    private void onGetNewKeyingColor() {
        Color color = JColorChooser.showDialog(main,
                "Colorchooser", main.keyingColorSelectButton.getBackground());
        if (color == null) return;
        main.keyingColor = color;
        main.keyingColorSelectButton.setBackground(color);
        if (color.getRed() + color.getGreen() + color.getBlue() > 420) {
            main.keyingColorSelectButton.setForeground(Color.BLACK);
        }
        else main.keyingColorSelectButton.setForeground(Color.WHITE);
    }

    private void onApplyEnhancement() {
        Position pos = (Position) main.enhancePositionBox.getSelectedItem();
        BackgroundEnhancement bge = new BackgroundEnhancement(new BufferedScreenImage(main.backgroundImage), pos);
        main.outputImage = GraphicUtils.screenImageToBufferedImage(bge.enhance(new BufferedScreenImage(main.outputImage)));
    }

    private void onLoadBackgroundImage() {
        BufferedImage image = getImageFromFileChooser();
        if (image != null) {
            main.backgroundImage = image;
        }
    }


    private BufferedImage getImageFromFileChooser() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Images, only png, jpg or jpeg", "png", "jpg", "jpeg"));
        fc.setAcceptAllFileFilterUsed(false);
        int result = fc.showOpenDialog(main);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selected = fc.getSelectedFile();
            BufferedImage image = null;
            try {
                image = ImageIO.read(selected);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(main, "Something went wrong loading the file!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            return image;
        }
        return null;
    }

}
