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
public class ChromaKeying implements Keying {

  private Color key;
  private double distance;

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
  public ChromaKeying(String keyRepresentation, double distance) {
    //throw new RuntimeException("to be implemented");

    new ChromaKeying(Color.decode(keyRepresentation), distance);
  }

  /**
   * Create a new chroma keying process.
   *
   * @param key      color key that matches the greenscreen background color
   * @param distance maximal distance to the key color
   * @throws IllegalArgumentException if the distance is negative
   */
  public ChromaKeying(Color key, double distance) {
    //throw new RuntimeException("to be implemented");

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
    throw new RuntimeException("to be implemented");
  }

  /**
   * Calculate the difference between two colors in the RGBA color space using the euclidean norm.
   *
   * @param a color a
   * @param b color b
   * @return euclidean distance between two colors in the RGBA color space
   */
  protected double colorDistance(Color a, Color b) {
    throw new RuntimeException("to be implemented");
  }

  /**
   * Get the color key that is used to replace the background with transparent pixels.
   *
   * @return color key
   */
  public Color getKey() {
    throw new RuntimeException("to be implemented");
  }
}