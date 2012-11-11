package com.everhack.compassnote;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.everhack.compassnote.activity.PlacesSearchActivity;
import com.evernote.client.oauth.android.EvernoteSession;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteStore.Client;
import com.evernote.edam.type.Notebook;

public class AuthenticationActivity extends Activity {
    private Button mLoginButton;
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
        switch (requestCode) {
        // Update UI when oauth activity returns result
        case EvernoteSession.REQUEST_CODE_OAUTH:
            if (resultCode == Activity.RESULT_OK) {
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

    private void updateUi() {
        if (!mEvernoteSession.isLoggedIn()) {
            mInformationMessagesText.setVisibility(View.VISIBLE);
            mInformationMessagesText.setText(R.string.authentication_error);
        } else {

            // FIXME: Verificar se aqui eh o lugar certo para verificar se jah
            // temos o guid no applicationCtx, o ideal era verificar sempre que
            // abrisse o app
            CompassApp app = ((CompassApp) getApplication());

            try {
                app.getSessionContext().setNoteStore(mEvernoteSession.createNoteStore());
            } catch (TTransportException e) {
                e.printStackTrace();
            }


            Client c = app.getSessionContext().getNoteStore();
            Notebook nb = null;

            if (app.getSessionContext().getNotebookGuid() == null) {
                List<Notebook> nbs = new ArrayList<Notebook>();
                try {
                    nbs = app.getSessionContext().getNoteStore().listNotebooks(mEvernoteSession.getAuthToken());
                    for (Notebook n : nbs) {
                        if (n.getName().equals(getResources().getString(R.string.app_name))) {
                            app.getSessionContext().setNotebookGuid(n.getGuid());
                            return;
                        }
                    }

                    nb = new Notebook();
                    nb.setName(getResources().getString(R.string.app_name));
                    nb = c.createNotebook(mEvernoteSession.getAuthToken(), nb);
                } catch (EDAMUserException e) {
                    e.printStackTrace();
                } catch (EDAMSystemException e) {
                    e.printStackTrace();
                } catch (TException e) {
                    e.printStackTrace();
                }

                if (nb != null)
                    app.getSessionContext().setNotebookGuid(nb.getGuid());
            }
        }
    }
}

