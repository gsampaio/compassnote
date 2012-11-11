package com.everhack.compassnote;

import org.apache.thrift.transport.TTransportException;

import android.content.Context;

import com.everhack.compassnote.foursquare.FoursquareVenue;
import com.everhack.compassnote.model.Checklist;
import com.everhack.compassnote.model.Roteiro;
import com.evernote.client.oauth.android.EvernoteSession;
import com.evernote.edam.notestore.NoteStore.Client;
import com.evernote.edam.type.Note;

public class ApplicationContext {

    private static ApplicationContext sApplicationContext;

    private EvernoteSession mEvernoteSession;
    private Roteiro roteiro;
    private Client noteStore;
    private String notebookGuid;

    // Your Evernote API key. See http://dev.evernote.com/documentation/cloud/
    // Please obfuscate your code to help keep these values secret.
    private static final String CONSUMER_KEY = "spitfire2903";
    private static final String CONSUMER_SECRET = "0b670c072a62aab0";

    // Initial development is done on Evernote's testing service, the sandbox.
    // Change to HOST_PRODUCTION to use the Evernote production service
    // once your code is complete, or HOST_CHINA to use the Yinxiang Biji
    // (Evernote China) production service.
    private static final String EVERNOTE_HOST = EvernoteSession.HOST_SANDBOX;

    public EvernoteSession getSession(Context context) {
        if (mEvernoteSession == null) {
            mEvernoteSession = EvernoteSession.init(context, CONSUMER_KEY,
                    CONSUMER_SECRET, EVERNOTE_HOST, null);
        }

        return mEvernoteSession;
    }

    public Roteiro getRoteiro() {
        return roteiro;
    }

    public void setRoteiro(Roteiro roteiro) {
        this.roteiro = roteiro;
    }

    public Client getNoteStore() {
        return this.noteStore;
    }

    public void setNoteStore(Client noteStore) {
        this.noteStore = noteStore;
    }

    public String getNotebookGuid() {
        return this.notebookGuid;
    }

    public void setNotebookGuid(String guid) {
        this.notebookGuid = guid;
    }

    // TODO # Fazer metodos que fazem o parse do roteiro pro note e viceversa,
    // talvez jogar numa class util
    public Note getNoteFromRoteiro(Roteiro r) {
        Note n = new Note();
        n.setTitle(r.getCity());
        n.setContent(r.toEvernote());

        return n;
    }

    public Roteiro getRoteiroFromNote(Note n) {
        return new Roteiro();
    }

}
