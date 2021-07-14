package org.iMage.screengen.benchmark;

import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.parallel.ParallelBackgroundEnhancement;
import org.iMage.screengen.parallel.ParallelChromaKeying;
import org.iMage.screengen.positions.UpperLeftCornerPosition;
import org.junit.jupiter.api.BeforeEach;
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

  private static final int ITERATIONS = 2;
  private static final long RANDOM_SEED = 42;

  private static final Measurement[] measurements = new Measurement[THREADS.length + 1];

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
  void testParallelExecutionTime() {
    Benchmark sequentialBenchmark = new Benchmark(
            new BenchmarkRunnable(inputImage, sequentialKeying, sequentialEnhancement));
    measurements[0] = sequentialBenchmark.run(ITERATIONS);
    for (int i = 0; i < THREADS.length; i++) {
      System.out.println("threads: " + THREADS[i]);
      Benchmark parallelBenchmark = new Benchmark(new BenchmarkRunnable(inputImage,
              new ParallelChromaKeying(sequentialKeying, THREADS[i]),
              new ParallelBackgroundEnhancement(sequentialEnhancement, THREADS[i])));
      measurements[i + 1] = parallelBenchmark.run(ITERATIONS);
    }
  }

  /**
   *
   *
   */
  @Test
  void printBenchmarkResults() {
    final String[][] table = new String[THREADS.length + 2][];
    table[0] = new String[] { "Implementation", "Iterationns", "Avg[ms]", "SD[ms]" };
    table[1] = new String[] { "Sequential", Integer.toString(measurements[0].iterations()),
            Double.toString(measurements[0].mean()), Double.toString(measurements[0].standardDeviation()) };
    for (int i = 0; i < THREADS.length; i++) {
      table[i + 2] = new String[] { "Parallel(" + THREADS[i] + ")", Integer.toString(measurements[i + 1].iterations()),
              Double.toString(measurements[i + 1].mean()), Double.toString(measurements[i + 1].standardDeviation()) };
    }
    for (Object[] row : table) {
      System.out.format("%15s%15s%15s%15s%n", row);
    }

  }
}