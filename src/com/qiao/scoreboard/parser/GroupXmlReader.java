package com.qiao.scoreboard.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.qiao.scoreboard.data.Contacts;
import com.qiao.scoreboard.data.Group;
import com.qiao.scoreboard.data.TeamData;

public class GroupXmlReader {
	public static List<Group> readXml(InputStream inStream){
		
		XmlPullParser xpp = Xml.newPullParser();
		
		try {
			xpp.setInput(inStream, "UTF-8");
			int eventType = xpp.getEventType();
			Group currentGroup = null;
			List<Group> groups = null;
			TeamData currentData = null;
			List<TeamData> data = null;
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				switch(eventType){
				case XmlPullParser.START_DOCUMENT:
					groups = new ArrayList<Group>();
					break;
					
				case XmlPullParser.START_TAG:
					String name = xpp.getName();
					if(name.equalsIgnoreCase(Contacts.GROUP)){
						data = new ArrayList<TeamData>();
						currentGroup = new Group();
						
						currentGroup.setId(new Integer(
								xpp.getAttributeValue(null, Contacts.ID)));
						currentGroup.setName(xpp.getAttributeValue(null, Contacts.NAME));
					}else if(name.equalsIgnoreCase(Contacts.TEAM)){
						currentData = new TeamData();
						currentData.setId(new Integer(
								xpp.getAttributeValue(null, Contacts.ID)));
						currentData.setName(xpp.getAttributeValue(null, Contacts.NAME));
					}else if(currentData != null){
						if(name.equalsIgnoreCase(Contacts.GAMES)){
							currentData.setGames(new Integer(xpp.nextText()));
						}else if(name.equalsIgnoreCase(Contacts.WIN)){
							currentData.setWin(new Integer(xpp.nextText()));
						}else if(name.equalsIgnoreCase(Contacts.DRAW)){
							currentData.setDraw(new Integer(xpp.nextText()));
						}else if(name.equalsIgnoreCase(Contacts.LOSE)){
							currentData.setLose(new Integer(xpp.nextText()));
						}else if(name.equalsIgnoreCase(Contacts.GOALS)){
							currentData.setGoals(new Integer(xpp.nextText()));
						}else if(name.equalsIgnoreCase(Contacts.DROP)){
							currentData.setDrop(new Integer(xpp.nextText()));
						}else if(name.equalsIgnoreCase(Contacts.POINTS)){
							currentData.setPoints(new Integer(xpp.nextText()));
						}
					}
					break;
					
				case XmlPullParser.END_TAG:
					if(xpp.getName().equalsIgnoreCase(Contacts.TEAM) && 
							currentData != null){
						data.add(currentData);
						currentData = null;
					}else if(xpp.getName().equalsIgnoreCase(Contacts.GROUP) && data != null){
						currentGroup.setData(data);
						groups.add(currentGroup);
						
						currentGroup = null;
						data = null;
					}
					break;
				}
				
				eventType = xpp.next();
			}
			
			inStream.close();
			return groups;
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
