package org.iMage.company;

import org.iMage.company.csv.CompanyCSVReader;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TestCSVConverting {

    private static final String CSV_FILE = "/employees.CSV";
    private final CompanyCSVReader csvReader = new CompanyCSVReader(Objects.requireNonNull(getClass().getResource(CSV_FILE)).getPath());
    private final PearCorpCompany pearCorp = csvReader.createCompositionTree(csvReader.getData());

    @Test
    public void testCompositiontreeCreation() {

        String[] csvData = {
                "[Mitarbeiter*in, Team, Abteilung]",
                "[Edelgard Schlosser, Gezwitscher, Social Media]",
                "[Leo Kruse, Facezine, Social Media]",
                "[Finn Messner, Facezine, Social Media]",
                "[Reinhardt Garber, Analyse, Marketing]",
                "[Sarah Haupt, Analyse, Marketing]",
                "[Xaver Warner, Testen, Entwicklung]",
                "[Beata Reis, Implementierung, Entwicklung]",
                "[Rosa Loritz, Implementierung, Entwicklung]"
        };

        for (int i = 0; i < Math.min(csvReader.getData().size(), csvData.length); i++) {
            assertEquals(Arrays.toString(csvReader.getDataWithKeys().get(i)), csvData[i]);
        }
        pearCorp.work();
    }

    @Test
    public void testVistor() {

        ConcreteVisitor visitor1 = new ConcreteVisitor();
        pearCorp.accept(visitor1);
        assertEquals(visitor1.getEmployeeCounter(), 8);

        ConcreteVisitor visitor2 = new ConcreteVisitor();
        pearCorp.getChildren().get(0).accept(visitor2);
        assertEquals(visitor2.getEmployeeCounter(), 3);
    }
}