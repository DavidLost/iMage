package org.iMage.screengen.util;

import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.ScreenImage;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * This utility class provides helpful methods for images
 * to reduce duplicated code in test classes.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public final class ImageUtility {

  /**
   * Prevent creation of instances.
   */
  private ImageUtility() {

  }

  /**
   * Load an image from the resources directory.<br>
   * If loading the image fails, the test will fail as well.
   *
   * @param path image path including extension, has to start with {@code /}
   * @return loaded image
   */
  public static ScreenImage loadImage(String path) {
    try {
      InputStream inputStream = ImageUtility.class.getResourceAsStream(path);
      if (inputStream == null) {
        fail("Cannot find image.");
      }

      return new BufferedScreenImage(ImageIO.read(inputStream));
    } catch (IOException e) {
      fail("Failed to load image.", e);
      return null;
    }
  }

  /**
   * Fill an image with a given color.
   *
   * @param image image
   * @param color new image color
   */
  public static void fillImage(ScreenImage image, Color color) {
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        image.setColor(x, y, color.getRGB());
      }
    }
  }
}