/**
 * @file Kablewie.java
 * @author Hal
 * @date 23 Nov 2015
 * 
 * @brief Launches Kablewie game
 */

import javax.swing.JFrame;
/**
 * @class Kablewie
 * @brief  Launches Kablewie game
 */
public class Kablewie {

	/**
	 * Launches MenuForm, which takes game details and then starts game
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {

            //Create and display menu form
            MenuForm menu = new MenuForm();
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setVisible(true);
        });
	}
}
