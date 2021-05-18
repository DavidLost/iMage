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
   * Create a new upper left corner position.
   */
  public UpperLeftCornerPosition() {
    super("upper_left");
  }

  @Override
  public Point calculateCorner(ScreenImage background, ScreenImage foreground) {
    Point startPos = new Point();
    startPos.x = 0;
    startPos.y = 0;
    return startPos;
  }
}