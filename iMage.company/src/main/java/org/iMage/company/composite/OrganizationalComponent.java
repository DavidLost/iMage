package org.iMage.company.composite;

import org.iMage.company.visitor.Element;

/**
 * The {@link OrganizationalComponent} represents the general component in the composite pattern.
 * <br>
 * It has one common method called {@link #handleOrder()}.
 * The composite related methods are located in {@link OrganizationalComposite}.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public interface OrganizationalComponent extends Element {

  /**
   * Handle an incoming order.
   */
  void handleOrder();
}