package edu.msu.cse.boggle.droiddraw;

import java.io.Serializable;

import android.os.Bundle;

public class Players {
	
	public final static String PLAYERINFORMATION = "Players.information";
	public final static String PLAYERONE = "Players.one";
	public final static String PLAYERTWO = "Players.two";
	
	/*
	 * Player Information
	 */
	private PlayersInfo playersInfo = new PlayersInfo();
	
	public PlayersInfo getPlayersInfo() {
		return playersInfo;
	}

	public void setPlayersInfo(PlayersInfo playersInfo) {
		this.playersInfo = playersInfo;
	}

	private static final class PlayersInfo implements Serializable {
		// TODO ??? 
		private static final long serialVersionUID = 1;
		
		/*
		 * Player 1 name
		 */
		public String playerOneName = null;
		
		/*
		 * Player 2 name
		 */
		public String playerTwoName = null;
		
		/*
		 * Player 1 score
		 */
		public int playerOneScore = 0;
		
		/*
		 * Player 2 score
		 */
		public int playerTwoScore = 0;
	}
	
    /**
     * Save the view state to a bundle
     * @param key key name to use in the bundle
     * @param bundle bundle to save to
     */
	public void putToBundle(String key, Bundle bundle) {
		bundle.putSerializable(key, playersInfo);
	}
	
    /**
     * Load the view state from a bundle
     * @param key key name to use in the bundle
     * @param bundle bundle to get info from
     */
	public void getFromBundle(String key, Bundle bundle) {
		playersInfo = (PlayersInfo) bundle.getSerializable(key);
	}
	
	public String getName(String player) {
		if (player.equals(PLAYERONE)) {
			return playersInfo.playerOneName;
		}
		if (player.equals(PLAYERTWO)) {
			return playersInfo.playerTwoName;
		}
		return null;
	}
	
	public void setName(String player, String name) {
		if (player.equals(PLAYERONE)) {
			playersInfo.playerOneName = name;
		}
		if (player.equals(PLAYERTWO)) {
			playersInfo.playerTwoName = name;
		}
	}

	public int getScore(String player) {
		if (player.equals(PLAYERONE)) {
			return playersInfo.playerOneScore;
		}
		if (player.equals(PLAYERTWO)) {
			return playersInfo.playerTwoScore;
		}
		return -1;
	}

	public void setScore(String player, int score) {
		if (player.equals(PLAYERONE)) {
			playersInfo.playerOneScore = score;
		}
		if (player.equals(PLAYERTWO)) {
			playersInfo.playerTwoScore = score;
		}
	}
	
	public void incScore(String player, int increment) {
		if (player.equals(PLAYERONE)) {
			playersInfo.playerOneScore += increment;
		}
		if (player.equals(PLAYERTWO)) {
			playersInfo.playerTwoScore += increment;
		}
	}
	
}