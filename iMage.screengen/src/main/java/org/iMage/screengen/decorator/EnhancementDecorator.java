package org.iMage.screengen.decorator;

import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

/**
 * The {@link EnhancementDecorator} decorates any {@link ScreenImageEnhancement} and follows
 * the decorator design pattern.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public abstract class EnhancementDecorator implements ScreenImageEnhancement {

  private final ScreenImageEnhancement enhancement;

  /**
   * Create a new enhancement decorator with an enhancement component.
   *
   * @param enhancement enhancement reference that will be decorated
   */
  public EnhancementDecorator(ScreenImageEnhancement enhancement) {
    this.enhancement = enhancement;
  }

  /**
   * Delegate the enhancement to the passed enhancement reference.
   *
   * @param baseImage base image
   * @return enhanced image by the enhancement component that will be decorated
   */
  @Override
  public ScreenImage enhance(ScreenImage baseImage) {
    return enhancement.enhance(baseImage);
  }
}