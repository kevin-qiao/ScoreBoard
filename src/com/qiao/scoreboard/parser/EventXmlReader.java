package com.qiao.scoreboard.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.qiao.scoreboard.data.Contacts;
import com.qiao.scoreboard.data.Event;

import android.util.Log;
import android.util.Xml;

public class EventXmlReader {
	private static final String TAG = "event xml reader";
	
	public static List<Event> readXml(InputStream inStream){
		
		XmlPullParser xpp = Xml.newPullParser();
		
		try {
			xpp.setInput(inStream, "UTF-8");
			int eventType = xpp.getEventType();
			Event currentEvent = null;
			List<Event> events = null;
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				switch(eventType){
				case XmlPullParser.START_DOCUMENT:
					events = new ArrayList<Event>();
					break;
					
				case XmlPullParser.START_TAG:
					String name = xpp.getName();
					
					if(name.equalsIgnoreCase(Contacts.INFO)){
						currentEvent = new Event();
						
						currentEvent.setId(new Integer(xpp.getAttributeValue(null, Contacts.ID)));
						currentEvent.setStatus(new Integer(xpp.getAttributeValue(null, Contacts.STATUS)));
						currentEvent.setTeam(xpp.getAttributeValue(null, Contacts.TEAM));
						currentEvent.setTime(xpp.getAttributeValue(null, Contacts.TIME));
						currentEvent.setInfo(xpp.nextText());
					}
					break;
					
				case XmlPullParser.END_TAG:
					if(xpp.getName().equalsIgnoreCase(Contacts.INFO) && currentEvent != null){
						events.add(currentEvent);
						currentEvent = null;
					}
					break;
				}
				
				eventType = xpp.next();
			}
			
			inStream.close();
			return events;
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}
