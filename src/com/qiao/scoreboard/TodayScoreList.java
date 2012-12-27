package com.qiao.scoreboard;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ResponseHandler;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.qiao.scoreboard.adapter.TodayScoreAdapter;
import com.qiao.scoreboard.data.*;
import com.qiao.scoreboard.net.HTTPRequestHelper;
import com.qiao.scoreboard.parser.ScoreXmlReader;
import com.qiao.scoreboard.R;

/**
 * 今日比分列表，显示最新的比分情况，如果当日没有比分，显示最近一日的比分
 * 使用LIST列表，点击列表项，弹出选中比分的详细信息
 * 
 * @author qiaopenghui
 *
 */
public class TodayScoreList extends ListActivity {

	private static final String TAG = "today score";

	private static final long 	TIMER_DELAY = 2 * 1000;	//延时启动更新时间
	private static final long	TIMER_PERIOD = 60 * 1000;	//循环重复间隔时间
	private static final String	GET_SCORE_URL = "http://liveeuro.sinaapp.com/score.php?";
	private static boolean isFirstTime = true;
	private static final String FILE_NAME = "temp.xml";
	
	private List<Score> scores = null;
	private TodayScoreAdapter adapter;
	private TextView empty;	
	private String xml_save = null;
	
	private static Context context;
	private static ProgressDialog progress;	
	private static Timer timer = null;
	private static TimerTask task  = null;
	private static boolean isPause = false;
	private static ResponseHandler<String> responseHandler;	
	
	private Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Log.i(TAG, "receive update msg");
			
			progress.dismiss();
			
			String xmlInfo = msg.getData().getString("RESPONSE");
			
			switch(msg.what){
			case 0:				
				scores = getNewScore(xmlInfo);
				
				if(scores != null && scores.size() > 0){	
					if(isFirstTime){
						empty.setVisibility(View.GONE);
						adapter = new TodayScoreAdapter(TodayScoreList.this, scores);
						setListAdapter(adapter);
						isFirstTime = false;
					}else{
						adapter.dataChanged(scores);
					}
					
					xml_save = xmlInfo;
				}
				
				break;
				
			case 1:
				notifyFromHandler(xmlInfo, 1);
				break;
				
			case 2:
				notifyFromHandler(xmlInfo, 2);
				break;
				
			case 3:
				notifyFromHandler(xmlInfo, 3);
				break;
			}			
		}
		
	};	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.today_list);
		
		Log.i(TAG, "onCreate");
		
		isFirstTime = true;
		context = this;
		
		empty = (TextView)findViewById(R.id.empty);
		
		responseHandler = HTTPRequestHelper.getResponseHandlerInstance(handler, 0);
		
		String xml_from_file = this.readTempFile();
		
		if(xml_from_file != null){
			scores = getNewScore(xml_from_file);
			
			if(scores != null && scores.size() > 0){
				empty.setVisibility(View.GONE);
				adapter = new TodayScoreAdapter(this, scores);
				setListAdapter(adapter);
				isFirstTime = false;
			}
		}
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPause");
	
		if(!isPause)
			isPause = !isPause;
		
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
		
		if(isPause)
			isPause = !isPause;

		super.onResume();
//		timer.notify();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStart update app data");	
	
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStop");
		
		this.saveTempFile(xml_save);
		
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onDestroy");
		
		if(TodayScoreList.task != null){
			TodayScoreList.task.cancel();
			TodayScoreList.task = null;
		}
		
		if(TodayScoreList.timer != null){
			TodayScoreList.timer.cancel();
			TodayScoreList.timer.purge();		
			TodayScoreList.timer = null;
		}
		
		super.onDestroy();
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

	//检测完网络以后，从服务器获取最新的比分数据
	private List<Score> getNewScore(String xmlInfo){
		
		Log.i(TAG, "parser score list");
		
		InputStream in = new ByteArrayInputStream(xmlInfo.getBytes());
		
		return ScoreXmlReader.readXml(in);
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		if(position%2 == 0){
			
		}else{
			Intent intent = new Intent(this,ScoreDetailActivity.class);
			intent.putExtra("title", scores.get(position/2).getName() + " " + 
					scores.get(position/2).getRound());
			intent.putExtra("fid", scores.get(position/2).getFid());
			intent.putExtra("r", scores.get(position/2).getR());
			
			startActivity(intent);
		}
	}
	
	private void notifyFromHandler(String info, int error){
		Log.i(TAG, "net error: " + info);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.toast_item, null);
		
		TextView toast_item = (TextView)view.findViewById(R.id.item);
		toast_item.setText(getResources().getString(R.string.net_error_label));
		
		Toast toast = new Toast(this);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}

	public static void startTimer(){
		Log.i(TAG, "start timer task");
		
		progress = ProgressDialog.show(context, "请稍后", "正在更新数据...");
		progress.setCancelable(true);
		
		if(timer == null){
			Log.i(TAG, "create timer");
			if(task == null){
				Log.i(TAG, "create time task");
				task = new TimerTask(){
					
					HTTPRequestHelper helper = new HTTPRequestHelper(responseHandler);
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(isPause)
						{
						}else{
							helper.performGet(GET_SCORE_URL + "type=1", null, null, null);
						}						
					}
					
				};
			}
			timer = new Timer();
			timer.schedule(task, TIMER_DELAY, TIMER_PERIOD);
		}
	}
	
	private void saveTempFile(String xml){
		
		Log.i(TAG, "save tmp file");
		
		FileOutputStream fout = null;
		OutputStreamWriter osw = null;
		
		if(xml == null || xml.isEmpty()){
			return;
		}
		
		try {
			fout = openFileOutput(FILE_NAME, MODE_PRIVATE);
			osw = new OutputStreamWriter(fout);
			osw.write(xml);
			osw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} finally{
			try {
				osw.close();
				fout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
			}
			
		}
	}
	
	private String readTempFile(){
		
		Log.i(TAG, "read temp file");
		
		FileInputStream fin = null;
		InputStreamReader isr = null;
		boolean bFind = true;
		
		StringBuilder sb = new StringBuilder();
		
		try {
			fin = openFileInput(FILE_NAME);
			isr = new InputStreamReader(fin, "UTF-8");
			BufferedReader br = new BufferedReader(isr);			
	        String line = null;
	        
	        while ((line = br.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	        
	        br.close();
	      
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
			bFind = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} finally{
			try {
				if(bFind){
					isr.close();
					fin.close();
				}				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
			}
			
		}
		
		return sb.toString();
	}

}
