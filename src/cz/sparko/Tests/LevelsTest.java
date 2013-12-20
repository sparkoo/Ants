package cz.sparko.Tests;

import cz.sparko.Bugmaze.Level.Level;
import cz.sparko.Bugmaze.Level.World1.Level1;

public class LevelsTest {
    public static void main(String[] args) {
        Level level  = new Level1(null);
        while (true) {
            if (level.testLevel())
                System.out.println("Level " + level.getClass() + " ok.");
            else
                System.out.println("Level " + level.getClass() + " failed.");
            level = level.getNextLevel();
            if (level == null)
                break;
        }
    }
}
