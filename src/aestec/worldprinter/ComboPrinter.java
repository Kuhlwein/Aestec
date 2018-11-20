package aestec.worldprinter;

import aestec.Plate;
import aestec.lithosphere.Lithosphere;
import aestec.lithosphere.factory.AbstractStrategyFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ComboPrinter extends AbstractWorldPrinter {
    private Lithosphere lithosphere;
    private AbstractWorldPrinter printer;

    public ComboPrinter(Lithosphere lithosphere) {
        this.lithosphere = lithosphere;
        printer = new RelativeHeightmapPrinter(lithosphere);
    }

    public ComboPrinter(AbstractWorldPrinter printer) {
        this.lithosphere = printer.getLithosphere();
        this.printer = printer;
    }

    @Override
    public BufferedImage getRenderedWorld() {
        BufferedImage img = printer.getRenderedWorld();

        int N = lithosphere.getNumberOfPlates();
        int i = 0;
        for (Plate plate : lithosphere) {
            for (Point point : plate) {
                boolean isEdge = false;
                for (int dx=-1;dx<=1;dx++) for (int dy=-1;dy<=1;dy++) if (plate.get(point.x+dx,point.y+dy)==null) isEdge=true;
                if (isEdge) {
                    img.setRGB(point.x, point.y,Color.HSBtoRGB((float) i / N, (float) 0.8, (float) 0.8));
                }
            }
            i++;
        }

        return img;
    }

    @Override
    Lithosphere getLithosphere() {
        return lithosphere;
    }
}
