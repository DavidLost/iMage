package org.iMage.company;

public class Department extends CompanyInstitution implements CompanyComponent, Element {

    public Department(String name) {
        super(name);
    }

    public void accept(Visitor v) {
        v.visitDepartment(this);
    }
}
