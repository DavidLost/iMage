package org.iMage.company.composite;

import java.util.*;

/**
 * The {@link OrganizationalComposite} represents the composite in the composite pattern. <br>
 * In comparison to the general composite pattern, the {@link OrganizationalComposite} is abstract
 * and has multiple subclasses.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public abstract class OrganizationalComposite<E extends OrganizationalComponent>
    implements OrganizationalComponent {

  private final String name;

  private Set<E> children;

  /**
   * Create a new composite with an empty set of children.
   *
   * @param name
   *          composite name
   */
  public OrganizationalComposite(String name) {
    this.name = name;

    this.children = new HashSet<E>();
  }

  /**
   * Delegate the request to the children.
   */
  @Override
  public void handleOrder() {
    for (OrganizationalComponent child : children) {
      child.handleOrder();
    }
  }

  /**
   * Get the name.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Add a component to the composite.
   *
   * @param component
   *          component to add
   */
  public void add(E component) {
    children.add(component);
  }

  /**
   * Get an unmodifiable set of children.
   *
   * @return set of children components
   * @see #add(OrganizationalComponent)
   */
  public Set<E> getChildren() {
    return Collections.unmodifiableSet(children);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    OrganizationalComposite<OrganizationalComponent> that = (OrganizationalComposite<OrganizationalComponent>) object;

    // Check if children are equal
    if (children.size() != that.children.size()) {
      return false;
    }

    for (OrganizationalComponent child : that.children) {
      if (!hasEqualChild(child)) {
        return false;
      }
    }

    return name.equals(that.name);
  }

  /**
   * Check if the current composite has a child that is equal to the given component.
   *
   * @param component
   *          possible child component
   * @return <code>true</code> if the composite contains the child, otherwise <code>false</code>
   */
  private boolean hasEqualChild(OrganizationalComponent component) {
    for (OrganizationalComponent child : children) {
      if (child.equals(component)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, children);
  }

  @Override
  public String toString() {
    return "OrganizationalComposite{" + "name='" + name + '\'' + ", children=" + children + '}';
  }
}