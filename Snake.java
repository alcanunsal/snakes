package snake;

import simulator.Action;
import simulator.LocalInformation;
import ui.GridPanel;
import game.Direction;
import game.Drawable;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;


public class Snake implements Drawable {

	private LinkedList<Node> snake;

	/**
	 * Creates the first snake in the game.
	 */
	public Snake () {
		snake = new LinkedList<Node> ();

		for (int i = 0; i < 4; i++) {
			Node bodyNode = new Node (4-i, 1);
			snake.add(bodyNode);
		}
	}

	public Node move (Direction direction) {
		if (direction == Direction.UP) {
			Node head = new Node (snake.peekFirst().getX(), snake.peekFirst().getY()-1);
			snake.addFirst(head);
			head = snake.peekFirst();
		}
		if (direction == Direction.DOWN) {
			Node head = new Node(snake.peekFirst().getX(), snake.peekFirst().getY()+1);
			snake.addFirst(head);
			head = snake.peekFirst();
		}
		if (direction == Direction.LEFT) {
			Node head = new Node(snake.peekFirst().getX()-1, snake.peekFirst().getY());
			snake.addFirst(head);
			head = snake.peekFirst();
		}
		if (direction == Direction.RIGHT) {
			Node head = new Node(snake.peekFirst().getX()+1, snake.peekFirst().getY());
			snake.addFirst(head);
			head = snake.peekFirst();
		}
		return snake.removeLast();
	}

	public void eat (Node node) {
		snake.addFirst(new Node (node.getX(), node.getY()));
	}

	public void stay () {

	}

	public Action chooseAction (LocalInformation information) {

		boolean isFoodNearby = false;
		HashMap<Direction, Node> neighbors = information.getNeighbors();
		Direction foodDirection = null;
		for (Direction d : neighbors.keySet()) {
			if (neighbors.get(d) == null) {
				continue;
			}
			if (neighbors.get(d) instanceof Food) {
				isFoodNearby = true;
				foodDirection = d;
			} 
		}
		if (snake.size() >= 8) {
			return new Action (Action.Type.DIVIDE);
		}
		if (isFoodNearby) {
			return new Action(Action.Type.EAT, foodDirection);
		} else {
			if (!information.getFreeDirections().isEmpty()) {
				return new Action(Action.Type.MOVE, chooseBestDirection(information));
			} else {
				return new Action(Action.Type.STAY);
			} 
		}
	}



	public Node getHead () {
		return snake.peekFirst();
	}

	@Override
	public void draw(GridPanel panel) {
		for (Node n : snake) {
			panel.drawSquare(n.getX(), n.getY(), Color.GRAY);
		}
		panel.drawSquare(snake.peekFirst().getX(), snake.peekFirst().getY(), Color.RED);
	}

	public LinkedList<Node> getSnake() {
		return snake;
	}

	// eski olan metot
	//	public Direction chooseBestDirection (LocalInformation information) {
	//		if (information.getFreeDirections().size() > 0) {
	//			
	//			if (information.getFreeDirections().size() == 1) {
	//				return information.getFreeDirections().get(0);
	//			}
	//			
	//			HashMap<Direction, Integer> distanceToFood = new HashMap<> ();
	//			for (Direction direction : information.getFreeDirections()) {
	//				int targetX = getHead().getX();
	//				int targetY = getHead().getY();
	//				
	//				// get the neighbor node in given direction
	//				if (direction == Direction.UP) {
	//					targetY--;
	//				} else if (direction == Direction.DOWN) {
	//					targetY++;
	//				} else if (direction == Direction.LEFT) {
	//					targetX--;
	//				} else {
	//					targetX++;
	//				}
	//				int distance = (int)Math.sqrt((targetX - information.getFoodX())*(targetX - information.getFoodX())
	//						+ (targetY - information.getFoodY())*(targetY - information.getFoodY()));
	//				distanceToFood.put(direction, distance);
	//			}
	//			
	//			int minDistance = information.getGridHeight() + information.getGridWidth();
	//			Direction bestDirection = null;
	//			for (Direction d : distanceToFood.keySet()) {
	//				if (distanceToFood.get(d) < minDistance) {
	//					minDistance = distanceToFood.get(d);
	//					bestDirection = d;
	//				}
	//			}	
	//			return bestDirection;
	//		}  		
	//		return null;
	//	}

	public Direction chooseBestDirection (LocalInformation information) {
		if (information.getFreeDirections().size() <= 0) {
			return null;
		}

		if (information.getFreeDirections().size() == 1) {
			return information.getFreeDirections().get(0);
		}

		int snakeX = getHead().getX();
		int snakeY = getHead().getY();
		int foodX = information.getFoodX();
		int foodY = information.getFoodY();
		int dx = foodX-snakeX;
		int dy = foodY - snakeY;

		if (Math.abs(dx) > Math.abs(dy)) {
			if (dx > 0) {
				if (information.getFreeDirections().contains(Direction.RIGHT))
					return Direction.RIGHT;
			} else {
				if (information.getFreeDirections().contains(Direction.LEFT))
					return Direction.LEFT;
			}
		} else {
			if (dy > 0) {
				if (information.getFreeDirections().contains(Direction.DOWN))
					return Direction.DOWN;
			} else {
				if (information.getFreeDirections().contains(Direction.UP))
					return Direction.UP;
			}
		}
		System.out.println("xxx");
		int index = (int) Math.random()*information.getFreeDirections().size();
		return information.getFreeDirections().get(index);
	}


}
