package org.iMage.plugins;

/**
 * Record-class for storing various java-features.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 * @param name is the name of the code-example.
 * @param version is the java-version in which the example is written.
 * @param example contains a code-example in the specified version and is stored as String.
 */
public record Feature(String name, JavaVersion version, String example) {
    /**
     * This method prints all the information of the feature, nicely formatted in the System.out.
     * First comes the java-version and feature name, then a seperator, followed by the code-example.
     */
    public void print() {
        StringBuilder builder = new StringBuilder(System.lineSeparator()).append("-".repeat(72));
        String sep = builder.append(System.lineSeparator()).toString();
        System.out.println(name() + " example-code (" + version() + ")" + sep + example() + sep);
    }
}