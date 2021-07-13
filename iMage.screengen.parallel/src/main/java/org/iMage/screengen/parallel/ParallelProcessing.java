package org.iMage.screengen.parallel;

import org.iMage.screengen.base.ScreenImage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ParallelProcessing {

    protected final int threadCount;
    protected final ExecutorService threadPoolExecutor;

    public ParallelProcessing(int threadCount) {
        this.threadCount = threadCount;
        this.threadPoolExecutor = Executors.newFixedThreadPool(threadCount);
    }

    ScreenImage[] getSubImages(ScreenImage screenImage, int subWidth) {
        ScreenImage[] subImages = new ScreenImage[threadCount];
        for (int x = 0; x < threadCount; x++) {
            int width = subWidth;
            if (x == threadCount - 1) {
                width -= subWidth * threadCount - screenImage.getWidth();
            }
            subImages[x] = screenImage.getSubImage(x * subWidth, 0, width, screenImage.getHeight());
        }
        return subImages;
    }

    ScreenImage combineSubImages(SubImageProcessor[] tasks, ScreenImage resultImage, int subWidth) {
        for (int x = 0; x < resultImage.getWidth(); x++) {
            for (int y = 0; y < resultImage.getHeight(); y++) {
                resultImage.setColor(x, y, tasks[x / subWidth].processedSubImage.getColor(x % subWidth, y));
            }
        }
        return resultImage;
    }
}