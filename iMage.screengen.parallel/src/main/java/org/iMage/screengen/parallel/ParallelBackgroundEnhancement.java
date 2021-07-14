package org.iMage.screengen.parallel;

import org.iMage.screengen.AbstractBackgroundEnhancement;
import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;

import java.awt.Point;
import java.util.concurrent.TimeUnit;

/**
 * Multithreading version of {@link BackgroundEnhancement}
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public class ParallelBackgroundEnhancement extends ParallelProcessing implements ScreenImageEnhancement {

    private final AbstractBackgroundEnhancement enhancement;

    /**
     *
     * @param enhancement is a given AbstractBackgroundEnhancement
     * @param threadCount is the amount of threads used to do the processing
     */
    public ParallelBackgroundEnhancement(AbstractBackgroundEnhancement enhancement, int threadCount) {
        super(threadCount);
        this.enhancement = enhancement;
    }

    public ParallelBackgroundEnhancement(AbstractBackgroundEnhancement enhancement) {
        this(enhancement, Runtime.getRuntime().availableProcessors());
    }

    /**
     * This multithreading-optimized enhance-method, first crops the part of the background-image, which will
     * overlay with the foreground. Then the foreground and background-part are cut into vertical pieces, then
     * processed on top of each other. After that, they get recombined and put ontop of the background-image.
     *
     * @param foreground is the foreground image
     * @return the enhanced image
     */
    @Override
    public ScreenImage enhance(ScreenImage foreground) {
        
        ScreenImage background = enhancement.getBackground();
        ScreenImage result = background.copy();
        ScreenImage scaledForeground = foreground.copy();

        if (scaledForeground.getWidth() > background.getWidth()) {
            scaledForeground.scaleToWidth(background.getWidth());
        }
        if (scaledForeground.getHeight() > background.getHeight()) {
            scaledForeground.scaleToHeight(background.getHeight());
        }
        
        Point start = enhancement.getPosition().calculateCorner(background, scaledForeground);
        ScreenImage enhancedBgPart = background.getSubImage(start.x, start.y, scaledForeground.getWidth(), scaledForeground.getHeight());
        int subWidth = scaledForeground.getWidth() / threadCount;
        ScreenImage[] fgSubImages = getSubImages(scaledForeground, subWidth);
        ScreenImage[] bgSubImages = getSubImages(enhancedBgPart, subWidth);
        
        SubImageEnhancementProcessor[] tasks = new SubImageEnhancementProcessor[fgSubImages.length];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new SubImageEnhancementProcessor(fgSubImages[i], enhancement, bgSubImages[i]);
            threadPoolExecutor.execute(tasks[i]);
        }
        threadPoolExecutor.shutdown();
        try {
            if (threadPoolExecutor.awaitTermination(3, TimeUnit.MINUTES)) {
                enhancedBgPart = combineSubImages(tasks, enhancedBgPart.copy(), subWidth);
                for (int x = 0; x < enhancedBgPart.getWidth(); x++) {
                    for (int y = 0; y < enhancedBgPart.getHeight(); y++) {
                        result.setColor(start.x + x, start.y + y, enhancedBgPart.getColor(x, y));
                    }
                }
                return result;
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        return null;
    }
}