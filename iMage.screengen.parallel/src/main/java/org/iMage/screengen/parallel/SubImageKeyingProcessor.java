package org.iMage.screengen.parallel;

import org.iMage.screengen.base.Keying;
import org.iMage.screengen.base.ScreenImage;

public class SubImageKeyingProcessor extends SubImageProcessor implements Runnable {

    private final Keying keying;

    public SubImageKeyingProcessor(ScreenImage subImage, Keying keying) {
        super(subImage);
        this.keying = keying;
    }

    @Override
    public void run() {
        processedSubImage = keying.process(subImage);
    }
}