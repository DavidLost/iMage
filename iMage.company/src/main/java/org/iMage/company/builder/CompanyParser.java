package org.iMage.company.builder;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.iMage.company.components.Company;
import org.iMage.company.components.Department;
import org.iMage.company.components.Employee;
import org.iMage.company.components.Team;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The {@link CompanyParser} takes a CSV file and parses it in a {@link Company} based on the data.
 *
 * @author Paul Hoger
 * @version 1.0
 */
public class CompanyParser {

  private static final char SEPARATOR = ';';
  private static final int SKIPPED_LINES = 1;

  private static final int EMPLOYEE_COLUMN = 0;
  private static final int TEAM_COLUMN = 1;
  private static final int DEPARTMENT_COLUMN = 2;

  private final Reader reader;

  private final Company company;

  private final Map<String, Department> departments;
  private final Map<String, Team> teams;

  /**
   * Create a new company parser.
   *
   * @param companyName name of the company
   * @param reader      reader that reads a CSV file, separated by ';', with a heading line
   */
  public CompanyParser(String companyName, Reader reader) {
    this.reader = reader;

    this.company = new Company(companyName);

    this.departments = new HashMap<>();
    this.teams = new HashMap<>();
  }

  /**
   * Parse the table structured data into a {@link Company} composite structure.
   *
   * @return parsed company with departments, teams and employees
   * @throws IOException if reading the CSV file fails
   */
  public Company parse() throws IOException {
    CSVParser parser = new CSVParserBuilder()
        .withSeparator(SEPARATOR)
        .build();

    CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(reader)
        .withSkipLines(SKIPPED_LINES)
        .withCSVParser(parser);

    try (CSVReader csvReader = csvReaderBuilder.build()) {
      List<String[]> content = csvReader.readAll();

      // Parse departments
      List<String> departmentNames = content.stream()
          .map(arr -> arr[DEPARTMENT_COLUMN])
          .distinct()
          .collect(Collectors.toList());

      for (String name : departmentNames) {
        Department department = new Department(name);
        company.add(department);

        departments.put(name, department);
      }

      // Parse teams
      List<Pair<String, String>> teamDepartmentNames = content.stream()
          .map(arr -> new Pair<>(arr[TEAM_COLUMN], arr[DEPARTMENT_COLUMN]))
          .distinct()
          .collect(Collectors.toList());

      for (Pair<String, String> teamDepartment : teamDepartmentNames) {
        Department department = departments.get(teamDepartment.second());
        Team team = new Team(teamDepartment.first());
        department.add(team);

        teams.put(teamDepartment.first(), team);
      }

      // Parse employees
      for (String[] columns : content) {
        Team team = teams.get(columns[TEAM_COLUMN]);
        team.add(new Employee(columns[EMPLOYEE_COLUMN]));
      }

      return company;
    } catch (CsvException e) {
      throw new IOException("Failed to parse CSV.", e);
    }
  }

  /**
   * Parse a CSV file from resources into a {@link Company} composite structure.
   *
   * @param companyName  name of the company
   * @param resourcePath resource path to a CSV file, separated by ';', with a heading line
   * @return parsed company or <code>null</code> if reading the file fails
   */
  public static Company parseFromResource(String companyName, String resourcePath) {
    try (InputStream inputStream = CompanyParser.class.getClassLoader()
        .getResourceAsStream(resourcePath)) {
      if (inputStream == null) {
        return null;
      }

      CompanyParser builder = new CompanyParser(companyName, new InputStreamReader(inputStream));
      return builder.parse();
    } catch (IOException e) {
      return null;
    }
  }
}