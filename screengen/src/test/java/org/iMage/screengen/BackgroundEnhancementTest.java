package org.iMage.screengen;

import org.iMage.screengen.base.ScreenImage;
import org.iMage.screengen.positions.CenterPosition;
import org.iMage.screengen.positions.LowerCenterPosition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BackgroundEnhancementTest {

    BackgroundEnhancement backgroundEnhancement = new BackgroundEnhancement(ResourceLoader.loadImageResource(
            this.getClass().getName(), ResourceLoader.BACKGROUND_IMAGE_FILE), new LowerCenterPosition());
    ChromaKeying chromaKeying = new ChromaKeying(DefaultScreenGenerator.GREENSCREEN_COLOR_REPRESENTATION_KEY,
            DefaultScreenGenerator.DEFAULT_KEYING_DISTANCE);

    public BackgroundEnhancementTest() throws ClassNotFoundException { }

    @Test
    public void testEnhance() throws ClassNotFoundException, IOException {
        ScreenImage image = ResourceLoader.loadImageResource(
                this.getClass().getName(), ResourceLoader.FOREGROUND_IMAGE_FILE);
        assertNotNull(image);
        //image.save(System.getProperty("user.home") + "\\Desktop\\image.png");
        chromaKeying.process(image);
        //image.save(System.getProperty("user.home") + "\\Desktop\\processed.png");
        ScreenImage enhancedImage = backgroundEnhancement.enhance(image);
        ScreenImage combinedImage = ResourceLoader.loadImageResource(
                this.getClass().getName(), ResourceLoader.COMBINED_IMAGE_FILE);
        assertNotNull(combinedImage);
        //enhancedImage.save(System.getProperty("user.home") + "\\Desktop\\enhanced.png");
        //combinedImage.save(System.getProperty("user.home") + "\\Desktop\\combined.png");
        assertEquals(enhancedImage.getWidth(), combinedImage.getWidth());
        assertEquals(enhancedImage.getHeight(), combinedImage.getHeight());
        for (int x = 0; x < enhancedImage.getWidth(); x++) {
            for (int y = 0; y < enhancedImage.getHeight(); y++) {
                assertEquals(enhancedImage.getColor(x, y), combinedImage.getColor(x, y));
            }
        }
    }
}