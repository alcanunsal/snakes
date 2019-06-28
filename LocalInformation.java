package simulator;

import java.util.HashMap;
import java.util.List;
import game.Direction;
import snake.Node;

/**
 * Class representing the information a snake has about its surroundings.
 * Automatically created and passed by the game to each creature at each timer tick.
 */
public class LocalInformation {
	
	private int gridWidth;
	private int gridHeight;
    private HashMap<Direction, Node> neighbors;
	private List<Direction> freeDirections;
	private int foodX;
	private int foodY;
    
    /**
     * Constructs the local information for a snake
     * @param gridWidth width of the grid world
     * @param gridHeight height of the grid world
     * @param fullGrids mapping of directions to neighbor nodes
     * @param freeDirections list of free directions
     */
    LocalInformation (int gridWidth, int gridHeight, HashMap<Direction, Node> neighbors, List<Direction> freeDirections, int foodX, int foodY) {
    		this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.neighbors = neighbors;
        this.freeDirections = freeDirections;
        this.foodX = foodX;
        this.foodY = foodY;
    }
    
    /**
     * Getter for the width of the grid world.
     * Can be used to assess the boundaries of the world.
     * @return number of grid squares along the width
     */
    public int getGridWidth() {
		return gridWidth;
	}
    
    /**
     * Getter for the height of the grid world.
     * Can be used to assess the boundaries of the world.
     * @return number of grid squares along the height
     */
    public int getGridHeight() {
		return gridHeight;
	}
    
    /**
     * Returns the neighbor node one square up
     * @return Node or null if no creature exists
     */
    public Node getNodeUp () {
    		return neighbors.get(Direction.UP);
    }
    
    /**
     * Returns the neighbor node one square down
     * @return Node or null if no creature exists
     */
    public Node getNodeDown () {
		return neighbors.get(Direction.DOWN);
    }
    
    /**
     * Returns the neighbor node one square left
     * @return Node or null if no creature exists
     */
    public Node getNodeLeft () {
		return neighbors.get(Direction.LEFT);
    }
    
    /**
     * Returns the neighbor node one square right
     * @return Node or null if no creature exists
     */
    public Node getNodeRight () {
		return neighbors.get(Direction.RIGHT);
    }
    
    /**
     * Returns the list of free directions around the current position.
     * The list does not contain directions out of bounds or containing a full node.
     * Can be used to determine the directions available to move.
     * @return Node or null if no available node exists
     */
    public List<Direction> getFreeDirections() {
        return freeDirections;
    }
    
    /**
     * Utility function to get a randomly selected direction among multiple directions.
     * The selection is uniform random: All directions in the list have an equal chance to be chosen.
     * @param possibleDirections list of possible directions
     * @return direction randomly selected from the list of possible directions
     */
    public Direction getRandomDirection(List<Direction> possibleDirections) {
        if (possibleDirections.isEmpty()) {
            return null;
        }
        int randomIndex = (int)(Math.random() * possibleDirections.size());
        return possibleDirections.get(randomIndex);
    }
    
// METODU DOLDUR*****************************************************
    /**
     * Utility function to get a randomly selected direction among multiple directions.
     * The selection is uniform random: All directions in the list have an equal chance to be chosen.
     * @param possibleDirections list of possible directions
     * @return direction randomly selected from the list of possible directions
     */
    public static Direction chooseDirection(List<Direction> possibleDirections) {
        if (possibleDirections.isEmpty()) {
            return null;
        }
        return null;
    }
    
    public HashMap<Direction, Node> getNeighbors() {
		return neighbors;
	}
    
    public int getFoodX() {
		return foodX;
	}

	public int getFoodY() {
		return foodY;
	}
}
