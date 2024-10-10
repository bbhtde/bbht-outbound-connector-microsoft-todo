package de.bbht.development.connector.todo;

/**
 * Function interface to execute a function with four arguments
 *
 * @param <A> Generic type of first argument.
 * @param <B> Generic type of second argument.
 * @param <C> Generic type of third argument.
 * @param <D> Generic type of fourth argument.
 * @param <E> Generic type of fifth argument.
 * @param <R> Generic type of result.
 */
@FunctionalInterface
public interface QuinFunction<A, B, C, D, E, R> {

  /**
   * Applies this function to the given arguments
   *
   * @param a first argument.
   * @param b second argument.
   * @param c third argument.
   * @param d fourth argument.
   * @param e fifth argument.
   * @return the function result.
   */
  R apply(A a, B b, C c, D d, E e);
}
