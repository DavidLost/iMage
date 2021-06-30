package org.iMage.screengen;

import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

public class Decorator implements ScreenImageEnhancement {

    ScreenImageEnhancement screenImageEnhancement;

    public Decorator(ScreenImageEnhancement screenImageEnhancement) {
        this.screenImageEnhancement = screenImageEnhancement;
    }

    @Override
    public ScreenImage enhance(ScreenImage screenImage) {
        return screenImageEnhancement.enhance(screenImage);
    }
}