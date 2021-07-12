package org.iMage.screengen;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

import java.awt.Color;
import java.awt.Font;

/**
 * This enhancement applies a text in a given font to the image.<br>
 * However, there is no implementation at the moment.
 * It just draws a red square in the upper left corner to indicate that the enhancement was applied.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class TextEnhancement implements ScreenImageEnhancement {

  private final String text;
  private final Font font;
  private final Position position;

  /**
   * Create a new text enhancement.
   *
   * @param text     text that will be placed
   * @param font     font of the text
   * @param position position of the text on the image
   */
  public TextEnhancement(String text, Font font, Position position) {
    this.text = text;
    this.font = font;
    this.position = position;
  }

  @Override
  public ScreenImage enhance(ScreenImage image) {
    ScreenImage result = image.copy();

    System.out.printf("Apply '%s' in %s to the image (%s).%n", text, font.getFontName(),
        position.toString());

    for (int x = 0; x < Math.min(result.getWidth(), 10); x++) {
      for (int y = 0; y < Math.min(result.getHeight(), 10); y++) {
        result.setColor(x, y, Color.RED.getRGB());
      }
    }

    return result;
  }
}