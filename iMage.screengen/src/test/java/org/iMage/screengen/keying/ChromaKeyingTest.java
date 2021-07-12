package org.iMage.screengen.keying;

import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.ScreenImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.iMage.screengen.util.ImageUtility.fillImage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

/**
 * This class tests the implementation of {@link ChromaKeying#process(ScreenImage)}.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class ChromaKeyingTest {

  private ScreenImage greenImage;
  private ScreenImage resultImage;

  @BeforeEach
  void setUp() {
    this.greenImage = new BufferedScreenImage(10, 10);
    fillImage(greenImage, Color.GREEN);

    this.resultImage = new ChromaKeying(Color.GREEN, 0.0D).process(greenImage);
  }

  @Test
  void testResultReference() {
    assertNotSame(greenImage, resultImage);
  }

  @Test
  void testResultDimension() {
    assertEquals(greenImage.getWidth(), resultImage.getWidth());
    assertEquals(greenImage.getHeight(), resultImage.getHeight());
  }

  @Test
  void testTransparentPixels() {
    assertTransparentResult();
  }

  @Test
  void testDistanceBoundary() {
    Color key = new Color(3, 255, 4);
    double distance = 5;

    Keying keying = new ChromaKeying(key, distance);
    this.resultImage = keying.process(greenImage);

    assertTransparentResult();
  }

  @Test
  void testNoModification() {
    Keying keying = new ChromaKeying(Color.BLACK, 0);
    this.resultImage = keying.process(greenImage);

    assertNotSame(greenImage, resultImage);

    for (int x = 0; x < resultImage.getWidth(); x++) {
      for (int y = 0; y < resultImage.getHeight(); y++) {
        assertEquals(greenImage.getColor(x, y), resultImage.getColor(x, y));
      }
    }
  }

  @Test
  void testTransparentImage() {
    this.greenImage = new BufferedScreenImage(10, 10);
    fillImage(greenImage, new Color(0, 255, 0, 127));

    Keying keying = new ChromaKeying(new Color(0, 255, 0, 255), 0);
    this.resultImage = keying.process(greenImage);

    for (int x = 0; x < resultImage.getWidth(); x++) {
      for (int y = 0; y < resultImage.getHeight(); y++) {
        assertEquals(greenImage.getColor(x, y), resultImage.getColor(x, y));
      }
    }
  }

  /**
   * Assert that the result image is fully transparent.
   */
  private void assertTransparentResult() {
    for (int x = 0; x < resultImage.getWidth(); x++) {
      for (int y = 0; y < resultImage.getHeight(); y++) {
        Color color = new Color(resultImage.getColor(x, y), true);

        assertEquals(0, color.getAlpha());
      }
    }
  }
}