package cz.sparko.Bugmaze.Menu;

public enum MenuEnum {
    MAIN(0),
    PLAY(1),
    OPTIONS(2),
    ADVENTURE_WORLD_SELECTION(3),
    ADVENTURE_LEVEL_SELECTION(4);

    private final int value;

    private MenuEnum(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
