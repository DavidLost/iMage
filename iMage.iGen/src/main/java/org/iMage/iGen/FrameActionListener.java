package org.iMage.iGen;

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
            onLoadNewInputImage();
        } else if (e.getSource().equals(main.keyingApplyButton)) {

        } else if (e.getSource().equals(main.keyingColorSelectButton)) {
            onGetNewKeyingColor();
        }
    }

    private void onLoadNewInputImage() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Images, only png, jpg or jpeg", "png", "jpg", "jpeg"));
        fc.setAcceptAllFileFilterUsed(false);
        int result = fc.showOpenDialog(main);
        if (result == JFileChooser.APPROVE_OPTION) {
            System.out.println("approved");
            File selected = fc.getSelectedFile();
            BufferedImage image = null;
            try {
                image = ImageIO.read(selected);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            if (image != null) {
                main.inputImage = image;
                main.inputImageLabel.setIcon(new ImageIcon(GraphicUtils.canvasCentered(
                        GraphicUtils.resizeKeepRelation(image, Main.IMAGE_MAX_WIDTH, Main.IMAGE_MAX_HEIGHT),
                        Main.IMAGE_MAX_WIDTH, Main.IMAGE_MAX_HEIGHT, Color.BLACK)));
                main.outputImageLabel.setIcon(new ImageIcon(GraphicUtils.resizeKeepRelation(
                        image, Main.IMAGE_MAX_WIDTH, Main.IMAGE_MAX_HEIGHT)));
            }
        }
    }

    private void onGetNewKeyingColor() {
        Color color = JColorChooser.showDialog(main,
                "Colorchooser", main.keyingColorSelectButton.getBackground());
        main.keyingColor = color;
        main.keyingColorSelectButton.setBackground(color);
        if (color.getRed() + color.getGreen() + color.getBlue() > 420) {
            main.keyingColorSelectButton.setForeground(Color.BLACK);
        }
        else main.keyingColorSelectButton.setForeground(Color.WHITE);
    }
}
