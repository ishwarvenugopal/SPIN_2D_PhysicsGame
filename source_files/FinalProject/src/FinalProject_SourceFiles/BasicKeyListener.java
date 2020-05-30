package FinalProject_SourceFiles;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BasicKeyListener extends KeyAdapter {

	/* Author: Michael Fairbank
	 * Creation Date: 2016-01-28
	 * Significant changes applied: 2020-04-04 by Ishwar Venugopal
	 */

	private static boolean RightArrowPressed, LeftArrowPressed, UpArrowPressed;

	public static boolean isRightArrowPressed() {
		return RightArrowPressed;
	}

	public static boolean isLeftArrowPressed() {
		return LeftArrowPressed;
	}

	public static boolean isUpArrowPressed() {
		return UpArrowPressed;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			UpArrowPressed=true;
			break;
		case KeyEvent.VK_LEFT:
			LeftArrowPressed=true;
			break;
		case KeyEvent.VK_RIGHT:
			RightArrowPressed=true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			UpArrowPressed=false;
			break;
		case KeyEvent.VK_LEFT:
			LeftArrowPressed=false;
			break;
		case KeyEvent.VK_RIGHT:
			RightArrowPressed=false;
			break;
		}
	}
}
