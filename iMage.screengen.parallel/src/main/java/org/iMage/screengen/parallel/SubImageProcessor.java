package org.iMage.screengen.parallel;

import org.iMage.screengen.base.ScreenImage;

public abstract class SubImageProcessor {

    protected final ScreenImage subImage;
    protected ScreenImage processedSubImage = null;

    SubImageProcessor(ScreenImage subImage) {
        this.subImage = subImage;
    }
}