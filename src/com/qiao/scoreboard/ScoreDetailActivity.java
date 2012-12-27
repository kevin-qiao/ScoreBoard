package com.qiao.scoreboard;

import com.qiao.scoreboard.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

/**
 * 比分详细信息，由用户在TodayScoreList中点击List item触发，显示两对的首发情况，
 * 和比赛中的所有信息，包括进球，换人，红黄牌等等
 * 
 * @author qiaopenghui
 *
 */
public class ScoreDetailActivity extends TabActivity {

	private static final String TAG = "score detail";
	
	private Intent intent;	

	public static String mFid;
	public static String mR;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Log.i(TAG, "onCreate");
		
		intent = getIntent();		
		this.setTitle(intent.getStringExtra("title"));
		
		mFid = intent.getStringExtra("fid");
		mR = intent.getStringExtra("r");
		
		final TabHost tabHost = getTabHost();
		
		tabHost.addTab(tabHost.newTabSpec("event list")
			.setIndicator(getString(R.string.event_label))
			.setContent(new Intent(this, EventList.class)));
		
		tabHost.addTab(tabHost.newTabSpec("team list")
				.setIndicator(getString(R.string.team_label))
				.setContent(new Intent(this, TeamActivity.class)));
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onDestroy");
		super.onDestroy();
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
}
