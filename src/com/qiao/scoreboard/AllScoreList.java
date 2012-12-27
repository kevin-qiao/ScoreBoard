package com.qiao.scoreboard;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import com.qiao.scoreboard.adapter.EndAdapter;
import com.qiao.scoreboard.data.EndGame;
import com.qiao.scoreboard.net.HTTPRequestHelper;
import com.qiao.scoreboard.parser.EndGameXmlReader;
import com.qiao.scoreboard.R;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 所有比分列表，提供已完场的比赛的比分情况，每个列表项用日期显示，点击弹出下拉区域，
 * 显示当日比赛比分，不提供详细情况
 * 
 * @author qiaopenghui
 *
 */

public class AllScoreList extends ListActivity {

	private static final String TAG = "all score";
	private static final String BASE_URL = "http://liveeuro.sinaapp.com/score.php?";
	
	private List<EndGame> games;
	private EndAdapter adapter;
	private ResponseHandler<String> response; 
	private ProgressDialog progress;
	private boolean isFirst;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Log.i(TAG, "onCreate");	
		
		isFirst = true;
		
		response = HTTPRequestHelper.getResponseHandlerInstance(handler, 0);
			
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub

		((EndAdapter)getListAdapter()).toggle(position);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return getParent().onKeyDown(keyCode, event);
		}else{
			return super.onKeyDown(keyCode, event);
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
		
		progress = ProgressDialog.show(this, "请稍候", "正在获取完场比分数据...");
		progress.setCancelable(true);
		
		FetchThread fetch = new FetchThread();
		fetch.start();
		
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStop");
		super.onStop();
	}


	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Log.i(TAG, "receive handler msg: " + msg.what);
			
			progress.dismiss();
			
			String xmlInfo = msg.getData().getString("RESPONSE");
			
			switch(msg.what){
			case 0:
				
				games = getScoreList(xmlInfo);
				
				if(games != null && games.size() > 0){
					if(isFirst){
						adapter = new EndAdapter(AllScoreList.this, games);
						setListAdapter(adapter);
					}else{
						adapter.dataChanged(games);
					}
					
				}
				
				break;
				
			default:
				Log.e(TAG, "net connection error: " + msg.what);
				notifyFromHandler();
				break;
			}
		}
		
	};
	
	private class FetchThread extends Thread{
		private HTTPRequestHelper helper;
		
		public FetchThread(){
			helper = new HTTPRequestHelper(response);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			StringBuilder sb = new StringBuilder(BASE_URL);
			sb.append("type=2");
			
			helper.performGet(sb.toString(), null, null, null);

		}
		
	}
	
	private List<EndGame> getScoreList(String xml){
		
		Log.i(TAG, "start parser end games xml");
		
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		
		return EndGameXmlReader.readXml(in);
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
	
}
