package org.iMage.screengen.parallel;

import org.iMage.iGen.utils.ImageUtils;
import org.iMage.screengen.AbstractBackgroundEnhancement;
import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.base.ScreenImageEnhancement;
import org.iMage.screengen.positions.CenterPosition;
import org.iMage.screengen.positions.LowerCenterPosition;
import org.iMage.screengen.positions.UpperRightCornerPosition;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ParallelBackgroundEnhancement extends ParallelProcessing implements ScreenImageEnhancement {

    private final AbstractBackgroundEnhancement enhancement;

    public ParallelBackgroundEnhancement(AbstractBackgroundEnhancement enhancement, int threadCount) {
        super(threadCount);
        this.enhancement = enhancement;
    }

    public ParallelBackgroundEnhancement(AbstractBackgroundEnhancement enhancement) {
        this(enhancement, Runtime.getRuntime().availableProcessors());
    }

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
        int subWidth = (int) Math.ceil((double) scaledForeground.getWidth() / threadCount);
        ScreenImage[] fgSubImages = getSubImages(scaledForeground, subWidth);
        ScreenImage[] bgSubImages = getSubImages(enhancedBgPart, subWidth);
        
        SubImageEnhancementProcessor[] tasks = new SubImageEnhancementProcessor[fgSubImages.length];
        for (int i = 0; i < threadCount; i++) {
            tasks[i] = new SubImageEnhancementProcessor(fgSubImages[i], enhancement, bgSubImages[i]);
            threadPoolExecutor.execute(tasks[i]);
        }
        threadPoolExecutor.shutdown();
        try {
            if (threadPoolExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
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

    public static void main(String[] args) {
        ScreenImage image = new BufferedScreenImage(ImageUtils.getImageViaFileChooser());
        ScreenImage bg = new BufferedScreenImage(ImageUtils.getImageViaFileChooser());
        BackgroundEnhancement bge = new BackgroundEnhancement(bg, new UpperRightCornerPosition());
        ParallelBackgroundEnhancement enhancement = new ParallelBackgroundEnhancement(bge);
        try {
            bge.enhance(image).save("C:\\Users\\David\\Desktop\\result_single.png");
            enhancement.enhance(image).save("C:\\Users\\David\\Desktop\\result_parallel.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}