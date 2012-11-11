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
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ListPlacesActivity extends ListActivity {
    private PlacesAdapter mAdapter;

    private final int MENU_REFRESH = 0;

    public final static int EVENT_FAVORITE_ITEM = 0;

    public final static String INTENT_EXTRA_CITY = "city";

    private List<PlaceData> mEntries = new ArrayList<PlaceData>();
    private EventHandler mHandler = new EventHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_places);

        Intent intent = getIntent();
        String city = intent.getExtras().getString(INTENT_EXTRA_CITY);

        if (city != null) {
            FoursquareVenueService.getVenuesInCity(city, new FoursquareServiceDelegate<List<FoursquareVenue>>() {
                @Override
                public void receivedResponse(List<FoursquareVenue> response) {
                    List<FoursquareVenue> venueList =  response;

                    mAdapter = new PlacesAdapter(venueList, ListPlacesActivity.this, mHandler);
                    setListAdapter(mAdapter);
                    //Set loading false

                }

                @Override
                public void requestFailed() {
                    // TODO Auto-generated method stub

                }
            });
        }

        //TODO Set loading

        //        createFakeEntries();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        PlaceData entry = (PlaceData) getListAdapter().getItem(position);
        Toast.makeText(this, "TODO" + " " + entry.getTitle() + "!", Toast.LENGTH_SHORT).show();
    }


    private void createFakeEntries() {
        //Add fake entries
        //        mEntries.add(new PlaceData("Love & Death", "Woody Allen",
        //                        "In czarist Russia, a neurotic soldier and his distant cousin formulate a plot to assassinate Napoleon.",
        //                        R.drawable.boris_grush_screen));
        //
        //        mEntries.add(new PlaceData("8 1/2", "Federico Fellini",
        //                "A harried movie director retreats into his memories and fantasies.",
        //                R.drawable.oito_meio_screen));
        //
        //        mEntries.add(new PlaceData("Ice Age", "Chris Wedge & Carlos Saldanha",
        //                "Set during the Ice Age, a sabertooth tiger, a sloth, and a wooly mammoth find a lost human infant, and they try to return him to his tribe.",
        //                R.drawable.ice_age_screen));
        //
        //        mEntries.add(new PlaceData("Little Miss Sunsine", "Jonathan Dayton & Valerie Faris",
        //                "A family determined to get their young daughter into the finals of a beauty pageant take a cross-country trip in their VW bus.",
        //                R.drawable.little_miss_scene));
        //
        //        mEntries.add(new PlaceData("On the road", "Walter Salles",
        //                "Dean and Sal are the portrait of the Beat Generation. Their search for \"It\" results in a fast paced, energetic roller coaster ride with highs and lows throughout the U.S.",
        //                R.drawable.on_the_road_screen));
        //
        //        mEntries.add(new PlaceData("Marley & Me", "David Frankel",
        //                "A family learns important life lessons from their adorable, but naughty and neurotic dog.",
        //                R.drawable.marley_scene));
        //
        //        mEntries.add(new PlaceData("Trainspotting", "Danny Boyle",
        //                "Renton, deeply immersed in the Edinburgh drug scene, tries to clean up and get out, despite the allure of the drugs and influence of friends.",
        //                R.drawable.trainspotting_scene));
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

