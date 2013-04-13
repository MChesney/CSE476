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
    
    // To add user - USER_ADD_URL + "?user=" + USER + "&magic=" + MAGIC + "&pw=" + PASSWORD  + $gcm + GCMID; 
    private static final String USER_ADD_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/user-add.php";
    // To login user - USER_LOGIN_URL + "?user=" + USER + "&magic=" + MAGIC + "&pw=" + PASSWORD + $gcm + GCMID;
    private static final String USER_LOGIN_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/user-login.php";
    
    private static final String DRAWING_SAVE_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/drawing-save.php";
    // To load drawing - DRAWING_LOAD_URL + "?user=" + USER + "&magic=" + MAGIC + "&pw=" + PASSWORD + $drawid + DRAWID;
    private static final String DRAWING_LOAD_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/drawing-load.php";
    
    private static final String UPDATE_SCORES_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/guess-save-load.php";
    
    // To notify of end game - END_GAME_URL + "?user=" + USER + "&magic=" + MAGIC + "&pw=" + PASSWORD;
    private static final String END_GAME_URL = "https://www.cse.msu.edu/~chesne14/teamcranium/end-game.php";
    private static final String UTF8 = "UTF-8";
	
	public Cloud() {
		// TODO Auto-generated constructor stub
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
    	
    	String query = USER_ADD_URL + "?user=" + username + "&magic=" + MAGIC + "&pw=" + password  + "&gcm=" + Game.getGcmId();
    	
    	return XMLParser(getInputStream(query));
    }
    
    public boolean loginUser (String username, String password){
    	
    	String query = USER_LOGIN_URL + "?user=" + username + "&magic=" + MAGIC + "&pw=" + password + "&gcm=" + Game.getGcmId();
    	
    	return XMLParser(getInputStream(query));
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
            xml.attribute(null, "p1score", Integer.toString(Game.getScore(Game.PLAYERONE)));
            xml.attribute(null, "p2score", Integer.toString(Game.getScore(Game.PLAYERONE)));
            view.saveXml(xml);
            xml.endTag(null, "droiddraw");
            xml.endDocument();
             
        } catch (IOException e) {
            // This won't occur when writing to a string
        	android.util.Log.d("IOException1", e.toString());
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
        	android.util.Log.d("MalformedURLException1", e.toString());
            return false;
        } catch (IOException ex) {
        	android.util.Log.d("IOException2", ex.toString());
            return false;
        } finally {
            if(stream != null) {
                try {
                    stream.close();
                } catch(IOException ex) {
                    // Fail silently
                	android.util.Log.d("IOException3", ex.toString());
                }
            }
        }
    }
    
    public InputStream loadDrawing() {
    	String query = DRAWING_LOAD_URL + "?user=" + Game.getName(Game.PLAYERSELF) + "&magic=" + MAGIC + "&pw=" + Game.getPassword() + "&drawid=" + Game.getDrawID();
    	return getInputStream(query);
    }
    
    public boolean updateScores() {
    	String query = UPDATE_SCORES_URL + "?user=" + Game.getName(Game.PLAYERSELF) + "&magic=" + MAGIC + "&pw=" + Game.getPassword() + "&p1score=" + Game.getScore(Game.PLAYERONE) + "&p2score=" + Game.getScore(Game.PLAYERTWO);
    	return XMLParser(getInputStream(query));
    }
    
    public static void logStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream));
    
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                Log.i("476", line);
            }
        } catch (IOException ex) {
            return;
        }
    }
    
    /**
     * Skip the XML parser to the end tag for whatever 
     * tag we are currently within.
     * @param xml the parser
     * @throws IOException
     * @throws XmlPullParserException
     */
    public static void skipToEndTag(XmlPullParser xml) throws IOException, XmlPullParserException {
        int tag;
        do
        {
            tag = xml.next();
            if(tag == XmlPullParser.START_TAG) {
                // Recurse over any start tag
                skipToEndTag(xml);
            }
        } while(tag != XmlPullParser.END_TAG && 
        tag != XmlPullParser.END_DOCUMENT);
    }
    
    public InputStream getInputStream(String query) {
    	try {
            URL url = new URL(query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
            
            InputStream stream = conn.getInputStream();
            //logStream(stream);
            return stream;

        } catch (MalformedURLException e) {
            // Should never happen
            return null;
        } catch (IOException ex) {
            return null;
        }
    }
}
