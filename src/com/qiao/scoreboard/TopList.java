package com.qiao.scoreboard;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import com.qiao.scoreboard.adapter.TableAdapter;
import com.qiao.scoreboard.adapter.TableAdapter.TableCell;
import com.qiao.scoreboard.adapter.TableAdapter.TableRow;
import com.qiao.scoreboard.data.TopScorer;
import com.qiao.scoreboard.net.HTTPRequestHelper;
import com.qiao.scoreboard.parser.TopXmlReader;
import com.qiao.scoreboard.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 提供射手榜信息，助攻榜信息等
 * 
 * @author qiaopenghui
 *
 */
public class TopList extends Activity {

	private static final String TAG = "top";
	private static final String BASE_URL = 
		"http://liveeuro.sinaapp.com/standings.php?type=2";
	private static final String[] TITLES = {"排名", "姓名", "国家", "进球"};
	private static final int ROW = 4;
	
	private ListView list;
	private ProgressDialog progress;
	private ResponseHandler<String> response;
	private List<TopScorer> top_scorers;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top_list);
		
		Log.i(TAG, "onCreate");
		
		list = (ListView)findViewById(R.id.list);
		
		response = HTTPRequestHelper.getResponseHandlerInstance(handler, 1);
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
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStart");
		
		progress = ProgressDialog.show(this, "请稍候", "正在获取射手榜数据...");
		progress.setCancelable(true);
		
		FetchThread fetch = new FetchThread();
		fetch.start();
		
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Log.i(TAG, "receive top xml: " + msg.what);
			
			progress.dismiss();
			
			String xmlInfo = msg.getData().getString("RESPONSE");
			
			switch(msg.what){
			case 0:
				
				top_scorers = getTopList(xmlInfo);
				
				if(top_scorers != null && top_scorers.size() > 0){
					setTopAdapter(top_scorers);
				}
				
				break;
				
			default:
				Log.e(TAG, "net error: " + xmlInfo);
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
			
			helper.performGet(BASE_URL, null, null, null);
			
		}
		
	}
	
	private List<TopScorer> getTopList(String xml){
		Log.i(TAG, "start parser top list");
		
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		
		return TopXmlReader.readXml(in);
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
	
	private void setTopAdapter(List<TopScorer> tops){
		ArrayList<TableRow> rows = new ArrayList<TableRow>();
		
		int width = this.getWindowManager().getDefaultDisplay().getWidth() / ROW;
		
		TableCell[] title_cells = new TableCell[ROW];
		for(int i = 0; i < ROW; i++){
			
			int cell_width = 0;
			
			switch(i){
			case 0:
				cell_width = width - 20;
				break;
				
			case 1:
				cell_width = width + 18;
				break;
				
			case 2:
				cell_width = width + 10;
				break;
				
			case 3:
				cell_width = width - 15;
				break;
			
			default:
				cell_width = width;
				break;
			}
			
			title_cells[i] = new TableCell(TITLES[i], cell_width, 
					LayoutParams.MATCH_PARENT);
		}
		
		rows.add(new TableRow(title_cells));
		
		for(int i = 0; i < tops.size(); i++){
			TableCell[] content_cells = new TableCell[ROW];
			
			for(int ii = 0; ii < ROW; ii++){
				switch(ii){
				case 0:
					content_cells[ii] = new TableCell(String.valueOf(tops.get(i).getId()), 
							title_cells[ii].width, LayoutParams.MATCH_PARENT);
					break;
					
				case 1:
					content_cells[ii] = new TableCell(tops.get(i).getName(),
							title_cells[ii].width, LayoutParams.MATCH_PARENT);
					break;
					
				case 2:
					content_cells[ii] = new TableCell(tops.get(i).getNation(),
							title_cells[ii].width, LayoutParams.MATCH_PARENT);
					break;
					
				case 3:
					content_cells[ii] = new TableCell(tops.get(i).getGoals(),
							title_cells[ii].width, LayoutParams.MATCH_PARENT);
					break;
				}
			}
			
			rows.add(new TableRow(content_cells));
		}
		
		TableAdapter adapter = new TableAdapter(this, rows);
		list.setAdapter(adapter);
		list.setClickable(false);
	}
}
