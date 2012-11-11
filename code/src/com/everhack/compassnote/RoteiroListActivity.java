package com.everhack.compassnote;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.everhack.compassnote.model.Roteiro;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteMetadata;
import com.evernote.edam.notestore.NoteStore.Client;
import com.evernote.edam.notestore.NotesMetadataList;
import com.evernote.edam.notestore.NotesMetadataResultSpec;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;

public class RoteiroListActivity extends Activity {
	private ListView roteiroList;
	private List<Roteiro> roteiros;
	//private List<Note> ns;
	private RoteiroAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checklist);

		roteiros = new ArrayList<Roteiro>();

		// FIXME: Jogar numa asynctask separado

		CompassApp app = ((CompassApp) getApplication());
		Client c = app.getSessionContext().getNoteStore();
		try {
			Notebook nb = c.getNotebook(app.getSessionContext().getSession(this).getAuthToken(), app.getSessionContext().getNotebookGuid());

			int pageSize = 10;
			NoteFilter filter = new NoteFilter();
			filter.setOrder(NoteSortOrder.UPDATED.getValue());

			NotesMetadataResultSpec spec = new NotesMetadataResultSpec();
			spec.setIncludeTitle(true);

			NotesMetadataList notes = null;

			notes = c.findNotesMetadata(app.getSessionContext().getSession(this).getAuthToken(), filter, 0, pageSize, spec);

			if(notes != null){
				int matchingNotes = notes.getTotalNotes();
				int notesThisPage = notes.getNotes().size();

				for (NoteMetadata note : notes.getNotes()) {
					roteiros.add(app.getSessionContext().getRoteiroFromNote(c.getNote(app.getSessionContext().getSession(this).getAuthToken(), note.getGuid(), true, true, false, false)));
				}
			}
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
		/*
        nb.get
		Notebook nb = new Notebook();
		nb.setName("Novo notebook "+Math.random());

		nb = c.createNotebook(mEvernoteSession.getAuthToken(), nb);


		String t2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
				"<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">"+
				"<en-note>"+
				"Textoim"+
				"</en-note>";

		Note n2 = new Note();
		n2.setTitle("Nova note 3b");
		n2.setContent(t2);
		n2.setNotebookGuid(nb.getGuid());

		//n2 =
				c.createNote(mEvernoteSession.getAuthToken(), n2);

		String t1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
				"<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">"+
				"<en-note>"+
				"Tex <img src=\"http://www.judahfrangipane.com/blog/wp-content/uploads/2006/12/file_encoding.png\"/>"+
				"</en-note>";
		Note n = new Note();
		n.setTitle("Nova note 3a");
		n.setContent(t1);
		n.setNotebookGuid(nb.getGuid());

		n = c.createNote(mEvernoteSession.getAuthToken(), n);

		 */

		roteiroList = (ListView) findViewById(R.id.roteiro_list);

		adapter = new RoteiroAdapter(this, R.layout.row_roteirolist, roteiros);

		roteiroList.setAdapter(adapter);
		roteiroList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				CompassApp app = ((CompassApp) getApplication());
				app.getSessionContext().setRoteiro(roteiros.get(position));

				// TODO: jogar para a descricao do roteiro
			}
		});

	}
}
