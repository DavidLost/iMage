package org.iMage.screengen;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

/**
 * The {@link BackgroundEnhancement} adds a background to the base image.
 * In addition, the base image can be moved to a given position, e.g. upper left corner.
 *
 * @author Paul Hoger
 * @version 1.0
 * @see Position
 */
public class BackgroundEnhancement implements ScreenImageEnhancement {

  /**
   * Create a new background enhancement.
   *
   * @param background new background image
   * @param position   position of the base image on the background image
   */
  public BackgroundEnhancement(ScreenImage background, Position position) {
    throw new RuntimeException("to be implemented");
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
    throw new RuntimeException("to be implemented");
  }
}