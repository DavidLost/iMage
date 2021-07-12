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
   * Create a new position with description "center".
   */
  public CenterPosition() {
    super("center");
  }

  @Override
  public Point calculateCorner(ScreenImage background, ScreenImage foreground) {
    int x = (int) ((background.getWidth() - foreground.getWidth()) / 2.0);
    int y = (int) ((background.getHeight() - foreground.getHeight()) / 2.0);

    return new Point(x, y);
  }
}