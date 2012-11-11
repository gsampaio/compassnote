package com.everhack.compassnote.adapter;

import java.util.List;

import com.everhack.compassnote.R;
import com.everhack.compassnote.foursquare.FoursquareVenue;
import com.everhack.compassnote.model.PlaceData;
import com.everhack.compassnote.view.PlaceItemView;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PlacesAdapter extends BaseAdapter {

    private List<FoursquareVenue> mData;
    private LayoutInflater mInflater;
    // Helper for communication with owner activity
    private Handler mHandler;

    public PlacesAdapter(List<FoursquareVenue> data, Context context, Handler handler) {
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHandler = handler;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlaceItemView view;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_place, parent, false);
        }
        view = (PlaceItemView) convertView;

        FoursquareVenue venue = (FoursquareVenue) getItem(position);
        //movieData.setId(position);

        view.bindView(venue, mHandler); 
        return view;
    }
    
    public void setCommunicationHandler(Handler handler) {
        mHandler = handler;
    }

    public void updateItems(List<FoursquareVenue> entries) {
        mData = entries;
        notifyDataSetChanged();
    }
    
    public void removeItem(int id) {
        mData.remove(id);
        notifyDataSetChanged();
    }
}
