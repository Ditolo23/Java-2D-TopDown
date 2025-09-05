package core;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setTitle("Bergolio's adventures");

		// window.setLayout(new BorderLayout());

		GamePanel gamePanel = new GamePanel();
		// window.add(gamePanel, BorderLayout.CENTER);
		window.add(gamePanel);

		window.setLocationRelativeTo(null);
		// window.setSize(800, 600);

		window.pack();

		window.setVisible(true);

		gamePanel.setupOBJ();
		gamePanel.startGameThread();

	}

}

/*
 * Realizzato nella seconda e terza settimana di Agosto anno 2025
 */
