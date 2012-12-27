package com.qiao.scoreboard.data;

public class Match {
	Integer id;
	String  date;
	String  week;
	String  time;
	String  round;
	String  home;
	String  away;
	
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getWeek(){
		return week;
	}
	
	public void setWeek(String week){
		this.week = week;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String time){
		this.time = time;
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
}
