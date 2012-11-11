package com.everhack.compassnote;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class RoteiroViewActivity extends Activity {
    private WebView roteiro_webview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteiroview);

        roteiro_webview = (WebView) findViewById(R.id.roteiro_webview);

        String mime = "text/html";
        String encoding = "utf-8";

        CompassApp app = ((CompassApp) getApplication());
        roteiro_webview.loadDataWithBaseURL(null, app.getSessionContext().getRoteiro().toHTML(), mime, encoding, null);
    }
}
