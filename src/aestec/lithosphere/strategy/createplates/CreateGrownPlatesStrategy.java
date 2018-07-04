package aestec.lithosphere.strategy.createplates;

import aestec.Plate;
import aestec.PlateImpl;
import aestec.lithosphere.strategy.generateterrain.GenerateOceanTerrainStrategy;
import aestec.lithosphere.strategy.generateterrain.GenerateTerrainStrategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Splits the world in a number of plates
 */
public class CreateGrownPlatesStrategy implements CreatePlatesStrategy {
    private int plateNum;
    private int[][]owner;
    private int[] cells;
    private boolean[][] claimed;

    private int xDim;
    private int yDim;
    private GenerateTerrainStrategy strategy;

    Random random;

    public CreateGrownPlatesStrategy(int plateNum,GenerateTerrainStrategy strategy) {
        this.plateNum = plateNum;
        this.strategy = strategy;
    }

    @Override
    public List<Plate> createPlates(int xDim, int yDim) {
        LinkedList<Plate> plates = new LinkedList<>();
        owner = new int[xDim][yDim];
        claimed = new boolean[xDim][yDim];
        random = new Random(3134);

        this.xDim = xDim;
        this.yDim = yDim;

        cells = new int[xDim * yDim - plateNum];
        int numEmptyCells = 0;

        for (int plate = 1; plate <= plateNum; plate++) {
            plates.add(new PlateImpl(xDim,yDim));
            int x = random.nextInt(xDim);
            int y = random.nextInt(yDim);

            while (owner[x][y] != 0) {
                x = random.nextInt(xDim);
                y = random.nextInt(yDim);
            }

            numEmptyCells = setCell(x, y, plate, numEmptyCells);
            claimed[x][y] = true;
        }

        while (numEmptyCells > 0) {
            int index = random.nextInt(numEmptyCells);

            int cell = cells[index];
            cells[index] = cells[--numEmptyCells];

            int x = cell % xDim;
            int y = cell / xDim;
            int plate = selectPlate(x, y);

            numEmptyCells = setCell(x, y, plate, numEmptyCells);
        }

        for (int x = 0; x<xDim; x++) {
            for (int y = 0; y<yDim; y++) {
                plates.get(owner[x][y]-1).set(x,y,strategy.getTerrainAt(x,y)); //owner[x][y]-1
            }
        }

        return plates;
    }

    private int setCell(int x, int y, int plate, int cellsIndex) {
        owner[x][y] = plate;
        if (!claimed[left(x)][y]) {
            cells[cellsIndex++] = left(x) + y * xDim;
            claimed[left(x)][y] = true;
        }
        if (!claimed[right(x)][y]) {
            cells[cellsIndex++] = right(x) + y * xDim;
            claimed[right(x)][y] = true;
        }
        if (!claimed[x][up(y)]) {
            cells[cellsIndex++] = x + up(y) * xDim;
            claimed[x][up(y)] = true;
        }
        if (!claimed[x][down(y)]) {
            cells[cellsIndex++] = x + down(y) * xDim;
            claimed[x][down(y)] = true;
        }
        return cellsIndex;
    }

    private int selectPlate(int x, int y) {
        int plateCandidates[] = new int[4];
        int count = 0;

        if (owner[left(x)][y] != 0) plateCandidates[count++] = owner[left(x)][y];
        if (owner[right(x)][y] != 0) plateCandidates[count++] = owner[right(x)][y];
        if (owner[x][up(y)] != 0) plateCandidates[count++] = owner[x][up(y)];
        if (owner[x][down(y)] != 0) plateCandidates[count++] = owner[x][down(y)];

        return plateCandidates[random.nextInt(count)];
    }

    private int left(int x) {
        return (x == 0 ? xDim - 1 : x - 1);
    }
    private int right(int x) {
        return (x == xDim - 1 ? 0 : x + 1);
    }
    private int up(int y) {
        return (y == 0 ? yDim - 1 : y - 1);
    }
    private int down(int y) {
        return (y == yDim - 1 ? 0 : y + 1);
    }
}
