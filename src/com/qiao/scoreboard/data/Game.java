package com.qiao.scoreboard.data;

public class Game {
	private Integer id;
	private String  round;
	private String  home;
	private String  away;
	private String  score;
	
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getRound(){
		return round;
	}
	
	public void setRound(String round){
		this.round = round;
	}
	
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
	
	public String getScore(){
		return score;
	}
	
	public void setScore(String score){
		this.score = score;
	}
}
