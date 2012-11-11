package com.everhack.compassnote.activity;

import com.everhack.compassnote.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class PlacesSearchActivity extends Activity {

    private Button mButtonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
        setContentView(R.layout.activity_search_places);
    }

    private void setupUI() {
        mButtonNext = (Button) findViewById(R.id.buttonNextScreen);
    }
}
