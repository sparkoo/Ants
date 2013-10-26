package cz.sparko.Bugmaze.Helper;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3);

    private final int value;

    private Direction(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }

    private static final Map<Integer, Direction> intToTypeMap = new HashMap<Integer, Direction>();
    static {
        for (Direction type : Direction.values())
            intToTypeMap.put(type.value, type);
    }

    public static Direction fromInt(int i) {
        Direction type = intToTypeMap.get(Integer.valueOf(i));
        if (type == null)
            return Direction.UP;
        return type;
    }

    public float getDegree() { return 90 * value; }

    private static final Coordinate[] directions = {new Coordinate(0, -1),  new Coordinate(1, 0), new Coordinate(0, 1), new Coordinate(-1, 0)};
    public Coordinate getCoordinate() { return directions[value]; }
}
