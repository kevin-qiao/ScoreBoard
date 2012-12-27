package com.qiao.scoreboard;

import java.util.List;

import com.qiao.scoreboard.data.Player;
import com.qiao.scoreboard.data.Team;
import com.qiao.scoreboard.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TeamActivity extends Activity {
	
	private Team team;
	private TextView home_first_title;
	private TextView home_first;
	private TextView home_reserve_title;
	private TextView home_reserve;
	private TextView away_first_title;
	private TextView away_first;
	private TextView away_reserve_title;
	private TextView away_reserve;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.team);
		
		home_first_title = (TextView)findViewById(R.id.home_first_title);
		home_first = (TextView)findViewById(R.id.home_first);
		home_reserve_title = (TextView)findViewById(R.id.home_reserve_title);
		home_reserve = (TextView)findViewById(R.id.home_reserve);
		
		away_first_title = (TextView)findViewById(R.id.away_first_title);
		away_first = (TextView)findViewById(R.id.away_first);
		away_reserve_title = (TextView)findViewById(R.id.away_reserve_title);
		away_reserve = (TextView)findViewById(R.id.away_reserve);
		
		team = EventList.team;
		
		if(team != null){
			initView();
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
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	private void initView(){
		home_first_title.setText(team.getHome() + "  ◊∑¢’Û»›");
		home_reserve_title.setText(team.getHome() + " ÃÊ≤π’Û»›");
		away_first_title.setText(team.getAway() + "  ◊∑¢’Û»›");
		away_reserve_title.setText(team.getAway() + " ÃÊ≤π’Û»›");
		
		home_first.setText(formatPlayers(team.getHomeFirst()));
		home_reserve.setText(formatPlayers(team.getHomeReserve()));
		away_first.setText(formatPlayers(team.getAwayFirst()));
		away_reserve.setText(formatPlayers(team.getAwayReserve()));

	}

	private String formatPlayers(List<Player> players){
		StringBuilder sb = new StringBuilder();
		
		for(Player p : players){
			sb.append("(" + p.getNumber() + ")" + p.getName() + "; ");
		}
		
		return sb.toString();
	}
}
