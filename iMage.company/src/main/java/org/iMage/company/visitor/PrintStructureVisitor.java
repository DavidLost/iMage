package org.iMage.company.visitor;

import org.iMage.company.components.Company;
import org.iMage.company.components.Department;
import org.iMage.company.components.Employee;
import org.iMage.company.components.Team;
import org.iMage.company.composite.OrganizationalComponent;

/**
 * The {@link PrintStructureVisitor} prints the structure of a {@link Company}.<br>
 * It can be used for debugging purposes.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class PrintStructureVisitor implements Visitor {

  @Override
  public void visitEmployee(Employee employee) {
    System.out.println("      - " + employee.getName());
  }

  @Override
  public void visitTeam(Team team) {
    System.out.println("    - Team " + team.getName());

    for (OrganizationalComponent component : team.getChildren()) {
      component.accept(this);
    }
  }

  @Override
  public void visitDepartment(Department department) {
    System.out.println("  - " + department.getName());

    for (OrganizationalComponent component : department.getChildren()) {
      component.accept(this);
    }
  }

  @Override
  public void visitCompany(Company company) {
    System.out.println("Company: " + company.getName());
    System.out.println("Departments:");

    for (OrganizationalComponent component : company.getChildren()) {
      component.accept(this);
    }
  }
}