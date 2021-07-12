package org.iMage.screengen;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

import java.awt.Color;
import java.awt.Point;

/**
 * The {@link AbstractBackgroundEnhancement} adds a background to the base image.
 * In addition, the base image can be moved to a given position, e.g. upper left corner.<br>
 * Since the class is abstract, it provides the skeleton of a background enhancement.
 * The specific combination of background color and foreground color gets implemented by
 * sub classes.
 *
 * @author Paul Hoger
 * @version 1.0
 * @see Position
 */
public abstract class AbstractBackgroundEnhancement implements ScreenImageEnhancement {

  private final ScreenImage background;
  private final Position position;

  /**
   * Create a new abstract background enhancement.
   *
   * @param background new background image
   * @param position   position of the base image on the background image
   */
  public AbstractBackgroundEnhancement(ScreenImage background, Position position) {
    this.background = background;
    this.position = position;
  }

  /**
   * Calculate the combined color based on the background and foreground color.
   *
   * @param foreground foreground color
   * @param background background color
   * @return combined color, will be set on the result
   */
  public abstract Color combine(Color foreground, Color background);

  @Override
  public ScreenImage enhance(ScreenImage baseImage) {
    ScreenImage result = background.copy();

    ScreenImage scaledForeground = baseImage.copy();

    // Scale foreground image iff needed
    if (scaledForeground.getWidth() > background.getWidth()) {
      scaledForeground.scaleToWidth(background.getWidth());
    }
    if (scaledForeground.getHeight() > background.getHeight()) {
      scaledForeground.scaleToHeight(background.getHeight());
    }

    // Draw foreground onto background
    Point start = position.calculateCorner(background, scaledForeground);

    for (int x = 0; x < scaledForeground.getWidth(); x++) {
      for (int y = 0; y < scaledForeground.getHeight(); y++) {
        int backgroundX = (int) start.getX() + x;
        int backgroundY = (int) start.getY() + y;

        Color foregroundColor = new Color(scaledForeground.getColor(x, y), true);
        Color backgroundColor = new Color(background.getColor(backgroundX, backgroundY), true);
        Color combinedColor = combine(foregroundColor, backgroundColor);

        result.setColor(backgroundX, backgroundY, combinedColor.getRGB());
      }
    }

    return result;
  }

  /**
   * Get the background image.
   *
   * @return background image
   */
  public ScreenImage getBackground() {
    return background;
  }

  /**
   * Get the position of the foreground image.
   *
   * @return foreground image position
   */
  public Position getPosition() {
    return position;
  }
}