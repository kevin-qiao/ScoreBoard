package com.qiao.scoreboard;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import com.qiao.scoreboard.adapter.TableAdapter;
import com.qiao.scoreboard.adapter.TableAdapter.TableCell;
import com.qiao.scoreboard.adapter.TableAdapter.TableRow;
import com.qiao.scoreboard.data.Group;
import com.qiao.scoreboard.data.TeamData;
import com.qiao.scoreboard.net.HTTPRequestHelper;
import com.qiao.scoreboard.parser.GroupXmlReader;
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
 * 排名积分榜，提供每个小组的积分和排名情况，已经淘汰赛阶段的晋级情况
 * 
 * @author qiaopenghui
 *
 */
public class RankingList extends Activity{
	
	private static final String TAG = "rank";
	private static final int ROW = 8;
	private static final String BASE_URL = 
		"http://liveeuro.sinaapp.com/standings.php?type=1";
	private static final String[] TITLES = {
		"国家", "场次", "胜", "平", "负", "进球", "失球", "积分"	};
	
	private ListView list;
	private ProgressDialog progress;
	private ResponseHandler<String> response;
	private List<Group> groups;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rank_list);
		
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
		
		progress = ProgressDialog.show(this, "请稍候", "正在获取积分榜数据...");
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
			Log.i(TAG, "receive group xml: " + msg.what);
			
			progress.dismiss();
			
			String xmlInfo = msg.getData().getString("RESPONSE");
			
			switch(msg.what){
			case 0:
				
				groups = getGroupList(xmlInfo);
				
				if(groups != null && groups.size() > 0){
					setRankAdapter(groups);
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
	
	private List<Group> getGroupList(String xml){
		Log.i(TAG, "start parser group list");
		
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		
		return GroupXmlReader.readXml(in);
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
	
	private void setRankAdapter(List<Group> groups){
		ArrayList<TableRow> rows = new ArrayList<TableRow>();
		
		int width = this.getWindowManager().getDefaultDisplay().getWidth() / ROW;
		
		for(int index = 0; index < groups.size(); index++){
			TableCell[] name_cells = new TableCell[1];
			name_cells[0] = new TableCell(groups.get(index).getName(),
					width * ROW - 2, LayoutParams.MATCH_PARENT);
			
			rows.add(new TableRow(name_cells));
			
			TableCell[] title_cells = new TableCell[ROW];
			for(int i = 0; i < ROW; i++){
				
				int title_width = 0;
				
				if(i == 0){
					title_width = width + 25;
				}else if(i == 2 || i == 3 || i == 4){
					title_width = width - 6;
				}else{
					title_width = width - 4;
				}
				
				title_cells[i] = new TableCell(TITLES[i],
						title_width, LayoutParams.MATCH_PARENT);
			}
			
			rows.add(new TableRow(title_cells));
			
			List<TeamData> data_list = groups.get(index).getData();
			
			for(int count = 0; count < data_list.size(); count++){
				TableCell[] content = new TableCell[ROW];
				
				for(int i = 0; i < ROW; i++){
					
					String value = " ";
					
					switch(i){
					case 0:
						value = data_list.get(count).getName();
						break;
						
					case 1:
						value = String.valueOf(data_list.get(count).getGames());
						break;
						
					case 2:
						value = String.valueOf(data_list.get(count).getWin());
						break;
						
					case 3:
						value = String.valueOf(data_list.get(count).getDraw());
						break;
						
					case 4:
						value = String.valueOf(data_list.get(count).getLose());
						break;
						
					case 5:
						value = String.valueOf(data_list.get(count).getGoals());
						break;
						
					case 6:
						value = String.valueOf(data_list.get(count).getDrop());
						break;
						
					case 7:
						value = String.valueOf(data_list.get(count).getPoints());
						break;
					}
					
					content[i] = new TableCell(value, title_cells[i].width,
							LayoutParams.MATCH_PARENT);
				}
				
				rows.add(new TableRow(content));
			}
		}
		
		TableAdapter adapter = new TableAdapter(this, rows);
		list.setAdapter(adapter);
		list.setClickable(false);
	}
}
