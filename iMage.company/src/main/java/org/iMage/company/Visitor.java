package org.iMage.company;

public interface Visitor {

    void visitPearCorp(PearCorpCompany pearCorp);

    void visitDepartment(Department department);

    void visitTeam(Team team);

    void visitEmployee(Employee employee);
}