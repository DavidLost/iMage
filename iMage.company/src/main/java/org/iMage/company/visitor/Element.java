package org.iMage.company.visitor;

/**
 * The {@link Element} refers to the element in the visitor design pattern.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public interface Element {

  /**
   * Accept a visitor on the given data structure.
   *
   * @param visitor visitor
   */
  void accept(Visitor visitor);
}