package org.iMage.screengen;

import org.iMage.screengen.base.ScreenImage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChromaKeyingTest {

    private final Random random = new Random(ThreadLocalRandom.current().nextInt());
    private final ChromaKeying_old chromaKeying = new ChromaKeying_old(
            DefaultScreenGenerator.GREENSCREEN_COLOR_REPRESENTATION_KEY,random.nextDouble() * 255);

    /**
     * Test if the ChromaKeying-constructor actually throws the right exception, if an invalid color-code is passed.
     */
    @Test
    public void testChromaKeyingConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new ChromaKeying_old("#048CFG", 42));
    }

    /**
     * Test if the process-method sets the expected pixels transparent, by counting the pixel that should be made
     * transparent and comparing the result to the actual transparent-set pixels.
     */
    @Test
    public void testProcess() throws ClassNotFoundException {
        ScreenImage image = ResourceLoader.loadImageResource(
                this.getClass().getName(), ResourceLoader.FOREGROUND_IMAGE_FILE);
        assertNotNull(image);
        int pixelCounterBefore = getEqualPixelCounter(image, chromaKeying.getKey(), chromaKeying.getDistance());
        image = chromaKeying.process(image);
        int pixelCounterAfter = getEqualPixelCounter(image, new Color(ScreenImage.TRANSPARENT_ALPHA_CHANNEL), 0);
        assertEquals(pixelCounterBefore, pixelCounterAfter);
        //image.save(System.getProperty("user.home") + "\\Desktop\\thumb.png");
    }

    /**
     *
     * @param image is an image
     * @param colorKey is the color that is searched for
     * @param distance is the toleration-radius of the colorKey, for which pixels are counted
     * @return the amount of pixels that are in the range of the colorKey
     */
    public int getEqualPixelCounter(ScreenImage image, Color colorKey, double distance) {
        int pixelCounter = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (chromaKeying.colorDistance(new Color(image.getColor(x, y)), colorKey) <= distance) {
                    pixelCounter++;
                }
            }
        }
        return pixelCounter;
    }
}
