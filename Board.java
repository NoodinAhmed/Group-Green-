/**
 * @file Board.java
 * @author Hal
 * @author Kit
 * @date 23 Nov 2015
 * <p>
 * @brief Stores information about the state of the Kablewie board
 */

import java.awt.*;
import java.util.Random;
/**
 * @class Board
 * @brief Stores information about the state of the Kablewie board
 */
 
public class Board {

    /**
     * Gets number of bombs on board
     *
     * @return Number of bombs
     */
    public int getBombCount() {
        return m_bombCount;
    }

    /**
     * Gets number of diffused tiles on board
     *
     * @return Number of diffused tiles
     */
    public int getDiffused() {
        return m_diffused;
    }

    /**
     * Gets number of hidden tiles on board
     *
     * @return Number of hidden tiles
     */
    public int getHidden() {
        return m_hidden;
    }

    /**
     * Gets number of revealed tiles on board
     *
     * @return Number of revealed tiles
     */
    public int getRevealed() {
        return m_revealed;
    }

    // maximum allowable board size
    private static final int MAX_BOARD_SIZE = 30;

    // stores the tiles on the board
    private final Tile[][] m_tiles;

    // holds the width and height of the board
    private final int m_boardSize;

    // holds number of bombs on board
    private final int m_bombCount;

    // holds number of tiles in given state
    private int m_diffused;
    private int m_hidden;
    private int m_revealed;
    

	/**
	* Adds all tiles to a DisplayBoard object
	*
	* @param board DisplayBoard to add tiles to
	*/
    public void addTilesToDisplay(DisplayBoard board) {
        for (Tile[] tiles : m_tiles) {
            for (Tile tile : tiles) {
                board.add(tile);
                tile.addMouseListener(board);
            }
        }
    }


	
    /**
     * Constructs board object
     *
     * @param bombCount Number of bombs to place in board
     * @param boardSize Width and height of board
     */
    public Board(int bombCount, int boardSize) {
        int bombs;
        Random rnd = new Random();

        // check the parameters are valid
        if ((boardSize <= 0) || (boardSize > MAX_BOARD_SIZE)) {
            throw new IllegalArgumentException("Invalid boardSize: "
		                                       + boardSize);
        }

        if ((bombCount < 0) || (bombCount >= (boardSize * boardSize))) {
            throw new IllegalArgumentException("Invalid bombCount: "
                                               + bombCount);
        }

        // initialise variables
        this.m_bombCount = bombCount;
        m_boardSize = boardSize;
        m_tiles = new Tile[boardSize][boardSize];

        m_diffused = 0;
        m_hidden = boardSize * boardSize;
        m_revealed = 0;

        // setup bombs
        bombs = 0;

        while (bombs < bombCount) {

            // get random x,y coordinates
            int x = rnd.nextInt(boardSize);
            int y = rnd.nextInt(boardSize);

            // if that tile is not already a bomb, make it one
            if (m_tiles[x][y] == null) {
                m_tiles[x][y] = new BombTile(new Point(x, y));
                bombs++;
            }
        }

        // make all the other tiles empty
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {

                // check if we already set this tile to be a bomb
                if (m_tiles[x][y] == null) {
                    m_tiles[x][y] = new EmptyTile(new Point(x, y),
                            getAdjacentCount(x, y));
                }
            }
        }
    }

    /**
     * Gets a boolean indicating the game is lost
     *
     * @return True if game is lost, false otherwise
     */
    public boolean isLost() {
        for (Tile[] tileRow : m_tiles) {
            for (Tile tile : tileRow) {
                if (tile.hasLost()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Gets a boolean indicating the game is won
     *
     * @return True if game is won, false otherwise
     */
    public boolean isWon() {
        for (Tile[] tileRow : m_tiles) {
            for (Tile tile : tileRow) {
                if (!tile.isWinning()) {
                    return false;
                }
            }
        }

        return true;
    }
	
	/**
     * Reveals a tile
     *
     * @param x X coordinate of tile
     * @param y Y coordinate of tile
     */
    public void reveal(int x, int y) {
        Tile tile = m_tiles[x][y];

        if (!tile.isDiffused() && !tile.isRevealed()) {
            tile.reveal();
            m_revealed++;
            m_hidden--;

            if (tile.getAdjacentBombs() == 0) {
                revealAdjacent(x, y);
            }
        }
    }

	
   /**

     * show a bombtile

     */
    public void showBombTile( boolean s ) {

	
	 for (Tile[] tileRow : m_tiles) {
            for (Tile tile : tileRow) {
               if( tile != null && tile.isBomb()  ){
			tile.show(s);
		}
            }
        }

       
    }




    /**
     * Diffuses a tile
     *
     * @param x X coordinate of tile
     * @param y Y coordinate of tile
     */
    public void toggleDiffused(int x, int y) {

        // check is tile is already revealed
        if (m_tiles[x][y].isRevealed()) {
            return;
        }

        m_tiles[x][y].toggleDiffused();

        if (m_tiles[x][y].isDiffused()) {
            m_diffused++;
        } else {
            m_diffused--;
        }
    }
	
	/**
     * Gets the number of bombs adjacent to a tile
     *
     * @param x X coordinate of tile
     * @param y Y coordinate of tile
     * @return The number of adjacent bombs
     */
    private int getAdjacentCount(int x, int y) {
        int bombs = 0;

        // loop through the adjacent x columns
        for (int i = x - 1; i <= (x + 1); i++) {

            // loop through adjacent y rows
            for (int j = y - 1; j <= (y + 1); j++) {

                // check tile is valid (e.g not off edge)
                if ((i < m_boardSize) && (i >= 0)
                        && (j < m_boardSize) && (j >= 0)) {

                    // if tile is a bomb, increment counter
                    if ((m_tiles[i][j] != null) && m_tiles[i][j].isBomb()) {
                        bombs++;
                    }
                }

            }
        }

        return bombs;
    }
	    /**
     * Reveals tiles adjacent to a given tile
     *
     * @param x X coordinate of tile
     * @param y Y coordinate of tile
     */
    private void revealAdjacent(int x, int y) {
        for (int i = x - 1; i <= (x + 1); i++) {
            for (int j = y - 1; j <= (y + 1); j++) {

                // check that the tile is in the board
                if ((i < m_boardSize) && (i >= 0)
                        && (j < m_boardSize) && (j >= 0)) {

                    // check that we aren't revealing the already revealed tile
                    if ((x != i) || (y != j)) {
                        reveal(i, j);
                    }
                }
            }
        }
    }
}
