package net.retroloop.duct.note;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class Notes implements Iterable<Note> {
	
	
	public static Notes make(AnnotatedElement annotated) throws NoteException {
		List<Note> out = new ArrayList<Note>();
		for (Annotation ann : annotated.getAnnotations()) {
			NoteAnnotation noteAnn = ann.annotationType().getAnnotation(NoteAnnotation.class);
			if (noteAnn != null) {
				try {
					out.add(noteAnn.value()
							.getConstructor(ann.annotationType())
							.newInstance(noteAnn));
				} catch (SecurityException e) {
					throw new NoteException(e);
				} catch (NoSuchMethodException e) {
					throw new NoteException(e);
				} catch (IllegalArgumentException e) {
					throw new NoteException(e);
				} catch (InstantiationException e) {
					throw new NoteException(e);
				} catch (IllegalAccessException e) {
					throw new NoteException(e);
				} catch (InvocationTargetException e) {
					throw new NoteException(e);
				}
			}
		}
		return new Notes(out);
	}
	
	
	public static Notes make(Note... notes) {
		List<Note> out = new ArrayList<Note>();
		for (Note note : notes) {
			out.add(note);
		}
		return new Notes(out);
	}
	
	
	public Notes(List<Note> notes) {
		this.notes = notes;
	}

	private final List<Note> notes;

	
	@Override
	public Iterator<Note> iterator() {
		return notes.iterator();
	}
	
	
	public <N extends Note> Note note(Class<N> noteClass) {
		for (Note note : notes) {
			if (noteClass.isAssignableFrom(note.getClass())) {
				return note;
			}
		}
		return null;
	}
	
	
}
