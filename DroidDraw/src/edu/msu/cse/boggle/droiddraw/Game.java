package edu.msu.cse.boggle.droiddraw;

public class Game {
	
	public static final String PLAYERONE = "playerOne";
	public static final String PLAYERTWO = "playerTwo";
	public static final String PLAYERSELF = "playerSelf";

	private static String playerOneName = "";
	private static String playerTwoName = "";
	private static String playerSelfName = "";
	private static String playerSelfPassword = "";
	private static int playerSelfNumber = -1;
	
	private static int playerOneScore = 0;
	private static int playerTwoScore = 0;
	
	private static int editor = 1;
	private static String hint = "";
	private static String answer = "";
	private static String category = "";
	
	// Waiting statuses
	public static final String WAITFORPLAYER = "Waiting for second player";
	public static final String WAITFORDRAW = "Waiting for player to draw";
	public static final String WAITFORGUESS = "Waiting for player to guess";
	public static String waitStatus = "";
	
	public static String getName(String player) {
		if (player.equals(PLAYERSELF)) {
			return playerSelfName;
		}
		if (player.equals(PLAYERONE)) {
			return playerOneName;
		}
		if (player.equals(PLAYERTWO)) {
			return playerTwoName;
		}
		return null;
	}
	
	public static void setName(String player, String name) {
		if (player.equals(PLAYERSELF)) {
			playerSelfName = name;
		}
		if (player.equals(PLAYERONE)) {
			playerOneName = name;
		}
		if (player.equals(PLAYERTWO)) {
			playerTwoName = name;
		}
	}
	public static String getPassword() {
		return playerSelfPassword;
	}

	public static void setPassword(String playerSelfPassword) {
		Game.playerSelfPassword = playerSelfPassword;
	}
	public static int getScore(String player) {
		if (player.equals(PLAYERONE)) {
			return playerOneScore;
		}
		if (player.equals(PLAYERTWO)) {
			return playerTwoScore;
		}
		return -1;
	}

	public static void setScore(String player, int score) {
		if (player.equals(PLAYERONE)) {
			playerOneScore += score;
		}
		if (player.equals(PLAYERTWO)) {
			playerTwoScore += score;
		}
	}
	
	public static void setCategory(String cat){
		category = cat;
	}
	public static String getCategory(){
		return category;
	}
	
	public static void setEditor(Integer cat){
		editor = cat;
	}
	public static Integer getEditor(){
		return editor;
	}
	
	
	
	public static String getHint() {
		return hint;
	}

	public static void setHint(String hint) {
		Game.hint = hint;
	}

	public static String getAnswer() {
		return answer;
	}

	public static void setAnswer(String answer) {
		Game.answer = answer;
	}

	public static void incScore(String player, int increment) {
		if (player.equals(PLAYERONE)) {
			playerOneScore += increment;
		}
		if (player.equals(PLAYERTWO)) {
			playerTwoScore += increment;
		}
	}
	
	public static int getSelfNumber() {
		return playerSelfNumber;
	}

	public static void setSelfNumber(int playerSelfNumber) {
		Game.playerSelfNumber = playerSelfNumber;
	}
	
	public static String getWaitStatus() {
		return waitStatus;
	}
	
	public static void setWaitStatus(String status) {
		waitStatus = status;
	}

	
}
