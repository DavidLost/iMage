package org.iMage.screengen.enhancement;

import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;
import org.iMage.screengen.positions.CenterPosition;
import org.iMage.screengen.positions.LowerCenterPosition;
import org.iMage.screengen.positions.UpperLeftCornerPosition;
import org.iMage.screengen.positions.UpperRightCornerPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.Color;
import java.util.stream.Stream;

import static org.iMage.screengen.util.ImageUtility.fillImage;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests if the foreground is placed correctly on the background
 * according to the provided postion.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class BackgroundPositionTest {

  private ScreenImage background;
  private ScreenImage foreground;

  @BeforeEach
  void setUp() {
    this.background = new BufferedScreenImage(9, 9);
    fillImage(background, Color.WHITE);

    this.foreground = new BufferedScreenImage(1, 1);
    fillImage(foreground, Color.BLACK);
  }

  @ParameterizedTest
  @MethodSource("providePositions")
  void testPositions(int x, int y, Position position) {
    ScreenImageEnhancement enhancement = new BackgroundEnhancement(background, position);
    ScreenImage result = enhancement.enhance(foreground);

    assertEquals(Color.BLACK.getRGB(), result.getColor(x, y));
  }

  /**
   * Provide a stream of arguments containing the point to a given position.
   *
   * @return stream of arguments used in the parametrized test
   */
  private static Stream<Arguments> providePositions() {
    return Stream.of(
        Arguments.of(4, 8, new LowerCenterPosition()),
        Arguments.of(4, 4, new CenterPosition()),
        Arguments.of(0, 0, new UpperLeftCornerPosition()),
        Arguments.of(8, 0, new UpperRightCornerPosition())
    );
  }
}