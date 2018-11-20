package aestec.worldprinter;

import aestec.lithosphere.Lithosphere;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class AtlasPrinter extends AbstractWorldPrinter {
    private Lithosphere lithosphere;
    public AtlasPrinter(Lithosphere lithosphere) {
        this.lithosphere = lithosphere;
    }

    @Override
    public BufferedImage getRenderedWorld() {
        BufferedImage img = new BufferedImage(lithosphere.getXDim(), lithosphere.getYDim(),TYPE_INT_RGB);

        Colormap colormap = new Colormap();
        colormap.add(-9500,new Color(56,91,140));
        colormap.add(-8500,new Color(43,102,166));
        colormap.add(-7000,new Color(66,124,179));
        colormap.add(-6000,new Color(82,143,204));
        colormap.add(-4500,new Color(98,159,217));
        colormap.add(-3000,new Color(134,179,235));
        colormap.add(-1000,new Color(149,188,230));
        colormap.add(-400,new Color(170,207,242));
        colormap.add(-150,new Color(181,215,247));
        colormap.add(-35,new Color(191,224,255));
        colormap.add(-1,new Color(209,233,255));

        colormap.add(0,new Color(172, 208, 165));
        colormap.add(600,new Color(148, 191, 139));
        colormap.add(1200,new Color(168, 198, 143));
        colormap.add(1800,new Color(189, 204, 150));
        colormap.add(2400,new Color(209, 215, 171));
        colormap.add(3000,new Color(225, 228, 181));
        colormap.add(3600,new Color(239, 235, 192));
        colormap.add(4200,new Color(232, 225, 182));
        colormap.add(4800,new Color(222, 214, 163));
        colormap.add(5400,new Color(211, 202, 157));
        colormap.add(6000,new Color(202, 185, 130));
        colormap.add(6600,new Color(195, 167, 107));
        colormap.add(7200,new Color(185, 152, 90));
        colormap.add(7800,new Color(170, 135, 83));
        colormap.add(8400,new Color(172, 154, 124));
        colormap.add(9000,new Color(186, 174, 154));
        colormap.add(9400,new Color(202, 195, 184));
        colormap.add(9700,new Color(224, 222, 216));
        colormap.add(10000,new Color(245, 244, 242));


        int value;
        for (int x=0;x<lithosphere.getXDim();x++) for (int y=0;y<lithosphere.getYDim();y++) {
                value = lithosphere.get(x,y);
                Color c = colormap.continuum(value);
                img.setRGB(x, y, c.getRGB());
        }

        return img;
    }

    @Override
    Lithosphere getLithosphere() {
        return lithosphere;
    }
}
