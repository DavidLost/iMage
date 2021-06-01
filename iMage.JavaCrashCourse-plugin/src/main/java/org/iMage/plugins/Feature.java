package org.iMage.plugins;

public record Feature(String name, JavaVersion version, String example) {

    public Feature(String name, JavaVersion version, String example) {
        this.name = name;
        this.version = version;
        this.example = example;
    }

    public String getName() {
        return name;
    }

    public JavaVersion getVersion() {
        return version;
    }

    public String getExample() {
        return example;
    }
}