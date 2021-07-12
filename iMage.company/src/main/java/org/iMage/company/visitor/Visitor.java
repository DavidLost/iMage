package org.iMage.company.visitor;

import org.iMage.company.components.Company;
import org.iMage.company.components.Department;
import org.iMage.company.components.Employee;
import org.iMage.company.components.Team;

/**
 * The {@link Visitor} refers to the visitor in the visitor design pattern.<br>
 * It can visit the company composite structure.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public interface Visitor {

  /**
   * Visit an employee.
   *
   * @param employee employee to visit
   */
  void visitEmployee(Employee employee);

  /**
   * Visit a team.
   *
   * @param team team to visit
   */
  void visitTeam(Team team);

  /**
   * Visit a department.
   *
   * @param department department to visit
   */
  void visitDepartment(Department department);

  /**
   * Visit a company.
   *
   * @param company company to visit
   */
  void visitCompany(Company company);
}