package org.iMage.screengen;

import org.iMage.screengen.base.Position;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

import java.awt.Font;

public class TextDecorator extends Decorator {

    private final String text;
    private final Font font;
    private final Position position;

    public TextDecorator(ScreenImageEnhancement screenImageEnhancement, String text, Font font, Position position) {
        super(screenImageEnhancement);
        this.text = text;
        this.font = font;
        this.position = position;
    }

    @Override
    public ScreenImage enhance(ScreenImage screenImage) {
        return super.enhance(screenImage);
    }
}