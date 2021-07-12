package org.iMage.screengen.decorator;

import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The {@link TextDecorator} decorates an enhancement with a text that will be drawn to the image.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class TextDecorator extends EnhancementDecorator {

  private final String text;
  private final Font font;
  private final Position position;

  /**
   * Create a new text decorator.
   *
   * @param enhancement enhancement that will be decorated with a text
   * @param text        text that will be placed
   * @param font        font of the text
   * @param position    position of the text on the image
   */
  public TextDecorator(ScreenImageEnhancement enhancement, String text, Font font,
      Position position) {
    super(enhancement);

    this.text = text;
    this.font = font;
    this.position = position;
  }

  /**
   * Enhance the base image by the superclass enhancement.
   * Afterwards, the text will be applied to the enhanced image.
   *
   * @param baseImage base image
   * @return enhanced image with a supplied text
   */
  @Override
  public ScreenImage enhance(ScreenImage baseImage) {
    ScreenImage enhancedImage = super.enhance(baseImage);

    return applyText(enhancedImage);
  }

  /**
   * Apply text on the given image by using a background enhancement.
   *
   * @param image given image
   * @return modified image with text
   * @see BackgroundEnhancement
   */
  private ScreenImage applyText(ScreenImage image) {
    BufferedImage textImage = createBufferedTextImage();

    BackgroundEnhancement enhancement = new BackgroundEnhancement(image, position);
    return enhancement.enhance(new BufferedScreenImage(textImage));
  }

  /**
   * Create a buffered image with the text on it.
   * The size fits to the font and the actual text.
   *
   * @return buffered image with text on it
   * @see #determineDimension()
   */
  private BufferedImage createBufferedTextImage() {
    Dimension dimension = determineDimension();

    BufferedImage image = new BufferedImage((int) dimension.getWidth(),
        (int) dimension.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = image.createGraphics();
    graphics.setFont(font);

    FontMetrics metrics = graphics.getFontMetrics();
    graphics.setColor(Color.BLACK);
    graphics.drawString(text, 0, metrics.getAscent());
    graphics.dispose();

    return image;
  }

  /**
   * Determine the dimension of the buffered image that contains the text.<br>
   * The size of the image is based on the font and the actual text text.
   *
   * @return dimension of text image
   */
  private Dimension determineDimension() {
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = image.createGraphics();
    graphics.setFont(font);

    FontMetrics metrics = graphics.getFontMetrics();
    int width = metrics.stringWidth(text);
    int height = metrics.getHeight();

    Dimension dimension = new Dimension(width, height);

    graphics.dispose();

    return dimension;
  }
}