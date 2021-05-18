package org.iMage.screengen;

import org.iMage.screengen.base.BufferedScreenImage;
import org.iMage.screengen.base.ScreenGenerator;
import org.iMage.screengen.base.ScreenImage;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Resource-loading class, which can load image-resources of a given class.
 *
 * @author David Rösler
 * @version 1.0
 */
public class ResourceLoader {

    /**
     * Paths to the image-resources.
     */
    public static final String FOREGROUND_IMAGE_FILE = "/thumb.png";
    public static final String BACKGROUND_IMAGE_FILE = "/karlsruhe.jpg";
    public static final String COMBINED_IMAGE_FILE = "/thumb-ka.png";

    /**
     * Given a classname, and the filePath, the method returns the loaded image-resource as ScreenImage.
     *
     * @param className is the class from where the resource will be loaded
     * @param filePath is the filePathName as String
     * @return the loaded image-resource as ScreenImage
     * @throws ClassNotFoundException is thrown, if the class couldn't be found
     */
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