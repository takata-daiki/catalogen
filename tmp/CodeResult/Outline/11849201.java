package ecicpip.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import ecicpip.highlight.Paint;
import ecicpip.stack.Tagging;

// outline viewer using a simple SWT list
public class Outline implements IContentOutlinePage {
	public void setActionBars(IActionBars actionBars) {}
	private IDocument doc;
	private ISelectionProvider SelProv;
	private ISelectionChangedListener SelCh;
	private SelectionListener SelList;

	private List list;
	private ArrayList<Integer> marks;
	
	public Outline(IDocument doc, final ISelectionProvider SelProv, final TextEditor editor) {
		this.doc = doc;
		this.SelProv = SelProv;
		
		// set in outline then in editor
		this.SelCh = new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				setSelection(event.getSelection());
			}
		};
		this.SelList = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				editor.selectAndReveal(marks.get(list.getSelectionIndex()), 0);
			}
		};
	}
	
	// threads setup
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		list.addSelectionListener(SelList);
		SelProv.addSelectionChangedListener(SelCh);
	}
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		list.removeSelectionListener(SelList);
		SelProv.removeSelectionChangedListener(SelCh);
	}	

	// when activating outline manually, build new; otherwise just locate
	public ISelection getSelection() {
		newOutline();
		setSelection(SelProv.getSelection());
		return null;
	}
	public void setSelection(ISelection selection) {
		TextSelection ts = (TextSelection) selection;
		int offset = ts.getOffset();
		int result = Collections.binarySearch(marks, offset);
		if (result < 0) result = (result * -1) - 2;
		list.setSelection(result);		
	}
	
	// graphics setup
	public void dispose() {
		list.dispose();
	}
	public Control getControl() {
		return list;
	}
	public void setFocus() {
		list.setFocus();
	}
	public void createControl(Composite parent) {
		list = new List (parent, SWT.SINGLE | SWT.V_SCROLL);
		newOutline();
	}
	
	///////////////////////
	private void newOutline() {
		TreeMap<Integer, String> readings = makeIndices(doc.get());		
		ArrayList<String> items = new ArrayList<String>();
		int lastLine = -1;
		Iterator<Integer> e = readings.keySet().iterator();
		while (e.hasNext()) {
			
			try {
				int idx = e.next();
				int line = doc.getLineOfOffset(idx);
				int end = doc.getLineOffset(line) + doc.getLineLength(line);
			
				// ignore comments and "statements" on same line
				if ((readings.get(idx).equals(Tagging.comment)) || (line == lastLine)) {
					e.remove();
					continue;
				}
				else lastLine = line;
				
				items.add(doc.get().substring(idx, end)); // make heading end correctly
			} catch (BadLocationException ex) {}			
		}
		
		// setup
		list.setItems(items.toArray(new String[items.size()]));
		marks = new ArrayList<Integer> (readings.keySet());
	}
	
	// for use by reconciling without knowledge of readings
	public static ArrayList<Integer> makeOutline(String code) {
		return new ArrayList<Integer> (makeIndices(code).keySet());
	}
	
	// extract just top statements from readings
	private static TreeMap<Integer, String> makeIndices(String code) {
		TreeMap<Integer, String> ret = new TreeMap<Integer, String>();
		TreeMap<Integer, String> readings = Tagging.read(code);
		int key = readings.firstKey();
		while (key != -1) {
			ret.put(key, readings.get(key));
			key = Paint.endForm(key, readings, false);
			
			key = readings.higherKey(key) == readings.lastKey() ? -1 : readings.higherKey(key);
		}
		return ret;
	}
}
