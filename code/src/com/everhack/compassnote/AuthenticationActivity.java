package com.everhack.compassnote;

import com.everhack.compassnote.activity.PlacesSearchActivity;
import com.evernote.client.oauth.android.EvernoteSession;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AuthenticationActivity extends Activity {
    private Button mLoginButton;
    private Button mTestButton;
    private TextView mInformationMessagesText;
    
    private EvernoteSession mEvernoteSession;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        setupUI();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);

        CompassApp app = ((CompassApp) getApplication()); 
        mEvernoteSession = app.getSessionContext().getSession(getApplicationContext());
    }
    
    /**
     * Called when the control returns from an activity that we launched.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      switch(requestCode) {
        //Update UI when oauth activity returns result
        case EvernoteSession.REQUEST_CODE_OAUTH:
          if(resultCode == Activity.RESULT_OK) {
              moveToNextActivityIfNeeded();
          }
          break;
      }
    }

    @Override
    public void onResume() {
      super.onResume();

      moveToNextActivityIfNeeded();
    }
    
    private void moveToNextActivityIfNeeded() {
        if (mEvernoteSession.isLoggedIn()) {
            Intent intent = new Intent(this, PlacesSearchActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void setupUI() {

        mInformationMessagesText = (TextView) findViewById(R.id.textInformationMessages);
        mInformationMessagesText.setText(R.string.welcome_message);

        mLoginButton = (Button) findViewById(R.id.buttonLogin);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                startAuth();
            }
        });
        
        mTestButton = (Button) findViewById(R.id.testButton);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthenticationActivity.this, PlacesSearchActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_authentication, menu);
        return true;
    }

    public void startAuth() {
        if (!mEvernoteSession.isLoggedIn()) {
            mEvernoteSession.authenticate(this);
        }
        updateUi();
    }
    
    /**
     * Update the UI based on Evernote authentication state.
     */
    private void updateUi() {
      if (!mEvernoteSession.isLoggedIn()) {
          mInformationMessagesText.setVisibility(View.VISIBLE);
          mInformationMessagesText.setText(R.string.authentication_error);
      }
    }
}
