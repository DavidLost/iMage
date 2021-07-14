package org.iMage.screengen.benchmark;

import org.iMage.screengen.DefaultScreenGenerator;
import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

/**
 * Runnable implementation of a keying and enhancement process for benchmarking purposes.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public class BenchmarkRunnable implements Runnable {

    protected final ScreenImage image;
    protected final Keying keying;
    protected final ScreenImageEnhancement enhancement;

    /**
     *
     * @param image is the image, which is to be processed
     * @param keying is a keying procedure
     * @param enhancement is a enhancement procedure
     */
    public BenchmarkRunnable(ScreenImage image, Keying keying, ScreenImageEnhancement enhancement) {
        this.image = image;
        this.keying = keying;
        this.enhancement = enhancement;
    }

    /**
     * The run-method creates a new DefaultScreenGenerator and generates a image from the given parameters.
     */
    @Override
    public void run() {
        new DefaultScreenGenerator().generate(image, keying, enhancement);
    }
}