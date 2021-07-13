package org.iMage.screengen.parallel;

import org.iMage.screengen.BackgroundEnhancement;
import org.iMage.screengen.ChromaKeying;
import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.positions.LowerCenterPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScreenGenParallelTest {

    public static final String TEST_IMAGE = "/donald.jpg";
    public static final String TEST_BACKGROUND = "/background.jpg";

    private ScreenImage testImage;
    private ScreenImage testBackround;

    private ChromaKeying chromaKeying;
    private ParallelChromaKeying parallelChromaKeying;
    private BackgroundEnhancement backgroundEnhancement;
    private ParallelBackgroundEnhancement parallelBackgroundEnhancement;

    @BeforeEach
    void setUp() {
        try {
            testImage = new BufferedScreenImage(ImageIO.read(this.getClass().getResource(TEST_IMAGE)));
            testBackround = new BufferedScreenImage(ImageIO.read(this.getClass().getResource(TEST_BACKGROUND)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        chromaKeying = new ChromaKeying(Color.GREEN, 120);
        parallelChromaKeying = new ParallelChromaKeying(chromaKeying);
        backgroundEnhancement = new BackgroundEnhancement(testBackround, new LowerCenterPosition());
        parallelBackgroundEnhancement = new ParallelBackgroundEnhancement(backgroundEnhancement);
    }

    @Test
    void testParallelKeying() {
        ScreenImage processedImage = chromaKeying.process(testImage);
        ScreenImage parallelProcessedImage = parallelChromaKeying.process(testImage);
        assertTrue(imageEquals(processedImage, parallelProcessedImage));
    }

    @Test
    void testParallelEnhancement() {
        ScreenImage processedImage = chromaKeying.process(testImage);
        ScreenImage enhancedImage = backgroundEnhancement.enhance(processedImage);
        ScreenImage parallelEnhancedImage = parallelBackgroundEnhancement.enhance(processedImage);
        assertTrue(imageEquals(enhancedImage, parallelEnhancedImage));
    }

    public boolean imageEquals(ScreenImage image1, ScreenImage image2) {
        if (image1.getWidth() != image2.getWidth()) return false;
        if (image1.getHeight() != image2.getHeight()) return false;
        for (int x = 0; x < image1.getWidth(); x++) {
            for (int y = 0; y < image1.getHeight(); y++) {
                if (image1.getColor(x, y) != image2.getColor(x, y)) return false;
            }
        }
        return true;
    }

}