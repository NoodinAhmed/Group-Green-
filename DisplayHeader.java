/**
 * @file DisplayHeader.java
 * @author Hal
 * @author Will
 * @date 1 Dec 2015
 * @brief Shows information to user about kablewie board
 */

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/** @class DisplayHeader
 *  @brief Shows information to user about kablewie board
 */
public class DisplayHeader extends JPanel {

	/**
	 * Sets number of mines
	 *
	 * @param bombCount Number of mines
	 */
	public void setBombCount(int bombCount) {
		m_mineCountLabel.setText("Mines: " + bombCount);
	}

	/**
	 * Sets number of diffused bombs shown
	 *
	 * @param diffused Number of diffused tiles
	 */
	public void setDiffused(int diffused) {
		m_diffusedCountLabel.setText("Diffused: " + diffused);
	}

	/**
	 * Sets number of hidden tiles
	 *
	 * @param hidden Number of hidden tiles
	 */
	public void setHidden(int hidden) {
		m_hiddenCountLabel.setText("Hidden: " + hidden);
	}

	/**
	 * Sets number of revealed tiles
	 *
	 * @param revealed Number of revealed tiles
	 */
	public void setRevealed(int revealed) {
		m_revealedCountLabel.setText("Revealed: " + revealed);
	}

	// interval for timer
	private static final int TIMER_INTERVAL = 1000;

	// positions of widgets
	private static final Rectangle PLAYER_NAME_LABEL_POS =
			new Rectangle(10, 10, 100, 20);
	private static final Rectangle MINE_COUNT_LABEL_POS =
			new Rectangle(130, 10, 100, 20);
	private static final Rectangle DIFFUSED_COUNT_LABEL_POS =
			new Rectangle(250, 10, 100, 20);
	private static final Rectangle HIDDEN_COUNT_LABEL_POS =
			new Rectangle(10, 30, 100, 20);
	private static final Rectangle REVEALED_COUNT_LABEL_POS =
			new Rectangle(130, 30, 100, 20);
	private static final Rectangle TIMER_LABEL_POS =
			new Rectangle(250, 30, 100, 20);

	// size of display header
	private static final Dimension SIZE = new Dimension(375, 75);

	// widgets to display
	private final JLabel m_playerNameLabel;
	private final JLabel m_mineCountLabel;
	private final JLabel m_diffusedCountLabel;
	private final JLabel m_hiddenCountLabel;
	private final JLabel m_revealedCountLabel;
	private final JLabel m_timerLabel;

	// data relating to the timer
	private final long m_startTime;
	private final Timer m_timer;

	/**
	 * Constructs object and sets up widgets
	 *
	 * @param playerName Name of player
	 */
	public DisplayHeader(String playerName) {

		// use absolute layout and set size of panel
		super(null);
		setSize(SIZE);

		// initialise widgets
		m_playerNameLabel = new JLabel(playerName);
		m_playerNameLabel.setBounds(PLAYER_NAME_LABEL_POS);
		add(m_playerNameLabel);

		m_mineCountLabel = new JLabel();
		m_mineCountLabel.setBounds(MINE_COUNT_LABEL_POS);
		add(m_mineCountLabel);

		m_diffusedCountLabel = new JLabel();
		m_diffusedCountLabel.setBounds(DIFFUSED_COUNT_LABEL_POS);
		add(m_diffusedCountLabel);

		m_hiddenCountLabel = new JLabel();
		m_hiddenCountLabel.setBounds(HIDDEN_COUNT_LABEL_POS);
		add(m_hiddenCountLabel);

		m_revealedCountLabel = new JLabel();
		m_revealedCountLabel.setBounds(REVEALED_COUNT_LABEL_POS);
		add(m_revealedCountLabel);

		m_timerLabel = new JLabel();
		m_timerLabel.setBounds(TIMER_LABEL_POS);
		add(m_timerLabel);

		// setup timer
		m_startTime = System.currentTimeMillis();

		m_timer = new Timer(TIMER_INTERVAL, e -> updateTimer());
		m_timer.start();
		updateTimer();

	}
	
	/**
	* Stops the timer
	*/
	public void stopTimer() {
		m_timer.stop();
	}
	
	/**
	 * Updates timer with elapsed time
	 */
	private void updateTimer() {

		// calculate time since start
		long elapsed = System.currentTimeMillis() - m_startTime;
		long hours = TimeUnit.MILLISECONDS.toHours(elapsed);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) - (hours * 60);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed) - (hours * 60)
				       - (minutes * 60);

		// show the time since start in HH:MM:SS format
		m_timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes,
										   seconds));
	}

}
