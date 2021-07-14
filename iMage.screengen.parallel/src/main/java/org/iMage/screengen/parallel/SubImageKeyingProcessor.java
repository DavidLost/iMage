package org.iMage.screengen.parallel;

import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.ScreenImage;

/**
 * Concrete implementation of the processing for an subimage of the ChromaKeying.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public class SubImageKeyingProcessor extends SubImageProcessor implements Runnable {

    private final Keying keying;

    public SubImageKeyingProcessor(ScreenImage subImage, Keying keying) {
        super(subImage);
        this.keying = keying;
    }

    /**
     * No special processing, the native method is used.
     */
    @Override
    public void run() {
        processedSubImage = keying.process(subImage);
    }
}