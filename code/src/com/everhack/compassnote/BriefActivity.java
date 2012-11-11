package com.everhack.compassnote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class BriefActivity extends Activity {
    private WebView brief_webview;
    private Button btn_next;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brief);

        // TODO: pegar lista de itens do checklist de algum lugar fixo (pode ter uma lista no strings.xml)

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
				// TODO: salvar o roteiro e enviar para a tela de escolha de cidade, ou de roteiros feitos
			}
		});

    }
}
