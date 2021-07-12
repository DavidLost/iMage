package org.iMage.company.components;

import org.iMage.company.composite.OrganizationalComponent;
import org.iMage.company.visitor.Visitor;

import java.util.Objects;

/**
 * The {@link Employee} is a leaf component and lays one level below the {@link Team}.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class Employee implements OrganizationalComponent {

  private final String name;

  /**
   * Create a new employee.
   *
   * @param name
   *          employee name
   */
  public Employee(String name) {
    this.name = name;
  }

  @Override
  public void handleOrder() {
    System.out.printf("%s will handle the order.%n", getName());
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitEmployee(this);
  }

  /**
   * Get the name.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    Employee employee = (Employee) object;
    return Objects.equals(name, employee.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Employee{" + "name='" + name + '\'' + '}';
  }
}