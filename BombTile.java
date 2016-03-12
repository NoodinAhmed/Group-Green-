/**
 * @file Tile.java
 * @author Fatima
 * @date 24/11/15
 * @see Tile.java
*/

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @class BombTile
 * @brief Stores the location of the bomb tiles and
 * how to animate them.
 */
public class BombTile extends Tile {

	//variable which allows the user to show the tile
	public boolean m_show_bomb_tile ;

	/**
	* Constructs object
	*
	* @param position Position of tile in board
	*/
	public BombTile(Point position) {
		super(position, true);
	}

	/**
	* Draws current tile on graphics object
	*
	* @param g Graphics object to draw tile on
	*/
	@Override
	public void draw(Graphics g) {
	
		if ( isRevealed() || isCheating() ) {
			g.setColor(Color.RED);
		} else if (isDiffused()) {
			g.setColor(DIFFUSED_COLOUR);
		} else {
			g.setColor(HIDDEN_COLOUR);
		}

		g.fillRect(0, 0, TILE_LENGTH, TILE_LENGTH);

		if (isRevealed()) {

			// we probably want to make this look more interesting
			g.setColor(Color.WHITE);
			Rectangle2D size = g.getFontMetrics().getStringBounds("*", g);
			int width = (int) ((Tile.TILE_LENGTH - size.getWidth()) / 2);
			int height = (int) size.getHeight();
			g.drawString("*", width, height);
		}
	}

	/**
	* Determines whether this tile will cause the game to be lost
	*
	* @return True if if tile has been revealed, false otherwise
	*/
	@Override
	public boolean hasLost() {
		return isRevealed();
	}
	
	/**
	* Determines whether this tile will allow the game to be won
	*
	* @return True if tile is diffused, false otherwise
	*/
	@Override
	public boolean isWinning() {
		return isDiffused();
	}
	
	/**
	* state of the show function
	*
	* @return True if show function is on, false otherwise
	*/
	public boolean isCheating() {
		return m_show_bomb_tile;
	}
	

	/**
	* shows the tile if s is true 
	*/
	public void show( boolean s ) {
		m_show_bomb_tile = s ;
		draw(getGraphics());
	}

}

