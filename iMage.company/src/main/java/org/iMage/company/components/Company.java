package org.iMage.company.components;

import org.iMage.company.composite.OrganizationalComposite;
import org.iMage.company.visitor.Visitor;

import java.util.Objects;

/**
 * The {@link Company} is a composite and represents the root for every further component.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class Company extends OrganizationalComposite<Department> {

  /**
   * Create a new company.
   *
   * @param name
   *          company name
   */
  public Company(String name) {
    super(name);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitCompany(this);
  }
}