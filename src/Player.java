import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock; // We'll repurpose Rock for lasers
import info.gridworld.grid.Location;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class Player extends Actor {
    private long lastShotTime;
    private int shootCooldown; // in milliseconds
    private int difficulty;

    public Player(int difficulty) {
        this.difficulty = difficulty;
        setColor(Color.GREEN);
        lastShotTime = 0;
        shootCooldown = 500 - (difficulty * 50); // Faster shooting at higher difficulty
    }

    public void act() {
        // Player doesn't act automatically, only responds to key presses
    }

    public void move(int direction) {
        Location next = getLocation().getAdjacentLocation(direction);
        if (getGrid().isValid(next)) {
            moveTo(next);
        }
    }

    public void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= shootCooldown) {
            Laser laser = new Laser(getLocation().getDirectionToward(new Location(0, getLocation().getCol())));
            getGrid().put(getLocation().getAdjacentLocation(Location.NORTH), laser);
            lastShotTime = currentTime;
        }
    }

    // Handle key presses from the world
    public void handleKeyPress(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                move(Location.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                move(Location.EAST);
                break;
            case KeyEvent.VK_SPACE:
                shoot();
                break;
        }
    }
}