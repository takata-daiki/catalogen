package de.infoasset.platform.services.asset.imf.graphview.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import de.infoasset.imf.assets.AssetsViewPart;
import de.infoasset.imf.whitebox.IntrospectiveClass;
import de.infoasset.platform.services.asset.imf.graphview.visual.GraphScene;

public class Model {
	
	private GraphScene scene;
	private Set<Clazz> clazzes = new HashSet<Clazz>();
	private Set<Relation> relations = new HashSet<Relation>();
	private Map<Clazz, String> inheritanceRelations = new HashMap<Clazz, String>();
	private boolean propertyImagesEnabled = false;
	private boolean propertiesEnabled = true;
	private boolean mixinsEnabled = true;
	private boolean rolesEnabled = true;	
	private boolean manualLayout = false;
	private AssetsViewPart viewPart = null;
		
	public Model(AssetsViewPart viewPart){
		this.scene = new GraphScene(this);
		this.viewPart = viewPart;
	}

	protected GraphScene getScene() {
		return scene;
	}
	
	protected void addElement(Element element){
		if( element instanceof Clazz ) clazzes.add((Clazz)element);
		if( element instanceof Relation ) relations.add((Relation)element);			
	}
	
	public Clazz getClazz(String name, boolean complete, String extend){
		for (Clazz clazz : clazzes) {
			if( name.equals( clazz.getName() )){
				if( complete ) clazz.setToComplete();
				clazz.setExtend(extend);
				return clazz;
			}
		}
		return new Clazz(this, name, complete, extend);
	}
	
	public Clazz getClazz(String name, boolean complete){
		return getClazz(name, complete, null);
	}
	
	public Set<Clazz> getClazzes(){
		return clazzes;
	}
		
	public Relation getRelation(Clazz source, Clazz target, String sourceRoleName, String targetRoleName, String sourceMultplicity, String targetMultiplicity){
		for (Relation relation : relations) {
			if(relation.getSource().equals( target ) && relation.getTarget().equals( source ) && 
				relation.getSourceRole().getName().equals(targetRoleName) && relation.getTargetRole().getName().equals(sourceRoleName) )
					return relation;			
		}
		return new Relation(source, target, sourceRoleName, targetRoleName, sourceMultplicity, targetMultiplicity);
	}
	
	private boolean clazzExists(String name){
		for (Clazz clazz : clazzes)
			if(name.equals(clazz.getName()))
				return true;		
		return false;
	}
		
	public void visualize(){
		for (Clazz clazz : clazzes)
			clazz.visualize();			
				
		Set<Clazz> keys = inheritanceRelations.keySet();		
		for (Clazz clazz : keys) {
			if( clazzExists(inheritanceRelations.get(clazz)) ){
				Relation r = new Relation(clazz, getClazz(inheritanceRelations.get(clazz), false), null, null, null, null);
				r.setExtend();				
			}
		}
				
		for (Relation relation : relations)			
			relation.visualize();
				
		if(!manualLayout) scene.layoutScene();
		scene.createView();
	}
	
	public JComponent getView(){		
		return scene.getView();
	}
	
	public void saveAsJPG(String file) throws IOException{		
    	BufferedImage img = new BufferedImage(scene.getBounds().width, scene.getBounds().height, BufferedImage.TYPE_INT_RGB);
    	scene.getView().paint(img.getGraphics());
    	
    	try {
	    	ByteArrayOutputStream out = new ByteArrayOutputStream();
	    	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	    	JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(img);
	    	param.setQuality(1.0f, true);
	    	encoder.encode(img, param);
	    	FileOutputStream fos = new FileOutputStream(file);
	    	fos.write(out.toByteArray());
	    	fos.close();
	    	out.close();
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
	}
	
	public void changePropertyImagesVisibility(){
		propertyImagesEnabled = !propertyImagesEnabled;
		for (Clazz clazz : clazzes)
			clazz.enablePropertyImages(propertyImagesEnabled);			
	}
	
	public void changePropertyVisibility(){
		propertiesEnabled = !propertiesEnabled;
		for (Clazz clazz : clazzes)
			clazz.enableProperties(propertiesEnabled);
		changeAnchor();
	}
	
	public void changeMixinVisibility(){
		mixinsEnabled = !mixinsEnabled;
		for (Clazz clazz : clazzes)
			clazz.enableMixins(mixinsEnabled);		
		changeAnchor();
	}
	
	public void changeRoleVisibility(){
		rolesEnabled = !rolesEnabled;
		for (Relation relation : relations)
			relation.enableRoles(rolesEnabled);
	}
	
	private void changeAnchor(){
		if(!propertiesEnabled && !mixinsEnabled)
			for (Relation relation : relations) 
				scene.changeAnchor(relation, true);			
		else 
			for (Relation relation : relations) 
				scene.changeAnchor(relation, false);			
		
	}

	protected void addInheritanceEdge(Clazz clazz, String extend) {
		inheritanceRelations.put(clazz, extend);
	}

	public void setManualLayout(boolean manualLayout) {
		this.manualLayout = manualLayout;
	}
	
	public void openAssetsChangeDialog(IntrospectiveClass introspectiveClass){
		viewPart.openAssetsChangeDialog(introspectiveClass);
	}
}
