package org.iMage.screengen;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;

import java.awt.Color;

/**
 * The {@link PorterDuffBackgroundEnhancement} implements the Porter/Duff composition
 * for two colors.
 * It uses the source over mode.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class PorterDuffBackgroundEnhancement extends AbstractBackgroundEnhancement {

  private static final double EPS = 1e-5;

  /**
   * Create a new source over background enhancement.
   *
   * @param background new background image
   * @param position   position of the base image on the background image
   */
  public PorterDuffBackgroundEnhancement(ScreenImage background,
      Position position) {
    super(background, position);
  }

  @Override
  public Color combine(Color foreground, Color background) {
    double sourceAlpha = normalize(foreground.getAlpha());
    double destinationAlpha = normalize(background.getAlpha());

    if (sourceAlpha < EPS) {
      return background;
    }

    double outputAlpha = sourceAlpha + (1 - sourceAlpha) * destinationAlpha;

    double outputRed = 1 / outputAlpha * (sourceAlpha * normalize(foreground.getRed())
        + (1 - sourceAlpha) * destinationAlpha * normalize(background.getRed()));
    double outputGreen = 1 / outputAlpha * (sourceAlpha * normalize(foreground.getGreen())
        + (1 - sourceAlpha) * destinationAlpha * normalize(background.getGreen()));
    double outputBlue = 1 / outputAlpha * (sourceAlpha * normalize(foreground.getBlue())
        + (1 - sourceAlpha) * destinationAlpha * normalize(background.getBlue()));

    return new Color(
        (int) (outputRed * 255),
        (int) (outputGreen * 255),
        (int) (outputBlue * 255),
        (int) (outputAlpha * 255)
    );
  }

  /**
   * Normalize a color component from 0 to 255 to 0 to 1.
   *
   * @param colorComponent color component from 0 to 255
   * @return color component between 0 and 1
   */
  private double normalize(int colorComponent) {
    return colorComponent / 255.0;
  }
}