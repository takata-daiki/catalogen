//Copyright (C) 2010  Novabit Informationssysteme GmbH
//
//This file is part of Nuclos.
//
//Nuclos is free software: you can redistribute it and/or modify
//it under the terms of the GNU Affero General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//Nuclos is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Affero General Public License for more details.
//
//You should have received a copy of the GNU Affero General Public License
//along with Nuclos.  If not, see <http://www.gnu.org/licenses/>.
package org.nuclos.common2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nuclos.common.NuclosMarshalledInputStream;
import org.nuclos.common2.exception.CommonFatalException;

/**
 * Utility methods for input/output.
 * <br>
 * <br>Created by Novabit Informationssysteme GmbH
 * <br>Please visit <a href="http://www.novabit.de">www.novabit.de</a>
 *
 * @author	<a href="mailto:Christoph.Radig@novabit.de">Christoph.Radig</a>
 * @version	01.00.00
 */
public class IOUtils {

	private IOUtils() {
	}

	/**
	 * reads the contents of a text file, using the default encoding.
	 * 
	 * ยงtodo the returned String does not contain any newlines - correct this!
	 * ยงtodo move ElisaConsole.readFromTextFile to this class
	 * ยงtodo call readFromTextFile( File file, String sEncoding)
	 *
	 * @param file a File that must have a size &lt; 2GB.
	 * @return a String containing the contents of the file.
	 * @throws java.io.IOException
	 * 
	 * @deprecated Use {@link #readFromTextFile(File, String)}
	 */
	public static String readFromTextFile(File file) throws IOException {
		final FileReader fr = new FileReader(file);
		try {
			final BufferedReader br = new BufferedReader(fr);
			try {
				final StringBuffer sb = new StringBuffer();
				while (true) {
					final String sLine = br.readLine();
					if (sLine == null) {
						break;
					}
					sb.append(sLine);
				}
				return sb.toString();
			}
			finally {
				br.close();
			}
		}
		finally {
			fr.close();
		}
	}
	
	private static final Pattern XML_PREABLE_ENCODING = Pattern.compile("^<\\?xml.* encoding=[\'\"]([^\'\"]*)[\'\"].*\\?>");
	
	public static Charset guessXmlEncoding(String xml) {
		final Matcher matcher = XML_PREABLE_ENCODING.matcher(xml);
		Charset result = null;
		if (matcher.find()) {
			result = Charset.forName(matcher.group(1));
		}
		return result;
	}
	
	public static Charset guessXmlEncoding(BufferedReader input) throws IOException {
		assert input.markSupported();
		input.mark(200);
		try {
			char[] buffer = new char[199];
			input.read(buffer);
			return guessXmlEncoding(new String(buffer));
		}
		finally {
			input.reset();
		}
	}
	
	public static Charset guessXmlEncoding(BufferedInputStream input) throws IOException {
		assert input.markSupported();
		input.mark(400);
		try {
			byte[] buffer = new byte[399];
			input.read(buffer);
			return guessXmlEncoding(new String(buffer));
		}
		finally {
			input.reset();
		}
	}	

	/**
	 * Reads the text contents of the given input stream with the specified encoding.
	 *
	 * @param file a File that must have a size &lt; 2GB.
	 * @param encoding encoding of the resulting String
	 * @return a String containing the contents of the file.
	 * @throws java.io.IOException
	 */
	public static String readFromTextFile(File file, String encoding) throws IOException, UnsupportedEncodingException {
		return readFromTextStream(new FileInputStream(file), encoding);
	}

	public static String readFromXmlFile(File file, String encodingIfItCantBeGuessed) throws UnsupportedEncodingException, IOException {
		final BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
		Charset cs = guessXmlEncoding(input);
		if (cs == null) {
			cs = Charset.forName(encodingIfItCantBeGuessed);
		}
		return readFromTextStream(input, cs.name());
	}
	
	/**
	 * Reads the text contents of a given input stream with the specified encoding.
	 *
	 * @param is a input stream
	 * @param encoding encoding of the resulting String
	 * @return a String containing the contents of the file.
	 * @throws java.io.IOException
	 */
	public static String readFromTextStream(InputStream is, String encoding) throws IOException, UnsupportedEncodingException {
		final InputStreamReader isr = encoding == null ? new InputStreamReader(is) :new InputStreamReader(is, encoding);
		try {
			final BufferedReader br = new BufferedReader(isr);
			try {
				final StringBuilder sb = new StringBuilder();
				while (true) {
					final String sLine = br.readLine();
					if (sLine == null) {
						break;
					}
					sb.append(sLine);
					sb.append('\n');
				}
				if (sb.length() > 0) {
					sb.deleteCharAt(sb.length() - 1);
				}
				return sb.toString();
			}
			finally {
				br.close();
			}
		}
		finally {
			isr.close();
		}
	}
	/**
	 *
	 * @param file the file to write
	 * @param sText String to write in file
	 * @param sEncoding the used charecter encoding
	 */
	public static void writeToTextFile(File file, String sText, String sEncoding) throws IOException, UnsupportedEncodingException {
		final Writer osw = new OutputStreamWriter(new FileOutputStream(file), sEncoding);
		try {
			final Writer bw = new BufferedWriter(osw);
			try {
				bw.write(sText);
			}
			finally {
				bw.close();
			}
		}
		finally {
			osw.close();
		}
	}
	
	public static void writeToTextFile(File file, InputStream sText, String sEncoding) throws IOException, UnsupportedEncodingException {
		final Reader reader = new BufferedReader(new InputStreamReader(sText, sEncoding));
		final char[] buffer = new char[1024 * 16];
		final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), sEncoding));
		int i;
		try {
			while ((i = reader.read(buffer)) >= 0) {
				if (i > 0) {
					writer.write(buffer, 0, i);
				} else if (i == 0) {
					Thread.yield();
				}
			}
		} finally {
			reader.close();
			writer.close();
		}
	}
	
	public static void writeToXmlFile(File file, String xml, String encodingIfItCantBeGuessed) throws UnsupportedEncodingException, IOException {
		Charset cs = guessXmlEncoding(xml);
		if (cs == null) {
			cs = Charset.forName(encodingIfItCantBeGuessed);
		}
		writeToTextFile(file, xml, cs.name());
	}

	public static void writeToXmlFile(File file, BufferedInputStream xml, String encodingIfItCantBeGuessed) throws UnsupportedEncodingException, IOException {
		Charset cs = guessXmlEncoding(xml);
		if (cs == null) {
			cs = Charset.forName(encodingIfItCantBeGuessed);
		}
		writeToTextFile(file, xml, cs.name());
	}

	/**
	 * reads the contents of a binary file.
	 *
	 * @param file a File that must have a size &lt; 2GB.
	 * @return a byte[] containing the contents of the file.
	 * @throws java.io.IOException
	 */
	public static byte[] readFromBinaryFile(File file) throws IOException {
		final FileInputStream fis = new FileInputStream(file);
		final long lFileSize = file.length();
		if (lFileSize > Integer.MAX_VALUE) {
			fis.close();
			throw new CommonFatalException(StringUtils.getParameterizedExceptionMessage("ioutils.file.error", file.getAbsolutePath()));
		}
		return readFromBinaryStream(fis);
	}
	
	public static byte[] readFromBinaryStream(InputStream fis) throws IOException {
		try {
			final BufferedInputStream bis = new BufferedInputStream(fis);
			try {
				return org.apache.commons.io.IOUtils.toByteArray(bis);
			}
			finally {
				bis.close();
			}
		}
		finally {
			fis.close();
		}
	}

	/**
	 * writes the given byte array to the given binary file.
	 *
	 * @param file
	 * @throws java.io.IOException
	 */
	public static void writeToBinaryFile(File file, byte[] ab) throws IOException {
		final FileOutputStream fos = new FileOutputStream(file);
		try {
			final BufferedOutputStream bos = new BufferedOutputStream(fos);
			try {
				bos.write(ab);
			}
			finally {
				bos.close();
			}
		}
		finally {
			fos.close();
		}
	}

	/**
	 * @param fileIn
	 * @param fileOut
	 * @throws IOException
	 */
	public static void copyFile(File fileIn, File fileOut) throws IOException {
		FileChannel sourceChannel = null;
		FileChannel destinationChannel = null;
		try {
			sourceChannel = new FileInputStream(fileIn).getChannel();
			destinationChannel = new FileOutputStream(fileOut).getChannel();
			sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
		}
		finally {
			try {
				if (sourceChannel != null) {
					sourceChannel.close();
				}
			}
			finally {
				if (destinationChannel != null) {
					destinationChannel.close();
				}
			}
		}
	}

	/**
	 * @param o must be serializable.
	 * @return a byte array containing the serialized object.
	 * @throws IOException
	 */
	public static byte[] toByteArray(Serializable o) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final ObjectOutputStream oos = new ObjectOutputStream(baos);
		try {
			oos.writeObject(o);
		}
		finally {
			oos.close();
		}
		return baos.toByteArray();
	}

	/**
	 *
	 * @param ab a byte array containing a serialized object.
	 * @return the object deserialized from <code>ab</code>.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Serializable fromByteArray(final byte[] ab) throws IOException, ClassNotFoundException {
		final ObjectInputStream ois = new NuclosMarshalledInputStream(new ByteArrayInputStream(ab));
		final Object result;
		try {
			result = ois.readObject();
		}
		finally {
			ois.close();
		}
		return (Serializable) result;
	}

	/**
	 * @deprecated Try to avoid because it uses BOM (https://de.wikipedia.org/wiki/Byte_Order_Mark) stuff. (tp)
	 */
	public static byte[] convert(byte[] bytes, String encout) throws Exception {
		if (encout == null) 
			return bytes; // no conversion - return.
		
		// Workaround for bug that will not be fixed by SUN
		// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4508058
		UnicodeInputStream uis = new UnicodeInputStream(
				new ByteArrayInputStream(bytes), "ASCII");
		boolean unicodeOutputReqd = (getBOM(encout) != null) ? true : false;
		String enc = uis.getEncoding();
		byte[] BOM = getBOM(enc); // get the BOM of the inputstream

		if (BOM == null) {
			// inputstream looks like ascii...
			// create a BOM based on the outputstream
			BOM = getBOM(encout);
		}
		uis.close();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(bytes, uis.getBOMOffset(),
						bytes.length), enc));
		Writer w = new BufferedWriter(new OutputStreamWriter(out, encout));

		// dont write a BOM for ascii(out) as the OutputStreamWriter
		// will not process it correctly.
		if (BOM != null && unicodeOutputReqd) {
			out.write(BOM);
		}

		char[] buffer = new char[4096];
		int len;
		while ((len = br.read(buffer)) != -1) {
			w.write(buffer, 0, len);
		}

		br.close(); // Close the input.
		w.close(); // Flush and close output.
		return out.toByteArray();
	}

	/**
	 * BOM = Byte Order Mark
	 * 
	 * @see https://de.wikipedia.org/wiki/Byte_Order_Mark
	 * @deprecated Try to avoid. (tp)
	 */
	public static byte[] getBOM(String enc) throws UnsupportedEncodingException {
		if ("UTF-8".equals(enc)) {
			byte[] bom = new byte[3];
			bom[0] = (byte) 0xEF;
			bom[1] = (byte) 0xBB;
			bom[2] = (byte) 0xBF;
			return bom;
		} else if ("UTF-16BE".equals(enc)) {
			byte[] bom = new byte[2];
			bom[0] = (byte) 0xFE;
			bom[1] = (byte) 0xFF;
			return bom;
		} else if ("UTF-16LE".equals(enc)) {
			byte[] bom = new byte[2];
			bom[0] = (byte) 0xFF;
			bom[1] = (byte) 0xFE;
			return bom;
		} else if ("UTF-32BE".equals(enc)) {
			byte[] bom = new byte[4];
			bom[0] = (byte) 0x00;
			bom[1] = (byte) 0x00;
			bom[2] = (byte) 0xFE;
			bom[3] = (byte) 0xFF;
			return bom;
		} else if ("UTF-32LE".equals(enc)) {
			byte[] bom = new byte[4];
			bom[0] = (byte) 0x00;
			bom[1] = (byte) 0x00;
			bom[2] = (byte) 0xFF;
			bom[3] = (byte) 0xFE;
			return bom;
		} else {
			return null;
		}
	}

	public static class UnicodeInputStream extends InputStream {
		private PushbackInputStream internalIn;

		private boolean isInited = false;

		private int BOMOffset = -1;

		private String defaultEnc;

		private String encoding;

		public static final int BOM_SIZE = 4;

		public UnicodeInputStream(InputStream in, String defaultEnc) {
			internalIn = new PushbackInputStream(in, BOM_SIZE);
			this.defaultEnc = defaultEnc;
		}

		public String getDefaultEncoding() {
			return defaultEnc;
		}

		public String getEncoding() {
			if (!isInited) {
				try {
					init();
				} catch (IOException ex) {
					IllegalStateException ise = new IllegalStateException(
							"Init method failed.");
					ise.initCause(ise);
					throw ise;
				}
			}
			return encoding;
		}

		/**
		 * Read-ahead four bytes and check for BOM marks. Extra bytes are unread
		 * back to the stream, only BOM bytes are skipped.
		 */
		protected void init() throws IOException {
			if (isInited)
				return;

			byte bom[] = new byte[BOM_SIZE];
			int n, unread;
			n = internalIn.read(bom, 0, bom.length);

			if ((bom[0] == (byte) 0x00) && (bom[1] == (byte) 0x00)
					&& (bom[2] == (byte) 0xFE) && (bom[3] == (byte) 0xFF)) {
				encoding = "UTF-32BE";
				unread = n - 4;
			} else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)
					&& (bom[2] == (byte) 0x00) && (bom[3] == (byte) 0x00)) {
				encoding = "UTF-32LE";
				unread = n - 4;
			} else if ((bom[0] == (byte) 0xEF) && (bom[1] == (byte) 0xBB)
					&& (bom[2] == (byte) 0xBF)) {
				encoding = "UTF-8";
				unread = n - 3;
			} else if ((bom[0] == (byte) 0xFE) && (bom[1] == (byte) 0xFF)) {
				encoding = "UTF-16BE";
				unread = n - 2;
			} else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)) {
				encoding = "UTF-16LE";
				unread = n - 2;
			} else {
				// Unicode BOM mark not found, unread all bytes
				encoding = defaultEnc;
				unread = n;
			}
			BOMOffset = BOM_SIZE - unread;
			if (unread > 0)
				internalIn.unread(bom, (n - unread), unread);

			isInited = true;
		}

		public void close() throws IOException {
			// init();
			isInited = true;
			internalIn.close();
		}

		public int read() throws IOException {
			// init();
			isInited = true;
			return internalIn.read();
		}

		public int getBOMOffset() {
			return BOMOffset;
		}
	}

}  // class IOUtils
