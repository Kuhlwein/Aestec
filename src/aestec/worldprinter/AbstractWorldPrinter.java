package aestec.worldprinter;

import aestec.lithosphere.Lithosphere;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class AbstractWorldPrinter implements WorldPrinter {
    @Override
    public void printWorld(String fileName) {
        try {
            ImageIO.write(getRenderedWorld(), "png", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract BufferedImage getRenderedWorld();
    abstract Lithosphere getLithosphere();
}
