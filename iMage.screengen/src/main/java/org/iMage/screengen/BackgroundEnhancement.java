package org.iMage.screengen;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

import java.awt.Point;

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
    /*
     * If the foreground with is greater than the background width, the difference factor is calculated and the
     * foreground image is scaled down to the background with and the height will also be scaled by the calculated
     * factor to keep the image in ratio.
     */
    ScreenImage resultImage = baseImage.copy();
    if (resultImage.getWidth() > background.getWidth()) {
      double scaleFactor = (double) background.getWidth() / resultImage.getWidth();
      resultImage.scaleToWidth(background.getWidth());
      resultImage.scaleToHeight((int) Math.round(resultImage.getHeight() * scaleFactor));
    }
    if (resultImage.getHeight() > background.getHeight()) {
      double scaleFactor = (double) background.getHeight() / resultImage.getHeight();
      resultImage.scaleToHeight(background.getHeight());
      resultImage.scaleToWidth((int) Math.round(resultImage.getWidth() * scaleFactor));
    }
    Point startPos = position.calculateCorner(background, resultImage);
    ScreenImage backgroundCopy = background.copy();
    //Every pixel in the resultImage, which is not transparent, will overwrite the background.
    for (int x = 0; x < resultImage.getWidth(); x++) {
      for (int y = 0; y < resultImage.getHeight(); y++) {
        if (resultImage.getColor(x, y) != ScreenImage.TRANSPARENT_ALPHA_CHANNEL) {
          backgroundCopy.setColor(startPos.x + x, startPos.y + y, resultImage.getColor(x, y));
        }
      }
    }
    return backgroundCopy;
  }
}