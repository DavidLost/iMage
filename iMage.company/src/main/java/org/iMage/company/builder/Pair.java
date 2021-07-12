package org.iMage.company.builder;

/**
 * The {@link Pair} record class is an immutable tuple and holds two values.
 *
 * @param first  first value
 * @param second second value
 * @param <S>    first value type
 * @param <T>    second value type
 * @author Paul Hoger
 * @version 1.0
 */
public record Pair<S, T>(S first, T second) {

}