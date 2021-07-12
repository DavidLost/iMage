package org.iMage.screengen.parallel;

import org.iMage.iGen.utils.ImageUtils;
import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.ScreenImage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelChromaKeying implements Keying {

    private final ChromaKeying keying;
    private final int threadCount;
    private final ExecutorService threadPoolExecutor;

    public ParallelChromaKeying(ChromaKeying keying, int threadCount) {
        this.keying = keying;
        this.threadCount = threadCount;
        this.threadPoolExecutor = Executors.newFixedThreadPool(threadCount);
    }

    public ParallelChromaKeying(ChromaKeying keying) {
        this(keying, Runtime.getRuntime().availableProcessors());
    }

    @Override
    public ScreenImage process(ScreenImage screenImage) {
        int subWidth = (int) Math.ceil((double) screenImage.getWidth() / threadCount);
        ScreenImage[] subImages = getSubImages(screenImage, subWidth);
        SubImageProcessor[] tasks = new SubImageProcessor[subImages.length];
        for (int i = 0; i < threadCount; i++) {
            tasks[i] = new SubImageProcessor(subImages[i], keying);
            threadPoolExecutor.execute(tasks[i]);
        }
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.isTerminated()) { }
        ScreenImage resultImage = screenImage.copy();
        for (int x = 0; x < screenImage.getWidth(); x++) {
            for (int y = 0; y < screenImage.getHeight(); y++) {
                resultImage.setColor(x, y, tasks[x / subWidth].processedSubImage.getColor(x % subWidth, y));
            }
        }
        return resultImage;
    }

    private ScreenImage[] getSubImages(ScreenImage screenImage, int subWidth) {
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

    static class SubImageProcessor implements Runnable {

        private final ScreenImage subImage;
        private final Keying keying;
        protected ScreenImage processedSubImage = null;

        SubImageProcessor(ScreenImage subImage, ChromaKeying keying) {
            this.subImage = subImage;
            this.keying = keying;
        }

        @Override
        public void run() {
            processedSubImage = keying.process(subImage);
        }
    }

    public static void main(String[] args) {
        ScreenImage image = new BufferedScreenImage(ImageUtils.getImageViaFileChooser());
        ParallelChromaKeying keying = new ParallelChromaKeying(new ChromaKeying("#43E23D", 100));
        try {
            keying.process(image).save("C:\\Users\\David\\Desktop\\result_yayy.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}