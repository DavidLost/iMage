package org.iMage.screengen;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;

/**
 * Base-class for the BackgroundEnhancment and PorterDuffEnhancment, based on the factory-method.
 */
public class EnhancmentBase {

    final ScreenImage background;
    final Position position;

    /**
     * Create a new enhancement-base.
     *
     * @param background new background image
     * @param position   position of the base image on the background image
     */
    public EnhancmentBase(ScreenImage background, Position position) {
        this.background = background;
        this.position = position;
    }

}
