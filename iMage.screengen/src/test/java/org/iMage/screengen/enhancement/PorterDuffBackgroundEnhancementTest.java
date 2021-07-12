package org.iMage.screengen.enhancement;

import org.iMage.screengen.PorterDuffBackgroundEnhancement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.Color;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * This test class tests the calculation of the combined foreground and background color of the
 * {@link PorterDuffBackgroundEnhancement}.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class PorterDuffBackgroundEnhancementTest {

  private static final double EPS = 1e-10;

  /**
   * Combine a foreground and background color using {@link PorterDuffBackgroundEnhancement}.
   * The <code>combine</code> method may vary.
   *
   * @param foregroundColor foreground color
   * @param backgroundColor background color
   * @return combined color
   */
  private Color combinedColor(Color foregroundColor, Color backgroundColor) {
    return new PorterDuffBackgroundEnhancement(null, null)
        .combine(foregroundColor, backgroundColor);
  }

  @ParameterizedTest
  @MethodSource("provideAlphaComponents")
  void testAlphaCalculation(int foregroundAlpha, int backgroundAlpha, int expectedAlpha) {
    Color foregroundColor = new Color(0, 0, 0, foregroundAlpha);
    Color backgroundColor = new Color(0, 0, 0, backgroundAlpha);

    Color combinedColor = combinedColor(foregroundColor, backgroundColor);

    assertEquals(expectedAlpha, combinedColor.getAlpha());
  }

  /**
   * Create a stream of foreground, background and expected alpha components.
   *
   * @return stream of arguments
   */
  private static Stream<Arguments> provideAlphaComponents() {
    return Stream.of(
        Arguments.of(0, 0, 0),
        Arguments.of(255, 0, 255),
        Arguments.of(0, 255, 255),
        Arguments.of(127, 255, 255),
        Arguments.of(0, 42, 42),
        Arguments.of(0, 43, 43)
    );
  }

  @Test
  void testZeroAlphaResult() {
    Color foregroundColor = new Color(0, 0, 0, 0);
    Color backgroundColor = new Color(0, 0, 0, 255);

    // Ensure that the result alpha component would be zero
    int foregroundAlpha = 0;
    int backgroundAlpha = 0;
    assumeTrue(Math.abs(foregroundAlpha / 255.0 + (1 - foregroundAlpha / 255.0)
        * (backgroundAlpha / 255.0)) < EPS);

    assertEquals(backgroundColor, combinedColor(foregroundColor, backgroundColor));
  }

  @ParameterizedTest
  @MethodSource("provideColorComponents")
  void test(Color foregroundColor, Color backgroundColor, Color resultColor) {
    assertEquals(resultColor, combinedColor(foregroundColor, backgroundColor));
  }

  /**
   * Create a stream of foreground, background and expected color.
   *
   * @return stream of arguments
   */
  private static Stream<Arguments> provideColorComponents() {
    return Stream.of(
        Arguments.of(
            new Color(0, 255, 0, 255), new Color(255, 0, 0, 255),
            new Color(0, 255, 0, 255)
        ),
        Arguments.of(
            new Color(0, 255, 0, 100), new Color(255, 0, 0, 255),
            new Color(155, 100, 0, 255)
        ),
        Arguments.of(
            new Color(0, 255, 0, 127), new Color(255, 0, 0, 127),
            new Color(85, 169, 0, 190)
        ),
        Arguments.of(
            new Color(255, 255, 0, 127), new Color(0, 0, 255, 255),
            new Color(127, 127, 128, 255)
        ),
        Arguments.of(
            new Color(255, 50, 255, 127), new Color(42, 222, 100, 127),
            new Color(183, 107, 203, 190)
        )
    );
  }
}