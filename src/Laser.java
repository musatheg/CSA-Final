import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

import java.awt.*;

public class Laser extends Actor {
    private int direction;

    public Laser(int direction) {
        this.direction = direction;
        setColor(Color.YELLOW);
    }

    public void act() {
        Location next = getLocation().getAdjacentLocation(direction);

        if (!getGrid().isValid(next)) {
            removeSelfFromGrid();
            return;
        }

        Actor neighbor = getGrid().get(next);
        if (neighbor != null) {
            if (neighbor instanceof Alien) {
                ((Alien)neighbor).takeDamage(1);
            }
            removeSelfFromGrid();
            return;
        }

        moveTo(next);
    }
}
