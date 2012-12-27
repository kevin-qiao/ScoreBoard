package com.qiao.scoreboard.data;

public class TeamData {
	private Integer id;
	private String name;
	private Integer games;
	private Integer win;
	private Integer draw;
	private Integer lose;
	private Integer goals;
	private Integer drop;
	private Integer points;
	
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public Integer getGames(){
		return games;
	}
	
	public void setGames(Integer games){
		this.games = games;
	}
	
	public Integer getWin(){
		return win;
	}
	
	public void setWin(Integer win){
		this.win = win;
	}
	
	public Integer getDraw(){
		return draw;
	}
	
	public void setDraw(Integer draw){
		this.draw = draw;
	}
	
	public Integer getLose(){
		return lose;
	}
	
	public void setLose(Integer lose){
		this.lose = lose;
	}
	
	public Integer getGoals(){
		return goals;
	}
	
	public void setGoals(Integer goals){
		this.goals = goals;
	}
	
	public Integer getDrop(){
		return drop;
	}
	
	public void setDrop(Integer drop){
		this.drop = drop;
	}
	
	public Integer getPoints(){
		return points;
	}
	
	public void setPoints(Integer points){
		this.points = points;
	}
}
