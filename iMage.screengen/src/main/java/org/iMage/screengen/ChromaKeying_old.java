package org.iMage.screengen;

import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.ScreenImage;

import java.awt.Color;

/**
 * An implementation of the chroma keying process.<br>
 * The process sets a pixel transparent iff the distance of the color to the key color
 * is smaller than or equal to the given distance.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class ChromaKeying_old implements Keying {

  private final Color key;
  private final double distance;

  /**
   * Create a new chroma keying process.
   *
   * @param keyRepresentation 24-bit hexadecimal number in RGB scheme with 8 bits per channel
   *                          (alpha is set to 255), has to start with {@code #}
   * @param distance          maximal distance to the key color
   * @throws IllegalArgumentException if the color key representation doesn't start with {@code #}
   *                                  or cannot be parsed to a color
   * @throws IllegalArgumentException if the distance is negative
   */
  public ChromaKeying_old(String keyRepresentation, double distance) {
    if (!keyRepresentation.startsWith("#")) {
      throw new IllegalArgumentException("color key representation has to start with: #");
    }
    if (distance < 0) {
      throw new IllegalArgumentException("distance can't be negative!");
    }
    try {
      this.key = Color.decode(keyRepresentation);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("invalid color key representation!");
    }
    this.distance = distance;
  }

  /**
   * Create a new chroma keying process.
   *
   * @param key      color key that matches the greenscreen background color
   * @param distance maximal distance to the key color
   * @throws IllegalArgumentException if the distance is negative
   */
  public ChromaKeying_old(Color key, double distance) {
    if (distance < 0) {
      throw new IllegalArgumentException("distance can't be negative!");
    }
    this.key = key;
    this.distance = distance;
  }

  /**
   * Apply the chroma keying process to the given input image.<br>
   * The method should not manipulate the provided image and has to return a new image reference.
   *
   * @param image input image
   * @return new image reference with applied chroma keying effect
   */
  @Override
  public ScreenImage process(ScreenImage image) {
    ScreenImage resultImage = image.copy();
    for (int x = 0; x < resultImage.getWidth(); x++) {
      for (int y = 0; y < resultImage.getHeight(); y++) {
        if (colorDistance(new Color(resultImage.getColor(x, y)), getKey()) <= distance) {
          resultImage.setColor(x, y, ScreenImage.TRANSPARENT_ALPHA_CHANNEL);
        }
      }
    }
    return resultImage;
  }

  /**
   * Calculate the difference between two colors in the RGBA color space using the euclidean norm.
   *
   * @param a color a
   * @param b color b
   * @return euclidean distance between two colors in the RGBA color space
   */
  protected double colorDistance(Color a, Color b) {
    return Math.sqrt(nativeSquare(a.getRed() - b.getRed())
            + nativeSquare(a.getGreen() - b.getGreen())
            + nativeSquare(a.getBlue() - b.getBlue())
            + nativeSquare(a.getAlpha() - b.getAlpha())
    );
  }

  private int nativeSquare(int a) {
    return a * a;
  }

  /**
   * Get the color key that is used to replace the background with transparent pixels.
   *
   * @return color key
   */
  public Color getKey() {
    return key;
  }

  /**
   * Get the distance that is used to determine the amount of deviation, for replacing pixels.
   *
   * @return distance
   */
  public double getDistance() {
    return distance;
  }
}