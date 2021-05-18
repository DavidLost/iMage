package org.iMage.screengen;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;
import org.iMage.screengen.positions.CenterPosition;
import org.iMage.screengen.positions.LowerCenterPosition;
import org.iMage.screengen.positions.UpperLeftCornerPosition;
import org.iMage.screengen.positions.UpperRightCornerPosition;

import java.awt.*;
import java.io.IOException;

/**
 * The {@link BackgroundEnhancement} adds a background to the base image.
 * In addition, the base image can be moved to a given position, e.g. upper left corner.
 *
 * @author Paul Hoger
 * @version 1.0
 * @see Position
 */
public class BackgroundEnhancement implements ScreenImageEnhancement {

  private final ScreenImage background;
  private final Position position;

  /**
   * Create a new background enhancement.
   *
   * @param background new background image
   * @param position   position of the base image on the background image
   */
  public BackgroundEnhancement(ScreenImage background, Position position) {
    this.background = background;
    this.position = position;
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
    if (baseImage.getWidth() > background.getWidth()) {
      double scaleFactor = (double) background.getWidth() / baseImage.getWidth();
      baseImage.scaleToWidth(background.getWidth());
      baseImage.scaleToHeight((int) Math.round(baseImage.getHeight() * scaleFactor));
    }
    if (baseImage.getHeight() > background.getHeight()) {
      double scaleFactor = (double) background.getHeight() / baseImage.getHeight();
      baseImage.scaleToHeight(background.getHeight());
      baseImage.scaleToWidth((int) Math.round(baseImage.getWidth() * scaleFactor));
    }
    Point startPos = position.calculateCorner(background, baseImage);
    ScreenImage backgroundCopy = background.copy();
    for (int x = 0; x < baseImage.getWidth(); x++) {
      for (int y = 0; y < baseImage.getHeight(); y++) {
        if (baseImage.getColor(x, y) != Color.TRANSLUCENT) {
          backgroundCopy.setColor(startPos.x + x, startPos.y + y, baseImage.getColor(x, y));
        }
      }
    }
    return baseImage;
  }
}