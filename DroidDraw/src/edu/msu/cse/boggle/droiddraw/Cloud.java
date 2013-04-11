package edu.msu.cse.boggle.droiddraw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

public class Cloud {

    private static final String MAGIC = "NechAtHa6RuzeR8x";
    //private static final String USER = "chesne14";
    //private static final String PASSWORD = "drowssap";
    private static String GCMID = "";

	// I believe these two are done
    // To add user - USER_ADD_URL + "?user=" + USER + "&magic=" + MAGIC + "&pw=" + PASSWORD  + $gcm + GCMID; 
    private static final String USER_ADD_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/user-add.php";
    // To login user - USER_LOGIN_URL + "?user=" + USER + "&magic=" + MAGIC + "&pw=" + PASSWORD + $gcm + GCMID;
    private static final String USER_LOGIN_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/user-login.php";
    
    // I haven't touched these two yet
    private static final String DRAWING_SAVE_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/drawing-save.php";
    // To load drawing - DRAWING_LOAD_URL + "?user=" + USER + "&magic=" + MAGIC + "&pw=" + PASSWORD + $drawid + DRAWID;
    private static final String DRAWING_LOAD_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/drawing-load.php";
    
    // To notify of end game - END_GAME_URL + "?user=" + USER + "&magic=" + MAGIC + "&pw=" + PASSWORD;
    private static final String END_GAME_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/end-game.php";
    private static final String UTF8 = "UTF-8";
	
	public Cloud() {
		// TODO Auto-generated constructor stub
	}
	
	// This might be a really bad way to do this...
	public static void setGcmId(String id) {
		GCMID = id;
	}
	
    public static String getGCMID() {
		return GCMID;
	}

    public boolean XMLParser(InputStream stream){
    	/**
         * Create an XML parser for the result
         */
        try {
            XmlPullParser xmlR = Xml.newPullParser();
            xmlR.setInput(stream, UTF8);
            
            xmlR.nextTag();      // Advance to first tag
            xmlR.require(XmlPullParser.START_TAG, null, "droiddraw");
            
            String status = xmlR.getAttributeValue(null, "status");
            if(status.equals("no")) {
                return false;
            }
            
            // We are done
        } catch(XmlPullParserException ex) {
            return false;
        } catch(IOException ex) {
            return false;
        }
        return true;
    }
    
    public boolean addUser (String username, String password){
    	
    	String query = USER_ADD_URL + "?user=" + username + "&magic=" + MAGIC + "&pw=" + password  + "&gcm=" + GCMID;
    	
    	try {
            URL url = new URL(query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                return false;
            }
            
            InputStream stream = conn.getInputStream();
//            logStream(stream);
            return XMLParser(stream);

        } catch (MalformedURLException e) {
            // Should never happen
            return false;
        } catch (IOException ex) {
            return false;
        }
    }
    
    public boolean loginUser (String username, String password){
    	
    	String query = USER_LOGIN_URL + "?user=" + username + "&magic=" + MAGIC + "&pw=" + password + "&gcm=" + GCMID;
    	
    	try {
            URL url = new URL(query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                return false;
            }
            
            InputStream stream = conn.getInputStream();
//            logStream(stream);
            return XMLParser(stream);

        } catch (MalformedURLException e) {
            // Should never happen
            return false;
        } catch (IOException ex) {
            return false;
        }
    }
    
    public boolean addDrawing (DrawView view){
    	XmlSerializer xml = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        
        try {
            xml.setOutput(writer);
            
            xml.startDocument("UTF-8", true);
            
            xml.startTag(null, "droiddraw");
            xml.attribute(null, "user", Game.getName(Game.PLAYERSELF));
            xml.attribute(null, "pw", Game.getPassword());
            xml.attribute(null, "magic", MAGIC);
            xml.attribute(null, "hint", Game.getHint());
            xml.attribute(null, "answer", Game.getAnswer());
            xml.attribute(null, "category", Game.getCategory());
            view.saveXml(xml);
            xml.endTag(null, "droiddraw");
            xml.endDocument();
             
        } catch (IOException e) {
            // This won't occur when writing to a string
            return false;
        }
        
        final String xmlStr = writer.toString();
        
        /*
         * Convert the XML into HTTP POST data
         */
        String postDataStr;
        try {
            postDataStr = "xml=" + URLEncoder.encode(xmlStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        
        /*
         * Send the data to the server
         */
        byte[] postData = postDataStr.getBytes();
        
        InputStream stream = null;
        try {
            URL url = new URL(DRAWING_SAVE_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
            conn.setUseCaches(false);

            OutputStream out = conn.getOutputStream();
            out.write(postData);
            out.close();

            int responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                return false;
            } 
            
            stream = conn.getInputStream();
//            logStream(stream);
            return XMLParser(stream);
            
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException ex) {
            return false;
        } finally {
            if(stream != null) {
                try {
                    stream.close();
                } catch(IOException ex) {
                    // Fail silently
                }
            }
        }
    }
    
    public static void logStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream));
    
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                Log.e("476", line);
            }
        } catch (IOException ex) {
            return;
        }
    }
}
