package org.iMage.screengen;

import org.iMage.screengen.base.ScreenImage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChromaKeyingTest {



    private final Random random = new Random(ThreadLocalRandom.current().nextInt());
    private final ChromaKeying chromaKeying = new ChromaKeying(
            DefaultScreenGenerator.GREENSCREEN_COLOR_REPRESENTATION_KEY,random.nextDouble() * 255);

    @Test
    public void testChromaKeyingConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new ChromaKeying("#048CFG", 42));
    }

    @Test
    public void testProcess() throws IOException, ClassNotFoundException {
        ScreenImage image = ResourceLoader.loadImageResource(
                this.getClass().getName(), ResourceLoader.FOREGROUND_IMAGE_FILE);
        assertNotNull(image);
        int pixelCounterBefore = getPixelCounter(image, true);
        chromaKeying.process(image);
        int pixelCounterAfter = getPixelCounter(image, false);
        assertEquals(pixelCounterBefore, pixelCounterAfter);
        image.save(System.getProperty("user.home") + "\\Desktop\\thumb.png");
    }

    public int getPixelCounter(ScreenImage image, boolean before) {
        int pixelCounter = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (before) {
                    if (chromaKeying.colorDistance(new Color(image.getColor(x, y)), chromaKeying.getKey())
                            <= chromaKeying.getDistance()) {
                        pixelCounter++;
                    }
                } else {
                    if (image.getColor(x, y) == ScreenImage.TRANSPARENT_ALPHA_CHANNEL) {
                        pixelCounter++;
                    }
                }
            }
        }
        return pixelCounter;
    }

}
