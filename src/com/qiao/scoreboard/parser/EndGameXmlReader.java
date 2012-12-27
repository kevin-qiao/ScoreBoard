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
import com.qiao.scoreboard.data.EndGame;
import com.qiao.scoreboard.data.Game;

public class EndGameXmlReader {
	private static final String TAG = "end game xml reader";
	
	public static List<EndGame> readXml(InputStream inStream){
		
		Log.i(TAG, "start parser end game xml");
		
		XmlPullParser xpp = Xml.newPullParser();
		
		try {
			xpp.setInput(inStream, "UTF-8");
			int eventType = xpp.getEventType();
			Game currentGame = null;
			List<Game> games = null;
			EndGame currentEndGame = null;
			List<EndGame> EndGames = null;			

			while(eventType != XmlPullParser.END_DOCUMENT){
				switch(eventType){
				case XmlPullParser.START_DOCUMENT:
					EndGames = new ArrayList<EndGame>();
					break;
					
				case XmlPullParser.START_TAG:
					String name = xpp.getName();
					
					if(name.equalsIgnoreCase(Contacts.DATE)){
						currentEndGame = new EndGame();
						games = new ArrayList<Game>();
						
						currentEndGame.setExpanded(false);
						currentEndGame.setId(
								new Integer(xpp.getAttributeValue(null, Contacts.ID)));
						currentEndGame.setDate(
								xpp.getAttributeValue(null, Contacts.VALUE));
					}else if(name.equalsIgnoreCase(Contacts.GAME)){
						currentGame = new Game();
						
						currentGame.setId(
								new Integer(xpp.getAttributeValue(null, Contacts.ID)));
					}else if(currentGame != null){
						if(name.equalsIgnoreCase(Contacts.ROUND)){
							currentGame.setRound(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.HOME)){
							currentGame.setHome(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.AWAY)){
							currentGame.setAway(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.SCORE)){
							currentGame.setScore(xpp.nextText());
						}
					}
					break;
					
				case XmlPullParser.END_TAG:
					if(xpp.getName().equalsIgnoreCase(Contacts.GAME) && 
							currentGame != null){
						games.add(currentGame);
						currentGame = null;
					}else if(xpp.getName().equalsIgnoreCase(Contacts.DATE)){
						currentEndGame.setGames(games);
						EndGames.add(currentEndGame);
						
						currentEndGame = null;
						games = null;
					}
					break;
				}
				
				eventType = xpp.next();
			}
			
			inStream.close();
			return EndGames;
			
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
