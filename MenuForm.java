/**
 * @file MenuForm.java
 * @author Luke
 * @date 20 Nov 2015
 * @brief Gets information for the next game, Shows a dialogue
 * where the player enters their name, the board size and the 
 * number of mines for the upcoming game.
 */

import javax.swing.*;
import java.awt.*;

/** 
 * @class MenuForm
 * @brief Gets information for the next game, Shows a dialogue
 * where the player enters their name, the board size and the 
 * number of mines for the upcoming game.
 */
public class MenuForm extends JFrame {

    // Private

    // Input constraints
    private static final int MIN_BOARD_SIZE = 1;
    private static final int MAX_BOARD_SIZE = 30;
    private static final int DEFAULT_BOARD_SIZE = 10;
    private static final int BOARD_SIZE_INCREMENT = 1;
    private static final int MIN_BOMB_COUNT = 0;
    private static final int DEFAULT_BOMB_COUNT = 10;
    private static final int BOMB_COUNT_INCREMENT = 1;
	private static final int MAX_BOMBS = 150;

    // MenuForm dimensions
    private static final int FORM_WIDTH = 600;
    private static final int FORM_HEIGHT = 400;

	// Form component bounds
    private static final Rectangle WELCOME_LABEL_BOUNDS =
            new Rectangle(200, 30, 200, 30);
    private static final Rectangle PLAYER_NAME_LABEL_BOUNDS =
            new Rectangle(80, 100, 200, 20);
    private static final Rectangle BOARD_SIZE_LABEL_BOUNDS =
            new Rectangle(80, 150, 200, 20);
    private static final Rectangle BOMB_COUNT_LABEL_BOUNDS =
            new Rectangle(80, 200, 200, 20);
    private static final Rectangle PLAYER_NAME_FIELD_BOUNDS =
            new Rectangle(300, 100, 200, 20);
    private static final Rectangle BOARD_SIZE_SPINNER_BOUNDS =
            new Rectangle(300, 150, 200, 20);
    private static final Rectangle BOMB_COUNT_SPINNER_BOUNDS =
            new Rectangle(300, 200, 200, 20);
    private static final Rectangle START_BUTTON_BOUNDS = 
            new Rectangle(225, 270, 150, 30);

	/**
	* Constructs MenuForm
	*/
    public MenuForm() {

        // Set MenuForm size and title, and centre window
        setSize(FORM_WIDTH, FORM_HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Kablewie");

        // Create JPanel content pane with absolute layout
        JPanel form = new JPanel(null);
        setContentPane(form);

        // Setup labels
        JLabel welcomeLabel = new JLabel("Welcome to Kablewie!",
                                         SwingConstants.CENTER);
        welcomeLabel.setBounds(WELCOME_LABEL_BOUNDS);
        form.add(welcomeLabel);

        JLabel playerNameLabel = new JLabel("Enter your name:",
                                            SwingConstants.RIGHT);
        playerNameLabel.setBounds(PLAYER_NAME_LABEL_BOUNDS);
        form.add(playerNameLabel);

        JLabel boardSizeLabel = new JLabel("Board height and width:",
                                           SwingConstants.RIGHT);
        boardSizeLabel.setBounds(BOARD_SIZE_LABEL_BOUNDS);
        form.add(boardSizeLabel);

        JLabel bombCountLabel = new JLabel("Mine Count:",SwingConstants.RIGHT);
        bombCountLabel.setBounds(BOMB_COUNT_LABEL_BOUNDS);
        form.add(bombCountLabel);

        // Setup player name entry text box
        JTextField playerNameField = new JTextField();
        playerNameField.setBounds(PLAYER_NAME_FIELD_BOUNDS);
        form.add(playerNameField);

        // Setup board size entry spinner
        SpinnerModel boardSizeModel = new SpinnerNumberModel(
                DEFAULT_BOARD_SIZE, MIN_BOARD_SIZE, MAX_BOARD_SIZE,
                BOARD_SIZE_INCREMENT);
        JSpinner boardSizeSpinner = new JSpinner(boardSizeModel);
        boardSizeSpinner.setBounds(BOARD_SIZE_SPINNER_BOUNDS);
        form.add(boardSizeSpinner);

        // Setup bomb count entry spinner
        SpinnerModel bombCountModel = new SpinnerNumberModel(
                DEFAULT_BOMB_COUNT, MIN_BOMB_COUNT,
                (DEFAULT_BOARD_SIZE * DEFAULT_BOARD_SIZE) - 1,
                BOMB_COUNT_INCREMENT);
        JSpinner bombCountSpinner = new JSpinner(bombCountModel);
        bombCountSpinner.setBounds(BOMB_COUNT_SPINNER_BOUNDS);
        form.add(bombCountSpinner);

	    // Add listener to keep correct bomb count maximum
        boardSizeSpinner.addChangeListener(e -> {
            int boardSize = (int) boardSizeSpinner.getValue();
            int currentBombCount = (int) bombCountSpinner.getValue();

            if (currentBombCount > ((boardSize * boardSize) - 1)) {
                currentBombCount = (boardSize * boardSize) - 1;
            }

            bombCountSpinner.setModel(new SpinnerNumberModel(
                    currentBombCount, MIN_BOMB_COUNT,
                    Math.min((boardSize * boardSize) - 1, MAX_BOMBS), 1));
        });

        // Setup start button
        JButton startButton = new JButton("Start Game");
        startButton.setBounds(START_BUTTON_BOUNDS);
        form.add(startButton);

        startButton.addActionListener(e -> {
            MainForm main = new MainForm(playerNameField.getText(),
                                         (int) bombCountSpinner.getValue(),
                                         (int) boardSizeSpinner.getValue());
            setVisible(false);
            main.setVisible(true);
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
