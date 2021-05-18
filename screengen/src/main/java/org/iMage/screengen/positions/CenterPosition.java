package org.iMage.screengen.positions;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;

import java.awt.Point;

/**
 * The center position of an image.
 * <p>
 * Representation:<br>
 * [ ] [ ] [ ]<br>
 * [ ] [X] [ ]<br>
 * [ ] [ ] [ ]
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class CenterPosition extends Position {

  /**
   * Create a new center position.
   */
  public CenterPosition() {
    super("to be implemented");
  }

  @Override
  public Point calculateCorner(ScreenImage background, ScreenImage foreground) {
    Point startPos = new Point();
    startPos.x = (background.getWidth() - foreground.getWidth()) / 2;
    startPos.y = (background.getHeight() - foreground.getHeight()) / 2;
    return startPos;
  }
}