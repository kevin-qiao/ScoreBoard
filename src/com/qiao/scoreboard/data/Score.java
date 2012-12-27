package com.qiao.scoreboard.data;

/**
 * 提供公共的数据结构
 * 
 * @author qiaopenghui
 *
 */
public class Score {
	private Integer id;
	private Integer status;
	private String fid;
	private String r;
	private String name;
	private String round;
	private String date;
	private String start;
	private String time;
	private String home;
	private String away;
	private String score;
	private String odds;
	
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
	
	public String getFid(){
		return fid;
	}
	
	public void setFid(String fid){
		this.fid = fid;
	}
	
	public String getR(){
		return r;
	}
	
	public void setR(String r){
		this.r = r;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getRound(){
		return round;
	}
	
	public void setRound(String round){
		this.round = round;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}	

	public String getStart(){
		return start;
	}
	
	public void setStart(String start){
		this.start = start;
	}
	
	public String getTime(){
		return time;
	}

	public void setTime(String time){
		this.time = time;
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
	
	public String getOdds(){
		return odds;
	}
	
	public void setOdds(String odds){
		this.odds = odds;
	}
}
