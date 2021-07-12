package org.iMage.company.components;

import org.iMage.company.composite.OrganizationalComposite;
import org.iMage.company.visitor.Visitor;

import java.util.Objects;

/**
 * The {@link Team} is a composite and lays one level below the {@link Department}.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class Team extends OrganizationalComposite<Employee> {

  /**
   * Create a new team.
   *
   * @param name
   *          team name
   */
  public Team(String name) {
    super(name);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitTeam(this);
  }
}
