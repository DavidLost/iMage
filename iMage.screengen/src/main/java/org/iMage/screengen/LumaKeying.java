package org.iMage.screengen;

import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.ScreenImage;

import java.awt.Color;

/**
 * This keying is based on the brightness values of the pixels.<br>
 * However, there is no implementation at the moment.
 * It just draws a red square in the upper left corner to indicate that the keying was applied.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class LumaKeying implements Keying {

  private final float minBrightness;
  private final float maxBrightness;

  /**
   * Create a new luma keying.
   *
   * @param minBrightness minimal brightness value
   * @param maxBrightness maximal brightness value
   */
  public LumaKeying(float minBrightness, float maxBrightness) {
    if (minBrightness > maxBrightness) {
      throw new IllegalArgumentException("Minimal value is greater than the maximal value.");
    }

    this.minBrightness = minBrightness;
    this.maxBrightness = maxBrightness;
  }

  @Override
  public ScreenImage process(ScreenImage image) {
    ScreenImage result = image.copy();

    System.out.printf("Apply luma keying with [%.3f, %.3f].%n", minBrightness, maxBrightness);

    for (int x = 0; x < Math.min(result.getWidth(), 10); x++) {
      for (int y = 0; y < Math.min(result.getHeight(), 10); y++) {
        result.setColor(x, y, Color.RED.getRGB());
      }
    }

    return result;
  }
}