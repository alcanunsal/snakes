package main;

import java.awt.EventQueue;

import simulator.Simulator;
import snake.Snake;
import ui.ApplicationWindow;


public class Main {
	
	/**
     * Main entry point for the application.
     *
     * IMPORTANT: You can change anything in this method to test your game,
     * but your changes will be discarded when grading your project.
     *
     * @param args application arguments
     */
	public static void main (String[] args) {
		EventQueue.invokeLater(() -> {
            try {
                // Create game
                // You can change the world width and height, size of each grid square in pixels or the game speed
                Simulator game = new Simulator(10, 10, 50, 2);


                // Create application window that contains the game panel
                ApplicationWindow window = new ApplicationWindow(game.getGamePanel());
                window.getFrame().setVisible(true);

                // Start game
                game.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

}
