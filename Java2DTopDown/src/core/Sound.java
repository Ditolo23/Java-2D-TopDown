package core;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	Clip clip; 
	Clip clip2;
	URL soundURL[] = new URL[30];
	
	// test attribute for stopping and resuming a soundtrack
	private int currentFrame;
	
	// constructor, each index holds a soundtrack
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/unlock.wav");
		soundURL[1] = getClass().getResource("/sound/unlock.wav");
		soundURL[2] = getClass().getResource("");
		soundURL[3] = getClass().getResource("/sound/FriariellADormm.wav");
	}
	
	
	//choose the soundtrack
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			AudioInputStream ais2 = AudioSystem.getAudioInputStream(getClass().getResource("/sound/Bensound - Elevator Bossa Nova ver2.wav"));
			clip2 = AudioSystem.getClip();
			clip2.open(ais2);
			
		} catch (Exception e) {
			
		}
		
	}
	
	//plays the soundtrack
	public void play() {
		clip.start();
	}
	
	//loops the soundtrack
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	//stops the soundtrack
	public void stop() {
		clip.stop();
	}
	
	//stops the soundtrack and keeps the frame in case one wants to resume
	public void stop2() {
		currentFrame = clip.getFramePosition();
		clip.stop();
	}
	
	public void resume() {
		clip.setFramePosition(currentFrame);
		clip.start();
	}
	
	// pause Music
	public void playPauseMusic() {
		clip2.start();
	}
	
	public void loopPauseMusic() {
		clip2.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopPauseMusic() {
		clip2.stop();
	}

	public int getCurrentFrame() {
		return currentFrame;
	}


	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	
	
}
