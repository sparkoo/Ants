package cz.sparko.Ants;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public boolean equals(Object that) {
        if (that == null) return false;
        if (that == this) return true;
        if (!(that instanceof Coordinate)) return false;
        Coordinate c = (Coordinate)that;
        if (c.getX() == x && c.getY() == y) return true;
        return false;
    }
}
