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
   * Create a new upper right corner position.
   */
  public UpperRightCornerPosition() {
    super("upper_right");
  }

  @Override
  public Point calculateCorner(ScreenImage background, ScreenImage foreground) {
    Point startPos = new Point();
    startPos.x = (background.getWidth() - foreground.getWidth()) / 2;
    startPos.y = 0;
    return startPos;
  }
}