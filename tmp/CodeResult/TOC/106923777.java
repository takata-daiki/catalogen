package br.com.caelum.tubaina.parser.html;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.BookBuilder;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class BookToTOCTest {

	private Configuration cfg;

	private String sectionIdentifier;

	private String chapterIdentifier;

	@Before
	public void setUp() throws IOException {
		cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(TubainaBuilder.DEFAULT_TEMPLATE_DIR);
		cfg.setObjectWrapper(new BeansWrapper());

		chapterIdentifier = "class=\"indexChapter\"";
		sectionIdentifier = "class=\"indexSection\"";
	}

	@Test
	public void testGenerateBookWithoutSections() {
		Book b = createBook("[chapter primeiro] um conteúdo");
		BookToTOC generator = new BookToTOC();
		List<String> dirTree = new ArrayList<String>();
		dirTree.add("livro");
		dirTree.add("livro/01-primeiro");
		dirTree.add("livro/02-segundo");

		String toc = generator.generateTOC(b, cfg, dirTree).toString();

		// System.out.println(toc);
		Assert.assertEquals(0, countOccurrences(toc, sectionIdentifier));
		Assert.assertEquals(2, countOccurrences(toc, chapterIdentifier));
		Assert.assertEquals(1, countOccurrences(toc, "href=\"../livro/01-primeiro\""));
	}

	@Test
	public void testGenerateBookWithSections() {
		Book b = createBook("[chapter unico]"
				+ "[section uma] Conteúdo da section 1 \n[section duas] conteudo da section 2");
		BookToTOC generator = new BookToTOC();

		List<String> dirTree = new ArrayList<String>();
		dirTree.add("livro");
		dirTree.add("livro/01-unico");
		dirTree.add("livro/01-unico/01-uma");
		dirTree.add("livro/01-unico/02-duas");

		String toc = generator.generateTOC(b, cfg, dirTree).toString();

		Assert.assertEquals(2, countOccurrences(toc, sectionIdentifier));
		Assert.assertEquals(2, countOccurrences(toc, chapterIdentifier));
		Assert.assertEquals(1, countOccurrences(toc, "href=\"../livro/01-unico\""));
		Assert.assertEquals(1, countOccurrences(toc, "href=\"../livro/01-unico/01-uma\""));
		Assert.assertEquals(1, countOccurrences(toc, "href=\"../livro/01-unico/02-duas\""));
	}

	private Book createBook(final String bookText) {
		BookBuilder builder = new BookBuilder("Title");
		builder.add(new StringReader(bookText));
		Book b = builder.build();
		return b;
	}

	private int countOccurrences(final String text, final String substring) {
		String[] tokens = text.split(substring);
		return tokens.length - 1;
	}
}
