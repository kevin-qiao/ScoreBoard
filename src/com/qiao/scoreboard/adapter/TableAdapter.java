package com.qiao.scoreboard.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TableAdapter extends BaseAdapter {
	
	private Context context;
	private List<TableRow> rows;
	
	public TableAdapter(Context context, List<TableRow> rows){
		this.context = context;
		this.rows = rows;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rows.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rows.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TableRow row = rows.get(position);
		return new TableRowView(this.context, row);
	}
	
	class TableRowView extends LinearLayout{
		public TableRowView(Context context, TableRow row){
			super(context);
			
			this.setOrientation(LinearLayout.HORIZONTAL);
			for(int i = 0; i < row.getSize(); i++){
				TableCell cell = row.getCellValue(i);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						cell.width, cell.height);
				params.setMargins(0, 0, 1, 1);
				
				TextView tvCell = new TextView(context);
				tvCell.setLines(1);
				tvCell.setGravity(Gravity.CENTER);
				tvCell.setBackgroundColor(Color.BLACK);
				tvCell.setText(cell.value);
				addView(tvCell, params);
			}
			
			this.setBackgroundColor(Color.WHITE);
		}
	}
	
	public static class TableRow{
		private TableCell[] cells;
		
		public TableRow(TableCell[] cells){
			this.cells = cells;
		}
		
		public int getSize(){
			return cells.length;
		}
		
		public TableCell getCellValue(int index){
			if(index >= cells.length){
				return null;
			}
			
			return cells[index];
		}
	}
	
	public static class TableCell{
		public String value;
		public int width;
		public int height;
		
		public TableCell(String value, int width, int height){
			this.value = value;
			this.width = width;
			this.height = height;
		}
	}
}
