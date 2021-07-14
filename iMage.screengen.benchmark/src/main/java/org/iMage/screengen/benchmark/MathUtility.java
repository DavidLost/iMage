package org.iMage.screengen.benchmark;

import java.util.Arrays;

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
    double mean = 0;
    for (long d : data) {
      mean += d;
    }
    return mean / data.length;
  }

  /**
   * Calculate the standard deviation of a given data array.<br>
   * It indicates the distance of each element to the mean.
   *
   * @param data data array with at least two elements
   * @return standard deviation
   */
  public static double calculateStandardDeviation(long[] data) {
    double mean = calculateMean(data);
    double sum = 0;
    for (long d : data) {
      sum += Math.pow(d - mean, 2);
    }
    double factor = 1.0 / (data.length - 1);
    return Math.sqrt(factor * sum);
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
    if (data.length < 3) return data;
    long[] newData = new long[data.length - 2];
    System.arraycopy(Arrays.stream(data).sorted().toArray(), 1, newData, 0, data.length - 2);
    return newData;
  }

  public static void main(String[] args) {
    long[] test = new long[] {5,7,9,2,16,4,6,1,8,6,3};
    System.out.println(Arrays.toString(removeMinMax(test)));
  }

}