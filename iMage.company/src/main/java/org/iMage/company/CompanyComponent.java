package org.iMage.company;

public interface CompanyComponent {

    String getName();

    void work();

    void accept(Visitor v);
}