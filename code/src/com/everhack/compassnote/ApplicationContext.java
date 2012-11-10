package com.everhack.compassnote;

import android.content.Context;

import com.evernote.client.oauth.android.EvernoteSession;

public class ApplicationContext {

    private static ApplicationContext sApplicationContext;
    
    private EvernoteSession mEvernoteSession;
    
    // Your Evernote API key. See http://dev.evernote.com/documentation/cloud/
    // Please obfuscate your code to help keep these values secret.
    private static final String CONSUMER_KEY = "spitfire2903";
    private static final String CONSUMER_SECRET = "0b670c072a62aab0";

    // Initial development is done on Evernote's testing service, the sandbox.
    // Change to HOST_PRODUCTION to use the Evernote production service 
    // once your code is complete, or HOST_CHINA to use the Yinxiang Biji
    // (Evernote China) production service.
    private static final String EVERNOTE_HOST = EvernoteSession.HOST_SANDBOX;

    public EvernoteSession getSession(Context context){
        if (mEvernoteSession == null) {
            mEvernoteSession = EvernoteSession.init(context, CONSUMER_KEY, CONSUMER_SECRET, EVERNOTE_HOST, null);
        } 
        return mEvernoteSession;
    }
}
