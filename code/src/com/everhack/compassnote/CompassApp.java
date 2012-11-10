package com.everhack.compassnote;

import android.app.Application;

public class CompassApp extends Application {

    private ApplicationContext mApplicationContext;
    
    public CompassApp() {
        mApplicationContext = new ApplicationContext();
    }

    public ApplicationContext getSessionContext() {
       return mApplicationContext;
    }

}
