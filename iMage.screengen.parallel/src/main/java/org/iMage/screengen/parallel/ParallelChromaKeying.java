package org.iMage.screengen.parallel;

import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.ScreenImage;

import java.util.concurrent.TimeUnit;

public class ParallelChromaKeying extends ParallelProcessing implements Keying {

    private final ChromaKeying keying;

    public ParallelChromaKeying(ChromaKeying keying, int threadCount) {
        super(threadCount);
        this.keying = keying;
    }

    public ParallelChromaKeying(ChromaKeying keying) {
        this(keying, Runtime.getRuntime().availableProcessors());
    }

    @Override
    public ScreenImage process(ScreenImage screenImage) {
        int subWidth = (int) Math.ceil((double) screenImage.getWidth() / threadCount);
        ScreenImage[] subImages = getSubImages(screenImage, subWidth);
        SubImageKeyingProcessor[] tasks = new SubImageKeyingProcessor[subImages.length];
        for (int i = 0; i < threadCount; i++) {
            tasks[i] = new SubImageKeyingProcessor(subImages[i], keying);
            threadPoolExecutor.execute(tasks[i]);
        }
        threadPoolExecutor.shutdown();
        try {
            if (threadPoolExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                return combineSubImages(tasks, screenImage.copy(), subWidth);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        return null;
    }
}