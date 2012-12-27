package com.qiao.scoreboard.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.qiao.scoreboard.data.Match;
import com.qiao.scoreboard.R;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScheduleAdapter extends BaseAdapter {
	
	private static final String TAG = "schedule adapter";
	
	private Context context;
	private List<Match> matches;

	public ScheduleAdapter(Context context, List<Match> matches){
		this.context = context;
		this.matches = matches;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.i(TAG, "getCount: " + matches.size());
		
		return matches.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "getItem: " + arg0);
		
		return matches.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "getItemid: " + arg0);
		
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewHold holder = null;
		
		if(arg1 == null){
			holder = new ViewHold();
			
			arg1 = inflater.inflate(R.layout.schedule_item, null);
			
			holder.title = (TextView)arg1.findViewById(R.id.title);
			holder.content = (TextView)arg1.findViewById(R.id.content);
			
			arg1.setClickable(false);
			arg1.setTag(holder);
		}else{
			holder = (ViewHold)arg1.getTag();
		}
		
		holder.title.setText(formatTitle(matches.get(arg0)));
		holder.content.setText(formatContent(matches.get(arg0)));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");		
		
		try {
			Date today = new Date();
			
			StringBuilder sb = new StringBuilder("2012年");
			sb.append(matches.get(arg0).getDate());
			Date get_date = format.parse(sb.toString());
			
			int differ = compareDays(today, get_date);
			
			if(differ == 0 || differ == -1){
				holder.title.setTextColor(Color.RED);
				holder.content.setTextColor(Color.RED);
				
				TextPaint tp = holder.title.getPaint();
				tp.setFakeBoldText(true);
				tp = holder.content.getPaint();
				tp.setFakeBoldText(true);
			}else{
				holder.title.setTextColor(Color.LTGRAY);
				holder.content.setTextColor(Color.LTGRAY);
				
				TextPaint tp = holder.title.getPaint();
				tp.setFakeBoldText(false);
				tp = holder.content.getPaint();
				tp.setFakeBoldText(false);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		
		return arg1;
	}

	private class ViewHold{
		TextView title;
		TextView content;
	}
	
	private String formatTitle(Match match){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(match.getId() + ". ");
		sb.append(match.getDate() + "(" + match.getWeek() + ")");
		sb.append("\t" + match.getTime());
		
		return sb.toString();
	}
	
	private String formatContent(Match match){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(match.getRound() + "\t");
		sb.append(match.getHome() + " VS " + match.getAway()); 
		
		return sb.toString();
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
