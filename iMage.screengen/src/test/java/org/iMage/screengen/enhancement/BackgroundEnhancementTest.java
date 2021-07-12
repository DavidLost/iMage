package org.iMage.screengen.enhancement;

import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;
import org.iMage.screengen.positions.CenterPosition;
import org.iMage.screengen.positions.LowerCenterPosition;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.iMage.screengen.util.ImageUtility.fillImage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * This class tests the {@link BackgroundEnhancement} process with in-memory images.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class BackgroundEnhancementTest {

  @Test
  void testFullTransparentBase() {
    ScreenImage transparentImage = new BufferedScreenImage(10, 10);
    ScreenImage redImage = new BufferedScreenImage(10, 10);

    ScreenImageEnhancement enhancement = new BackgroundEnhancement(redImage, new CenterPosition());
    ScreenImage resultImage = enhancement.enhance(transparentImage);

    for (int x = 0; x < redImage.getWidth(); x++) {
      for (int y = 0; y < redImage.getHeight(); y++) {
        assertEquals(redImage.getColor(x, y), resultImage.getColor(x, y));
      }
    }
  }

  @Test
  void testResultReference() {
    ScreenImage background = new BufferedScreenImage(10, 10);
    ScreenImage foreground = new BufferedScreenImage(5, 5);

    ScreenImageEnhancement enhancement = new BackgroundEnhancement(background,
        new CenterPosition());
    ScreenImage result = enhancement.enhance(foreground);

    assertNotSame(background, result);
  }

  @Test
  void testWidthScaling() {
    ScreenImage background = new BufferedScreenImage(10, 10);
    fillImage(background, Color.BLACK);

    ScreenImage foreground = new BufferedScreenImage(20, 10);
    fillImage(foreground, Color.WHITE);

    ScreenImageEnhancement enhancement = new BackgroundEnhancement(background,
        new LowerCenterPosition());
    ScreenImage result = enhancement.enhance(foreground);

    for (int x = 0; x < background.getWidth(); x++) {
      for (int y = 0; y < 5; y++) {
        assertEquals(Color.BLACK.getRGB(), result.getColor(x, y));
      }
    }
    for (int x = 0; x < background.getWidth(); x++) {
      for (int y = 5; y < background.getHeight(); y++) {
        assertEquals(Color.WHITE.getRGB(), result.getColor(x, y));
      }
    }
  }
}