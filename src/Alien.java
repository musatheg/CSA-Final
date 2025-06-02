import info.gridworld.actor.Actor;
import java.awt.Color;
import java.util.ArrayList;

public class Alien extends Actor {
    private int health;
    private int maxHealth;
    private int pointsValue;

    public Alien(int difficulty) {
        // Health scales with difficulty plus random variation
        maxHealth = 1 + difficulty + (int)(Math.random() * difficulty);
        health = maxHealth;
        pointsValue = 10 * difficulty + maxHealth * 5;

        // Set color based on health (redder = stronger)
        int redValue = Math.min(255, 100 + maxHealth * 30);
        int greenValue = Math.max(0, 255 - maxHealth * 40);
        setColor(new Color(redValue, greenValue, 0));
    }

    public void act() {
        // Movement handled by AlienController
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            removeSelfFromGrid();
            // Add to score through game manager
        }
    }

    public int getPointsValue() {
        return pointsValue;
    }
}
