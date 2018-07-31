package views.documents.provider;

import java.util.LinkedList;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.weblearn.model.core.document.DocumentClusterGroup;
import org.weblearn.model.core.document.DocumentCollection;
import org.weblearn.model.core.document.DocumentEntry;
import org.weblearn.model.core.document.provider.WrapperDocumentItemProvider;
import org.weblearn.model.core.utils.DocumentFormat;

import annotationide.LearnUIImages;



/**
 * 
 * WrapperDocumentItemProvider2.java
 *
 *
 *
 * Created: May 10, 2009 11:36:40 PM
 *
 * @author mcg <goebel@gmail.com>
 * @version 1.0
 */
public class WrapperDocumentItemProvider2 
extends WrapperDocumentItemProvider 
{

	public WrapperDocumentItemProvider2(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	private boolean isResolved(DocumentEntry de) {
		return de.getCachedJavaDOM()!=null;
	}


	@Override
	public Object getImage(Object object) 
	{
		Object result = null;	
		if (object instanceof DocumentEntry) {
			DocumentEntry de = (DocumentEntry) object;
				
			if (de.getFileType().equals(DocumentFormat.HTML_LITERAL)) {
				result = LearnUIImages.getImageDescriptor("icons/full/obj16/htmlDoc16");
			} else if (de.getFileType().equals(DocumentFormat.PDF_LITERAL)) {
				result = LearnUIImages.getImageDescriptor("icons/full/obj16/pdfDoc16");
			} else if (isResolved(de)) { //check if DOM is cached
				result = LearnUIImages.getImageDescriptor("icons/full/obj16/document");
			} else {
				result = LearnUIImages.getImageDescriptor("icons/full/obj16/document_unresolved");
			}
			
			return result;
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

	@SuppressWarnings("unchecked")
	@Override
	public Object getParent(Object object) {
		Object obj = super.getParent(object);

		if (obj instanceof DocumentCollection) {
			DocumentCollectionItemProvider2 itemProvider = 
				(DocumentCollectionItemProvider2) 
				adapterFactory.adapt(obj, IEditingDomainItemProvider.class);
			if (itemProvider!=null) {
				LinkedList<Object> ll = new LinkedList<Object>(itemProvider.getChildren(obj));
				Object last = ll.isEmpty() ? null : ll.getLast();
				if (last instanceof DocumentEntry) {
					return last;
				}
				
				return null;
			}
		}
		else if (obj instanceof DocumentClusterGroup) {
			DocumentGroupItemProvider2 itemProvider =
				(DocumentGroupItemProvider2) adapterFactory.adapt(obj, IEditingDomainItemProvider.class);
			if (itemProvider!=null) {
				LinkedList<Object> ll = new LinkedList<Object>(itemProvider.getChildren(obj));
				Object last = ll.isEmpty() ? null : ll.getLast();
				if (last instanceof UnclassifiedDocumentEntriesItemProvider) {
					return last;
				}

				return null;
			}
		}
		
		return obj;
	}
	
	
}//LiveDocumentItemProvider2
