package org.iMage.screengen;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * This class tests the implementation of {@link ChromaKeying#colorDistance(Color, Color)}.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class ColorDistanceTest {

  private static final double EPS = 0.00001;

  @Test
  void testAlphaChannel() {
    Color green = new Color(0, 255, 0, 121);
    Color transparentGreen = new Color(0, 255, 0, 0);

    assertEquals(121, colorDistance(green, transparentGreen), EPS);
  }

  @Test
  void testLargeDistance() {
    Color black = new Color(0, 0, 0, 255);
    Color white = new Color(255, 255, 255, 255);

    assertEquals(Math.sqrt(3 * Math.pow(255, 2)), colorDistance(black, white), EPS);

    Color transparentBlack = new Color(0, 0, 0, 0);

    assertEquals(2 * Math.sqrt(Math.pow(255, 2)), colorDistance(transparentBlack, white), EPS);
  }

  @Test
  void testSmallDistance() {
    Color green = new Color(0, 156, 41, 255);
    Color nearlyGreen = new Color(0, 163, 65, 255);

    assertEquals(25, colorDistance(green, nearlyGreen), EPS);
  }

  @Test
  void testAccuracy() {
    Color green = new Color(45, 201, 83, 255);
    Color purple = new Color(84, 5, 210, 255);

    assertEquals(236.7826, colorDistance(green, purple), EPS);
  }

  @Test
  void testEqualColor() {
    Color color = new Color(42, 42, 42, 255);
    Color equalColor = new Color(color.getRGB(), true);

    assumeTrue(color.equals(equalColor));

    assertEquals(0, colorDistance(color, color), EPS);
    assertEquals(0, colorDistance(color, equalColor), EPS);
  }

  @Test
  void testCommutativity() {
    Color blue = new Color(43, 66, 165, 255);
    Color red = new Color(201, 5, 5, 255);

    assertEquals(colorDistance(blue, red), colorDistance(red, blue), EPS);
  }

  /**
   * Calculate the color distance by using {@link ChromaKeying#colorDistance(Color, Color)}.
   *
   * @param a color a
   * @param b color b
   * @return distance between a and b in the RGBA space
   */
  private double colorDistance(Color a, Color b) {
    return new ChromaKeying(new Color(0, 0, 0, 0), 0.0).colorDistance(a, b);
  }
}