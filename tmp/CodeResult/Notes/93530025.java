package com.toukubo.e2h;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.notestore.NoteMetadata;
import com.evernote.edam.notestore.NotesMetadataList;
import com.evernote.edam.notestore.NotesMetadataResultSpec;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteSortOrder;

public class Notes {
	//
	//	  private static final String AUTH_TOKEN = "S=s1:U=5e207:E=14dc76cabc7:C=1466fbb7fca:P=1cd:A=en-devtoken:V=2:H=df4abf0a86fdeadfd0c1b5f675491fab"; // sandbox
	Collection<NoteMetadata> notes = null;
	//		  private NoteStoreClient noteStore;
	private String newNoteGuid;
	EvernoteConfig config = EvernoteConfig.createConfig();;
	ArrayList<com.toukubo.e2h.Note> ourNotes = new ArrayList<com.toukubo.e2h.Note>();


	public List<com.toukubo.e2h.Note> getOurNotes() {
		return ourNotes;
	}

	public void setOurNotes(ArrayList<com.toukubo.e2h.Note> ourNotes) {
		this.ourNotes = ourNotes;
	}

	public Notes(String query){
		try {

			this.notes = searchNotes(query).getNotes();
			for (NoteMetadata note : notes) {
				com.toukubo.e2h.Note ournote = new com.toukubo.e2h.Note(note);
				ourNotes.add(ournote);

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private NotesMetadataList searchNotes(String query) throws Exception {

		NoteFilter filter = new NoteFilter();

		filter.setWords(query);
		filter.setOrder(NoteSortOrder.UPDATED.getValue());
		filter.setAscending(false);

		// Find the first 50 notes matching the search
		System.out.println("Searching for notes matching query: " + query);
		NotesMetadataResultSpec spec = new NotesMetadataResultSpec();
		spec.setIncludeTitle(true);
		spec.setIncludeTagGuids(true);
		spec.setIncludeNotebookGuid(true);
		NotesMetadataList notes = EvernoteConfig.createConfig().getNoteStore().findNotesMetadata(filter, 0, 50,spec);

		return notes;
	}

	public Collection<NoteMetadata> getNotes() {
		return this.notes;
	}

	public static void main(String[] args) {
		Collection<com.toukubo.e2h.Note> notes = new Notes("tag:@pomodoro").getOurNotes();
		for (com.toukubo.e2h.Note note : notes) {
			note.note.getTagNames();
		}
	}

}
