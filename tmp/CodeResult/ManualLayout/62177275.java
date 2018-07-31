package de.infoasset.platform.services.asset.imf.graphview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.apache.commons.lang.ObjectUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import com.thoughtworks.xstream.XStream;

import de.infoasset.imf.assets.AssetsViewPart;
import de.infoasset.imf.core.Model;
import de.infoasset.imf.core.ModelElement;
import de.infoasset.imf.core.SwtViewer;
import de.infoasset.imf.core.View;
import de.infoasset.imf.core.Viewpoint;
import de.infoasset.imf.core.plugin.AnalyzerViewPart.InitClassLoader;
import de.infoasset.imf.core.treeView.ModelTreeProvider;
import de.infoasset.imf.util.ImfUtilities;
import de.infoasset.imf.whitebox.IntrospectiveClass;
import de.infoasset.imf.whitebox.ObjectMethod;
import de.infoasset.imf.whitebox.ValueMethod;
import de.infoasset.imf.whitebox.plugin.ActionParameters;
import de.infoasset.imf.whitebox.plugin.IntrospectionViewPart;
import de.infoasset.platform.services.asset.Asset;
import de.infoasset.platform.services.asset.Feature;
import de.infoasset.platform.services.asset.Role;
import de.infoasset.platform.services.asset.imf.NvlView;
import de.infoasset.platform.services.asset.imf.SchemaViewpoint;
import de.infoasset.platform.services.asset.imf.ToText;
import de.infoasset.platform.services.asset.imf.ToTextModelTreeProvider;
import de.infoasset.platform.services.asset.imf.graphview.model.Clazz;
import de.infoasset.platform.services.asset.imf.graphview.model.Mixin;
import de.infoasset.platform.services.asset.imf.graphview.model.Property;
import de.infoasset.platform.services.asset.imf.graphview.model.Relation;

public class UMLView extends View {

    private static SwtViewer swtViewer;

    private Composite swtAwtComponent;

	private static boolean manualLayout;
    
    private static de.infoasset.platform.services.asset.imf.graphview.model.Model model;
    
    private static final String CASCADE_ON_DELETE = "cascade";
    
    private static ModelTreeProvider mtp = new ToTextModelTreeProvider();
    
    private static XStream xstream = new XStream();
    
    private static NodeRoot nodeRoot;

    @Override
    public String getName() {
        return "UML";
    }

    private static ModelTreeProvider MTP() {
        return mtp;
    }
    
    private static XStream getXstream(){    		
    	return xstream;
    }
       
    @Override
    public void createPartControl(final Composite p, final SwtViewer swtViewer) {    	
        UMLView.swtViewer = swtViewer;  
             	
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {				
			e1.printStackTrace();
		}
 		 
 		JPanel mainPanel = new JPanel(new BorderLayout()); 
 		
 		// BUTTONS
 		JPanel buttonPanel = new JPanel();
 		
 		JButton loadGraphModel = new JButton("Load Graphmodel");
 		loadGraphModel.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent e) {
				loadGraphModel();
			}
		});
 		
 		buttonPanel.add(loadGraphModel);
 		
 		if(swtViewer.getModel() == null){ 			
 			mainPanel.add(buttonPanel, BorderLayout.CENTER); 			
 		} else { 		
	 		
	 		JButton saveGraphModel = new JButton("Save Graphmodel");
	 		saveGraphModel.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					saveGraphModel();					
				}
			});     		
	 		buttonPanel.add(saveGraphModel);
	 		
	 		
	 		JButton saveJPG = new JButton("Save as JPG");
	 		saveJPG.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					saveAsJPG();					
				}
			});     		
	 		buttonPanel.add(saveJPG);
	 		
	 		JPanel showPanel = new JPanel();
	 		     		
	 		showPanel.add(new JLabel("Show: "));
	 		
	 		JCheckBox propertyImages = new JCheckBox();
	 		propertyImages.setText("Property Images");
	 		propertyImages.setSelected(false);
	 		propertyImages.addItemListener(new ItemListener() {			
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					model.changePropertyImagesVisibility();
				}
			});
	 		showPanel.add(propertyImages);
	 		
	 		JCheckBox properties = new JCheckBox();
	 		properties.setText("Properties");
	 		properties.setSelected(true);
	 		properties.addItemListener(new ItemListener() {			
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					model.changePropertyVisibility();
				}
			});
	 		showPanel.add(properties);
	 		
	 		JCheckBox mixins = new JCheckBox();
	 		mixins.setText("Mixins"); 
	 		mixins.setSelected(true);
	 		mixins.addItemListener(new ItemListener() {			
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					model.changeMixinVisibility();
				}
			});
	 		showPanel.add(mixins);
	 		
	 		JCheckBox roles = new JCheckBox();
	 		roles.setText("Roles"); 
	 		roles.setSelected(true);
	 		roles.addItemListener(new ItemListener() {			
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					model.changeRoleVisibility();
				}
			});
	 		showPanel.add(roles);
	 		
	 		showPanel.setBorder(BorderFactory.createEtchedBorder());
	 		
	 		buttonPanel.add(showPanel);
	 		 		
	 		mainPanel.add(buttonPanel, BorderLayout.NORTH);
	 		  
	 		// GRAPH     		
	        JPanel graphPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
	        graphPanel.add(initScene(getSwtViewer().getModel())); 
	        graphPanel.setBackground(Color.WHITE);
	        
	 		mainPanel.add(graphPanel, BorderLayout.CENTER);
	 		
	 		mainPanel.setBackground(Color.WHITE);
 		}	 	
 		
 		swtAwtComponent = new Composite(p, SWT.EMBEDDED);
		swtAwtComponent.setSize(p.getClientArea().width, p.getClientArea().height);
		     		     			
 		Frame f = SWT_AWT.new_Frame(swtAwtComponent);
 		f.add(new JScrollPane(mainPanel)); 		 		      
    }
    
    private static void saveAsJPG(){    	   
		swtViewer.getDisplay().asyncExec(new Runnable() {				
			@Override
			public void run() {
				try{
					Shell shell = swtViewer.getViewSite().getWorkbenchWindow().getShell();
									
					FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);				
					fileDialog.open();
            
		            String path = fileDialog.getFilterPath();
		            String fileName = fileDialog.getFileName();
		            
		            String completePath = path + File.separator + fileName;
		            
		            boolean override = false;
		            
		            if( new File(completePath).exists() || new File(completePath + ".jpg").exists() || new File(completePath + ".JPG").exists()){
		            	MessageBox sure = new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
		            	sure.setMessage("Do you really want to replace " + fileName + " ?");
		            	if( sure.open() == SWT.YES ) override = true;
		            } else override = true;
		            
		            if( override ){
		            	if( fileName.endsWith(".jpg") || fileName.endsWith(".JPG"))
		            		model.saveAsJPG(completePath);
		            	else model.saveAsJPG(completePath + ".jpg");
		            }
		            							            
				} catch (IOException e) {
					e.printStackTrace();
				}		
			}
		});
    }
    
    private static void saveGraphModel(){ 
    	    	
		final NodeRoot nodes = new NodeRoot();
    	
		Clazz lastClazz = null;
    	for (Clazz clazz : model.getClazzes()) {
			Point location = clazz.getWidget().getPreferredLocation();
			
			if(clazz.isComplete()){				
				nodes.addNode(((IntrospectiveClass)clazz.getModelElement()).className, location.x, location.y, true);
				lastClazz = clazz;
			} else
				nodes.addNode(clazz.toString(), location.x, location.y, false);		
    	}
    	
    	if(lastClazz != null ) nodes.setProject(lastClazz.getModelElement().getJavaProject().getResource().getName());
    	else nodes.setProject("toro");
    	
		swtViewer.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				Shell shell = swtViewer.getViewSite().getWorkbenchWindow().getShell();
				
				FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);				
				fileDialog.open();
				
				String fileName = fileDialog.getFileName();
				String completePath = fileDialog.getFilterPath() + File.separator + fileName;
				
				boolean override = false;
				if( new File(completePath).exists() || new File(completePath + ".xml").exists() ){
	            	MessageBox sure = new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
	            	sure.setMessage("Do you really want to replace " + fileName + " ?");
	            	if( sure.open() == SWT.YES ) override = true;
	            } else override = true;
	            
	            if( override ){
	            	if(! fileName.endsWith(".xml") ) 
	            		completePath += ".xml";	
	            		
	            	FileOutputStream fileOutputStream = null;
	            	OutputStreamWriter out = null;
					try {
						fileOutputStream = new FileOutputStream(new File(completePath));
						out = new OutputStreamWriter(fileOutputStream, "UTF8");
						getXstream().toXML(nodes, out);
						fileOutputStream.close();
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							fileOutputStream.close();
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}						
					}
	            }
			}
		});
		
    }
        
    private static void loadGraphModel(){
    	
 		swtViewer.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				Shell shell = swtViewer.getViewSite().getWorkbenchWindow().getShell();
				
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);				
				fileDialog.open();
        	     				
				try {
					nodeRoot = (NodeRoot) getXstream().fromXML(new InputStreamReader(new FileInputStream(
							new File(fileDialog.getFilterPath() + File.separator + fileDialog.getFileName())), "UTF8"));
					
					final List<String> classes = new ArrayList<String>();
					
					for (Node node : nodeRoot.getCompleteNodes()) {
						classes.add(node.getName());
					}
										
					IWorkbenchPart viewPart = swtViewer.getWorkbenchPart();
			 		final IntrospectionViewPart introspectionViewPart;
			 		if(viewPart instanceof IntrospectionViewPart) introspectionViewPart = (IntrospectionViewPart)viewPart; 
			 		else introspectionViewPart = null;			 		
			    	
			 		introspectionViewPart.runIntrospectionAction(new ActionParameters(){

						@Override
						public InitClassLoader chooseProject() {						
							return InitClassLoader.MANUAL;
						}

						@Override
						public List<String> getClassNames() throws Exception {							
							return classes;
						}

						@Override
						public Viewpoint getViewpoint() throws Exception {							
							return new SchemaViewpoint();										
						}  
						
						@Override
						public String getProject(){
							return nodeRoot.getProject();
						}
	        		});	
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			} 			
 		});
 		manualLayout = true;
    }
               
    public static JComponent initScene(Model m){
    	
    	model = new de.infoasset.platform.services.asset.imf.graphview.model.Model((AssetsViewPart)swtViewer.getWorkbenchPart());
    	model.setManualLayout(manualLayout);
    	
    	// classes
    	for (ModelElement me : ToText.breadthFirstEnumeration(m, MTP())) {    		
            if (me.getParent() instanceof Model
                    && (SchemaViewpoint.TYPE_PERSISTENT_ASSET.equals(me.getAnnotationValue(SchemaViewpoint.TYPE)) || SchemaViewpoint.TYPE_MIXIN_ASSET
                            .equals(me.getAnnotationValue(SchemaViewpoint.TYPE)))) {
                IntrospectiveClass ic = (IntrospectiveClass) me;
                String name = ic.getAnnotationValue(SchemaViewpoint.NAME);
                
                Clazz clazz = model.getClazz(name, true, ic.getAnnotationValue(SchemaViewpoint.EXTENDS));
                clazz.setModelElement(ic);
                
                setClazzLocation(clazz);          
                
                // mixins
                if (SchemaViewpoint.TYPE_PERSISTENT_ASSET.equals(me.getAnnotationValue(SchemaViewpoint.TYPE))) {
                    String mts = ic.getAnnotationValue(SchemaViewpoint.MIXIN_TYPES);
                    if (mts != null) {
                        for (StringTokenizer st = new StringTokenizer(mts, ","); st.hasMoreTokens();) {
                            String mixinName = st.nextToken();
                            mixinName = stripEnd(mixinName, "Mixin");                            
                            clazz.getMixin(mixinName);                            
                        }
                    }
                }
                
                // features
                for (ModelElement child : ToText.breadthFirstEnumeration(me, MTP())) {
                    if (child.getParent() instanceof ObjectMethod) {
                        ObjectMethod om = (ObjectMethod) child.getParent();
                                                
                        if (Asset.GET_DEFINED_FEATURES_METHOD.equals(om.getMethodEncoding())) {
                            if (SchemaViewpoint.TYPE_PROPERTY.equals(child.getAnnotationValue(SchemaViewpoint.TYPE))
                                    || SchemaViewpoint.TYPE_ROLE.equals(child.getAnnotationValue(SchemaViewpoint.TYPE))) {

                            	String pinName = child.getAnnotationValue(SchemaViewpoint.NAME);                                
                                String pinTypeOrRole = child.getAnnotationValue(SchemaViewpoint.MODEL_PROPERTY_TYPE);
                                
                                // properties
                                if( pinTypeOrRole.endsWith("Property") ){
                                	pinTypeOrRole = stripEnd(pinTypeOrRole, "Property");
                                                                   	
                                	Property p = clazz.getProperty(pinName, pinTypeOrRole, ToText.toString(child),
                                			NvlView.hasChangeListener((IntrospectiveClass) child), NvlView.hasValidator((IntrospectiveClass) child),
                                				!isPersistent(child));
                                	p.setModelElement(child);
                                }
                                
                                
                                // relations
                                if( pinTypeOrRole.endsWith("Role") ){
	                                if (SchemaViewpoint.TYPE_ROLE.equals(child.getAnnotationValue(SchemaViewpoint.TYPE))) {
	                                    String roleTargetType = child.getAnnotationValue(SchemaViewpoint.ROLE_TARGET_TYPE);	                                    
	                                    String otherRoleName = child.getAnnotationValue(SchemaViewpoint.OTHER_ROLE_NAME);
	                                    
	                                    Clazz targetClazz = model.getClazz(roleTargetType, false); 
	                                     
	                                    setClazzLocation(targetClazz);
	                                    
	                                    String[] result = getOtherRole(pinName, om);
	                                   	                                    
	                                    pinTypeOrRole = stripEnd(pinTypeOrRole, "Role");
	                                    String otherRole = stripEnd(result[0], "Role");
	                                    
	                                    Relation r = model.getRelation(clazz, targetClazz, otherRoleName, pinName, otherRole, pinTypeOrRole);	                                    
	                                    if( CASCADE_ON_DELETE.equals(result[1]) ) r.setCascadeDelete(otherRoleName);
	                                }
                                }
                            }
                        }
                    }
                }
            }
    	}
    	    	    	
    	model.visualize();
    	manualLayout = false;
    	model.setManualLayout(manualLayout);
    	
    	for (Clazz clazz : model.getClazzes()) {    		
			for(Mixin mixin : clazz.getMixins()){
				//System.out.println(mixin.getName());
			}			
		}
    	
    	return model.getView();
    }
    
    // HELPER-METHODS
    
    private static void setClazzLocation(Clazz clazz){
    	if(manualLayout){	                                    	
        	for (Node node : nodeRoot.getNodes()) {
     			String nodename = node.getName();             			
 	 			if(nodename.contains(".")) nodename = nodename.substring(nodename.lastIndexOf(".") + 1);

 	 			if(clazz.getName().equals(nodename)){         	 				
 	 				clazz.setLocation(node.getX(), node.getY());
 	 				break;
 	 			}             	    	
    		}
        } 
    }
    
    private static String[] getOtherRole(String roleName, ObjectMethod objectMethod){
    	String[] result = new String[2];
    	
        for (IntrospectiveClass iclazz : objectMethod.getChildElements(IntrospectiveClass.class)) { 
        	
        	if(roleName.equals(iclazz.getAnnotationValue(SchemaViewpoint.NAME))){                        	 
            	        		
            	for (ObjectMethod method : iclazz.getChildElements(ObjectMethod.class)) {            		
            		if (Role.OTHER_ROLE_STRING.equals(method.getMethodEncoding())) {
            			 
            			 	List<IntrospectiveClass> children = method.getChildElements(IntrospectiveClass.class);
            			 	if(children.size() == 1)
            			 		result[0] = children.get(0).getAnnotationValue(SchemaViewpoint.MODEL_PROPERTY_TYPE);             			 	                            			 	
            		}
				}
            	
            	for(ValueMethod valueMethod : iclazz.getChildElements(ValueMethod.class)) {
            		if(Role.OTHER_MULTIPLICITY.equals(valueMethod.getMethodEncoding())){            				                                        			
            			if(valueMethod.getValue() != null)
            				result[0] = String.valueOf(valueMethod.getValue());             			
            		}
            		            		
            		if(Role.IS_CASCADE_DELETE_STRING.equals(valueMethod.getMethodEncoding()) && valueMethod.getValue().equals(true))
            				result[1] = CASCADE_ON_DELETE;  
            	}
        	} 
		}
        return result;
    }
    
    private static boolean isPersistent(ModelElement child) {
        for (ModelElement m : ToText.breadthFirstEnumeration(child, MTP())) {
            if (m instanceof ValueMethod) {
                ValueMethod valueMethod = (ValueMethod) m;
                if (Feature.IS_PERSISTENT.equals(valueMethod.getMethodEncoding())) {
                    return ObjectUtils.equals(Boolean.TRUE, valueMethod.getValue());
                }
            }
        }
        return false;
    }
       
    private static String stripEnd(String mixinName, String suffix) {
        if (mixinName.endsWith(suffix))
            return mixinName.substring(0, mixinName.length() - suffix.length());        
        return mixinName;
    }

    @Override
    public void dispose() {
        swtAwtComponent.dispose();
    }

    @Override
    public SwtViewer getSwtViewer() {
        return swtViewer;
    }

    @Override
    public void refresh() {
    }
      
    @Override
    public void setFocus() {
    	swtAwtComponent.setFocus();
    }

    @Override
    public void setSelection(Object element) {
    }
    
    public static void main(String[] args) {
        try {        	
            Model m = (Model) ModelElement.deserialize(ImfUtilities.deserializeFromFile("D:\\downloads\\t.xml"),
            		UMLView.class.getClassLoader());
                         
            int width=1000,height=900;
            final JFrame frame = new JFrame ();                     
            frame.add (initScene(m), BorderLayout.CENTER);
            frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            frame.setBounds((screenSize.width-width)/2, (screenSize.height-height)/2, width, height);                                    
            frame.setVisible (true);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
