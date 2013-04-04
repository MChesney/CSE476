package edu.msu.cse.boggle.droiddraw;

public class Cloud {

    private static final String MAGIC = "NechAtHa6RuzeR8x";
    private static final String USER = "chesne14";
    private static final String PASSWORD = "drowssap";
    private static String GCMID = "";
    
    // I believe these two are done
    // To add user - USER_ADD_URL + "?user=" + USER + "&magic=" + MAGIC + "&pw=" + PASSWORD  + $gcm + GCMID; 
    private static final String USER_ADD_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/user-add.php";
    // To login user - USER_LOGIN_URL + "?user=" + USER + "&magic=" + MAGIC + "&pw=" + PASSWORD + $gcm + GCMID;
    private static final String USER_LOGIN_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/user-login.php";
    
    // I haven't touched these two yet
    private static final String DRAWING_SAVE_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/drawing-save.php";
    private static final String DRAWING_LOAD_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/drawing-load.php";
    private static final String UTF8 = "UTF-8";
	
	public Cloud() {
		// TODO Auto-generated constructor stub
	}
	
	// This might be a really bad way to do this...
	public static void setGcmId(String id) {
		GCMID = id;
	}
	
	

}
