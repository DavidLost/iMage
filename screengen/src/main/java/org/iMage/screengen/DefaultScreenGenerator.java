package org.iMage.screengen;

import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenGenerator;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;
import org.iMage.screengen.positions.CenterPosition;
import org.iMage.screengen.positions.LowerCenterPosition;
import org.iMage.screengen.positions.UpperLeftCornerPosition;
import org.iMage.screengen.positions.UpperRightCornerPosition;

import java.util.Set;

/**
 * The default implementation of the {@link ScreenGenerator} tool.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class DefaultScreenGenerator implements ScreenGenerator {

  /**
   * 24-Bit hexadecimal representation of the default green-value for the used greenscreen.
   */
  public static final String GREENSCREEN_COLOR_REPRESENTATION_KEY = "#43E23D";
  /**
   * Default value for the keying-distance used in the example-image.
   */
  public static final double DEFAULT_KEYING_DISTANCE = 100;

  /**
   * Set of all possible positions.
   */
  public static final Set<Position> POSITIONS = Set
      .of(new CenterPosition(), new UpperLeftCornerPosition(), new UpperRightCornerPosition(),
          new LowerCenterPosition());

  @Override
  public ScreenImage generate(ScreenImage greenscreenImage, Keying keying,
      ScreenImageEnhancement enhancement) {
      ScreenImage generatedImage = keying.process(greenscreenImage);
      if (enhancement != null) {
        enhancement.enhance(generatedImage);
      }
      return generatedImage;
    }
}