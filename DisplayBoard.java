/**
 * @file DisplayBoard.java
 * @author Victoria Charvis
 * @date 21 Nov 2015
 * <p>
 * @brief Displays tiles in a grid and handles user interaction
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.*;


/**
 * @class DisplayBoard
 * @brief Displays tiles in a grid and handles user interaction
 */
public class DisplayBoard extends JPanel implements MouseListener {

	// stores all the internal information for the board
	private final Board m_board;
	private final DisplayHeader m_header;
	private final MainForm m_mainForm;

	/**
	 * Constructs a DisplayBoard object
	 * Calls drawBoard to set up the board
	 *
	 * @param header    the display header
	 * @param boardSize the width/height of the board
	 * @param bombCount the amount of bombs in the board
	 */
	public DisplayBoard(DisplayHeader header, MainForm mainform, int boardSize,
	                    int bombCount) {
		m_header = header;
		m_mainForm = mainform;
		m_board = new Board(bombCount, boardSize);
		drawBoard(boardSize);

		m_header.setBombCount(m_board.getBombCount());
		m_header.setHidden(boardSize * boardSize);
		m_header.setRevealed(0);
		m_header.setDiffused(0);

	    header.m_showBombTileCheckBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
			m_board.showBombTile(true);
		}
		else m_board.showBombTile(false);
            }
        });

	}

	/**
	 * Sets up the tiles and displays them on a grid
	 * 
	 * @param boardSize the width/height of the board
	 */
	public void drawBoard(int boardSize) {

		// set the panel to Grid Layout and the amount of rows, columns and
		// gaps between components
		setLayout(new GridLayout(boardSize, boardSize, Tile.PADDING,
				                 Tile.PADDING));

		// set the tiles to be added in left to right order
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		m_board.addTilesToDisplay(this);
	}

	/**
	 * Determines what happens when a tile is clicked
	 *
	 * @param e is an instance of MouseEvent that holds a user action
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		Tile tile = (Tile) e.getSource();
		Point position = tile.getPosition();
		if (e.getButton() == MouseEvent.BUTTON1) {
			m_board.reveal((int) position.getX(), (int) position.getY());

			// you can only lose from revealing, so we'll check that here
			if (m_board.isLost()) {
				m_mainForm.gameOver(false);
				return;
			}

		} else if (e.getButton() == MouseEvent.BUTTON3) {
			m_board.toggleDiffused((int) position.getX(),
					               (int) position.getY());
		}

		updateDisplay();

		// you can win by revealing or diffusing, so we'll check that now
		if (m_board.isWon()) {
			m_mainForm.gameOver(true);
		}
		
	}

	
	
	//Unused event handlers

	@Override
	public void mouseEntered(MouseEvent e) {

	}
	
	@Override
	public void mouseExited(MouseEvent e) {

	}	
	
	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Updates header display with latest values
	 */
	private void updateDisplay() {
		m_header.setDiffused(m_board.getDiffused());
		m_header.setHidden(m_board.getHidden());
		m_header.setRevealed(m_board.getRevealed());
	}

}
