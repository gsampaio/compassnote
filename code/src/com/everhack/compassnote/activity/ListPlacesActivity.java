package com.everhack.compassnote.activity;

import java.util.ArrayList;
import java.util.List;

import com.everhack.compassnote.R;
import com.everhack.compassnote.adapter.PlacesAdapter;
import com.everhack.compassnote.foursquare.FoursquareServiceDelegate;
import com.everhack.compassnote.foursquare.FoursquareVenue;
import com.everhack.compassnote.foursquare.FoursquareVenueService;
import com.everhack.compassnote.model.PlaceData;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class ListPlacesActivity extends ListActivity {
    private PlacesAdapter mAdapter;

    private final int MENU_REFRESH = 0;

    public final static int EVENT_FAVORITE_ITEM = 0;

    public final static String INTENT_EXTRA_CITY = "city";

    private List<FoursquareVenue> mSelectedVenues = new ArrayList<FoursquareVenue>();
    private EventHandler mHandler = new EventHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_list_places);

        Intent intent = getIntent();
        String city = intent.getExtras().getString(INTENT_EXTRA_CITY);

        setProgressBarIndeterminateVisibility(true);

        if (city != null) {
            FoursquareVenueService.getVenuesInCity(city, new FoursquareServiceDelegate<List<FoursquareVenue>>() {
                @Override
                public void receivedResponse(List<FoursquareVenue> response) {
                    List<FoursquareVenue> venueList =  response;

                    mAdapter = new PlacesAdapter(venueList, ListPlacesActivity.this, mHandler);
                    setListAdapter(mAdapter);
                    setProgressBarIndeterminateVisibility(false);
                }

                @Override
                public void requestFailed() {
                    setProgressBarIndeterminateVisibility(false);
                }
            });
        }

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_places, menu);
        return true;
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    /**
     * Communicator handler. It should be used in order to link list item views and this acitivty.
     */
    private class EventHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case EVENT_FAVORITE_ITEM:
            {
                int id = msg.arg1;
                mAdapter.removeItem(id);
            }
            }
        }
    }


}

