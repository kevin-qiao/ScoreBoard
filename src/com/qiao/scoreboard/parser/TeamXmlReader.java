package com.qiao.scoreboard.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.qiao.scoreboard.data.Contacts;
import com.qiao.scoreboard.data.Player;
import com.qiao.scoreboard.data.Team;

import android.util.Log;
import android.util.Xml;

public class TeamXmlReader {
	
	private static final String TAG = "team xml reader";
	
	public static Team readXml(InputStream inStream) throws IOException{
		
		XmlPullParser xpp = Xml.newPullParser();
		
		try {
			xpp.setInput(inStream, "UTF-8");
			int eventType = xpp.getEventType();
			Team team = null;
			Player currentPlayer = null;
			List<Player> players = null;
			
			Integer id = 1;
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				switch(eventType){
				case XmlPullParser.START_DOCUMENT:
					team = new Team();
					break;
					
				case XmlPullParser.START_TAG:
					String name = xpp.getName();
					if(name.equalsIgnoreCase(Contacts.HOME)){
						id = new Integer(xpp.getAttributeValue(null, Contacts.ID));
						team.setHome(xpp.getAttributeValue(null, Contacts.NAME));
					}else if(name.equalsIgnoreCase(Contacts.AWAY)){
						id = new Integer(xpp.getAttributeValue(null, Contacts.ID));
						team.setAway(xpp.getAttributeValue(null, Contacts.NAME));
					}else if(name.equalsIgnoreCase(Contacts.FIRST)){
						players = new ArrayList<Player>();
					}else if(name.equalsIgnoreCase(Contacts.RESERVE)){
						players = new ArrayList<Player>();
					}else if(name.equalsIgnoreCase(Contacts.PLAYER)){
						currentPlayer = new Player();
						
						currentPlayer.setIndex(new Integer(xpp.getAttributeValue(null, Contacts.INDEX)));
						currentPlayer.setNumber(new Integer(xpp.getAttributeValue(null, Contacts.NUMBER)));
						currentPlayer.setName(xpp.nextText());
					}
					break;
					
				case XmlPullParser.END_TAG:
					if(xpp.getName().equalsIgnoreCase(Contacts.PLAYER) && currentPlayer != null &&
							players != null){
						players.add(currentPlayer);
						currentPlayer = null;
					}else if(xpp.getName().equalsIgnoreCase(Contacts.FIRST) && players != null){
						if(id == 1){
							team.setHomeFirst(players);
						}else if(id == 2){
							team.setAwayFirst(players);
						}
						
						players = null;
					}else if(xpp.getName().equalsIgnoreCase(Contacts.RESERVE) && players != null){
						if(id == 1){
							team.setHomeReserve(players);
						}else if(id == 2){
							team.setAwayReserve(players);
						}
						
						players = null;
					}
					break;
				}
				
				eventType = xpp.next();
			}
			
			inStream.close();
			return team;
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}
