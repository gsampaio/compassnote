package com.everhack.compassnote;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.everhack.compassnote.adapter.ChecklistAdapter;
import com.everhack.compassnote.model.Checklist;
import com.evernote.edam.notestore.NoteStore.Client;

public class ChecklistActivity extends Activity {
    private ListView checklist;
    private List<Checklist> checks;
    private ChecklistAdapter adapter;
    private Button btn_next;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        // TODO: pegar lista de itens do checklist de algum lugar fixo (pode ter uma lista no strings.xml)

        checklist = (ListView) findViewById(R.id.checklist_list);
        btn_next = (Button) findViewById(R.id.btn_next);

        adapter = new ChecklistAdapter(this, R.layout.row_checklist, checks);

        checklist.setAdapter(adapter);
        checklist.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        		if(checks.get(position).getChecked()){
        			checks.get(position).setChecked(false);
        		} else{
        			checks.get(position).setChecked(true);
        		}
        		adapter.notifyDataSetChanged();
        	}
		});

        btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO # pegar itens selecionados, jogar na aplicacao e passar para o activity de resumo
			    List<Checklist> removeList = new ArrayList<Checklist>();
			    for (Checklist c : checks) {
                    if(!c.getChecked())
                        removeList.add(c);
                }
			    checks.removeAll(removeList);

			    CompassApp app = ((CompassApp) getApplication());
		        app.getSessionContext().getRoteiro().setChecklist(checks);

                Intent intent = new Intent(ChecklistActivity.this, BriefActivity.class);
                startActivity(intent);
			}
		});

    }
}
