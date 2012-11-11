package com.everhack.compassnote;

import org.apache.thrift.TException;

import com.everhack.compassnote.activity.ListPlacesActivity;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteStore.Client;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class BriefActivity extends Activity {
    private WebView brief_webview;
    private Button btn_next;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brief);

        brief_webview = (WebView) findViewById(R.id.brief_webview);
        btn_next = (Button) findViewById(R.id.btn_next);

        //brief_webview.setd
        String mime = "text/html";
        String encoding = "utf-8";

        //brief_webview.getSettings().setJavaScriptEnabled(true);

        CompassApp app = ((CompassApp) getApplication());
        brief_webview.loadDataWithBaseURL(null, app.getSessionContext().getRoteiro().toHTML(), mime, encoding, null);

        btn_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO # salvar o roteiro e enviar para a tela de escolha de cidade, ou de roteiros feitos
                try {
                    CompassApp app = ((CompassApp) getApplication());
                    Client c = app.getSessionContext().getNoteStore();
                    Notebook nb = c.getNotebook(app.getSessionContext().getSession(BriefActivity.this).getAuthToken(), app.getSessionContext().getNotebookGuid());

                    Note note = app.getSessionContext().getNoteFromRoteiro(app.getSessionContext().getRoteiro());
                    note.setNotebookGuid(nb.getGuid());
                    note = c.createNote(app.getSessionContext().getSession(BriefActivity.this).getAuthToken(), note);

                    Intent intent = new Intent(BriefActivity.this, ListPlacesActivity.class);
                    startActivity(intent);
                } catch (EDAMUserException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (EDAMSystemException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (EDAMNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (TException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

    }
}
