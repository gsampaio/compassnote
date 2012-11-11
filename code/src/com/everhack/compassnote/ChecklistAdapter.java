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

import com.everhack.compassnote.model.Checklist;

public class ChecklistAdapter extends ArrayAdapter<Checklist> {
	private Context context;
	private List<Checklist> items;
	private int layoutRsrcId;

	public ChecklistAdapter(Context ctx, int layoutResourceId,
			List<Checklist> objects) {
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

			holder.checklist_text = (TextView) row.findViewById(R.id.checklist_text);
			holder.checklist_value = (CheckBox) row.findViewById(R.id.checklist_value);
			row.setTag(holder);
		} else{
			holder = (ViewHolder) row.getTag();
		}

		Checklist c = getItem(position);

		holder.checklist_text.setText(c.getValue());
		holder.checklist_value.setChecked(c.getChecked());

		return row;
	}


	static class ViewHolder	{
		TextView checklist_text;
		CheckBox checklist_value;
	}

	@Override
	public Checklist getItem(int position) {
		return this.items != null && this.items.size() > 0 ? this.items.get(position) : null;
	}

}
