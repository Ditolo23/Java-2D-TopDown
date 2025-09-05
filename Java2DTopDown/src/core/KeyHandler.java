package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean enterPressed;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// WASD MOVEMENT, when I'm pressing a button
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();

		// TITLE STATE
		if (gp.gameState == gp.titleState) {

			if (code == KeyEvent.VK_W)
				if (gp.ui.getCommandNum() == 0) {
					// do nothing
				} else {
					gp.ui.setCommandNum(gp.ui.getCommandNum() - 1);
				}
			if (code == KeyEvent.VK_S)
				if (gp.ui.getCommandNum() == 3) {
					// do nothing or add something
				} else {
					gp.ui.setCommandNum(gp.ui.getCommandNum() + 1);
				}
			if (code == KeyEvent.VK_ENTER) {
				switch (gp.ui.getCommandNum()) {
				case 0:
					gp.gameState = gp.playState;
					gp.playMusic(3);
					break;
				case 1:
					// load state
					break;
				case 2:
					gp.gameState = gp.creditState;
					break;
				case 3:
					// quit
					System.exit(0);

				}
			}
		}
		
		if(gp.gameState == gp.creditState) {
			if(code == KeyEvent.VK_ESCAPE)
				gp.gameState = gp.titleState;
		}

		// PLAYSTATE
		if (gp.gameState == gp.playState) {
			if (code == KeyEvent.VK_W)
				upPressed = true;
			if (code == KeyEvent.VK_S)
				downPressed = true;
			if (code == KeyEvent.VK_A)
				leftPressed = true;
			if (code == KeyEvent.VK_D)
				rightPressed = true;
			if (code == KeyEvent.VK_ESCAPE)
			{
				gp.gameState = gp.pauseState;
				
				gp.stopMusic();
				gp.playPauseMusic();
			
			}
			if (code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}

		} else if (gp.gameState == gp.pauseState) {
			pauseStateToPlay(code);
			if(gp.gameState == gp.playState)
			{
				gp.stopPauseMusic();
				gp.resumeMusic();
			}
		} else if (gp.gameState == gp.dialogueState) {
			dialogueStateToPlay(code);
		}

		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_W)
				if (gp.ui.getCommandNumPause() == 0) {

				} else {
					gp.ui.setCommandNumPause(gp.ui.getCommandNumPause() - 1);
				}
			if (code == KeyEvent.VK_S)
				if (gp.ui.getCommandNumPause() == 4) {

				} else {
					gp.ui.setCommandNumPause(gp.ui.getCommandNumPause() + 1);
				}

			if (code == KeyEvent.VK_ENTER) {

				switch (gp.ui.getCommandNumPause()) {
				case 0:
					gp.gameState = gp.playState;
					gp.stopPauseMusic();
					gp.resumeMusic();
					break;
				case 1:
					// nothing for now
					break;
				case 2:
					// nothing for now 
					break;
				case 3:
					// nothing for now
					break;
				case 4:
					gp.gameState = gp.titleState;
					gp.stopPauseMusic();
					break;

				}
			}
		}
	}

	// When the button is released
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W)
			upPressed = false;
		if (code == KeyEvent.VK_S)
			downPressed = false;
		if (code == KeyEvent.VK_A)
			leftPressed = false;
		if (code == KeyEvent.VK_D)
			rightPressed = false;

	}

	public void pauseStateToPlay(int code) {
		if (code == KeyEvent.VK_ESCAPE)
			gp.gameState = gp.playState;
	}

	public void dialogueStateToPlay(int code) {
		if (code == KeyEvent.VK_ENTER)
			gp.gameState = gp.playState;
	}

}
