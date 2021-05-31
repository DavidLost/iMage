package org.iMage.plugins.teaching;

/**
 * A subclass of {@link Animal}, represents a cat.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class Cat extends Animal {

  /**
   * Create a new cat.
   *
   * @param age current age
   */
  public Cat(int age) {
    super("Cat", age);
  }

  @Override
  public void sleep() {
    System.out.println("Cat is now sleeping.");
  }

  /**
   * Let the cat meow.
   */
  public void meow() {
    System.out.println("Meow meow!");
  }
}