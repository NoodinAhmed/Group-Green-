/**
 * @file MainForm.java
 * @author Alex
 * @date 25 Nov 2015
 * 
 * @brief Displays the main form of the game
 */

import javax.swing.*;
import java.awt.*;

/**
 * @class MainForm
 * @brief Displays the main form of the game
 */
public class MainForm extends JFrame {

	private static final int HEADER_WIDTH = 375;
	private static final int HEADER_HEIGHT = 75;
	private static final int ANIMATION_SPEED = 5;
	
	// header that displays information to user
	private final DisplayHeader m_header;

	// Kablewie board
	private final DisplayBoard m_board;
	
	private final String m_playerName;
	private final int m_bombCount;
	private final int m_boardSize;
	private Timer m_animation;
	
	/**
	 * Constructs object
	 * @param playerName Name of the human player
	 * @param bombCount Number of mines to be placed on the board
	 * @param boardSize Width and height of Kablewie board
	 */
	public MainForm(String playerName, int bombCount, int boardSize) {
		super("Kablewie");

		// get form size
		int boardLength = (boardSize * (Tile.TILE_LENGTH + Tile.PADDING))
						  + Tile.PADDING;
		int width = Math.max(HEADER_WIDTH, boardLength);
	
		m_playerName = playerName;
		m_bombCount = bombCount;
		m_boardSize = boardSize;
		
		// create content panel
		JPanel panel = new JPanel(null);
		panel.setPreferredSize(new Dimension(width, HEADER_HEIGHT
													+ boardLength));
		setContentPane(panel);
		pack();

		//Create header display
		m_header = new DisplayHeader(playerName);
		m_header.setBounds((getWidth() - HEADER_WIDTH) / 2, 0, width, HEADER_HEIGHT);
		panel.add(m_header);

		//Create board display
		m_board = new DisplayBoard(m_header, this, boardSize, bombCount);

		// determine the board's x-coordinate
		int boardX;
		if (width > boardLength) {
			boardX = (HEADER_WIDTH - boardLength) / 2;
		} else {
			boardX = 0;
		}

		m_board.setBounds(boardX, HEADER_HEIGHT, boardLength, boardLength);
		panel.add(m_board);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	/**
	* Informs user game is over and asks them to play again
	*
	* @param won Specifies if user won the game
	*/
	public void gameOver(boolean won) {
		
		//Show animation
		m_animation = new Timer(1, e -> {
			int boardTop = m_board.getY() + ANIMATION_SPEED;
			if(boardTop <= getHeight()) {
				m_board.setLocation(m_board.getX(), boardTop);
			} else {

				//If animation is finished ask user to play again
				m_animation.stop();
				showRestart(won);
			}
		});
		
		m_animation.start();
		m_animation.setInitialDelay(0);
		m_header.stopTimer();
		
	}
	
	/**
	* Prompts the user if they want to play again
	*
	* @param won Whether the previous game was won
	*/
	private void showRestart(boolean won) {
		String prompt;
		int answer;

		//Ask if user wants to play again
		if(won) {
			prompt = "You won! Do you want to play again?";
		} else {
			prompt = "You lost! Do you want to play again?";
		}

		answer = JOptionPane.showConfirmDialog(this, prompt, "Kablewie",
											   JOptionPane.YES_NO_OPTION);
		
		//If user said yes, show new game, else exit
		dispose();
		
		if(answer == JOptionPane.YES_OPTION) {
			MainForm newGame = new MainForm(m_playerName, m_bombCount,
											m_boardSize);
			newGame.setVisible(true);
		} else {
			System.exit(0);
		}
	}
	
}
