package com.qiao.scoreboard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import com.qiao.scoreboard.adapter.EventAdapter;
import com.qiao.scoreboard.data.Event;
import com.qiao.scoreboard.data.Team;
import com.qiao.scoreboard.net.HTTPRequestHelper;
import com.qiao.scoreboard.parser.EventXmlReader;
import com.qiao.scoreboard.parser.TeamXmlReader;
import com.qiao.scoreboard.R;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EventList extends ListActivity {

	private static final String TAG = "event list";
	private static final String BASE_URL = "http://liveeuro.sinaapp.com/detail.php?";
	
	public static Team team;
	
	private ProgressDialog progress;
	private ResponseHandler<String> event_response;
	private ResponseHandler<String> team_response;
	private List<Event> events;
	private EventAdapter adapter;
	private String mFid;
	private String mR;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		
		event_response = HTTPRequestHelper.getResponseHandlerInstance(EHandler, 0);
		team_response = HTTPRequestHelper.getResponseHandlerInstance(THandler, 0);
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onRestart");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStart");
		
		mFid = ScoreDetailActivity.mFid;
		mR = ScoreDetailActivity.mR;
		
		progress = ProgressDialog.show(this, "请稍候", "正在更新比赛即时数据...");
		progress.setCancelable(true);
		
		FetchTeamThread fetch = new FetchTeamThread();
		fetch.start();
		
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStop");
		super.onStop();
	}
	
	private Handler THandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Log.i(TAG, "receive team msg: " + msg.what);
			
			String xmlInfo = msg.getData().getString("RESPONSE");
			
			switch(msg.what){
			case 0:
				
				team = getTeamInfo(xmlInfo);
				
				break;
				
			default:
				Log.e(TAG, "net error: " + msg.what + " " + xmlInfo);
				break;
			}
			
			startEventThread();
		}		
	};
	
	private Handler EHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Log.i(TAG, "receive event msg: " + msg.what);
			
			progress.dismiss();
			
			String xmlInfo = msg.getData().getString("RESPONSE");
			
			switch(msg.what){
			case 0:
				
				events = getEventInfo(xmlInfo);
				
				if(events != null && events.size() > 0){
					adapter = new EventAdapter(EventList.this, events);
					setListAdapter(adapter);
				}
				
				break;
				
			default:
				Log.e(TAG, "net error: " + msg.what);
				notifyFromHandler();
				break;	
			}
			
		}
		
	};
	
	private class FetchEventThread extends Thread{

		private HTTPRequestHelper helper;
		
		public FetchEventThread(){
			helper = new HTTPRequestHelper(event_response);
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			StringBuilder sb = new StringBuilder(BASE_URL);
			
			sb.append("fid=" + mFid)
			  .append("&r=" + mR)
			  .append("&type=2");
			
			helper.performGet(sb.toString(), null, null, null);

		}
		
	}
	
	private class FetchTeamThread extends Thread{
		private HTTPRequestHelper helper;
		
		public FetchTeamThread(){
			helper = new HTTPRequestHelper(team_response);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			StringBuilder sb = new StringBuilder(BASE_URL);
			sb.append("fid=" + mFid)
			  .append("&r=" + mR)
			  .append("&type=1");
			
			helper.performGet(sb.toString(), null, null, null);
		}
		
	}
	
	private void notifyFromHandler(){
		Log.i(TAG, "notify the error msg");
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.toast_item, null);
		
		TextView toast_item = (TextView)view.findViewById(R.id.item);
		toast_item.setText(getResources().getString(R.string.net_error_label));
		
		Toast toast = new Toast(this);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}
	
	private List<Event> getEventInfo(String xml){
		
		Log.i(TAG, "start parser event info");
		
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		
		return EventXmlReader.readXml(in);
	}
	
	private Team getTeamInfo(String xml){
		Log.i(TAG, "parser team info");
		
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		
		try {
			return TeamXmlReader.readXml(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void startEventThread(){
		FetchEventThread fetch = new FetchEventThread();
		fetch.start();
	}
}
