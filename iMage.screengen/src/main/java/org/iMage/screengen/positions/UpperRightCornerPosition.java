package org.iMage.screengen.positions;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;

import java.awt.Point;

/**
 * The upper right corner position of an image.
 * <p>
 * Representation:<br>
 * [ ] [ ] [X]<br>
 * [ ] [ ] [ ]<br>
 * [ ] [ ] [ ]
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class UpperRightCornerPosition extends Position {

  /**
   * Create a new position with description "upper right corner".
   */
  public UpperRightCornerPosition() {
    super("upper right corner");
  }

  @Override
  public Point calculateCorner(ScreenImage background, ScreenImage foreground) {
    return new Point(background.getWidth() - foreground.getWidth(), 0);
  }
}