package com.qiao.scoreboard.data;

import java.util.HashMap;

import com.qiao.scoreboard.R;

public class TeamAndDrawable {
	private static HashMap<String, Integer> map;
	
	private static void initMap(){
		
		map = new HashMap<String, Integer>();		
		map.put("����", R.drawable.a1_pol);		
		map.put("ϣ��", R.drawable.a2_gre);
		map.put("����˹", R.drawable.a3_rus);
		map.put("�ݿ�", R.drawable.a4_cze);	
		map.put("����", R.drawable.b1_ned);		
		map.put("����", R.drawable.b2_den);		
		map.put("�¹�", R.drawable.b3_ger);
		map.put("������", R.drawable.b4_por);
		map.put("������", R.drawable.c1_esp);
		map.put("�����", R.drawable.c2_ita);
		map.put("������", R.drawable.c3_irl);
		map.put("���޵���", R.drawable.c4_cro);
		map.put("�ڿ���", R.drawable.d1_ukr);
		map.put("���", R.drawable.d2_swe);
		map.put("����", R.drawable.d3_fra);
		map.put("Ӣ����", R.drawable.d4_eng);
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
