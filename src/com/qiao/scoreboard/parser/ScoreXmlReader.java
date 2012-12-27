package com.qiao.scoreboard.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.qiao.scoreboard.data.Contacts;
import com.qiao.scoreboard.data.Score;

import android.util.Log;
import android.util.Xml;

public class ScoreXmlReader {
	
	private static final String TAG = "XMLREADER";
	
	public static List<Score> readXml(InputStream inStream){
		
		XmlPullParser xpp = Xml.newPullParser();
		
		try{
			xpp.setInput(inStream, "UTF-8");
			int eventType = xpp.getEventType();
			Score currentScore = null;
			List<Score> scores = null;
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				switch(eventType){
				case XmlPullParser.START_DOCUMENT:
					scores = new ArrayList<Score>();
					break;
					
				case XmlPullParser.START_TAG:
					String name = xpp.getName();
					if(name.equalsIgnoreCase(Contacts.MATCH)){
						currentScore = new Score();
						currentScore.setId(new Integer(xpp.getAttributeValue(null, Contacts.ID)));
					}else if(currentScore != null){
						if(name.equalsIgnoreCase(Contacts.FID)){
							currentScore.setFid(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.R)){
							currentScore.setR(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.STATUS)){
							currentScore.setStatus(new Integer(xpp.next()));
						}else if(name.equalsIgnoreCase(Contacts.NAME)){
							currentScore.setName(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.ROUND)){
							currentScore.setRound(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.DATE)){
							currentScore.setDate(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.START)){
							currentScore.setStart(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.TIME)){
							currentScore.setTime(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.HOME)){
							currentScore.setHome(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.AWAY)){
							currentScore.setAway(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.SCORE)){
							currentScore.setScore(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.ODDS)){
							currentScore.setOdds(xpp.nextText());
						}
					}
					break;
					
				case XmlPullParser.END_TAG:
					if(xpp.getName().equalsIgnoreCase(Contacts.MATCH) && currentScore != null){
						scores.add(currentScore);
						currentScore = null;
					}
					break;
					
				default:
					break;
				}
				
				eventType = xpp.next();
			}
			
			inStream.close();
			return scores;
			
		}catch(Exception e){
			Log.e(TAG, e.getMessage());
		}
		
		return null;
	}
}
