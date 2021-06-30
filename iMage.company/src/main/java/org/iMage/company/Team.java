package org.iMage.company;

public class Team extends CompanyInstitution implements CompanyComponent, Element {

    public Team(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor v) {
        v.visitTeam(this);
    }
}