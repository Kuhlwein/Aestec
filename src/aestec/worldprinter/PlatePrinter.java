package aestec.worldprinter;


import aestec.lithosphere.Lithosphere;
import aestec.Plate;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class PlatePrinter extends AbstractWorldPrinter {
    private Lithosphere lithosphere;

    public PlatePrinter(Lithosphere lithosphere) {
        this.lithosphere = lithosphere;
    }

    @Override
    public BufferedImage getRenderedWorld() {
        BufferedImage img = new BufferedImage(lithosphere.getXDim(), lithosphere.getYDim(),TYPE_INT_RGB);;
        int N = lithosphere.getNumberOfPlates();
        int i = 0;
        for (Plate plate : lithosphere) {
            for (Point point : plate) {
                if (img.getRGB(point.x,point.y )== -16777216) { //unused tile
                    img.setRGB(point.x, point.y, Color.HSBtoRGB((float) i / N, (float) 0.8, (float) 0.8));
                } else {
                    img.setRGB(point.x, point.y,Color.HSBtoRGB((float) i / N, (float) 0.8, (float) 0.8)/2+img.getRGB(point.x,point.y )/2);
                }
            }
            i++;
        }
        return img;
    }
}
