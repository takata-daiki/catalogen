package code.lucamarrocco.scala.eclipse;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.*;
import org.eclipse.ui.*;

import com.google.inject.*;

public class ScalaModule extends AbstractModule {

	private final Plugin plugin;

	private final ILog log;

	private final IEditorRegistry editorRegistry;

	private final IContentTypeManager contentTypeManager;

	private final SimpleLog simpleLog;

	private final IWorkspace workspace;

	public ScalaModule(final Plugin plugin, final ILog log, final IEditorRegistry editorRegistry, final IContentTypeManager contentTypeManager, final IWorkspace workspace) {
		this.plugin = plugin;
		this.log = log;
		this.editorRegistry = editorRegistry;
		this.contentTypeManager = contentTypeManager;
		this.workspace = workspace;
		this.simpleLog = new SimpleLog(log);
	}

	@Override
	protected void configure() {
		simpleLog.ok("configuring scala module...");
		bind(IScalaWorkspace.class).to(ScalaWorkspace.class);
		bind(IIndex.class).to(Index.class);
		bind(IScalaEditor.class).to(ScalaEditor.class);
		simpleLog.ok("configuring scala module... done.");
	}

	public void dispose() {
		simpleLog.ok("disposing scala module...");

		simpleLog.ok("disposing scala module... done.");
	}

	@Provides
	IContentTypeManager provideContentTypeManager() {
		return contentTypeManager;
	}

	@Provides
	IEditorRegistry provideEditorRegistry() {
		return editorRegistry;
	}

	@Provides
	ILog provideILog() {
		return log;
	}

	@Provides
	IWorkspace provideWorkspace() {
		return workspace;
	}

	@Provides
	Plugin providePlugin() {
		return plugin;
	}

	@Provides
	ISimpleLog provideSimpleLog() {
		return simpleLog;
	}

}
