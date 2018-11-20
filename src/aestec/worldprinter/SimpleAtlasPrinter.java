package aestec.worldprinter;

import aestec.Plate;
import aestec.lithosphere.Lithosphere;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class SimpleAtlasPrinter extends AbstractWorldPrinter {
    private Lithosphere lithosphere;

    public SimpleAtlasPrinter(Lithosphere lithosphere) {
        this.lithosphere = lithosphere;
    }

    @Override
    public BufferedImage getRenderedWorld() {
        BufferedImage img = new BufferedImage(lithosphere.getXDim(), lithosphere.getYDim(),TYPE_INT_RGB);;
        int max = 0;
        int min = 0;
        for (Plate plate : lithosphere) {
            for (Point point : plate) {
                if (plate.get(point.x,point.y) > max) max = plate.get(point.x,point.y);
                if (plate.get(point.x,point.y) < min) min = plate.get(point.x,point.y);
            }
        }
        float value;
        for (Plate plate : lithosphere) {
            for (Point point : plate) {
                value = plate.get(point.x,point.y);
                if (value<0) img.setRGB(point.x, point.y, Color.HSBtoRGB((float)0.61, (float) 0.8, (value+13000)/13000));
                else img.setRGB(point.x, point.y, Color.HSBtoRGB((float)0.33, (float) 0.8, (value+3000)/13000));
            }
        }

        return img;
    }

    @Override
    Lithosphere getLithosphere() {
        return lithosphere;
    }
}
