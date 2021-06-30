package org.iMage.company;

public class Employee implements CompanyComponent {

    private final String name;

    public Employee(String name) {
        this.name = name;
        System.out.println("created " + name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void work() {
        System.out.println(name + " is executing!");
    }
}