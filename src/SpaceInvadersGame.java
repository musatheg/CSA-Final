import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class SpaceInvadersGame {
    private ActorWorld world;
    private Player player;
    private Alien[][] aliens;
    private AlienController alienController;
    private int score;
    private long startTime;
    private int difficulty;

    public SpaceInvadersGame(int difficulty) {
        this.difficulty = difficulty;
        // Create a bounded grid (typical Space Invaders size)
        world = new ActorWorld(new BoundedGrid<Actor>(30, 20));
        setupGame();
    }

    private void setupGame() {
        // Initialize game state
        score = 0;
        startTime = System.currentTimeMillis();

        // Add player at bottom center
        player = new Player(difficulty);
        world.add(new Location(29, 10), player);

        // Initialize aliens
        initializeAliens();

        // Set up the world
        world.setMessage("Score: 0 - Destroy all aliens!");
        world.show();
    }

    private void initializeAliens() {
        int rows = 3 + difficulty / 2;
        int cols = 5 + difficulty;
        aliens = new Alien[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                aliens[row][col] = new Alien(difficulty);
                world.add(new Location(row, col + 5), aliens[row][col]);
            }
        }

        alienController = new AlienController(aliens);
    }

    public void update() {
        // Move aliens
        alienController.moveAliens();

        // Check for game over conditions
        if (alienController.checkReachedPlayer(player.getLocation().getRow())) {
            gameOver(false);
        }

        if (alienController.allAliensDead()) {
            gameOver(true);
        }

        // Update score based on time
        updateScore();
    }

    private void updateScore() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - startTime;
        score = 10000 - (int)(timeElapsed / 1000) * difficulty * 10;
        world.setMessage("Score: " + score + " - Time: " + (timeElapsed / 1000) + "s");
    }

    private void gameOver(boolean won) {
        long timeElapsed = System.currentTimeMillis() - startTime;
        if (won) {
            score += 1000 * difficulty;
            JOptionPane.showMessageDialog(null, "You won! Final score: " + score +
                    "\nTime: " + (timeElapsed / 1000) + " seconds");
        } else {
            JOptionPane.showMessageDialog(null, "Game Over! The aliens reached you.\nFinal score: " + score);
        }
        world.setMessage("Game Over - Final Score: " + score);
        System.exit(0);
    }

    public Player getPlayer() {
        return player;
    }

    public static void main(String[] args) {
        // Set up difficulty selection
        int difficulty;
        try {
            difficulty = Integer.parseInt(JOptionPane.showInputDialog("Enter difficulty (1-5):"));
            difficulty = Math.max(1, Math.min(5, difficulty)); // Ensure difficulty is between 1-5
        } catch (NumberFormatException e) {
            difficulty = 3; // Default if invalid input
        }

        // Create and setup game
        SpaceInvadersGame game = new SpaceInvadersGame(difficulty);

        // Add keyboard listener
        game.world.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                game.getPlayer().handleKeyPress(e.getKeyCode());
            }
        });

        // Create game loop timer
        javax.swing.Timer timer = new javax.swing.Timer(100, e -> {
            game.update();
            game.world.show(); // Refresh the display
        });

        timer.start(); // Start the game loop
    }
}