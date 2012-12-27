package com.qiao.scoreboard.data;

import java.util.List;

public class Group {
	private Integer id;
	private String name;
	private List<TeamData> data;
	
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
	
	public List<TeamData> getData(){
		return data;
	}
	
	public void setData(List<TeamData> data){
		this.data = data;
	}
}
