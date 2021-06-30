package org.iMage.company.csv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CompanyCSVReader {

    public static final char DEFAULT_SEPERATION_CHAR = ';';

    private final String filePath;
    private final char seperator;
    private List<String[]> data;

    public CompanyCSVReader(String filePath, char seperator) {
        this.filePath = filePath;
        this.seperator = seperator;
        data = read();
    }

    public CompanyCSVReader(String filePath) {
        this(filePath, DEFAULT_SEPERATION_CHAR);
    }

    public List<String[]> read() {
        FileReader reader = null;
        try {
            reader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (reader == null) return null;
        CSVParser csvParser = new CSVParserBuilder().withSeparator(seperator).build();
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(csvParser).build();
        try {
            data = csvReader.readAll();
            return data;
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String[]> getDataWithKeys() {
        return data;
    }

    public List<String[]> getData() {
        return data.subList(1, data.size() - 1);
    }

    public String[] getKeys() {
        return data.get(0);
    }

}
