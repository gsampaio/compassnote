package com.everhack.compassnote;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.everhack.compassnote.model.Roteiro;

public class RoteiroAdapter extends ArrayAdapter<Roteiro> {
	private Context context;
	private List<Roteiro> items;
	private int layoutRsrcId;

	public RoteiroAdapter(Context ctx, int layoutResourceId,
			List<Roteiro> objects) {
		super(ctx, layoutResourceId, objects);
		this.items = objects;
		this.layoutRsrcId = layoutResourceId;
		this.context = ctx;
	}

	public View getView(int position, View row, ViewGroup parent){
		ViewHolder holder;

		if(row == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutRsrcId, parent, false);

			holder = new ViewHolder();

			holder.roteiro_title = (TextView) row.findViewById(R.id.checklist_text);
			holder.roteiro_data = (CheckBox) row.findViewById(R.id.checklist_value);
			row.setTag(holder);
		} else{
			holder = (ViewHolder) row.getTag();
		}

		Roteiro r = getItem(position);

		holder.roteiro_title.setText(r.getCity());
		holder.roteiro_data.setText(r.getDataString());

		return row;
	}


	static class ViewHolder	{
		TextView roteiro_title;
		TextView roteiro_data;
	}

	@Override
	public Roteiro getItem(int position) {
		return this.items != null && this.items.size() > 0 ? this.items.get(position) : null;
	}

}
