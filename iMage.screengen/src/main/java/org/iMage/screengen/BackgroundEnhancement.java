package org.iMage.screengen;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;

import java.awt.Color;

/**
 * The {@link BackgroundEnhancement} is a naive implementation
 * of {@link AbstractBackgroundEnhancement}.
 * It combines both colors by returning the background if the foreground is fully transparent
 * (alpha value of 0), otherwise the foreground will be returned.
 *
 * @author Paul Hoger
 * @version 2.0
 */
public class BackgroundEnhancement extends AbstractBackgroundEnhancement {

  /**
   * Create a new background enhancement.
   *
   * @param background new background image
   * @param position   position of the base image on the background image
   */
  public BackgroundEnhancement(ScreenImage background, Position position) {
    super(background, position);
  }

  @Override
  public Color combine(Color foreground, Color background) {
    if (foreground.getAlpha() == ScreenImage.TRANSPARENT_ALPHA_CHANNEL) {
      return background;
    } else {
      return foreground;
    }
  }
}