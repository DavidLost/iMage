package org.iMage.screengen.positions;

import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.Point;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * This test class holds a parameterized tests to check the position calculation.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class PositionsTest {

  @ParameterizedTest
  @MethodSource("providePoints")
  void testPositions(int backgroundWidth, int backgroundHeight, int foregroundWidth,
      int foregroundHeight, Point expected, Position position) {
    assumeTrue(backgroundWidth >= foregroundWidth);
    assumeTrue(backgroundHeight >= foregroundHeight);

    ScreenImage background = new BufferedScreenImage(backgroundWidth, backgroundHeight);
    ScreenImage foreground = new BufferedScreenImage(foregroundWidth, foregroundHeight);

    Point point = position.calculateCorner(background, foreground);
    assertEquals(expected, point);
  }

  /**
   * Provide a stream of arguments containing background dimension, foreground dimension and
   * the corresponding point to a given position.
   *
   * @return stream of arguments used in the parametrized test
   */
  private static Stream<Arguments> providePoints() {
    return Stream.of(
        // Center positions
        Arguments.of(200, 200, 200, 200, new Point(0, 0), new CenterPosition()),
        Arguments.of(42, 420, 42, 420, new Point(0, 0), new CenterPosition()),
        Arguments.of(10, 10, 5, 5, new Point(2, 2), new CenterPosition()),
        Arguments.of(10, 4, 10, 3, new Point(0, 0), new CenterPosition()),
        Arguments.of(10, 4, 10, 2, new Point(0, 1), new CenterPosition()),
        // Upper left corner positions
        Arguments.of(200, 200, 200, 200, new Point(0, 0), new UpperLeftCornerPosition()),
        Arguments.of(42, 420, 42, 420, new Point(0, 0), new UpperLeftCornerPosition()),
        Arguments.of(10, 10, 5, 5, new Point(0, 0), new UpperLeftCornerPosition()),
        Arguments.of(10, 4, 10, 3, new Point(0, 0), new UpperLeftCornerPosition()),
        Arguments.of(10, 4, 10, 2, new Point(0, 0), new UpperLeftCornerPosition()),
        Arguments.of(587, 233, 450, 200, new Point(0, 0), new UpperLeftCornerPosition()),
        // Upper right corner positions
        Arguments.of(200, 200, 200, 200, new Point(0, 0), new UpperRightCornerPosition()),
        Arguments.of(42, 420, 42, 420, new Point(0, 0), new UpperRightCornerPosition()),
        Arguments.of(10, 10, 5, 5, new Point(5, 0), new UpperRightCornerPosition()),
        Arguments.of(10, 4, 9, 3, new Point(1, 0), new UpperRightCornerPosition()),
        Arguments.of(100, 20, 42, 10, new Point(58, 0), new UpperRightCornerPosition()),
        Arguments.of(587, 233, 400, 200, new Point(187, 0), new UpperRightCornerPosition()),
        // Lower center positions
        Arguments.of(200, 200, 200, 200, new Point(0, 0), new LowerCenterPosition()),
        Arguments.of(42, 420, 42, 420, new Point(0, 0), new LowerCenterPosition()),
        Arguments.of(10, 10, 5, 5, new Point(2, 5), new LowerCenterPosition()),
        Arguments.of(10, 4, 9, 3, new Point(0, 1), new LowerCenterPosition()),
        Arguments.of(100, 20, 42, 10, new Point(29, 10), new LowerCenterPosition()),
        Arguments.of(587, 233, 400, 200, new Point(93, 33), new LowerCenterPosition())
    );
  }
}