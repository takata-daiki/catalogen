package code.lucamarrocco.scala.eclipse;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.*;
import org.eclipse.ui.*;
import org.junit.*;

public class ScalaEditorTest {

	private IEditorRegistry editorRegistry;
	private IContentTypeManager contentTypeManager;
	private IContentType contentType;
	private ISimpleLog simpleLog;

	@Test
	public void testAddFileSpecificationForJavaSourcesContentType() throws CoreException {

		editorRegistry = mock(IEditorRegistry.class);
		contentTypeManager = mock(IContentTypeManager.class);
		contentType = mock(IContentType.class);
		simpleLog = mock(ISimpleLog.class);

		when(contentTypeManager.getContentType("java")).thenReturn(contentType);

		new ScalaEditor(contentTypeManager, editorRegistry, simpleLog);

		verify(contentType).addFileSpec("scala", IContentTypeSettings.FILE_EXTENSION_SPEC);
	}

	@Test
	public void testDefaultEditorForScalaFiles() {

		editorRegistry = mock(IEditorRegistry.class);
		contentTypeManager = mock(IContentTypeManager.class);
		simpleLog = mock(ISimpleLog.class);

		new ScalaEditor(contentTypeManager, editorRegistry, simpleLog);

		verify(editorRegistry).setDefaultEditor("*.scala", "code.lucamarrocco.scala.eclipse.editor");
	}

	@Test
	public void testInstantiatePrepareContent() {

		editorRegistry = mock(IEditorRegistry.class);
		contentTypeManager = mock(IContentTypeManager.class);
		contentType = mock(IContentType.class);
		simpleLog = mock(ISimpleLog.class);

		when(contentTypeManager.getContentType("java")).thenReturn(contentType);

		final ScalaEditor prepareContent = new ScalaEditor(contentTypeManager, editorRegistry, simpleLog);

		assertThat(prepareContent, is(notNullValue()));
	}

	@Test
	public void testInstantiatePrepareContentDoNotThrowExceptionIfContentTypeWanstFound() {

		editorRegistry = mock(IEditorRegistry.class);
		contentTypeManager = mock(IContentTypeManager.class);
		simpleLog = mock(ISimpleLog.class);

		when(contentTypeManager.getContentType("java")).thenReturn(null);

		final ScalaEditor prepareContent = new ScalaEditor(contentTypeManager, editorRegistry, simpleLog);

		assertThat(prepareContent, is(notNullValue()));
	}
}