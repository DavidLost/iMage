package org.iMage.screengen.benchmark;

/**
 * The {@link Measurement} class represents the result of a benchmark run.
 *
 * @param iterations        the amount of iterations (before removing min and max)
 * @param mean              the mean value of the (modified) measurements
 * @param standardDeviation the standard deviation of the (modified) measurements
 * @author Paul Hoger
 * @version 1.0
 */
public record Measurement(int iterations, double mean, double standardDeviation) {

}