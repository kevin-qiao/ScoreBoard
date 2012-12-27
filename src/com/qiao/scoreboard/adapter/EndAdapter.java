package com.qiao.scoreboard.adapter;

import java.util.List;

import com.qiao.scoreboard.data.EndGame;
import com.qiao.scoreboard.data.Game;
import com.qiao.scoreboard.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EndAdapter extends BaseAdapter {

	private static final String TAG = "end games adapter";
	
	private Context context;
	private List<EndGame> EndGames;
	
	public EndAdapter(Context context, List<EndGame> EndGames){
		this.context = context;
		this.EndGames = EndGames;
	}
	
	public void dataChanged(List<EndGame> games){
		EndGames = games;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.i(TAG, "get count: " + EndGames.size());
		
		return EndGames.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return EndGames.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewHold holder = null;
		
		if(arg1 == null){
			holder = new ViewHold();			
			arg1 = inflater.inflate(R.layout.all_score_item, null);
			
			holder.date = (TextView)arg1.findViewById(R.id.date);
			holder.score = (TextView)arg1.findViewById(R.id.score);
			
			arg1.setTag(holder);
		}else{
			holder = (ViewHold)arg1.getTag();
		}
		
		holder.date.setText(EndGames.get(arg0).getDate());
		holder.score.setText(formatGameInfo(EndGames.get(arg0).getGames()));
		holder.score.setVisibility(EndGames.get(arg0).getExpanded() ? 
				View.VISIBLE : View.GONE);
		
		return arg1;
	}
	
	public void toggle(int position){
		boolean bExpanded = EndGames.get(position).getExpanded();
		EndGames.get(position).setExpanded(!bExpanded);
		this.notifyDataSetChanged();
	}
	
	private class ViewHold{
		TextView date;
		TextView score;
	}
	
	private String formatGameInfo(List<Game> games){
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < games.size(); i++){
			sb.append(games.get(i).getRound() + "    ");
			sb.append(games.get(i).getHome() + " " + games.get(i).getScore() + 
					" " + games.get(i).getAway());
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
