package com.qiao.scoreboard.adapter;

import java.util.List;

import com.qiao.scoreboard.EventList;
import com.qiao.scoreboard.data.Event;
import com.qiao.scoreboard.data.EventMap;
import com.qiao.scoreboard.data.Player;
import com.qiao.scoreboard.data.Team;
import com.qiao.scoreboard.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter {
	
	private Context context;
	private List<Event> events;
	
	public EventAdapter(Context context, List<Event> events){
		this.context = context;
		this.events = events;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return events.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return events.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewHolder holder = null;
		
		if(convertView == null){
			holder = new ViewHolder();
			
			convertView = inflater.inflate(R.layout.detail_item, null);
			holder.img  = (ImageView)convertView.findViewById(R.id.event_img);
			holder.item = (TextView)convertView.findViewById(R.id.info_item);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.img.setImageResource(EventMap.getDrawable(events.get(position).getStatus()));
		holder.item.setText(formatEvent(events.get(position)));
		
		return convertView;
	}
	
	public final class ViewHolder{
		ImageView img;
		TextView item;
	}
	
	private String formatEvent(Event event){
		
		StringBuilder sb = new StringBuilder();
		
		String tmp = null;
		boolean bChange = false;
		
		switch(event.getStatus()){
		case 1:
			tmp = "进球";
			break;
		case 2:
			tmp = "乌龙球";
			break;
		case 3:
			tmp = "点球";
			break;
		case 4:
			tmp = "黄牌";
			break;
		case 5:
			tmp = "红牌";
			break;
			
		case 6:
			tmp = "两黄变红";
			break;
			
		case 7:
			tmp = "入球无效";
			break;
			
		case 8:
			tmp = "换人";
			bChange = true;
			break;
			
		case 9:
			tmp = "换入";
			bChange = true;
			break;
			
		case 10:
			tmp = "换出";
			bChange = true;
			break;
			
		default:
			tmp = "NON";
			break;
		}
		
		sb.append("[" + event.getTime() + "]");
		sb.append(event.getTeam() + ": ");
		sb.append(tmp + "---");
		if(bChange){
			sb.append(formatChange(event.getInfo(), event.getTeam()));
		}else{
			sb.append(event.getInfo());
		}
		
		
		return sb.toString();
	}
	
	//格式化换人信息，将球员号码匹配到相应的球员
	private String formatChange(String change, String teamName){
		
		StringBuilder sb = new StringBuilder(change);
		
		int index = sb.indexOf("(", 0);
		int start = 0;
		int end = 0;
		
		if(index == -1){
			return sb.toString();
		}
		
		do{
			start = index + 1;
			
			index = sb.indexOf(")", start);
			
			if(index != -1){
				end = index;
				
				Integer number = new Integer(sb.substring(start, end));
				
				sb.insert(end + 1, getNameFromNum(number, teamName));
				
				index = sb.indexOf("(", end + 1);
			}
			
		}while(index != -1);
		
		return sb.toString();
	}
	
	private String getNameFromNum(Integer number, String teamName){
		StringBuilder sb = new StringBuilder();
		boolean bFind = false;
		Team team = EventList.team;
		
		if(team == null || team.getHome() == null){
			return " ";
		}
		
		if(teamName.equalsIgnoreCase(team.getHome())){
			
			for(Player p : team.getHomeFirst()){
				if(p.getNumber().compareTo(number) == 0){
					bFind = true;
					sb.append(p.getName());
					break;
				}
			}
			
			if(!bFind){
				for(Player p : team.getHomeReserve()){
					if(p.getNumber().compareTo(number) == 0){
						bFind = true;
						sb.append(p.getName());
						break;
					}
				}
			}
			
		}else{
			
			for(Player p : team.getAwayFirst()){
				if(p.getNumber().compareTo(number) == 0){
					bFind = true;
					sb.append(p.getName());
					break;
				}
			}
			
			if(!bFind){
				for(Player p : team.getAwayReserve()){
					if(p.getNumber().compareTo(number) == 0){
						bFind = true;
						sb.append(p.getName());
						break;
					}
				}
			}
		}
	
		sb.append(" ");
		
		return sb.toString();
	}

}
