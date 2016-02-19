/**
 * @file Player.java
 * @author Christopher Lovering
 * @date 21/11/2015
 * @brief Creates a player and stores there name for both human
 * and AI player upon further implementation
 */

/**
 * @class Player
 * @brief Creates a player and stores there name for both human
 * and AI player upon further implementation
 */
public abstract class Player {

	/** The name of the player */
	private String m_playerName;

	public Player(String name) {
		m_playerName = name;
	}

	/**
	 * Gets the player name 
	 * @return the player name
	 */
	public String getPlayerName() {
		return m_playerName;
	}

	/**
	 * Sets the player name
	 */
	public void setPlayerName(String name) {
		m_playerName = name;
	}

	/**
	 * Take a turn as this player
	 */
	public abstract void takeTurn();
}
