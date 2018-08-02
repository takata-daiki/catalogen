/**
 * OLAT - Online Learning and Training<br>
 * http://www.olat.org
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); <br>
 * you may not use this file except in compliance with the License.<br>
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,<br>
 * software distributed under the License is distributed on an "AS IS" BASIS, <br>
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. <br>
 * See the License for the specific language governing permissions and <br>
 * limitations under the License.
 * <p>
 * Copyright (c) since 2004 at Multimedia- & E-Learning Services (MELS),<br>
 * University of Zurich, Switzerland.
 * <p>
 */

package org.olat.search.service.document.file;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.olat.core.logging.OLog;
import org.olat.core.logging.Tracing;
import org.olat.core.util.vfs.VFSLeaf;
import org.olat.search.service.SearchResourceContext;

/**
 * Lucene document mapper.
 * 
 * @author Christian Guretzki
 */
public class WordDocument extends FileDocument {
	private static final OLog log = Tracing.createLoggerFor(WordOOXMLDocument.class);

	public final static String FILE_TYPE = "type.file.word";

	public WordDocument() {
		super();
	}

	public static Document createDocument(final SearchResourceContext leafResourceContext, final VFSLeaf leaf) throws IOException, DocumentException,
			DocumentAccessException {
		final WordDocument wordDocument = new WordDocument();
		wordDocument.init(leafResourceContext, leaf);
		wordDocument.setFileType(FILE_TYPE);
		wordDocument.setCssIcon("b_filetype_doc");
		if (log.isDebug()) {
			log.debug(wordDocument.toString());
		}
		return wordDocument.getLuceneDocument();
	}

	@Override
	protected String readContent(final VFSLeaf leaf) throws IOException, DocumentException {
		BufferedInputStream bis = null;
		final StringBuilder sb = new StringBuilder();
		try {
			bis = new BufferedInputStream(leaf.getInputStream());
			final POIFSFileSystem filesystem = new POIFSFileSystem(bis);
			final Iterator<?> entries = filesystem.getRoot().getEntries();
			while (entries.hasNext()) {
				final Entry entry = (Entry) entries.next();
				final String name = entry.getName();
				if (!(entry instanceof DocumentEntry)) {
					// Skip directory entries
				} else if ("WordDocument".equals(name)) {
					collectWordDocument(filesystem, sb);
				}
			}
			return sb.toString();
		} catch (final Exception e) {
			throw new DocumentException(e.getMessage());
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
	}

	private void collectWordDocument(final POIFSFileSystem filesystem, final StringBuilder sb) throws IOException {
		final WordExtractor extractor = new WordExtractor(filesystem);
		addTextIfAny(sb, extractor.getHeaderText());
		for (final String paragraph : extractor.getParagraphText()) {
			sb.append(paragraph).append(' ');
		}

		for (final String paragraph : extractor.getFootnoteText()) {
			sb.append(paragraph).append(' ');
		}

		for (final String paragraph : extractor.getCommentsText()) {
			sb.append(paragraph).append(' ');
		}

		for (final String paragraph : extractor.getEndnoteText()) {
			sb.append(paragraph).append(' ');
		}
		addTextIfAny(sb, extractor.getFooterText());
	}

	private void addTextIfAny(final StringBuilder sb, final String text) {
		if (text != null && text.length() > 0) {
			sb.append(text).append(' ');
		}
	}
}
