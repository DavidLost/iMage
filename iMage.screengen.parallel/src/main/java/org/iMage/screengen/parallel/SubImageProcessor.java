package org.iMage.screengen.parallel;

import org.iMage.screengen.base.ScreenImage;

/**
 * Abtract class, which is the base for the subimage-processing.
 * It contains an subimage, which is to be processed and also the following result.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public abstract class SubImageProcessor {

    protected final ScreenImage subImage;
    protected ScreenImage processedSubImage = null;

    SubImageProcessor(ScreenImage subImage) {
        this.subImage = subImage;
    }
}