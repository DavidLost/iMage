package org.iMage.plugins.teaching;

/**
 * A subclass of {@link Animal}, represents a dog.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class Dog extends Animal {

  /**
   * Create a new dog.
   *
   * @param age current age
   */
  public Dog(int age) {
    super("Dog", age);
  }

  @Override
  public void sleep() {
    System.out.println("Dog is now sleeping.");
  }

  /**
   * Let the dog bark.
   */
  public void bark() {
    System.out.println("Woof woof!");
  }
}