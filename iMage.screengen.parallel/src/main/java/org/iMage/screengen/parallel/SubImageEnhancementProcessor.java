package org.iMage.screengen.parallel;

import org.iMage.screengen.AbstractBackgroundEnhancement;
import org.iMage.screengen.base.ScreenImage;

import java.awt.Color;

public class SubImageEnhancementProcessor extends SubImageProcessor implements Runnable {

    private final AbstractBackgroundEnhancement enhancement;
    private final ScreenImage bgSubImage;

    public SubImageEnhancementProcessor(ScreenImage fgSubImage, AbstractBackgroundEnhancement enhancement, ScreenImage bgSubImage) {
        super(fgSubImage);
        this.enhancement = enhancement;
        this.bgSubImage = bgSubImage;
    }

    @Override
    public void run() {
        assert subImage.getWidth() == bgSubImage.getWidth();
        assert subImage.getHeight() == bgSubImage.getHeight();
        processedSubImage = bgSubImage.copy();
        for (int x = 0; x < subImage.getWidth(); x++) {
            for (int y = 0; y < subImage.getHeight(); y++) {
                Color foregroundColor = new Color(subImage.getColor(x, y), true);
                Color backgroundColor = new Color(bgSubImage.getColor(x, y), true);
                Color combinedColor = enhancement.combine(foregroundColor, backgroundColor);
                processedSubImage.setColor(x, y, combinedColor.getRGB());
            }
        }
    }
}