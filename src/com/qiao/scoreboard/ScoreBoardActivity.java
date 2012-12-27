package com.qiao.scoreboard;

import com.qiao.scoreboard.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreBoardActivity extends TabActivity {
    /** Called when the activity is first created. */
	
	private static final String TAG = "Main Activity";
	private static final int	DELAY = 5;
	
	private static final int MENU_SCHEDULE = Menu.FIRST;
	private static final int MENU_EXIT = Menu.FIRST + 1;
	
	private ProgressDialog updateDialog;
	private MonitorThread  monitorThread;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       setContentView(R.layout.main);
        final TabHost tabHost = getTabHost();
        
        Log.i(TAG, "Main Activity onCreate");        
        
        tabHost.addTab(tabHost.newTabSpec("today list")
        		.setIndicator(getString(R.string.tab_today_label), 
        				getResources().getDrawable(R.drawable.today))
        		.setContent(new Intent(this, TodayScoreList.class)));
        
        tabHost.addTab(tabHost.newTabSpec("all list")
        		.setIndicator(getString(R.string.tab_all_label),
        				getResources().getDrawable(R.drawable.finish))
        		.setContent(new Intent(this, AllScoreList.class)));
        
        tabHost.addTab(tabHost.newTabSpec("rank list")
        		.setIndicator(getString(R.string.tab_rank_label),
        				getResources().getDrawable(R.drawable.board))
        		.setContent(new Intent(this, RankingList.class)));
        
        tabHost.addTab(tabHost.newTabSpec("top list")
        		.setIndicator(getString(R.string.tab_top_label), 
        				getResources().getDrawable(R.drawable.goals))
        		.setContent(new Intent(this, TopList.class)));
        
        this.updateDialog = ProgressDialog.show(this, "请稍候", "正在检查网络", true);
		this.updateDialog.setCancelable(true);
		
		monitorThread = new MonitorThread(handler);
		monitorThread.start();
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onDestroy");
		
		super.onDestroy();
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		menu.add(0, MENU_SCHEDULE, Menu.NONE, "赛程")
			.setIcon(R.drawable.course);
		
		menu.add(0, MENU_EXIT, Menu.NONE, "退出")
			.setIcon(R.drawable.finish);
		
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
		case MENU_SCHEDULE:
			Intent intent = new Intent(this, ScheduleList.class);
			startActivity(intent);
			break;
			
		case MENU_EXIT:
			finish();
			break;
		}
		
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(getResources().getString(R.string.alert_msg));
			builder.setPositiveButton(R.string.positive_label, 
					new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			
			builder.setNegativeButton(R.string.negative_label, 
					new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			
			builder.create().show();
			
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}		
		
	}



	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			Log.i(TAG, "receive msg: " + msg.what);
			
			if(updateDialog != null){
				updateDialog.dismiss();
				updateDialog = null;
			}
			
			if(msg.what == 0){
				startTodayTimer();
			}else{
				notifyFromHandler(msg.what);
			}
		}
		
	};
	
	private class MonitorThread extends Thread{

		Handler mHandler;
		
		MonitorThread(Handler handler){
			mHandler = handler;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			int delay = 0;
			
			while((NetworkInfo.State.CONNECTED != monitorNet()) && 
					(delay <= DELAY) && (updateDialog.isShowing())){
				try {
					Log.i(TAG, "thread state: " + delay);
					Thread.sleep(1000);
					delay++;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "Thread.sleep exception: " + e.getMessage());
					e.printStackTrace();
				}
			}
			
			Message msg = mHandler.obtainMessage();
			
			if(NetworkInfo.State.CONNECTED == monitorNet()){
				Log.i(TAG, "monitor network IS connected");
				msg.what = 0;
			}else{
				Log.i(TAG, "monitor network fail");
				msg.what = 1;
			}
			
			mHandler.sendMessage(msg);
		}		
	}
	
	private NetworkInfo.State monitorNet(){
		
		ConnectivityManager connection = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo nwInfo = connection.getActiveNetworkInfo();
		
		if(nwInfo != null){
			Log.i(TAG, "Network state: " + nwInfo.getState());
			return nwInfo.getState();
		}		
		
		return NetworkInfo.State.DISCONNECTED;
	}
	
	private void notifyFromHandler(int what){
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.main_notify_item, null);
		
		TextView item = (TextView)view.findViewById(R.id.main_toast);
		item.setText("网络连接出错，请检查一下网络连接");
		
		Toast toast = new Toast(this);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}
	
	private void startTodayTimer(){
		TodayScoreList.startTimer();
	}

}