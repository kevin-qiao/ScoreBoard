package com.qiao.scoreboard.data;

import java.util.HashMap;

import com.qiao.scoreboard.R;

public class EventMap {
	private static HashMap<Integer, Integer> map;
	
	private static void initMap(){
		map = new HashMap<Integer, Integer>();
		
		map.put(1, R.drawable.m_1);
		map.put(2, R.drawable.m_2);
		map.put(3, R.drawable.m_3);
		map.put(4, R.drawable.m_4);
		map.put(5, R.drawable.m_5);
		map.put(6, R.drawable.m_6);
		map.put(7, R.drawable.m_7);
		map.put(8, R.drawable.m_8);
		map.put(9, R.drawable.m_9);
		map.put(10, R.drawable.m_10);
	}
	
	public static Integer getDrawable(Integer status){
		if(map == null || map.isEmpty()){
			initMap();
		}
		
		if(map.containsKey(status)){
			return (Integer)map.get(status);
		}else{
			return (Integer)R.drawable.m_1;
		}
	}
}
