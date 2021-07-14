package org.iMage.screengen.parallel;

import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.ScreenImage;

import java.util.concurrent.TimeUnit;

/**
 * Multithreading version of {@link ChromaKeying}
 *
 * @author David Rösler (KIT)
 * @version 1.0
 */
public class ParallelChromaKeying extends ParallelProcessing implements Keying {

    private final ChromaKeying keying;

    /**
     *
     * @param keying is the keying, which is to be applied
     * @param threadCount is the amount of threads used to do the processing
     */
    public ParallelChromaKeying(ChromaKeying keying, int threadCount) {
        super(threadCount);
        this.keying = keying;
    }

    public ParallelChromaKeying(ChromaKeying keying) {
        this(keying, Runtime.getRuntime().availableProcessors());
    }

    /**
     * Multithreaded version of the ChromaKeying.process method.
     * First splits the image vertically into subimages, then processing them seperatly
     * and finally putting them back together.
     */
    @Override
    public ScreenImage process(ScreenImage screenImage) {
        int subWidth = screenImage.getWidth() / threadCount;
        ScreenImage[] subImages = getSubImages(screenImage, subWidth);
        SubImageKeyingProcessor[] tasks = new SubImageKeyingProcessor[subImages.length];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new SubImageKeyingProcessor(subImages[i], keying);
            threadPoolExecutor.execute(tasks[i]);
        }
        threadPoolExecutor.shutdown();
        try {
            if (threadPoolExecutor.awaitTermination(2, TimeUnit.MINUTES)) {
                return combineSubImages(tasks, screenImage.copy(), subWidth);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        return null;
    }
}