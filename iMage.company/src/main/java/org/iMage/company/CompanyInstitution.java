package org.iMage.company;

import java.util.ArrayList;

public abstract class CompanyInstitution implements CompanyComponent {

    private final String name;
    private final ArrayList<CompanyComponent> children = new ArrayList<>();

    public CompanyInstitution(String name) {
        this.name = name;
    }

    public void add(CompanyComponent component) {
        children.add(component);
    }

    public void remove(CompanyComponent component) {
        children.remove(component);
    }

    public ArrayList<CompanyComponent> getChildren() {
        return children;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void work() {
        for (CompanyComponent child : children) {
            System.out.println("work-distribution:  " + getName() + " -> " + child.getName());
            child.work();
        }
    }
}