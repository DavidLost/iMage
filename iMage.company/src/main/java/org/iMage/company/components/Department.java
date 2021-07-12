package org.iMage.company.components;

import org.iMage.company.composite.OrganizationalComposite;
import org.iMage.company.visitor.Visitor;

import java.util.Objects;

/**
 * The {@link Department} is a composite and lays one level below the {@link Company}.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class Department extends OrganizationalComposite<Team> {

  /**
   * Create a new department.
   *
   * @param name
   *          department name
   */
  public Department(String name) {
    super(name);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitDepartment(this);
  }
}