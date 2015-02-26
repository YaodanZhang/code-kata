package com.thoughtworks.kata.kongwei;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class KongWei {
    private final int[][] cellsMap;

    public KongWei(int[][] cellsMap) {
        checkNotNull(cellsMap);
        checkArgument(cellsMap.length > 0);
        checkArgument(cellsMap[0].length > 0);

        this.cellsMap = cellsMap;
    }

    public int[][] nextFrame() {
        int[][] nextFrame = new int[cellsMap.length][cellsMap[0].length];

        for (int i = 0; i < cellsMap.length; i++) {
            for (int j = 0; j < cellsMap[0].length; j++) {

            }
        }

        return nextFrame;
    }
}
