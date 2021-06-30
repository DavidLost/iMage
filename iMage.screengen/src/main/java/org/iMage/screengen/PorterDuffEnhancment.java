package org.iMage.screengen;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

public class PorterDuffEnhancment extends EnhancmentBase implements ScreenImageEnhancement {


    /**
     * Create a new enhancement-base.
     *
     * @param background new background image
     * @param position   position of the base image on the background image
     */
    public PorterDuffEnhancment(ScreenImage background, Position position) {
        super(background, position);
    }

    @Override
    public ScreenImage enhance(ScreenImage screenImage) {
        return null;
    }
}
