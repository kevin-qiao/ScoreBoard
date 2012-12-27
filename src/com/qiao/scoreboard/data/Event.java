package com.qiao.scoreboard.data;

public class Event {
	private Integer id;
	private Integer status;
	private String team;
	private String time;
	private String info;
	
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getStatus(){
		return status;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public String getTeam(){
		return team;
	}
	
	public void setTeam(String team){
		this.team = team;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	public String getInfo(){
		return info;
	}
	
	public void setInfo(String info){
		this.info = info;
	}
}
