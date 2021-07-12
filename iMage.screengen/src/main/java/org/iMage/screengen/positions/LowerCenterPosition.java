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
   * Create a new position with description "lower center".
   */
  public LowerCenterPosition() {
    super("lower center");
  }

  @Override
  public Point calculateCorner(ScreenImage background, ScreenImage foreground) {
    int x = (int) ((background.getWidth() - foreground.getWidth()) / 2.0);
    int y = background.getHeight() - foreground.getHeight();
    return new Point(x, y);
  }
}
