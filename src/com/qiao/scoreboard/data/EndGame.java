package com.qiao.scoreboard.data;

import java.util.List;

public class EndGame {
	private Integer id;
	private boolean expanded;
	private String  date;
	private List<Game> games;
	
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public boolean getExpanded(){
		return expanded;
	}
	
	public void setExpanded(boolean expanded){
		this.expanded = expanded;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public List<Game> getGames(){
		return games;
	}
	
	public void setGames(List<Game> games){
		this.games = games;
	}
}
