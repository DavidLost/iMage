package org.iMage.screengen.positions;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;

import java.awt.Point;

/**
 * The upper left corner position of an image.
 * <p>
 * Representation:<br>
 * [X] [ ] [ ]<br>
 * [ ] [ ] [ ]<br>
 * [ ] [ ] [ ]
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class UpperLeftCornerPosition extends Position {

  /**
   * Create a new position with description "upper left corner".
   */
  public UpperLeftCornerPosition() {
    super("upper left corner");
  }

  @Override
  public Point calculateCorner(ScreenImage background, ScreenImage foreground) {
    return new Point(0, 0);
  }
}