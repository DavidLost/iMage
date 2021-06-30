package org.iMage.screengen;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

import java.awt.Color;
import java.awt.Point;

/**
 * The {@link BackgroundEnhancement} adds a background to the base image.
 * In addition, the base image can be moved to a given position, e.g. upper left corner.
 *
 * @author Paul Hoger
 * @version 1.0
 * @see Position
 */
public class BackgroundEnhancement extends EnhancmentBase implements ScreenImageEnhancement {

  /**
   * Create a new background image.
   *
   * @param background new background image
   * @param position   position of the base image on the background image
   */
  public BackgroundEnhancement(ScreenImage background, Position position) {
    super(background, position);
  }

  /**
   * Place the base image onto the background image at the given position.<br>
   * Note that the base image may be larger than the background image.
   * If so, the base image has to be scaled to the background image size
   * keeping the original width-height ratio.
   *
   * @return a combined image with the foreground image on the background image at the given position
   * @see ScreenImageEnhancement#enhance(ScreenImage)
   */
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
        Color color = new Color(scaledForeground.getColor(x, y), true);
        if (color.getAlpha() == ScreenImage.TRANSPARENT_ALPHA_CHANNEL) {
          continue;
        }

        result.setColor((int) start.getX() + x, (int) start.getY() + y,
            scaledForeground.getColor(x, y));
      }
    }

    return result;
  }
}