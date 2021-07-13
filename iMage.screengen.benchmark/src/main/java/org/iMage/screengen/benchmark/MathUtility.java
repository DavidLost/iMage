package org.iMage.screengen.benchmark;

/**
 * The {@link MathUtility} class provides stochastic helper methods.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public final class MathUtility {

  /**
   * Prevent instances.
   */
  private MathUtility() {

  }

  /**
   * Calculate the mean of a given data array.
   *
   * @param data non-empty data array
   * @return mean value
   */
  public static double calculateMean(long[] data) {
    throw new RuntimeException("to be implemented");
  }

  /**
   * Calculate the standard deviation of a given data array.<br>
   * It indicates the distance of each element to the mean.
   *
   * @param data data array with at least two elements
   * @return standard deviation
   */
  public static double calculateStandardDeviation(long[] data) {
    throw new RuntimeException("to be implemented");
  }

  /**
   * Remove the minimal and maximal value from a data array, if it has more than two elements.<br>
   * The order of the elements may change.
   *
   * @param data data array
   * @return array without the minimal and maximal value or the same array,
   * if the array has less than 3 elements.
   */
  public static long[] removeMinMax(long[] data) {
    throw new RuntimeException("to be implemented");
  }
}