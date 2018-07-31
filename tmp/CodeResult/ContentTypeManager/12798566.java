package code.lucamarrocco.scala.eclipse;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.*;
import org.eclipse.ui.*;

import com.google.inject.*;

public class ScalaEditor implements IScalaEditor {

	public static final String EDITOR_ID = "code.lucamarrocco.scala.eclipse.editor";

	private final IEditorRegistry editorRegistry;

	private final IContentTypeManager contentTypeManager;

	private final ISimpleLog log;

	@Inject
	public ScalaEditor(final IContentTypeManager contentTypeManager, final IEditorRegistry editorRegistry, final ISimpleLog log) {

		this.contentTypeManager = contentTypeManager;
		this.editorRegistry = editorRegistry;
		this.log = log;

		addScalaSpecificationTo("java");
		setDefaultEditorFor("*.scala");

		log.ok("scala editor... done.");
	}

	private void addScalaSpecificationTo(final IContentType contentType) {
		try {
			contentType.addFileSpec("scala", IContentTypeSettings.FILE_EXTENSION_SPEC);
		} catch (final CoreException e) {
			throw new RuntimeException(e);
		}
	}

	private void addScalaSpecificationTo(final String name) {
		final IContentType contentType = contentType(name);

		if (thereIs(contentType)) {
			addScalaSpecificationTo(contentType);
		}
	}

	private IContentType contentType(final String contentTypeIdentifier) {
		return contentTypeManager.getContentType(contentTypeIdentifier);
	}

	private void setDefaultEditorFor(final String fileNameOrExtension) {
		editorRegistry.setDefaultEditor(fileNameOrExtension, EDITOR_ID);
	}

	private boolean thereIs(final IContentType contentType) {
		return contentType != null;
	}
}
