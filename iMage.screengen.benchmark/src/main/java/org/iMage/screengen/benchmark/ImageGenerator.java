package org.iMage.screengen.benchmark;

import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.ScreenImage;

import java.awt.Color;
import java.util.Random;

/**
 * The {@link ImageGenerator} generates random images that can be used to evaluate the processes.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class ImageGenerator {

  public static final int RGBA_BOUNDARY = 256;

  private final Random rnd;

  /**
   * Create a image generator with a given {@link Random} seed.
   *
   * @param seed initial seed
   */
  public ImageGenerator(long seed) {
    rnd = new Random(seed);
  }

  /**
   * Generate a random image with given width and height.<br>
   * In comparison to {@link #generate(int, int)}, an additional color is given.
   * For each pixel, either the <code>baseColor</code> or a random color is set.
   *
   * @param width     image width
   * @param height    image height
   * @param baseColor about every second pixel has this color
   * @return random generated image
   * @see #generate(int, int)
   */
  public ScreenImage generate(int width, int height, Color baseColor) {
    ScreenImage rndImage = new BufferedScreenImage(width, height);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int r = rnd.nextInt(RGBA_BOUNDARY);
        int g = rnd.nextInt(RGBA_BOUNDARY);
        int b = rnd.nextInt(RGBA_BOUNDARY);
        int a = rnd.nextInt(RGBA_BOUNDARY);
        Color color = rnd.nextBoolean() ? baseColor : new Color(r, g, b, a);
        rndImage.setColor(x, y, color.getRGB());
      }
    }
    return rndImage;
  }

  /**
   * Generate a random image with given width and height.<br>
   * Each pixel will have a random color.
   * To construct a random color, each color channel value (red, green, blue, alpha)
   * has to be chosen randomly between 0 and 255.
   *
   * @param width  image width
   * @param height image height
   * @return random generated image
   * @see Color#Color(int, int, int, int)
   */
  public ScreenImage generate(int width, int height) {
    ScreenImage rndImage = new BufferedScreenImage(width, height);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int r = rnd.nextInt(RGBA_BOUNDARY);
        int g = rnd.nextInt(RGBA_BOUNDARY);
        int b = rnd.nextInt(RGBA_BOUNDARY);
        int a = rnd.nextInt(RGBA_BOUNDARY);
        rndImage.setColor(x, y, new Color(r, g, b, a).getRGB());
      }
    }
    return rndImage;
  }
}