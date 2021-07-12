package org.iMage.company.visitor;

import org.iMage.company.components.Company;
import org.iMage.company.components.Department;
import org.iMage.company.components.Employee;
import org.iMage.company.components.Team;
import org.iMage.company.composite.OrganizationalComponent;
import org.iMage.company.composite.OrganizationalComposite;

/**
 * The {@link EmployeeCounterVisitor} counts every employee.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class EmployeeCounterVisitor implements Visitor {

  private int employees;

  /**
   * Create a new employee counter visitor.
   */
  public EmployeeCounterVisitor() {
    this.employees = 0;
  }

  @Override
  public void visitEmployee(Employee employee) {
    employees++;
  }

  @Override
  public void visitTeam(Team team) {
    doVisitComposite(team);
  }

  @Override
  public void visitDepartment(Department department) {
    doVisitComposite(department);
  }

  @Override
  public void visitCompany(Company company) {
    doVisitComposite(company);
  }

  /**
   * Visit all children in the composite.
   *
   * @param composite
   *          composite component
   */
  private void doVisitComposite(
      OrganizationalComposite<? extends OrganizationalComponent> composite) {
    for (OrganizationalComponent component : composite.getChildren()) {
      component.accept(this);
    }
  }

  /**
   * Get the counted employees.
   *
   * @return counted employees
   */
  public int getEmployees() {
    return employees;
  }
}