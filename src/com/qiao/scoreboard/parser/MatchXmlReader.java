package com.qiao.scoreboard.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.qiao.scoreboard.data.Contacts;
import com.qiao.scoreboard.data.Match;

public class MatchXmlReader {
	private static final String TAG = "match xml reader";
	
	public static List<Match> readXml(InputStream inStream){
		
		XmlPullParser xpp = Xml.newPullParser();
		
		try {
			xpp.setInput(inStream, "UTF-8");
			int eventType = xpp.getEventType();
			Match currentMatch = null;
			List<Match> matches = null;
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				switch(eventType){
				case XmlPullParser.START_DOCUMENT:
					matches = new ArrayList<Match>();
					break;
					
				case XmlPullParser.START_TAG:
					String name = xpp.getName();
					
					if(name.equalsIgnoreCase(Contacts.MATCH)){
						
					}else if(name.equalsIgnoreCase(Contacts.INFO)){
						currentMatch = new Match();
						
						currentMatch.setId(new Integer(xpp.getAttributeValue(null, Contacts.ID)));
					}else if(currentMatch != null){
						if(name.equalsIgnoreCase(Contacts.DATE)){
							currentMatch.setDate(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.WEEK)){
							currentMatch.setWeek(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.TIME)){
							currentMatch.setTime(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.ROUND)){
							currentMatch.setRound(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.HOME)){
							currentMatch.setHome(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.AWAY)){
							currentMatch.setAway(xpp.nextText());
						}
					}
					
					break;
					
				case XmlPullParser.END_TAG:
					
					if(xpp.getName().equalsIgnoreCase(Contacts.INFO) && currentMatch != null){
						matches.add(currentMatch);
						
						currentMatch = null;
					}
					
					break;
				}
				
				eventType = xpp.next();
			}
			
			inStream.close();
			return matches;
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}
