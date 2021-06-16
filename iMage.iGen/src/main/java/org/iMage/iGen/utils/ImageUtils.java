package org.iMage.iGen.utils;

import org.iMage.screengen.base.ScreenImage;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class contains various useful methods for image-processing f.e. resizing and filling canvas.
 * It is also capable of generating blank images and opening/saving images via dialog.
 */
public abstract class ImageUtils {

    /**
     * This method resizes a given image to its new dimension.
     *
     * @param image is the input image
     * @param width is the new width
     * @param height is the new height
     * @return the resized image
     */
    public static BufferedImage resize(BufferedImage image, int width, int height) {
        checkWidthAndHeightNotZero(width, height);
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    /**
     * This method resizes a given image to its new dimension, but keeping its relation.
     * So the image will afterwards be exactly as big, to fit in to the new dimension without overlaping borders.
     *
     * @param image is the input image
     * @param width is the new width
     * @param height is the new height
     * @return the resized image
     */
    public static BufferedImage resizeKeepRelation(BufferedImage image, int width, int height) {
        checkWidthAndHeightNotZero(width, height);
        double widthFactor = width / (double) image.getWidth();
        double heightFactor = height / (double) image.getHeight();
        double factor = Math.min(widthFactor, heightFactor);
        return resize(image, (int) Math.round(image.getWidth() * factor), (int) Math.round(image.getHeight() * factor));
    }

    /**
     * This method will take a picture and return a new one with the given dimension, while filling up so far not
     * existing pixels with the fill-color and croping pixels from the orginal-image, if its to large.
     * The croping and filling is oriented at the center of the image.
     *
     * @param image is the input image
     * @param width is the new width
     * @param height is the new height
     * @return the croped image
     */
    public static BufferedImage canvasCentered(BufferedImage image, int width, int height, Color fillColor) {
        checkWidthAndHeightNotZero(width, height);
        int widthDiff = width - image.getWidth();
        int heightDiff = height - image.getHeight();
        int sx1 = 0;
        int sy1 = 0;
        int sx2 = image.getWidth();
        int sy2 = image.getHeight();
        int dx1 = 0;
        int dy1 = 0;
        int dx2 = width;
        int dy2 = height;
        if (widthDiff < 0) {
            sx1 = -widthDiff / 2;
            sx2 = image.getWidth() + widthDiff / 2;
        }
        else if (widthDiff > 0) {
            dx1 = widthDiff / 2;
            dx2 = width - widthDiff / 2;
        }
        if (heightDiff < 0) {
            sy1 = -heightDiff / 2;
            sy2 = image.getHeight() + heightDiff / 2;
        }
        else if (heightDiff > 0) {
            dy1 = heightDiff / 2;
            dy2 = height - heightDiff / 2;
        }
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, fillColor, null);
        g.setColor(fillColor);
        if (widthDiff > 0) {
            g.fillRect(0, 0, dx1, height);
            g.fillRect(dx2, 0, width, height);
        }
        if (heightDiff > 0) {
            g.fillRect(0, 0, width, dy1);
            g.fillRect(0, dy2, width, height);
        }
        g.dispose();
        return resizedImage;
    }

    /**
     * This method combines the resizeKeepRelation and canvasCentered methods.
     */
    public static BufferedImage resizeKeepRelationAndFill(BufferedImage image, int width, int height, Color fillColor) {
        return canvasCentered(resizeKeepRelation(image, width, height), width, height, fillColor);
    }

    public static BufferedImage screenImageToBufferedImage(ScreenImage image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                bufferedImage.setRGB(i, j, image.getColor(i, j));
            }
        }
        return bufferedImage;
    }

    /**
     * This metod generates a blank image from the 3 given parameters.
     *
     * @param width is the witdh of the image
     * @param height is the height of the image
     * @param fillColor is the color of the image.
     * @return the generated image
     */
    public static BufferedImage getBlankImage(int width, int height, Color fillColor) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.setRGB(i, j, fillColor.getRGB());
            }
        }
        return image;
    }

    /**
     * Opens a file-open-dialog and returns the selected file, if approved, otherwise return null.
     */
    public static BufferedImage getImageViaFileChooser() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Images, only png, jpg or jpeg", "png", "jpg", "jpeg"));
        fc.setAcceptAllFileFilterUsed(false);
        int result = fc.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) return null;
        File selected = fc.getSelectedFile();
        BufferedImage image = null;
        try {
            image = ImageIO.read(selected);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong loading the file!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return image;
    }

    /**
     * This method saves a given image as png-file via a save-dialog.
     * It catches exceptions, always saves as png and also checks for overwriting.
     */
    public static void saveImageAsPngViaFileChooser(BufferedImage image) {
        JFileChooser fc = new JFileChooser();
        int result = fc.showSaveDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) return;
        File file = fc.getSelectedFile();
        if (!file.getName().endsWith(".png")) {
            file = new File(file.getAbsolutePath() + ".png");
        }
        if (file.exists()) {
            int overwrite = JOptionPane.showConfirmDialog(null,
                    "This file already exists! Do you want to overwrite it?");
            if (overwrite != JOptionPane.OK_OPTION) return;
        }
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong saving the image!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Check wether width or height are zero, to throw a exception.
     */
    private static void checkWidthAndHeightNotZero(int width, int height) {
        if (width == 0) throw new IllegalArgumentException("width must not be zero!");
        else if (height == 0) throw new IllegalArgumentException("height must not be zero!");
    }

}