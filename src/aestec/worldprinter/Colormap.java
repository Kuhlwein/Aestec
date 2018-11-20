package aestec.worldprinter;

import sun.reflect.generics.tree.Tree;

import java.awt.*;
import java.util.SortedMap;
import java.util.TreeMap;

public class Colormap {
    SortedMap<Integer,Color> map = new TreeMap<>();

    public Color contour(int x) {
        if (x<=map.firstKey()) return map.get(map.firstKey());
        if (x>=map.lastKey()) return map.get(map.lastKey());

        int lastKey=map.firstKey();
        for (Integer key: map.keySet()) {
            if (x<=key && x>lastKey) {
                return map.get(key);
            }
            lastKey=key;
        }
        return new Color(0,0,0);
    }

    public Color continuum(int x) {
        if (x<=map.firstKey()) return map.get(map.firstKey());
        if (x>=map.lastKey()) return map.get(map.lastKey());

        int lastKey=map.firstKey();
        for (Integer key: map.keySet()) {
            if (x<=key && x>lastKey) {
                Color c1 = map.get(lastKey), c2= map.get(key);
                float f1[] = Color.RGBtoHSB(c1.getRed(),c1.getGreen(),c1.getBlue(),null);
                float f2[] = Color.RGBtoHSB(c2.getRed(),c2.getGreen(),c2.getBlue(),null);

                float w = (float)(x-lastKey)/(key-lastKey);
                for (int i=0; i<3; i++) f1[i]=f1[i]*(1-w)+f2[i]*w;

                return Color.getHSBColor(f1[0],f1[1],f1[2]);
            }
            lastKey=key;
        }
        return new Color(0,0,0);
    }

    public void add(int x, Color c) {
        map.put(x,c);
    }
}
