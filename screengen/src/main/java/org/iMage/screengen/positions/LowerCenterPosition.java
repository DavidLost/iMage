package org.iMage.screengen.positions;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;

import java.awt.Point;

/**
 * The lower center position of an image.
 * <p>
 * Representation:<br>
 * [ ] [ ] [ ]<br>
 * [ ] [ ] [ ]<br>
 * [ ] [X] [ ]
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class LowerCenterPosition extends Position {

  /**
   * Create a new lower center position.
   */
  public LowerCenterPosition() {
    super("to be implemented");
  }

  @Override
  public Point calculateCorner(ScreenImage background, ScreenImage foreground) {
    Point startPos = new Point();
    startPos.x = (background.getWidth() - foreground.getWidth()) / 2;
    startPos.y = background.getHeight() - foreground.getHeight();
    return startPos;
  }
}