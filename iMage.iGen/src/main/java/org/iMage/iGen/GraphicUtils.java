package org.iMage.iGen;

import org.iMage.screengen.base.ScreenImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GraphicUtils {

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        checkWidthAndHeightNotZero(width, height);
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static BufferedImage resizeKeepRelation(BufferedImage image, int width, int height) {
        checkWidthAndHeightNotZero(width, height);
        double widthFactor = width / (double) image.getWidth();
        double heightFactor = height / (double) image.getHeight();
        double factor = Math.min(widthFactor, heightFactor);
        return resize(image, (int) Math.round(image.getWidth() * factor), (int) Math.round(image.getHeight() * factor));
    }

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

    public static BufferedImage getBlankImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.setRGB(i, j, Color.WHITE.getRGB());
            }
        }
        return image;
    }

    private static void checkWidthAndHeightNotZero(int width, int height) {
        if (width == 0) throw new IllegalArgumentException("width must not be zero!");
        else if (height == 0) throw new IllegalArgumentException("height must not be zero!");
    }

}
