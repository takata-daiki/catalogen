package tk.exgerm.eventtracker;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class DocumentOutputStream extends OutputStream {

	private Document _doc;

	/** Creates a new instance of DocumentOutputStream */
	public DocumentOutputStream(Document doc) {
		if (doc == null) {
			throw new NullPointerException(
					"Cannot pass a null document to this constructor");
		}
		_doc = doc;
	}

	public void write(byte[] buf, int offset, int length) throws IOException {
		try {
			_doc.insertString(_doc.getLength(),
					new String(buf, offset, length), null);
		} catch (BadLocationException ble) {
			throw new IOException("Document append failed : " + ble);
		}
	}

	public void write(int b) throws IOException {
		try {
			_doc.insertString(_doc.getLength(), new String(
					new byte[] { (byte) b }), null);
		} catch (BadLocationException ble) {
			throw new IOException("Document append failed : " + ble);
		}
	}
}
