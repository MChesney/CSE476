package edu.msu.cse.boggle.droiddraw;

public class Player {
	
	/**
	 * TODO is this okay?
	 * The name of the bundle keys to save the puzzle
	 */
	public final static String PLAYERONENAME = "Player.one_name";
	public final static String PLAYERTWONAME = "Player.two_name";
	public final static String PLAYERONESCORE = "Player.one_score";
	public final static String PLAYERTWOSCORE = "Player.two_score";
	
	/*
	 * Player name
	 */
	private String name = "";
	
	/*
	 * Player score
	 */
	private int score = 0;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void incScore(int increment) {
		this.score += increment;
	}
	
}
