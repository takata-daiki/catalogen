package views.documents.actions;

import i18n.mt;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.weblearn.model.core.document.DocumentCollection;
import org.weblearn.model.core.document.DocumentCollectionList;
import org.weblearn.model.core.document.DocumentEntry;
import org.weblearn.model.core.document.DocumentFactory;
import org.weblearn.model.core.document.DocumentPackage;
import org.weblearn.model.core.document.WrapperDocument;

import views.documents.DocumentsPage;

/**
 * BaseAddURLsAction
 * 
 * 
 * 
 * @author ceresna
 * @author mcgoebel@gmail.com
 */
public abstract class BaseAddURLsAction
extends Action
{

	protected final DocumentsPage page;
	protected final IEditingDomainProvider edp;

	public BaseAddURLsAction(String text, DocumentsPage page)
	{
		super(text);
		this.page = page;

		IWorkbenchPart p = page.boundPart;
		this.edp = (IEditingDomainProvider) p.getAdapter(IEditingDomainProvider.class);

		setEnabled(edp!=null);
	}

	public void run(String uri) {
		if (uri==null || uri.length()==0) return;
		run(Collections.singletonList(uri));
	}

    @SuppressWarnings("unchecked")
    public void run(List<String> uris) 
    {
    	boolean isNewCollection = false;
    	
        if (uris.isEmpty()) return;
        for (String uri : uris) {
            if (uri==null || uri.length()==0) return;
        }
        uris = new LinkedList<String>(uris);

        ISelection s = page.viewer.getSelection();
        if (!(s instanceof StructuredSelection)) return;

        EditingDomain ed = edp.getEditingDomain();
        if (ed==null) return;
//        DocumentClusterGroup docClustGroup = page.docCol.getDocumentClusterGroup();
//        if (docClustGroup==null) return;

        final DocumentCollection dc;
        StructuredSelection sel = (StructuredSelection) s;
        Object selobj = sel.getFirstElement();
        if (selobj instanceof DocumentEntry) 
        {
        	DocumentEntry de = (DocumentEntry) selobj;
            if (de.eContainer() instanceof DocumentCollection) {
                dc = (DocumentCollection) de.eContainer();
            } else {
                //unclassified de is selected
                dc = null;
            }
        } 
        else if (selobj instanceof DocumentCollection) 
        {
            dc = (DocumentCollection) selobj;
        } 
        else
        {
        	/* */
        	List<DocumentCollection> collections = page.docCol.getDocumentCollectionList().getCollections();
        	if (collections.size()==0) 
        	{
        		dc = DocumentFactory.eINSTANCE.createDocumentCollection();
        		isNewCollection = true;
        	}
        	else 
        	{
        		//take first collection
        		dc = collections.get(0);
        	}

        }

        List<String> newUris = new LinkedList<String>();
        if (dc!=null) {
            Iterator<String> it = uris.iterator();
            NEXT_URI: while (it.hasNext()) {
                String uri = it.next();
                for (DocumentEntry de : (List<DocumentEntry>) dc.getDocuments()) {
                    if (uri.equals(de.getUri())) {
                        //it.remove();
                        continue NEXT_URI;
                    }
                }
                newUris.add(uri);
            }
        } 
//        else 
//        {
//            Iterator<String> it = uris.iterator();
//            NEXT_URI: while (it.hasNext()) {
//                String uri = it.next();
//                for (DocumentEntry de : (List<DocumentEntry>) docClustGroup.getUnclassified()) {
//                    if (uri.equals(de.getUri())) {
//                        //it.remove();
//                        continue NEXT_URI;
//                    }
//                }
//                newUris.add(uri);
//            }
//        }
        //if (uris.isEmpty()) return;
        if (newUris.size()<uris.size()) {
            String msg =
                uris.size()==1?
                mt.bind(mt.msgfmt_info_doc_not_added_already_in_collection, uris.get(0)) :
                mt.msg_info_docs_not_added_already_in_collection;
            MessageDialog.
            openInformation(page.getSite().getShell(),
                              mt.dlg_Information,
                              msg);
        }
        if (newUris.isEmpty()) return;

        CompoundCommand ccmd = new CompoundCommand(mt.cmd_Add_Document);
        for (String uri : newUris) 
        {
        	WrapperDocument de = DocumentFactory.eINSTANCE.createWrapperDocument();
            de.setUri(uri);
     		
            if (dc!=null) 
            {
                AddCommand cmd =
                    new AddCommand(
                    		ed,
                    		dc,
                    		DocumentPackage.eINSTANCE.getDocumentCollection_Documents(),
                    		de);
                ccmd.append(cmd);
            } 
        }
        ed.getCommandStack().execute(ccmd);

        /* finally, add collection dc to collection list */
        if (isNewCollection) 
        {
        	ccmd = new CompoundCommand(mt.cmd_Add_Document);
        	DocumentCollectionList doccols = page.docCol.getDocumentCollectionList();     
        	AddCommand cmd =
        		new AddCommand(
        				ed,
        				doccols,
        				DocumentPackage.eINSTANCE.getDocumentCollectionList_Collections(),
        				dc);
        	ccmd.append(cmd);
        	ed.getCommandStack().execute(ccmd);
        }
        
		page.viewer.refresh();
		page.viewer.expandToLevel(page.viewer.getAutoExpandLevel());
    }
//    
//	@SuppressWarnings("unchecked")
//	public void run(List<String> uris) {
//		if (uris.isEmpty()) return;
//		for (String uri : uris) {
//			if (uri==null || uri.length()==0) return;
//		}
//		uris = new LinkedList<String>(uris);
//
//		ISelection s = page.viewer.getSelection();
//		if (!(s instanceof StructuredSelection)) return;
//
//		EditingDomain ed = edp.getEditingDomain();
//		if (ed==null) return;
//		
//		 DocumentCollection docCol = page.docCol.getDocumentCollection();
//		 if (docCol==null) return;
//
//		 final DocumentClusterGroup docGroup;
//		 StructuredSelection sel = (StructuredSelection) s;
//		 Object selobj = sel.getFirstElement();
//		 if (selobj instanceof DocumentEntry) {
//			 DocumentEntry de = (DocumentEntry) selobj;
//			 if (de.eContainer() instanceof DocumentClusterGroup) {
//				 docGroup = (DocumentClusterGroup) de.eContainer();
//			 } else {
//				 //unclassified de is selected
//				 docGroup = null;
//			 }
//		 } else if (selobj instanceof DocumentClusterGroup) {
//			 docGroup = (DocumentClusterGroup) selobj;
//		 } else {
//			 docGroup = null;
//		 }
////	        
////		DocumentClusterGroup docCluster = page.docCol.getDocumentClusterGroup();
////		if (docCluster==null) return;
////
////		final DocumentCollection docColl;
////		StructuredSelection sel = (StructuredSelection) s;
////		Object selobj = sel.getFirstElement();
////		if (selobj instanceof DocumentEntry) {
////			DocumentEntry de = (DocumentEntry) selobj;
////			if (de.eContainer() instanceof DocumentClusterGroup) {
////				DocumentClusterGroup dcg = (DocumentClusterGroup) de.eContainer();
////				docColl = (DocumentCollection) de.eContainer();
////			} else {
////				//unclassified de is selected
////				docColl = null;
////			}
////		} else if (selobj instanceof DocumentCollection) {
////			docColl = (DocumentCollection) selobj;
////		} else {
////			docColl = null;
////		}
//
//		List<String> newUris = new LinkedList<String>();
//		if (docColl!=null) {
//			Iterator<String> it = uris.iterator();
//			NEXT_URI: while (it.hasNext()) {
//				String uri = it.next();
//				for (DocumentEntry de : (List<DocumentEntry>) docColl.getDocuments()) {
//					if (uri.equals(de.getUri())) {
//						//it.remove();
//						continue NEXT_URI;
//					}
//				}
//				newUris.add(uri);
//			}
//		} else {
//			Iterator<String> it = uris.iterator();
//			NEXT_URI: while (it.hasNext()) {
//				String uri = it.next();
//				for (DocumentEntry de : (List<DocumentEntry>) docCluster.getUnclassified()) {
//					if (uri.equals(de.getUri())) {
//						//it.remove();
//						continue NEXT_URI;
//					}
//				}
//				newUris.add(uri);
//			}
//		}
//		//if (uris.isEmpty()) return;
//		if (newUris.size()<uris.size()) {
//			String msg =
//				uris.size()==1?
//						mt.bind(mt.msgfmt_info_doc_not_added_already_in_collection, uris.get(0)) :
//							mt.msg_info_docs_not_added_already_in_collection;
//						MessageDialog.
//						openInformation(page.getSite().getShell(),
//								mt.dlg_Information,
//								msg);
//		}
//		if (newUris.isEmpty()) return;
//		
//		//add the document to the respective collection
//		CompoundCommand ccmd = new CompoundCommand(mt.cmd_Add_Document);
//		for (String uri : newUris) {
//			DocumentEntry de = DocumentFactory.eINSTANCE.createWrapperDocument();
//			de.setUri(uri);
//			
//			//set file type
//			String fileType = uri.substring(uri.lastIndexOf(".")+1);
//			if (fileType.equalsIgnoreCase("PDF")) {
//				de.setFileType(DocumentFormat.PDF_LITERAL);
//			} else if (fileType.equalsIgnoreCase("HTML")) {
//				de.setFileType(DocumentFormat.HTML_LITERAL);
//			}
//			if (page.docCol.getDocumentClusterGroup()!=null) {
//				AddCommand cmd =
//					new AddCommand(ed,
//							page.docCol.getDocumentClusterGroup(),
//							DocumentPackage.eINSTANCE.getDocumentCollection_Documents(),
//							de);
//				ccmd.append(cmd);
//			} else {
//				AddCommand cmd =
//					new AddCommand(ed,
//							page.docCol.getDocumentClusterGroup(),
//							DocumentPackage.eINSTANCE.getDocumentClusterGroup_Unclassified(),
//							de);
//				ccmd.append(cmd);
//			}
//		}
//		ed.getCommandStack().execute(ccmd);
//
//		page.viewer.refresh();
//		page.viewer.expandToLevel(page.viewer.getAutoExpandLevel());
//	}

}
