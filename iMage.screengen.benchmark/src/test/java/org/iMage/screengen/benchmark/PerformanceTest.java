package org.iMage.screengen.benchmark;

import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.positions.UpperLeftCornerPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Dimension;

/**
 * The {@link PerformanceTest} ensures that a parallel implementation is faster than a given
 * duration and benchmarks the sequential and parallel implementations.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class PerformanceTest {

  private static final int[] THREADS = { 1, 2, 4, 8, 16, 32, 64, 128 };

  private static final Dimension INPUT_IMAGE_SIZE = new Dimension(2000, 2000);
  private static final Color KEY_COLOR = Color.GREEN;

  private static final Dimension BACKGROUND_IMAGE_SIZE = new Dimension(4000, 4000);
  private static final Position FOREGROUND_POSITION = new UpperLeftCornerPosition();

  private static final int ITERATIONS = 5;
  private static final long RANDOM_SEED = 42;

  private ScreenImage inputImage;

  private ChromaKeying sequentialKeying;
  private BackgroundEnhancement sequentialEnhancement;

  @BeforeEach
  void setUp() {
    ImageGenerator imageGenerator = new ImageGenerator(RANDOM_SEED);
    this.inputImage = imageGenerator.generate((int) INPUT_IMAGE_SIZE.getWidth(),
        (int) INPUT_IMAGE_SIZE.getHeight(), KEY_COLOR);
    ScreenImage backgroundImage = imageGenerator.generate((int) BACKGROUND_IMAGE_SIZE.getWidth(),
        (int) BACKGROUND_IMAGE_SIZE.getWidth());

    this.sequentialKeying = new ChromaKeying(KEY_COLOR, 0);
    this.sequentialEnhancement = new BackgroundEnhancement(backgroundImage,
        FOREGROUND_POSITION);
  }

  @Test
  @Disabled("to be implemented")
  void testParallelExecutionTime() {

  }

  @Test
  @Disabled("to be implemented")
  void printBenchmarkResults() {
    
  }
}