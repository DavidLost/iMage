package org.iMage.screengen;

import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.ScreenImage;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResourceLoader {

    public static final String FOREGROUND_IMAGE_FILE = "/thumb.png";
    public static final String BACKGROUND_IMAGE_FILE = "/karlsruhe.jpg";
    public static final String COMBINED_IMAGE_FILE = "/thumb-ka.png";

    public static ScreenImage loadImageResource(String className, String filePath) throws ClassNotFoundException {
        final URL imageUrl = Class.forName(className).getResource(filePath);
        assertNotNull(imageUrl);
        try {
            return new BufferedScreenImage(ImageIO.read(imageUrl));
        } catch (IOException ioe) {
            return null;
        }
    }

}
