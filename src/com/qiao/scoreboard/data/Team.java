package com.qiao.scoreboard.data;

import java.util.List;

public class Team {
	private String home;
	private String away;
	private List<Player> home_first;		//主队首发
	private List<Player> home_reserve;		//主队替补
	private List<Player> away_first;
	private List<Player> away_reserve;
	
	public String getHome(){
		return home;
	}
	
	public void setHome(String home){
		this.home = home;
	}
	
	public String getAway(){
		return away;
	}
	
	public void setAway(String away){
		this.away = away;
	}
	
	public List<Player> getHomeFirst(){
		return home_first;
	}
	
	public void setHomeFirst(List<Player> players){
		home_first = players;
	}
	
	public List<Player> getHomeReserve(){
		return home_reserve;
	}
	
	public void setHomeReserve(List<Player> players){
		home_reserve = players;
	}
	
	public List<Player> getAwayFirst(){
		return away_first;
	}
	
	public void setAwayFirst(List<Player> players){
		away_first = players;
	}
	
	public List<Player> getAwayReserve(){
		return away_reserve;
	}
	
	public void setAwayReserve(List<Player> players){
		away_reserve = players;
	}
}
