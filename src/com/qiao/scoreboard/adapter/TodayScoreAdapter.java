package com.qiao.scoreboard.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiao.scoreboard.data.*;
import com.qiao.scoreboard.R;

public class TodayScoreAdapter extends BaseAdapter {

	private static final String TAG = "Today Adapter";
	private Context context;
	private List<Score> mScores;
	
	public TodayScoreAdapter(Context context, List<Score> scores){
		this.context = context;
		mScores = scores;
	}
	
	public void dataChanged(List<Score> scores){
		mScores = scores;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.i(TAG, "getCount: " + mScores.size());
		
		return mScores.size()*2;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		
		Log.i(TAG, "get item index: " + arg0 + "match id: " + mScores.get(arg0).getId());
		
		return mScores.get(arg0/2);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "get item id: " + arg0);
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		
		Log.i(TAG, "get view, position: " + arg0);
		
		ViewTitle viewTitle = null;
		ViewInfo  viewInfo  = null;		
		
		if(arg0%2 == 0){
			//偶数行，显示标题
			viewTitle = new ViewTitle();
				
			arg1 = inflater.inflate(R.layout.score_title_item, null);
				
			viewTitle.title = (TextView)arg1.findViewById(R.id.title);
			viewTitle.img_home = (ImageView)arg1.findViewById(R.id.img_home);
			viewTitle.img_away = (ImageView)arg1.findViewById(R.id.img_away);
			viewTitle.date = (TextView)arg1.findViewById(R.id.date);
			viewTitle.time = (TextView)arg1.findViewById(R.id.time);
				
			arg1.setEnabled(false);
			arg1.setTag(viewTitle);
		}else{
			//奇数行，显示详细信息
			viewInfo = new ViewInfo();
				
			arg1 = inflater.inflate(R.layout.score_item, null);
				
			viewInfo.home = (TextView)arg1.findViewById(R.id.home);
			viewInfo.away = (TextView)arg1.findViewById(R.id.away);
			viewInfo.score = (TextView)arg1.findViewById(R.id.score);
			viewInfo.odds = (TextView)arg1.findViewById(R.id.odds);
				
			arg1.setTag(viewInfo);
		}		
		
		Log.i(TAG, "position: " + arg0 + "\thome: " + mScores.get(arg0/2).getHome());
		
		if(arg0%2 == 0){
			viewTitle.title.setText("2012欧洲杯 " + mScores.get(arg0/2).getName() + " " + 
					mScores.get(arg0/2).getRound());
			
			viewTitle.img_home.setImageResource(TeamAndDrawable.getDrawable(mScores.get(arg0/2).getHome()));
			viewTitle.img_away.setImageResource(TeamAndDrawable.getDrawable(mScores.get(arg0/2).getAway()));
			viewTitle.date.setText(mScores.get(arg0/2).getDate() + " " + 
					mScores.get(arg0/2).getStart());
			viewTitle.time.setText(mScores.get(arg0/2).getTime());
		}else{
			viewInfo.home.setText(mScores.get(arg0/2).getHome());
			viewInfo.away.setText(mScores.get(arg0/2).getAway());
			viewInfo.score.setText("比分: " + mScores.get(arg0/2).getScore());
			viewInfo.odds.setText("盘口: " + mScores.get(arg0/2).getOdds());
		}
		
		return arg1;
	}
	
	public final class ViewTitle{
		public TextView title;
		public ImageView img_home;
		public ImageView img_away;
		public TextView date;
		public TextView time;
	}

	public final class ViewInfo{
		public TextView home;
		public TextView away;
		public TextView score;
		public TextView odds;
	}
}
