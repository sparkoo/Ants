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
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Coordinate)) return false;
        Coordinate c = (Coordinate)other;
        if (c.getX() == x && c.getY() == y) return true;
        return false;
    }
}
