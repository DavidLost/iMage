package org.iMage.screengen.benchmark;

/**
 * The {@link Benchmark} class measures the execution time of a {@link Runnable} and returns a
 * {@link Measurement} with more information about the measurements.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class Benchmark {

  /**
   * Create a new benchmark with a runnable.
   *
   * @param runnable runnable that will be measured
   */
  public Benchmark(Runnable runnable) {
    throw new RuntimeException("to be implemented");
  }

  /**
   * Run the benchmark and measure the time of the runnable.<br>
   * Afterwards, remove the minimal and maximal value of the measurements if there are more
   * than 2 iterations.
   *
   * @param iterations amount of iterations
   * @return measurement
   * @see MathUtility
   */
  public Measurement run(int iterations) {
    throw new RuntimeException("to be implemented");
  }
}