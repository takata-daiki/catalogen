package views.documents.provider;

import java.util.LinkedList;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.weblearn.model.core.document.DocumentEntry;
import org.weblearn.model.core.document.provider.BenchmarkDocumentItemProvider;

import annotationide.LearnUIImages;


/**
 * BenchmarkDocumentItemProvider2.java
 *
 *
 *
 * Created: May 10, 2009 11:38:55 PM
 *
 * @author mcg <goebel@gmail.com>
 * @version 1.0
 */
public class BenchmarkDocumentItemProvider2 
extends BenchmarkDocumentItemProvider 
{

//	private final DocumentClusterGroup docCluster;

	public BenchmarkDocumentItemProvider2(
			AdapterFactory adapterFactory
//			, DocumentClusterGroup docCol) {
			){
		super(adapterFactory);
//		this.docCluster = docCol;
//		docCol.eAdapters().add(this);
	}

	private boolean isResolved(DocumentEntry de) {
		return de.getCachedJavaDOM()!=null;
	}


	@Override
	public Object getImage(Object object) {
		if (object instanceof DocumentEntry) {
			DocumentEntry de = (DocumentEntry) object;
			byte[] img = de.getImage();
			if (img!=null) {
				return super.getImage(object); //FIXME
			}

			if (isResolved(de))
				return LearnUIImages.getImageDescriptor("icons/full/obj16/document");

			return LearnUIImages.getImageDescriptor("icons/full/obj16/document_unresolved");

		}

		return super.getImage(object);
	}

	@Override
	public String getText(Object object) {
		if (object instanceof DocumentEntry) {
			DocumentEntry de = (DocumentEntry) object;
			return de.getUri();
		}

		return super.getText(object);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getParent(Object object) {
		Object obj = super.getParent(object);

		//check for the virtual unclassified node
		BenchmarkItemProvider2 dcolItemProvider =
			(BenchmarkItemProvider2) adapterFactory.adapt(obj, IEditingDomainItemProvider.class);
		if (dcolItemProvider!=null) {
			LinkedList<?> ll = new LinkedList(dcolItemProvider.getChildren(obj));
			Object last = ll.isEmpty() ? null : ll.getLast();
			if (last instanceof BenchmarkDocumentItemProvider2) {
				return last;
			}

			return null;
		}
		return obj;
	}

}//BenchmarkDocumentItemProvider2
