package simulator;

import game.Direction;
import game.GridGame;
import snake.Food;
import snake.Node;
import snake.Snake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Class that implements the game logic for Snakes.
 */
public class Simulator extends GridGame {
	
	private List<Snake> snakes;
	private Node[][] gameMap;
	protected int foodX;
	protected int foodY;
	
	/**
     * Creates a new Nature Simulator game instance
     * @param gridWidth number of grid squares along the width
     * @param gridHeight number of grid squares along the height
     * @param gridSquareSize size of a grid square in pixels
     * @param frameRate frame rate (number of timer ticks per second)
     */
    public Simulator (int gridWidth, int gridHeight, int gridSquareSize, int frameRate) {
        super(gridWidth, gridHeight, gridSquareSize, frameRate);
        snakes = new ArrayList<>();
        gameMap = new Node[gridWidth][gridHeight];
        // create food
        int a = (int) (Math.random() * super.getGridWidth());
		int b = (int) (Math.random() * super.getGridHeight());
		while (!isPositionFree(a,b)) {
			a = (int) (Math.random() * super.getGridWidth());
			b = (int) (Math.random() * super.getGridHeight());
		}
		Food food = new Food (a, b);
		foodX = a;
		foodY = b;
        addDrawable(food);
        updateGameMap(food.getX(), food.getY(), food);
        // create first snake
        Snake snake1 = new Snake ();
        snakes.add(snake1);
        addDrawable(snake1);
        for (Node n : snake1.getSnake()) {
        		updateGameMap(n.getX(), n.getY(), n);
        }
    }

	@Override
	protected void timerTick() {
		ArrayList<Snake> snakesCopy = new ArrayList<>(snakes);
		
		for (Snake snake: snakesCopy) {
			LocalInformation information = createLocalInformation(snake);			
			Action action = snake.chooseAction(information);
			if (action != null) {
				if (action.getType() == Action.Type.STAY) {
                    // STAY
                    snake.stay();
                } else if (action.getType() == Action.Type.DIVIDE) {
                        // DIVIDE
                        //I M P L E M E N T  T H I S ***********************************************
                } else if (action.getType() == Action.Type.MOVE) {
                		if (isDirectionFree(snake.getHead().getX(), snake.getHead().getY(), action.getDirection())) {
                		   removeDrawable(snake);
                       Node tail = snake.move(action.getDirection());
                       updateGameMap(snake.getHead().getX(), snake.getHead().getY(), snake.getHead());
                       updateGameMap(tail.getX(), tail.getY(), null);
                       addDrawable(snake);
                    }
                } else if (action.getType() == Action.Type.EAT) {
                		Node node = getNodeAtDirection(snake.getHead().getX(), snake.getHead().getY(), action.getDirection());
                		if (node instanceof Food) {
                			removeDrawable(snake);
                			updateGameMap(node.getX(), node.getY(), snake.getHead());
                			Node tail = snake.eat(node);
                			updateGameMap(tail.getX(),tail.getY(), null);
                			updateGameMap(snake.getHead().getX(), snake.getHead().getY(), snake.getHead());
                			addDrawable(snake);
                			reallocateFood(node);
                		}
                }
			}
		}		
	}
	
	private LocalInformation createLocalInformation(Snake snake) {
		
		int x = snake.getHead().getX();
		int y = snake.getHead().getY();
		
		// create a hashmap containing neighbor cells
		HashMap<Direction, Node> neighbors = new HashMap<>();
		neighbors.put(Direction.UP, getNodeAtPosition(x, y - 1));
		neighbors.put(Direction.DOWN, getNodeAtPosition(x, y + 1));
		neighbors.put(Direction.LEFT, getNodeAtPosition(x - 1, y));
		neighbors.put(Direction.RIGHT, getNodeAtPosition(x + 1, y));
		
		// create an arraylist containing free directions
		ArrayList<Direction> freeDirections = new ArrayList<>();
		if (isPositionInsideGrid(x, y - 1) && neighbors.get(Direction.UP) == null) {
            freeDirections.add(Direction.UP);
        }
        if (isPositionInsideGrid(x, y + 1) && neighbors.get(Direction.DOWN) == null) {
            freeDirections.add(Direction.DOWN);
        }
        if (isPositionInsideGrid(x - 1, y) && neighbors.get(Direction.LEFT) == null) {
            freeDirections.add(Direction.LEFT);
        }
        if (isPositionInsideGrid(x + 1, y) && neighbors.get(Direction.RIGHT) == null) {
            freeDirections.add(Direction.RIGHT);
        }
        return new LocalInformation(getGridWidth(), getGridHeight(), neighbors, freeDirections, foodX, foodY);
	}
	
	private boolean isPositionInsideGrid(int x, int y) {
        return (x >= 0 && x < getGridWidth()) && (y >= 0 && y < getGridHeight());
    }
	
	private void updateGameMap(int x, int y, Node node) {
        if (isPositionInsideGrid(x, y)) {
            gameMap[x][y] = node;
        }
    }
	
	private Node getNodeAtPosition(int x, int y) {
        if (!isPositionInsideGrid(x, y)) {
            return null;
        }
        return gameMap[x][y];
    }
	
	private Node getNodeAtDirection(int x, int y, Direction direction) {
        if (direction == null) {
            return null;
        }
        int xTarget = x;
        int yTarget = y;
        if (direction == Direction.UP) {
            yTarget--;
        } else if (direction == Direction.DOWN) {
            yTarget++;
        } else if (direction == Direction.LEFT) {
            xTarget--;
        } else if (direction == Direction.RIGHT) {
            xTarget++;
        }
        return getNodeAtPosition(xTarget, yTarget);
    }
	
	private boolean isPositionFree(int x, int y) {
        return isPositionInsideGrid(x, y) && getNodeAtPosition(x, y) == null;
    }
	
	private boolean isDirectionFree(int x, int y, Direction direction) {
        if (direction == null) {
            return false;
        }
        int xTarget = x;
        int yTarget = y;
        if (direction == Direction.UP) {
            yTarget--;
        } else if (direction == Direction.DOWN) {
            yTarget++;
        } else if (direction == Direction.LEFT) {
            xTarget--;
        } else if (direction == Direction.RIGHT) {
            xTarget++;
        }
        return isPositionFree(xTarget, yTarget);
    }
		
    private void reallocateFood (Node node) {
    		removeDrawable(node);
    		updateGameMap(node.getX(), node.getY(), null);
    		int a = (int) (Math.random() * super.getGridWidth());
		int b = (int) (Math.random() * super.getGridHeight());
		while (!isPositionFree(a,b)) {
			a = (int) (Math.random() * super.getGridWidth());
			b = (int) (Math.random() * super.getGridHeight());
		}
		node.setX(a);
		node.setY(b);
		foodX = a;
		foodY = b;
        addDrawable(node);
        updateGameMap(node.getX(), node.getY(), node);
    }
    
    public void addSnake (Snake snake) {
    		if(snake != null) {
    			boolean available = true;
    			for (Node n : snake.getSnake()) {
    				if (!isPositionInsideGrid(n.getX(), n.getY()) || gameMap[n.getX()][n.getY()] != null) {
    					available = false;
    				}
    			}
    			if (available) {
    				snakes.add(snake);
    				addDrawable(snake);
    				for (Node n : snake.getSnake()) {
    					updateGameMap(n.getX(), n.getY(), n);
    				}
    			}
    		}
    		
    }
    
    public void removeSnake (Snake snake) {
    		if (snake != null) {
    			for (Node n : snake.getSnake()) {
    				if (isPositionInsideGrid(n.getX(), n.getY())) {
    					updateGameMap(n.getX(), n.getY(), null);
    				}
    			}
    			removeDrawable(snake);
    		}
    		
    }
    
    /////LOCALINFORMATIONA FOODUN YERINI KOY
//    public Direction chooseBestDirection (LocalInformation information, int foodX, int foodY) {
//    		if (information.getFreeDirections().size() > 0) {
//    			
//    			if (information.getFreeDirections().size() == 1) {
//    				return information.getFreeDirections().get(0);
//    			}
//    			
//    			HashMap<Direction, Integer> distanceToFood = new HashMap<> ();
//    			for (Direction direction : information.getFreeDirections()) {
//    				Node neighbor = getNodeAtDirection(snake.getHead().getX(), snake.getHead().getY(), direction);
//    				int distance = Math.abs(neighbor.getX() - foodX) + Math.abs(neighbor.getY() - foodY);
//    				distanceToFood.put(direction, distance);
//    			}
//    			
//    			int minDistance = information.getGridHeight() + information.getGridWidth();
//    			Direction bestDirection = null;
//    			for (Direction d : distanceToFood.keySet()) {
//    				if (distanceToFood.get(d) < minDistance) {
//    					minDistance = distanceToFood.get(d);
//    					bestDirection = d;
//    				}
//    			}
//    			
//    			return bestDirection;
//    		}  		
//    		return null;
//    }
    
}
