package edu.msu.cse.boggle.droiddraw;

public class Game {
	
	public static String PLAYERONE = "playerOne";
	public static String PLAYERTWO = "playerTwo";
	public static String PLAYERSELF = "playerSelf";
	
	private static String playerOneName = "";
	private static String playerTwoName = "";
	private static String playerSelf = "";
	private static int playerSelfNumber = -1;
	
	private static int playerOneScore = 0;
	private static int playerTwoScore = 0;
	
	private static int editor = 1;
	private static String hint = "";
	private static String answer = "";
	private static String category = "";
	
	public static String getName(String player) {
		if (player.equals(PLAYERSELF)) {
			return playerSelf;
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
			playerSelf = name;
		}
		if (player.equals(PLAYERONE)) {
			playerOneName = name;
		}
		if (player.equals(PLAYERTWO)) {
			playerTwoName = name;
		}
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

	
}
