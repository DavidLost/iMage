package org.iMage.bundle;

import org.jis.Main;

/**
 * App-class for starting jmjrst.main with plugins loaded.
 * 
 * @author David Rösler (KIT)
 * @version 1.0
 */
public final class App {

    private App() {
        throw new IllegalAccessError();
    }

    /**
     * This main method just starts the Main-class from jmjrst.main, while the arguments are just passed over.
     *
     * @param args are the program-arguments.
     */
    public static void main(String[] args) {
        Main.main(args);
    }

}