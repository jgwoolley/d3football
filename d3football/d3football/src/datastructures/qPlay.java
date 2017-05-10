package datastructures;

import runner.*;
import datastructures.*;
import functions.*;
import outputs.*;


public class qPlay {
	private int nextScore;
	private int down;
	private int yardline;
	private int distance;
	private int play; // 0 is fieldgoal, 1 is goforit, 2 is punt

	public qPlay(){
		nextScore = 0;
		down = 0;
		play = 0;
		yardline = 0;
		distance = 0;
	}

	public int getNextScore() {
		return nextScore;
	}

	public void setNextScore(int nextScore) {
		this.nextScore = nextScore;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public int getYardline() {
		return yardline;
	}

	public void setYardline(int yardline) {
		this.yardline = yardline;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getPlay() {
		return play;
	}

	public void setPlay(int play) {
		this.play = play;
	}
	
	
}
