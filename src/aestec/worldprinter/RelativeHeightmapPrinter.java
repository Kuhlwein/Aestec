package aestec.worldprinter;

import aestec.lithosphere.Lithosphere;
import aestec.Plate;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class RelativeHeightmapPrinter extends AbstractWorldPrinter {
    private Lithosphere lithosphere;

    public RelativeHeightmapPrinter(Lithosphere lithosphere) {
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
                img.setRGB(point.x, point.y, Color.HSBtoRGB(0,0,(value-min)/(max-min)));
            }
        }

        return img;
    }

    @Override
    Lithosphere getLithosphere() {
        return lithosphere;
    }
}
