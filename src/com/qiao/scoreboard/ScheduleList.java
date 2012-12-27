package com.qiao.scoreboard;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.qiao.scoreboard.adapter.ScheduleAdapter;
import com.qiao.scoreboard.data.Match;
import com.qiao.scoreboard.parser.MatchXmlReader;
import com.qiao.scoreboard.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

public class ScheduleList extends ListActivity {

	private static final String TAG = "schedule list";
	private List<Match> matches;
	private ScheduleAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Log.i(TAG, "onCreate");
		
		this.setTitle(getResources().getString(R.string.schedule_title));
		
		matches = new ArrayList<Match>();
		
		matches = getMatches();
		
		if(matches != null){
			adapter = new ScheduleAdapter(this, matches);
			
			setListAdapter(adapter);
			
			initListSelection();
		}
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
		
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStop");
		
		super.onStop();
	}
	
	private List<Match> getMatches(){
		
		InputStream in = getResources().openRawResource(R.raw.schedule);
		
		if(in == null){
			return null;
		}
		
		return MatchXmlReader.readXml(in);
	}
	
	private void initListSelection(){
		Log.i(TAG, "start init list selection" );
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		int position = 0;
		
		try {
			Date today = new Date();
			Date start = format.parse("2012年6月8日");
			Date end = format.parse("2012年7月2日");
			
			if(compareDays(today, start) <= 0){
				position = 0;
			}else if(compareDays(today, end) > 0){
				position = 0;
			}else{
				for(int i = 0; i < adapter.getCount(); i++){
					StringBuilder sb = new StringBuilder("2012年");
					sb.append(matches.get(i).getDate());
					
					Date get_date = format.parse(sb.toString());
					
					if(compareDays(today, get_date) <= 0){
						position = i;
						break;
					}
				}
			}
			
			this.setSelection(position);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private int compareDays(Date today, Date dest){
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(today);
		
		int day_today = cal.get(Calendar.DAY_OF_YEAR);
		
		cal.setTime(dest);
		
		int day_dest = cal.get(Calendar.DAY_OF_YEAR);
		
		return (day_today - day_dest);
	}
}
