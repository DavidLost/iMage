package org.iMage.screengen.parallel;

import org.iMage.screengen.base.ScreenImage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Abstract base-class for parallel processing.
 * Its capable of splitting a large image into smaller subimages and also recombining them together.
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public abstract class ParallelProcessing {

    protected final int threadCount;
    protected final ExecutorService threadPoolExecutor;

    public ParallelProcessing(int threadCount) {
        this.threadCount = threadCount;
        this.threadPoolExecutor = Executors.newFixedThreadPool(threadCount);
    }

    /**
     *
     * @param screenImage the to be processed image
     * @param subWidth is the width of the subimages
     * @return an ScreenImage Array of subimages
     */
    ScreenImage[] getSubImages(ScreenImage screenImage, int subWidth) {
        int taskAmount = (int) Math.ceil((double) screenImage.getWidth() / subWidth);
        ScreenImage[] subImages = new ScreenImage[taskAmount];
        for (int x = 0; x < taskAmount; x++) {
            int width = subWidth;
            if (x == taskAmount - 1) {
                int overlap = screenImage.getWidth() % subWidth;
                if (overlap != 0) width = overlap;
            }
            subImages[x] = screenImage.getSubImage(x * subWidth, 0, width, screenImage.getHeight());
        }
        return subImages;
    }

    /**
     *
     * @param tasks are instances of an processor, who processes an subimage and then stores the result
     * @param resultImage is the template for the recombined image
     * @param subWidth is the width of the subimage
     * @return the recombined image
     */
    ScreenImage combineSubImages(SubImageProcessor[] tasks, ScreenImage resultImage, int subWidth) {
        for (int x = 0; x < resultImage.getWidth(); x++) {
            for (int y = 0; y < resultImage.getHeight(); y++) {
                resultImage.setColor(x, y, tasks[x / subWidth].processedSubImage.getColor(x % subWidth, y));
            }
        }
        return resultImage;
    }
}