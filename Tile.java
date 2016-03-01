/**
 * @file Tile.java
 * @author Fatima
 * @date 24/11/15
 * @brief Is an abstract implamentaion of the tile classes.
 */

import javax.swing.*;
import java.awt.*;

/**
 * @class Tile
 * @brief Is an abstract implamentaion of the tile classes.
 */
public abstract class Tile extends JPanel {
	
	/**
	* Gets number of bombs adjacent to current tile
	* 
	* @return Number of adjacent bombs
	*/
	public int getAdjacentBombs() {
		return -1;
	}
	
	/**
	* Gets the position of the current tile
	*
	* @return Position of tile
	*/
	public Point getPosition() {
		return m_position;
	}
	
	/**
	* Sets whether the tile is revealed
	*
	* @param revealed Whether tile is revealed
	*/
	protected void setRevealed(boolean revealed) {
		m_revealed = revealed;
	}

	
	
	
	//size constants
	public static final int PADDING = 2;
	public static final int TILE_LENGTH = 20;

	
	//Position and state of tile
	private final boolean m_bomb;
	private final Point m_position;

	private boolean m_diffused;
	private boolean m_revealed;

	//Colour constants
	protected static final Color DIFFUSED_COLOUR = Color.BLACK;
	protected static final Color HIDDEN_COLOUR = Color.LIGHT_GRAY;
	
	/**
	* Draws current tile
	*/
	public abstract void draw(Graphics g);
	
	/**
	* Gets whether the tile will cause the game to be lost
	*
	* @return True if tile causes the game to be lost, false otherwise
	*/
	public abstract boolean hasLost();

	/**
	* Gets whether the tile is bombed
	*
	* @return True if tile is bombed, false otherwise
	*/
	public boolean isBomb() {
		return m_bomb;
	}

	/**
	* Gets whether the tile is diffused
	*
	* @return True if tile is diffused, false otherwise
	*/
	public boolean isDiffused() {
		return m_diffused;
	}

	/**
	* Gets whether the tile is revealed
	*
	* @return True if tile is revealed, false otherwise
	*/
	public boolean isRevealed() {
		return m_revealed;
	}
	
	/**
	* Gets whether the tile allows the game to be won
	*
	* @return True if tile allows game to be won, false otherwise
	*/
	public abstract boolean isWinning();
	
	/**
	* Reveals tile, if it is not already revealed
	*/
	public void reveal() {
	
		// a tile can only be revealed once
		if (m_revealed) {
			return;
		}
		
		m_revealed = true;

		draw(getGraphics());
	}
	
	/**
	* Toggles whether tile is diffused, if it is not already revealed
	*/
	public void toggleDiffused(){

		//can't diffuse a revealed tile
		if (m_revealed) {
			return;
		}

		// toggle the diffused boolean
		m_diffused = !m_diffused;

		draw(getGraphics());
	}
	
	/**
	* Paints tile
	*
	* @param g Graphics to paint tile to
	*/
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	/**
	* Constructs tile object
	*
	* @param position Position of tile
	* @param bomb Whether current tile is bombed
	*/
	protected Tile(Point position, boolean bomb) {
		m_position = position;
		m_bomb = bomb;
	}
}
	
	
