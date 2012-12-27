package com.qiao.scoreboard.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.qiao.scoreboard.data.Contacts;
import com.qiao.scoreboard.data.TopScorer;

public class TopXmlReader {
	public static List<TopScorer> readXml(InputStream inStream){
		
		XmlPullParser xpp = Xml.newPullParser();
		
		try {
			xpp.setInput(inStream, "UTF-8");
			int eventType = xpp.getEventType();
			TopScorer currentTop = null;
			List<TopScorer> tops = null;
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				switch(eventType){
				case XmlPullParser.START_DOCUMENT:
					tops = new ArrayList<TopScorer>();
					break;
					
				case XmlPullParser.START_TAG:
					String name = xpp.getName();
					
					if(name.equalsIgnoreCase(Contacts.PLAYER)){
						currentTop = new TopScorer();
						currentTop.setId(
								new Integer(xpp.getAttributeValue(null, Contacts.ID)));
					}else if(currentTop != null){
						if(name.equalsIgnoreCase(Contacts.NAME)){
							currentTop.setName(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.NATION)){
							currentTop.setNation(xpp.nextText());
						}else if(name.equalsIgnoreCase(Contacts.GOALS)){
							currentTop.setGoals(xpp.nextText());
						}
					}
					
					break;
					
				case XmlPullParser.END_TAG:
					
					if(xpp.getName().equalsIgnoreCase(Contacts.PLAYER) && 
							currentTop != null){
						tops.add(currentTop);
						currentTop = null;
					}
					
					break;
				}
				
				eventType = xpp.next();
			}
			
			inStream.close();
			return tops;
			
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
