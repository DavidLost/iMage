package org.iMage.company;

public class PearCorpCompany extends CompanyInstitution implements CompanyComponent, Element {

    public PearCorpCompany(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor v) {
        v.visitPearCorp(this);
    }
}