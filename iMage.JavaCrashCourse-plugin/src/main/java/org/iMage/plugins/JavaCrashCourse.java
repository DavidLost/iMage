package org.iMage.plugins;

import org.iMage.plugins.teaching.Animal;
import org.iMage.plugins.teaching.Cat;
import org.iMage.plugins.teaching.Dog;
import org.jis.Main;
import org.kohsuke.MetaInfServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@MetaInfServices
public class JavaCrashCourse extends JmjrstPlugin {

    private JavaVersion currentVersion = JavaVersion.JAVA_16;

    /**
     * Get the name of the plugin.
     *
     * @return plugin name
     */
    @Override
    public String getName() {
        return "JavaCrashCourse-plugin";
    }

    /**
     * Get a list of authors who developed the plugin.<br>
     * If the source is unknown, an empty list should be returned.
     *
     * @return (possible empty) list of authors
     */
    @Override
    public List<String> getAuthors() {
        List<String> authors = new ArrayList<>();
        authors.add("David Rösler");
        return authors;
    }

    /**
     * Initialize the plugin.<br>
     * JMJRST pushes the main application to every subclass - so plugins are allowed to look at Main
     * as well.
     *
     * @param main JMJRST main application
     */
    @Override
    public void init(Main main) {

        Random rnd = new Random(ThreadLocalRandom.current().nextInt());
        Animal animal = switch (rnd.nextInt(3)) {
            case 0 -> new Animal("Fish", 4);
            case 1 -> new Dog(5);
            case 2 -> new Cat(8);
            default -> throw new IllegalStateException("Unexpected value");
        };
        if (animal instanceof Dog) {
            ((Dog) animal).bark();
        } else if (animal instanceof Cat) {
            ((Cat) animal).meow();
        } else {
            animal.sleep();
        }

        List<Feature> features = new ArrayList<>();
        features.add(new Feature("test", JavaVersion.JAVA_8, "code"));
        features.add(new Feature("test", JavaVersion.JAVA_11, "code"));
        features.add(new Feature("test", JavaVersion.JAVA_16, "code"));
    }

    /**
     * Run the plugin.
     */
    @Override
    public void run() {
        System.out.println("running");
    }

    @Override
    public String getConfigurationDescription() {
        return "JavaCrashCourse is a plugin for test-purposes, "
                + "valid java-versions to configure are: JAVA_8, JAVA_11, JAVA_16";
    }

    @Override
    public void configure(String input) {
        currentVersion = JavaVersion.parseFromInput(input);
        System.out.println("verion is now: " + currentVersion.getVersion());
    }

    /**
     * Check whether the plugin can be configured by the user or not.<br>
     * If it is configurable, {@link #getConfigurationDescription()} and {@link #configure(String)}
     * have to be overwritten.
     *
     * @return true if the plugin is configurable, otherwise false
     */
    @Override
    public boolean isConfigurable() {
        return true;
    }
}