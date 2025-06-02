import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;

public class AlienController extends Actor {
    private Alien[][] aliens;
    private int direction; // 1 for right, -1 for left
    private int stepsUntilDescend;
    private int stepsMoved;

    public AlienController(Alien[][] aliens) {
        this.aliens = aliens;
        direction = 1;
        stepsUntilDescend = 10;
        stepsMoved = 0;
    }

    public void moveAliens() {
        if (stepsMoved >= stepsUntilDescend) {
            descendAliens();
            direction *= -1; // Reverse direction
            stepsMoved = 0;
        } else {
            moveHorizontally();
            stepsMoved++;
        }
    }

    private void moveHorizontally() {
        Grid<Actor> grid = aliens[0][0].getGrid();
        for (int row = 0; row < aliens.length; row++) {
            for (int col = 0; col < aliens[row].length; col++) {
                if (aliens[row][col] != null) {
                    Location current = aliens[row][col].getLocation();
                    Location next = new Location(current.getRow(), current.getCol() + direction);

                    if (grid.isValid(next) && grid.get(next) == null) {
                        aliens[row][col].moveTo(next);
                    }
                }
            }
        }
    }

    private void descendAliens() {
        Grid<Actor> grid = aliens[0][0].getGrid();
        for (int row = 0; row < aliens.length; row++) {
            for (int col = 0; col < aliens[row].length; col++) {
                if (aliens[row][col] != null) {
                    Location current = aliens[row][col].getLocation();
                    Location next = new Location(current.getRow() + 1, current.getCol());

                    if (grid.isValid(next) && grid.get(next) == null) {
                        aliens[row][col].moveTo(next);
                    }
                }
            }
        }
    }

    public boolean checkReachedPlayer(int playerRow) {
        for (Alien[] row : aliens) {
            for (Alien alien : row) {
                if (alien != null && alien.getLocation().getRow() >= playerRow) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean allAliensDead() {
        for (Alien[] row : aliens) {
            for (Alien alien : row) {
                if (alien != null) {
                    return false;
                }
            }
        }
        return true;
    }
}