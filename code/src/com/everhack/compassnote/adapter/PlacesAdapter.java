package com.everhack.compassnote.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.Handler;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.everhack.compassnote.R;
import com.everhack.compassnote.foursquare.FoursquareVenue;
import com.everhack.compassnote.view.PlaceItemView;
import com.everhack.compassnote.view.PlaceItemView.OnItemFavorited;
import com.everhack.compassnote.view.ToggleButton;

public class PlacesAdapter extends BaseAdapter implements OnItemFavorited{

    private List<FoursquareVenue> mData;
    private LayoutInflater mInflater;
    private HashMap<FoursquareVenue, Boolean> mSelecteVenues = new HashMap<FoursquareVenue, Boolean>();
    private LruCache mCache;

    // Helper for communication with owner activity
    private Handler mHandler;

    public PlacesAdapter(List<FoursquareVenue> data, Context context, LruCache cache) {
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCache = cache;
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
        ToggleButton button = (ToggleButton) view.findViewById(R.id.buttonFavorite);
        if (mSelecteVenues.containsKey(getItem(position))) {
            button.setOn();
        } else {
            button.setOff();
        }

        FoursquareVenue venue = (FoursquareVenue) getItem(position);

        view.bindView(venue, this, mCache);
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

    @Override
    public void onItemFavorited(FoursquareVenue venue, boolean isFavorite) {
        if (!isFavorite) {
            mSelecteVenues.remove(venue);
        }else {
            mSelecteVenues.put(venue, isFavorite);
        }
    }

    @Override
    public Set<FoursquareVenue> getFavoritedItems() {
        return mSelecteVenues.keySet();
    }
    
}
