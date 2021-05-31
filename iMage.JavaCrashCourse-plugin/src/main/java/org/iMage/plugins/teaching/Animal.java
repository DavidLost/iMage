package org.iMage.plugins.teaching;

/**
 * The {@link Animal} superclass to teach inheritance.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class Animal {

  private final String name;
  private final int age;

  /**
   * Create a new animal.
   *
   * @param name name of the animal, e.g. "Duck"
   * @param age  current age of the animal
   */
  public Animal(String name, int age) {
    this.name = name;
    this.age = age;
  }

  /**
   * Let the animal sleep.
   */
  public void sleep() {
    System.out.println("Animal is now sleeping.");
  }

  /**
   * Get the name of the animal, e.g. "Duck".
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the current age of the animal.
   *
   * @return current age
   */
  public int getAge() {
    return age;
  }
}