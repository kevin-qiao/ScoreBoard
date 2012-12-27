package com.qiao.scoreboard.data;

import java.util.HashMap;

import com.qiao.scoreboard.R;

public class TeamAndDrawable {
	private static HashMap<String, Integer> map;
	
	private static void initMap(){
		
		map = new HashMap<String, Integer>();		
		map.put("波兰", R.drawable.a1_pol);		
		map.put("希腊", R.drawable.a2_gre);
		map.put("俄罗斯", R.drawable.a3_rus);
		map.put("捷克", R.drawable.a4_cze);	
		map.put("荷兰", R.drawable.b1_ned);		
		map.put("丹麦", R.drawable.b2_den);		
		map.put("德国", R.drawable.b3_ger);
		map.put("葡萄牙", R.drawable.b4_por);
		map.put("西班牙", R.drawable.c1_esp);
		map.put("意大利", R.drawable.c2_ita);
		map.put("爱尔兰", R.drawable.c3_irl);
		map.put("克罗地亚", R.drawable.c4_cro);
		map.put("乌克兰", R.drawable.d1_ukr);
		map.put("瑞典", R.drawable.d2_swe);
		map.put("法国", R.drawable.d3_fra);
		map.put("英格兰", R.drawable.d4_eng);
	}
	
	public static Integer getDrawable(String name){
		
		if(map == null || map.isEmpty()){
			initMap();
		}
		
		if(map.containsKey(name)){
			return (Integer)map.get(name);
		}else{
			return (Integer)R.drawable.other;
		}
	}
}
