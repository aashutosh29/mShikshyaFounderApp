package com.bihanitech.shikshyaprasasak.curveGraph.models;

import java.util.HashMap;

public class PointMap {
    public int maxValue = 0;
    private final HashMap<Integer, Integer> pointMap;

    public PointMap() {
        pointMap = new HashMap<Integer, Integer>();
    }

    public void addPoint(int spanPos, int value) {
        if (maxValue < value) {
            maxValue = value;
        }
        pointMap.put(spanPos, value);
    }

    public HashMap<Integer, Integer> getPointMap() {
        return pointMap;
    }

    public GraphPoint get(int spanPos) {
        if (pointMap.containsKey(spanPos)) {
            return new GraphPoint(spanPos, pointMap.get(spanPos));
        } else {
            return new GraphPoint(spanPos, 0);
        }
    }
}