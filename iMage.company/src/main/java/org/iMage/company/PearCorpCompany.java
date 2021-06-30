package org.iMage.company;

import org.iMage.company.csv.CompanyCSVReader;

import java.util.List;

public class PearCorpCompany extends CompanyInstitution implements CompanyComponent {

    public PearCorpCompany(String name) {
        super(name);
        String filePath = System.getProperty("user.home") + "\\Desktop\\employees.CSV";
        CompanyCSVReader csvReader = new CompanyCSVReader(filePath);
        List<String[]> data = csvReader.getData();
        createTree(data);
        work();
    }

    private void createTree(List<String[]> data) {
        for (String[] entry : data) {
            Department department = null;
            for (CompanyComponent d : getChildren()) {
                if (d.getName().equals(entry[2])) {
                    department = (Department) d;
                    break;
                }
            }
            if (department == null) {
                department = new Department(entry[2]);
                add(department);
            }
            Team team = null;
            for (CompanyComponent t : department.getChildren()) {
                if (t.getName().equals(entry[1])) {
                    team = (Team) t;
                    break;
                }
            }
            if (team == null) {
                team = new Team(entry[1]);
                department.add(team);
            }
            team.add(new Employee(entry[0]));
        }
    }

    public static void main(String[] args) {
        new PearCorpCompany("PearCorp");
    }
}