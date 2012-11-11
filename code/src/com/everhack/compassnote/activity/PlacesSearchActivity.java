package com.everhack.compassnote.activity;

import com.everhack.compassnote.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PlacesSearchActivity extends Activity {

    private Button mButtonNext;
    private EditText mEditTextCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
        setContentView(R.layout.activity_search_places);

        setupUI();
    }

    private void setupUI() {
        mEditTextCity = (EditText) findViewById(R.id.editTextPlace);

        mButtonNext = (Button) findViewById(R.id.buttonNextScreen);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                String city = mEditTextCity.getText().toString();
                Intent intent = new Intent(PlacesSearchActivity.this, ListPlacesActivity.class);
                intent.putExtra(ListPlacesActivity.INTENT_EXTRA_CITY, city);
                startActivity(intent);
            }
        });
    }
}
