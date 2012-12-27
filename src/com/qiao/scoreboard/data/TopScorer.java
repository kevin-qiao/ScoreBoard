package com.qiao.scoreboard.data;

public class TopScorer {
	private Integer id;
	private String name;
	private String nation;
	private String goals;
	
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
	
	public String getNation(){
		return nation;
	}
	
	public void setNation(String nation){
		this.nation = nation;
	}
	
	public String getGoals(){
		return goals;
	}
	
	public void setGoals(String goals){
		this.goals = goals;
	}
}
