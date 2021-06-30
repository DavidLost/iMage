package org.iMage.company;

public class Employee implements CompanyComponent, Element {

    private final String name;

    public Employee(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void work() {
        System.out.println(name + " is working!");
    }

    @Override
    public void accept(Visitor v) {
        v.visitEmployee(this);
    }
}