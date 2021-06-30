package org.iMage.company;

public class ConcreteVisitor implements Visitor {

    private int employeeCounter = 0;

    @Override
    public void visitPearCorp(PearCorpCompany pearCorp) {
        for (CompanyComponent department : pearCorp.getChildren()) {
            for (CompanyComponent team : ((Department) department).getChildren()) {
                employeeCounter += ((Team) team).getChildren().size();
            }
        }
    }

    @Override
    public void visitDepartment(Department department) {
        for (CompanyComponent team : department.getChildren()) {
            employeeCounter += ((Team) team).getChildren().size();
        }
    }

    @Override
    public void visitTeam(Team team) {
        employeeCounter += team.getChildren().size();
    }

    @Override
    public void visitEmployee(Employee employee) {
        employeeCounter++;
    }

    public int getEmployeeCounter() {
        return employeeCounter;
    }
}