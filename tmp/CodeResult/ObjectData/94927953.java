package it.polimi.aasi;

import it.polimi.aasi.PickUtility.PickedObjects;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Light;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.pickfast.behaviors.PickRotateBehavior;
import com.sun.j3d.utils.pickfast.behaviors.PickTranslateBehavior;
import com.sun.j3d.utils.pickfast.behaviors.PickZoomBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class Editor3D extends JFrame implements Observer {
	public static final BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0), 1000);   

	private static final BoundingSphere boundsObj = new BoundingSphere(new Point3d(0,0,0), 1);
	private static final long serialVersionUID = 181119851510198630L;
	private static final Point3d USERPOSN = new Point3d(0,5,20);
	private static final float defaultDim = 1.0f;
	// Colors for solids
	private static final Color3f emissive = new Color3f(0.0f, 0.0f, 0.0f);
	private static final Color3f realColor = new Color3f(0.5f, 0.5f, 0.5f);
	private static final Color3f specular = new Color3f(0.9f, 0.9f, 0.9f);
	private static final float shininess = 25.0f;
	//lights
	private static final Vector3f lightDirection  = new Vector3f(-1.0f, -1.0f, -1.0f);
	private static final Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
	private static final Point3f lightPosition = new Point3f(0.0f, 0.0f, 0.0f);
	private static final Point3f lightAttenuation = new Point3f(0.0f, 0.0f, 0.0f);
	private static final float lightSpreadAngle = (float) Math.PI/2;
	private static final float lightConcentration = 50f;

	private SimpleUniverse univ = null;
	private BranchGroup scene = null;
	
	private NavigationTranslate myNavTrans = null;
	private NavigationRotation myNavRot = null;
	private Canvas3D c = null;
	private String[] LightNames = new String[200];
	private int LightCount = 0;
	private Map<String,BranchGroup> ObjectArray = new HashMap<String, BranchGroup>(200);

	public Editor3D(){
		setSize(800, 1000);
		
		// Initialize the GUI components
		setUpGUI();

		// Create Canvas3D and SimpleUniverse; add canvas to drawing panel
		c = createUniverse();
		drawingPanel.add(c, BorderLayout.CENTER);
		drawingPanel.requestFocusInWindow();
		
		// Create the content branch and add it to the universe
		createSceneGraph();
		
		univ.addBranchGraph(scene);
	}
	
	private Canvas3D createUniverse() {
		// Get the preferred graphics configuration for the default screen
		GraphicsConfiguration config =
			SimpleUniverse.getPreferredConfiguration();

		// Create a Canvas3D using the preferred configuration
		Canvas3D c = new Canvas3D(config);

		// Create simple universe with view branch
		univ = new SimpleUniverse(c);

		// Ensure at least 5 msec per frame (i.e., < 200Hz)
		univ.getViewer().getView().setMinimumFrameCycleTime(5);

		return c;
	}

	public void createSceneGraph() {
		// Create the root of the branch graph
		scene = new BranchGroup();
		scene.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		scene.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		scene.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		scene.setCapability(BranchGroup.ENABLE_PICK_REPORTING);

		// Set up the AmbientLight
		AmbientLight ambientLightNode = new AmbientLight(white);
		ambientLightNode.setInfluencingBounds(bounds);
		scene.addChild(ambientLightNode);

		// Set up a default DirectionalLight
		scene.addChild(new CreateDirLight(white, lightDirection).getBG());
		
		// Add a dark grey background
		Background back = new Background();
		back.setApplicationBounds(bounds);
		back.setColor(0.3f, 0.3f, 0.3f);
		scene.addChild(back);
		
		// Add checkered floor
		scene.addChild(new Floor().getBG());
		
		// Set the user's initial viewpoint using lookAt()
		ViewingPlatform vp = univ.getViewingPlatform();
		TransformGroup steerTG = vp.getViewPlatformTransform();
		Transform3D t3d = new Transform3D();
		steerTG.getTransform(t3d);

		// args are: viewer posn, where looking, up direction
		t3d.lookAt( USERPOSN, new Point3d(0,0,0), new Vector3d(0,1,0));
		t3d.invert();		

		steerTG.setTransform(t3d);
		
		//Navigation Behaviors section
		myNavTrans = new NavigationTranslate(steerTG);
		myNavTrans.setSchedulingBounds(bounds);
		myNavTrans.setEnable(false);
		scene.addChild(myNavTrans);
		
		myNavRot = new NavigationRotation(steerTG);
		myNavRot.setSchedulingBounds(bounds);
		myNavRot.setEnable(false);
		scene.addChild(myNavRot);
		
		MouseWheelZoom myMouseWheelZoom = new MouseWheelZoom(steerTG);
		myMouseWheelZoom.setSchedulingBounds(bounds);
		scene.addChild(myMouseWheelZoom);
		
		MouseZoom myMouseZoom = new MouseZoom(steerTG);
		myMouseZoom.setSchedulingBounds(bounds);
		scene.addChild(myMouseZoom);
		
		//PickCanvas that extends MouseAdapter, so it acts as a mouse listener
		PickUtility picker = new PickUtility(c, scene);
		c.addMouseListener(picker);
		
		picker.picked.addObserver(this);

		// Have Java 3D perform optimizations on this scene graph.
		scene.compile();
	}
	

	
	private void InsertObject(BranchGroup BG){
		//TODO InsertObject
		//if BG child is a TransformGroup, it is a solid, so add Behaviors
		if(BG.getChild(0) instanceof TransformGroup){
			String name = ((Primitive) ((TransformGroup) BG.getChild(0)).getChild(0)).getName() ;
			ObjectArray.put(name, BG);
			
			//adds picking behaviors for this solid

			PickTranslateBehavior pickTranslate = new PickTranslateBehavior(BG,c,boundsObj);
			BG.addChild(pickTranslate);

			PickZoomBehavior pickZoom = new PickZoomBehavior(BG,c,boundsObj);
			BG.addChild(pickZoom);

			if(!(name.contains("WF"))){
				
				PickRotateBehavior pickRotate = new PickRotateBehavior(BG,c,boundsObj);
				BG.addChild(pickRotate);
				
				CollisionDetector colDetector = new CollisionDetector((Primitive) 
						((TransformGroup) BG.getChild(0)).getChild(0), boundsObj);
				BG.addChild(colDetector);
			}
		}
		else{
			LightNames[LightCount] = ((Light) (BG.getChild(0))).getName();
			ObjectArray.put(LightNames[LightCount], BG);
			LightCount++;
		}		
		//adds the solid to the scene
		scene.addChild(BG);
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Editor3D().setVisible(true);
			}
		});
	}

	/**
	 * sets up GUI items, such as JPanels, JButtons, etc.
	 * directly modifies this class JFrame.
	 */
	private void setUpGUI(){
		//TODO setUpGUI
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(900, 800));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Editor3D");

		add(createToolbar(), BorderLayout.NORTH);

		drawingPanel = new JPanel();
		drawingPanel.setLayout(new BorderLayout());
		drawingPanel.setPreferredSize(new Dimension(600, 600));
		
		drawingPanel.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				if ((arg0.getKeyCode () == KeyEvent.VK_R) && (ExploreBtn.isSelected())){
					myNavRot.setEnable(false);
				}
				else if ((arg0.getKeyCode () == KeyEvent.VK_T) && (ExploreBtn.isSelected())){
					myNavTrans.setEnable(false);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if ((arg0.getKeyCode () == KeyEvent.VK_R) && (ExploreBtn.isSelected())){
					myNavRot.setEnable(true);
				}
				else if ((arg0.getKeyCode () == KeyEvent.VK_T) && (ExploreBtn.isSelected())){
					myNavTrans.setEnable(true);
				}
				else{
					myNavTrans.setEnable(false);
					myNavRot.setEnable(false);
				}
			}
		});
		
		drawingPanel.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if (ExploreBtn.isSelected())
					if (!e.getComponent().requestFocusInWindow())
						System.out.println("Richiesta di focus non accettata!");
			}
			
			@Override
			public void focusGained(FocusEvent e) {}
		});
		
		add(drawingPanel, BorderLayout.CENTER);
		
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout());
		lowerPanel.setFocusable(false);
		
		solidPanel = populateSolidPanel();
		solidPanel.setFocusable(false);
		solidPanel.setPreferredSize(new Dimension(new Dimension(this.getWidth(),(int) (this.getHeight()*0.15))));

		lightPanel = new JPanel();
		lightPanel.setLayout(new BorderLayout());
		lightPanel.setFocusable(false);
		
		lightPropertiesPanel = populateLightPanel();
		lightPropertiesPanel.setFocusable(false);
		lightPropertiesPanel.setPreferredSize(new Dimension((int)(this.getWidth()*0.87),(int) (this.getHeight()*0.15)));
		
		scrollLightList = new JScrollPane();
		scrollLightList.setPreferredSize(new Dimension((int)(this.getWidth()*0.13),(int) (this.getHeight()*0.15)));

		lightPanel.add(scrollLightList, BorderLayout.WEST);
		lightPanel.add(lightPropertiesPanel, BorderLayout.EAST);

		lowerPanel.add(solidPanel, BorderLayout.NORTH);
		lowerPanel.add(lightPanel, BorderLayout.SOUTH);

		add(lowerPanel, BorderLayout.SOUTH);
		
		lightPropertiesPanel.setVisible(false);
		solidPanel.setVisible(false);
		lightPanel.setVisible(false);
		
		pack();
	}

	/**
	 * Creates a JPanel which will contain information about a light
	 * @return a JPanel object with all fields compiled below
	 */
	private JPanel populateLightPanel(){
		//TODO populateLightPanel
		JPanel mPanel = new JPanel();
		mPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
		final String lightnameFieldString = new String("lightnameField");
		final String ldxFieldString = new String("ldxField");
		final String ldyFieldString = new String("ldyField");
		final String ldzFieldString = new String("ldzField");
		final String lpxFieldString = new String("lpxField");
		final String lpyFieldString = new String("lpyField");
		final String lpzFieldString = new String("lpzField");
		final String laxFieldString = new String("laxField");
		final String layFieldString = new String("layField");
		final String lazFieldString = new String("lazField");
		
		ActionListener mActionlistener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(lightnameField.getText())) ;
		         
				JTextField source = (JTextField)e.getSource();
				
				if(ldxFieldString.equals(e.getActionCommand())){
					if((mObj.getType()==ObjectData.DirectionalLight)||(mObj.getType()==ObjectData.SpotLight)){
					Vector3f pos = new Vector3f(Float.valueOf(source.getText()), mObj.getLightDirection().getY(), mObj.getLightDirection().getZ());
		         	mObj.setLightDirection(ObjectArray.get(lightnameField.getText()), pos);
				}}
				else if(ldyFieldString.equals(e.getActionCommand())){
					if((mObj.getType()==ObjectData.DirectionalLight)||(mObj.getType()==ObjectData.SpotLight)){
					Vector3f pos = new Vector3f(mObj.getLightDirection().getX(), Float.valueOf(source.getText()), mObj.getLightDirection().getZ());
			        mObj.setLightDirection(ObjectArray.get(lightnameField.getText()), pos);
				}}
				else if(ldzFieldString.equals(e.getActionCommand())){
					if((mObj.getType()==ObjectData.DirectionalLight)||(mObj.getType()==ObjectData.SpotLight)){
						Vector3f pos = new Vector3f(mObj.getLightDirection().getX(),mObj.getLightDirection().getY(),Float.valueOf(source.getText()));
				    mObj.setLightDirection(ObjectArray.get(lightnameField.getText()), pos);
				}}
				else if(lpxFieldString.equals(e.getActionCommand())){
					if((mObj.getType()==ObjectData.PointLight)||(mObj.getType()==ObjectData.SpotLight)){
						Point3f pos = new Point3f(Float.valueOf(source.getText()),mObj.getLightPosition().getY(),mObj.getLightPosition().getZ());
				    mObj.setLightPosition(ObjectArray.get(lightnameField.getText()), pos);
				}}
				else if(lpyFieldString.equals(e.getActionCommand())){
					if((mObj.getType()==ObjectData.PointLight)||(mObj.getType()==ObjectData.SpotLight)){
						Point3f pos = new Point3f(mObj.getLightPosition().getX(),Float.valueOf(source.getText()),mObj.getLightPosition().getZ());
				    mObj.setLightPosition(ObjectArray.get(lightnameField.getText()), pos);
				}}
				else if(lpzFieldString.equals(e.getActionCommand())){
					if((mObj.getType()==ObjectData.PointLight)||(mObj.getType()==ObjectData.SpotLight)){
						Point3f pos = new Point3f(mObj.getLightPosition().getX(),mObj.getLightPosition().getY(),Float.valueOf(source.getText()));
				    mObj.setLightPosition(ObjectArray.get(lightnameField.getText()), pos);
				}}
				else if(laxFieldString.equals(e.getActionCommand())){
					if((mObj.getType()==ObjectData.PointLight)||(mObj.getType()==ObjectData.SpotLight)){
						Point3f pos = new Point3f(Float.valueOf(source.getText()),mObj.getLightAttenuation().getY(),mObj.getLightAttenuation().getZ());
				    mObj.setLightAttenuation(ObjectArray.get(lightnameField.getText()), pos);
				}}
				else if(layFieldString.equals(e.getActionCommand())){
					if((mObj.getType()==ObjectData.PointLight)||(mObj.getType()==ObjectData.SpotLight)){
						Point3f pos = new Point3f(mObj.getLightAttenuation().getX(),Float.valueOf(source.getText()),mObj.getLightAttenuation().getZ());
				    mObj.setLightAttenuation(ObjectArray.get(lightnameField.getText()), pos);
				}}
				else if(lazFieldString.equals(e.getActionCommand())){
					if((mObj.getType()==ObjectData.PointLight)||(mObj.getType()==ObjectData.SpotLight)){
						Point3f pos = new Point3f(mObj.getLightAttenuation().getX(),mObj.getLightAttenuation().getY(),Float.valueOf(source.getText()));
				    mObj.setLightAttenuation(ObjectArray.get(lightnameField.getText()), pos);
				}}
					
			}
		};
		
		//name
		lightnameField = new JTextField(10);
        lightnameField.setActionCommand(lightnameFieldString);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 0;
        mPanel.add(lightnameField, c);   
        //name label
        JLabel lightnameFieldLabel = new JLabel("Light Name: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 0;  
        lightnameFieldLabel.setLabelFor(lightnameField);
        mPanel.add(lightnameFieldLabel,c);
        
        //lightcolor
        //R
        JSlider lcrField = new JSlider(0, 255);
        lcrField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
					ObjectData mObj = new ObjectData(ObjectArray.get(lightnameField.getText())) ;
					Color3f colore = mObj.getLightColor();
					colore.setX((float) ((JSlider)e.getSource()).getValue() /255);
					mObj.setLightColor(ObjectArray.get(lightnameField.getText()),colore);
					
				}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 1;  
        mPanel.add(lcrField,c);
        //G
        JSlider lcgField = new JSlider(0, 255);
        lcgField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(lightnameField.getText())) ;
				Color3f colore = mObj.getLightColor();
				colore.setY((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setLightColor(ObjectArray.get(lightnameField.getText()),colore);
				
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 2;  
        mPanel.add(lcgField,c);
        //B
        JSlider lcbField = new JSlider(0, 255);
        lcbField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(lightnameField.getText())) ;
				Color3f colore = mObj.getLightColor();
				colore.setZ((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setLightColor(ObjectArray.get(lightnameField.getText()),colore);
				
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 3;  
        mPanel.add(lcbField,c);
        //labelR
        JLabel lcrFieldLabel = new JLabel("Light color R: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 1;  
        lcrFieldLabel.setLabelFor(lcrField);
        mPanel.add(lcrFieldLabel,c);
        //labelG
        JLabel lcgFieldLabel = new JLabel("Light color G: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 2;  
        lcrFieldLabel.setLabelFor(lcgField);
        mPanel.add(lcgFieldLabel,c);
        //labelB
        JLabel lcbFieldLabel = new JLabel("Light color B: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 3;  
        lcrFieldLabel.setLabelFor(lcbField);
        mPanel.add(lcbFieldLabel,c);
	
        //light direction
        //X
        JTextField ldxField = new JTextField(10);
        ldxField.setActionCommand(ldxFieldString);
        ldxField.addActionListener(mActionlistener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 4;
        mPanel.add(ldxField,c);
        //Y
        JTextField ldyField = new JTextField(10);
        ldyField.setActionCommand(ldyFieldString);
        ldyField.addActionListener(mActionlistener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 5;
        mPanel.add(ldyField,c);
        //Z
        JTextField ldzField = new JTextField(10);
        ldzField.setActionCommand(ldzFieldString);
        ldzField.addActionListener(mActionlistener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 6;
        mPanel.add(ldzField,c);
        //labelx
        JLabel ldxFieldLabel = new JLabel("Light direction X: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 4;  
        ldxFieldLabel.setLabelFor(ldxField);
        mPanel.add(ldxFieldLabel,c);
        //labely
        JLabel ldyFieldLabel = new JLabel("Light direction Y: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 5;  
        ldyFieldLabel.setLabelFor(ldyField);
        mPanel.add(ldyFieldLabel,c);
        //labelz
        JLabel ldzFieldLabel = new JLabel("Light direction Z: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 6;  
        ldzFieldLabel.setLabelFor(ldzField);
        mPanel.add(ldzFieldLabel,c);
	
		//light position
        //X
        JTextField lpxField = new JTextField(10);
        lpxField.setActionCommand(lpxFieldString);
        lpxField.addActionListener(mActionlistener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 0;
        mPanel.add(lpxField,c);
        //Y
        JTextField lpyField = new JTextField(10);
        lpyField.setActionCommand(lpyFieldString);
        lpyField.addActionListener(mActionlistener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 1;
        mPanel.add(lpyField,c);
        //Z
        JTextField lpzField = new JTextField(10);
        lpzField.setActionCommand(lpzFieldString);
        lpzField.addActionListener(mActionlistener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 2;
        mPanel.add(lpzField,c);
        //labelx
        JLabel lpxFieldLabel = new JLabel("Light position X: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 2;
        c.gridy = 0;  
        lpxFieldLabel.setLabelFor(lpxField);
        mPanel.add(lpxFieldLabel,c);
        //labely
        JLabel lpyFieldLabel = new JLabel("Light position Y: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 2;
        c.gridy = 1;  
        lpyFieldLabel.setLabelFor(lpyField);
        mPanel.add(lpyFieldLabel,c);
        //labelz
        JLabel lpzFieldLabel = new JLabel("Light position Z: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 2;
        c.gridy = 2;  
        lpzFieldLabel.setLabelFor(lpzField);
        mPanel.add(lpzFieldLabel,c);
        
        //light attenuation
        //X
        JTextField laxField = new JTextField(10);
        laxField.setActionCommand(laxFieldString);
        laxField.addActionListener(mActionlistener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 3;
        mPanel.add(laxField,c);
        //Y
        JTextField layField = new JTextField(10);
        layField.setActionCommand(layFieldString);
        layField.addActionListener(mActionlistener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 4;
        mPanel.add(layField,c);
        //Z
        JTextField lazField = new JTextField(10);
        lazField.setActionCommand(lazFieldString);
        lazField.addActionListener(mActionlistener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 5;
        mPanel.add(lazField,c);
        //labelx
        JLabel laxFieldLabel = new JLabel("Light attenuation X: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 2;
        c.gridy = 3;  
        laxFieldLabel.setLabelFor(laxField);
        mPanel.add(laxFieldLabel,c);
        //labely
        JLabel layFieldLabel = new JLabel("Light attenuation Y: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 2;
        c.gridy = 4;  
        layFieldLabel.setLabelFor(layField);
        mPanel.add(layFieldLabel,c);
        //labelz
        JLabel lazFieldLabel = new JLabel("Light attenuation Z: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 2;
        c.gridy = 5;  
        lazFieldLabel.setLabelFor(lazField);
        mPanel.add(lazFieldLabel,c);
        
        //light spread angle
        JSlider lsaField = new JSlider(0, 90);
        lsaField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(lightnameField.getText())) ;
				mObj.setLightSpreadAngle(ObjectArray.get(lightnameField.getText()),(float) (((JSlider)e.getSource()).getValue()*Math.PI/180));
				
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 6;
        c.gridy = 0;
        mPanel.add(lsaField,c);
        //label spreadangle
        JLabel lsaFieldLabel = new JLabel("Light spread angle: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 5;
        c.gridy = 0;  
        lsaFieldLabel.setLabelFor(lsaField);
        mPanel.add(lsaFieldLabel,c);
        
        //light concentration
        JSlider lcField = new JSlider(0, 128);
        lcField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(lightnameField.getText())) ;
				mObj.setLightConcentration(ObjectArray.get(lightnameField.getText()),(float) (((JSlider)e.getSource()).getValue()));
				
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 6;
        c.gridy = 1;
        mPanel.add(lcField,c);
        //label lightconcentration
        JLabel lcFieldLabel = new JLabel("Light concentration: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 5;
        c.gridy = 1;  
        lcFieldLabel.setLabelFor(lcField);
        mPanel.add(lcFieldLabel,c);
        
        //delete
        JButton delButton = new JButton("Delete light");
        delButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scene.removeChild(ObjectArray.get(lightnameField.getText()));
				
				boolean found = false;
				for (int i=0; i< LightCount; i++){
					if(LightNames[i].equals(lightnameField.getText())) {
						found = true;
					}
					
					if (found && (LightNames[i+1]!=null))
						LightNames[i]=LightNames[i+1];
					else if (found)
						LightNames[i] = null;
				}
				LightCount--;
				
				JList LightList = new JList(LightNames);
				LightList.setLayoutOrientation(JList.VERTICAL);
				LightList.setVisibleRowCount(8);
				LightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				LightList.addListSelectionListener(new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						if (!arg0.getValueIsAdjusting()){
							showLightProperties(LightNames[((JList) arg0.getSource()).getSelectedIndex()]);
						}
					}
				});
				scrollLightList.getViewport().setView(LightList);
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 6;
        c.gridy = 6;
        mPanel.add(delButton,c);

        return mPanel;
	}
	
	/**
	 * Fills all lightPanel fields with selected light data
	 * @param name the selected solid name
	 */
	private void showLightProperties(String name){
		//TODO showLightProperties
		lightPropertiesPanel.setVisible(true);
		GridBagConstraints c = new GridBagConstraints();
		ObjectData lightData = new ObjectData(ObjectArray.get(name));
		
		//name
		JTextField lightnameField = (JTextField) lightPropertiesPanel.getComponent(0);
		lightnameField.setText(lightData.getName());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 0;
        lightPropertiesPanel.add(lightnameField, c,0);   
                
        //lightcolor
        Color3f colore = lightData.getLightColor();
        //R
        JSlider lcrField = (JSlider) lightPropertiesPanel.getComponent(2);
        lcrField.setValue((int) (255*(colore.getX())));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 1;  
        lightPropertiesPanel.add(lcrField,c,2);
        //G
        JSlider lcgField = (JSlider) lightPropertiesPanel.getComponent(3);
        lcgField.setValue((int) (255*(colore.getY())));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 2;  
        lightPropertiesPanel.add(lcgField,c,3);
        //B
        JSlider lcbField = (JSlider) lightPropertiesPanel.getComponent(4);
        lcbField.setValue((int) (255*(colore.getZ())));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 3;  
        lightPropertiesPanel.add(lcbField,c,4);
        
        //light direction
        if((lightData.getType()==ObjectData.DirectionalLight)||(lightData.getName().contains("Spot"))){
        	Vector3f dim = lightData.getLightDirection();
        	//X
            JTextField ldxField = (JTextField) lightPropertiesPanel.getComponent(8);
        	ldxField.setEnabled(true);
            ldxField.setText(String.valueOf(dim.getX()));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.1;
            c.gridx = 1;
            c.gridy = 4;
            lightPropertiesPanel.add(ldxField,c,8);
            //Y
            JTextField ldyField = (JTextField) lightPropertiesPanel.getComponent(9);
            ldyField.setText(String.valueOf(dim.getY()));
        	ldyField.setEnabled(true);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.1;
            c.gridx = 1;
            c.gridy = 5;
            lightPropertiesPanel.add(ldyField,c,9);
            //Z
            JTextField ldzField = (JTextField) lightPropertiesPanel.getComponent(10);
            ldzField.setText(String.valueOf(dim.getZ()));
        	ldzField.setEnabled(true);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.1;
            c.gridx = 1;
            c.gridy = 6;
            lightPropertiesPanel.add(ldzField,c,10);
        }
        else if(lightData.getType()==ObjectData.PointLight){
        	JTextField ldxField = (JTextField) lightPropertiesPanel.getComponent(8);
        	ldxField.setEnabled(false);
        	JTextField ldyField = (JTextField) lightPropertiesPanel.getComponent(9);
        	ldyField.setEnabled(false);
        	JTextField ldzField = (JTextField) lightPropertiesPanel.getComponent(10);
        	ldzField.setEnabled(false);
        };
        
        //light position
        if((lightData.getType()==ObjectData.PointLight)||(lightData.getType()==ObjectData.SpotLight)){
        Point3f dim = lightData.getLightPosition();
        //X
        JTextField lpxField = (JTextField) lightPropertiesPanel.getComponent(14);
        lpxField.setText(String.valueOf(dim.getX()));
    	lpxField.setEnabled(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 0;
        lightPropertiesPanel.add(lpxField,c,14);
        //Y
        JTextField lpyField = (JTextField) lightPropertiesPanel.getComponent(15);
        lpyField.setText(String.valueOf(dim.getY()));
    	lpyField.setEnabled(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 1;
        lightPropertiesPanel.add(lpyField,c,15);
        //Z
        JTextField lpzField = (JTextField) lightPropertiesPanel.getComponent(16);
        lpzField.setText(String.valueOf(dim.getZ()));
    	lpzField.setEnabled(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 2;
        lightPropertiesPanel.add(lpzField,c,16);
        }
        else if(lightData.getType()==ObjectData.DirectionalLight){
        	JTextField lpxField = (JTextField) lightPropertiesPanel.getComponent(14);
        	lpxField.setEnabled(false);
        	JTextField lpyField = (JTextField) lightPropertiesPanel.getComponent(15);
        	lpyField.setEnabled(false);
        	JTextField lpzField = (JTextField) lightPropertiesPanel.getComponent(16);
        	lpzField.setEnabled(false);
        }
        
        //light attenuation
        if((lightData.getType()==ObjectData.PointLight)||(lightData.getType()==ObjectData.SpotLight)){
        Point3f dim = lightData.getLightAttenuation();
        //X
        JTextField laxField = (JTextField) lightPropertiesPanel.getComponent(20);
        laxField.setText(String.valueOf(dim.getX()));
    	laxField.setEnabled(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 3;
        lightPropertiesPanel.add(laxField,c,20);
        //Y
        JTextField layField = (JTextField) lightPropertiesPanel.getComponent(21);
        layField.setText(String.valueOf(dim.getY()));
    	layField.setEnabled(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 4;
        lightPropertiesPanel.add(layField,c,21);
        //Z
        JTextField lazField = (JTextField) lightPropertiesPanel.getComponent(22);
        lazField.setText(String.valueOf(dim.getZ()));
    	lazField.setEnabled(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 5;
        lightPropertiesPanel.add(lazField,c,22);
      	}
        else if(lightData.getType()==ObjectData.DirectionalLight){
        	JTextField laxField = (JTextField) lightPropertiesPanel.getComponent(20);
        	laxField.setEnabled(false);
        	JTextField layField = (JTextField) lightPropertiesPanel.getComponent(21);
        	layField.setEnabled(false);
        	JTextField lazField = (JTextField) lightPropertiesPanel.getComponent(22);
        	lazField.setEnabled(false);
        }
        
        if(lightData.getName().contains("Spot")){
        //light spread angle
        JSlider lsaField = (JSlider)lightPropertiesPanel.getComponent(26);
        lsaField.setValue((int) ((lightData.getLightSpreadAngle())*180/Math.PI));
    	lsaField.setEnabled(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 6;
        c.gridy = 0;
        lightPropertiesPanel.add(lsaField,c,26);
                
        //light concentration
        JSlider lcField = (JSlider)lightPropertiesPanel.getComponent(28);
        lcField.setValue((int) (lightData.getLightConcentration()));
    	lcField.setEnabled(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 6;
        c.gridy = 1;
        lightPropertiesPanel.add(lcField,c,28);
        }
        else{
        	JSlider lsaField = (JSlider)lightPropertiesPanel.getComponent(26);
        	lsaField.setEnabled(false);
        	JSlider lcField = (JSlider)lightPropertiesPanel.getComponent(28);
        	lcField.setEnabled(false);
        }
        
      
	
	
	
	
	
	
	}
	
		
	/**
	 * Creates a JPanel which will contain information about a solid
	 * @return a JPanel object with all fields compiled below
	 */
	private JPanel populateSolidPanel(){
		//TODO populateSolidPanel()
		JPanel mPanel = new JPanel();
		mPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		final String nameFieldString = new String("nameField");
		final String posxFieldString = new String("posxField");
		final String posyFieldString = new String("posyField");
		final String poszFieldString = new String("poszField");
		final String dimxFieldString = new String("dimxField");
		final String dimyFieldString = new String("dimyField");
		final String dimzFieldString = new String("dimzField");
		final String shineFieldString = new String("shineField");
		
		final int MIN = 0;
		final int MAX = 255;

		ActionListener mActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
	         
				JTextField source = (JTextField)arg0.getSource();

				if(posxFieldString.equals(arg0.getActionCommand())){
					Vector3f pos = new Vector3f(Float.valueOf(source.getText()), mObj.getPosition().getY(), mObj.getPosition().getZ());
		         	mObj.setPosition(ObjectArray.get(nameField.getText()), pos);
		         	drawingPanel.requestFocusInWindow();
				}				
				else if(posyFieldString.equals(arg0.getActionCommand())){
					Vector3f pos = new Vector3f(mObj.getPosition().getX(),Float.valueOf(source.getText()), mObj.getPosition().getZ());
		            mObj.setPosition(ObjectArray.get(nameField.getText()), pos);
		            drawingPanel.requestFocusInWindow();
				}
				else if(poszFieldString.equals(arg0.getActionCommand())){
					Vector3f pos = new Vector3f(mObj.getPosition().getX(),mObj.getPosition().getY(),Float.valueOf(source.getText()));
		            mObj.setPosition(ObjectArray.get(nameField.getText()), pos);
		            drawingPanel.requestFocusInWindow();
				}
				else if(dimxFieldString.equals(arg0.getActionCommand())){
					if(mObj.getType()==ObjectData.Box){
						Vector3d scale = new Vector3d(Double.valueOf(source.getText()), mObj.getDimensions().getY(), mObj.getDimensions().getZ());
			            mObj.setDimensions(ObjectArray.get(nameField.getText()),scale);
					}
					else if ((mObj.getType() == ObjectData.Cone) || (mObj.getType() == ObjectData.Cylinder)){
						Vector3d scale = new Vector3d(Double.valueOf(source.getText()), mObj.getDimensions().getY(), Double.valueOf(source.getText()));
			            mObj.setDimensions(ObjectArray.get(nameField.getText()),scale);
					}
					else if (mObj.getType() == ObjectData.Sphere){
						Vector3d scale = new Vector3d(Double.valueOf(source.getText()), Double.valueOf(source.getText()), Double.valueOf(source.getText()));
			            mObj.setDimensions(ObjectArray.get(nameField.getText()),scale);
					}
					drawingPanel.requestFocusInWindow();
				}
				else if(dimyFieldString.equals(arg0.getActionCommand())){
					if((mObj.getType()==ObjectData.Box) || (mObj.getType() == ObjectData.Cone) || (mObj.getType() == ObjectData.Cylinder)){
						Vector3d scale = new Vector3d(mObj.getDimensions().getX(), Double.valueOf(source.getText()) ,mObj.getDimensions().getZ());
						mObj.setDimensions(ObjectArray.get(nameField.getText()), scale);
					}
					drawingPanel.requestFocusInWindow();
				}
				else if(dimzFieldString.equals(arg0.getActionCommand())){
					if(mObj.getType()==ObjectData.Box){
						Vector3d scale = new Vector3d(mObj.getDimensions().getX(), mObj.getDimensions().getY(), Double.valueOf(source.getText()));
						mObj.setDimensions(ObjectArray.get(nameField.getText()), scale);
					}
					drawingPanel.requestFocusInWindow();
				}
				else if(shineFieldString.equals(arg0.getActionCommand())){
		            mObj.setShininess(ObjectArray.get(nameField.getText()),Float.valueOf(source.getText()));
		            drawingPanel.requestFocusInWindow();
				}
				
				
			}
		};
		
		//name
		nameField = new JTextField(10);
        nameField.setActionCommand(nameFieldString);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 0;
        nameField.setFocusable(false);
        mPanel.add(nameField, c);   
        
        //name label
        JLabel nameFieldLabel = new JLabel("Solid Name: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 0;
        c.gridy = 0;  
        nameFieldLabel.setLabelFor(nameField);
        nameFieldLabel.setFocusable(false);
        mPanel.add(nameFieldLabel,c);
        
        //position
        JTextField posxField = new JTextField(10);
        posxField.setActionCommand(posxFieldString);
        posxField.addActionListener(mActionListener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 1;
        c.gridy = 1; 
        mPanel.add(posxField, c);  
        
        JTextField posyField = new JTextField(10);
        posyField.setActionCommand(posyFieldString);
        posyField.addActionListener(mActionListener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 1;
        c.gridy = 2; 
        mPanel.add(posyField, c);   
        
        JTextField poszField = new JTextField(10);
        poszField.setActionCommand(poszFieldString);
        poszField.addActionListener(mActionListener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.1;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 3;
        mPanel.add(poszField, c);   
        
        JLabel positionxFieldLabel = new JLabel("Position X: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        positionxFieldLabel.setLabelFor(posxField);
        positionxFieldLabel.setFocusable(false);
        mPanel.add(positionxFieldLabel,c);
        
        JLabel positionyFieldLabel = new JLabel("Position Y: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        positionyFieldLabel.setLabelFor(posyField);
        positionyFieldLabel.setFocusable(false);
        mPanel.add(positionyFieldLabel,c);
        
        JLabel positionzFieldLabel = new JLabel("Position Z: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 3;
        positionzFieldLabel.setLabelFor(poszField);
        positionzFieldLabel.setFocusable(false);
        mPanel.add(positionzFieldLabel,c);
        
        //dimension
        JTextField dimxField = new JTextField(10);
        dimxField.setActionCommand(dimxFieldString);
        dimxField.addActionListener(mActionListener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 1;
        c.gridy = 4;
        mPanel.add(dimxField, c);  
        
        JTextField dimyField = new JTextField(10);
        dimyField.setActionCommand(dimyFieldString);
        dimyField.addActionListener(mActionListener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 1;
        c.gridy = 5;
        mPanel.add(dimyField, c);   
        
        JTextField dimzField = new JTextField(10);
        dimzField.setActionCommand(dimzFieldString);
        dimzField.addActionListener(mActionListener);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 1;
        c.gridy = 6;
        mPanel.add(dimzField, c);   
        
        JLabel dimxFieldLabel = new JLabel("Dimension X: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 4;
        dimxFieldLabel.setLabelFor(dimxField);
        dimxFieldLabel.setFocusable(false);
        mPanel.add(dimxFieldLabel,c);
        
        JLabel dimyFieldLabel = new JLabel("Dimension Y: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 5;
        dimyFieldLabel.setLabelFor(dimyField);
        dimyFieldLabel.setFocusable(false);
        mPanel.add(dimyFieldLabel,c);
        
        JLabel dimzFieldLabel = new JLabel("Dimension Z: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 6;
        dimzFieldLabel.setLabelFor(dimzField);
        dimzFieldLabel.setFocusable(false);
        mPanel.add(dimzFieldLabel,c);
         
      //AmbientColor
        JSlider acrField = new JSlider(MIN, MAX);
        acrField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getAmbientColor();
				colore.setX((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setAmbientColor(ObjectArray.get(nameField.getText()),colore);
				drawingPanel.requestFocusInWindow();
			}
		});

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 0;
        mPanel.add(acrField, c);  
        
        JSlider acgField = new JSlider(MIN, MAX);
        acgField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getAmbientColor();
				colore.setY((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setAmbientColor(ObjectArray.get(nameField.getText()),colore);
				drawingPanel.requestFocusInWindow();
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 1;
        mPanel.add(acgField, c);   
        
        JSlider acbField = new JSlider(MIN, MAX);
        acbField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getAmbientColor();
				colore.setZ((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setAmbientColor(ObjectArray.get(nameField.getText()),colore);
				drawingPanel.requestFocusInWindow();
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 2;
        mPanel.add(acbField, c);   
        
        JLabel acrFieldLabel = new JLabel("Ambient Color R: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 2;
        c.gridy = 0;
        acrFieldLabel.setLabelFor(acrField);
        acrFieldLabel.setFocusable(false);
        mPanel.add(acrFieldLabel,c);
        
        JLabel acgFieldLabel = new JLabel("Ambient Color G: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 2;
        c.gridy = 1;
        acgFieldLabel.setLabelFor(acgField);
        acgFieldLabel.setFocusable(false);
        mPanel.add(acgFieldLabel,c);
        
        JLabel acbFieldLabel = new JLabel("Ambient Color B: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 2;
        c.gridy = 2;
        acbFieldLabel.setLabelFor(acbField);
        acbFieldLabel.setFocusable(false);
        mPanel.add(acbFieldLabel,c);
        
        //shine
        JTextField shineField = new JTextField(10);
        shineField.setActionCommand(shineFieldString);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 3;
        c.gridy = 6;
        mPanel.add(shineField, c);   
        
        JLabel shineFieldLabel = new JLabel("Shininess: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.gridx = 2;
        c.gridy = 6;  
        shineFieldLabel.setLabelFor(shineField);
        shineFieldLabel.setFocusable(false);
        mPanel.add(shineFieldLabel,c);
        
        //EmissiveColor
        JSlider ecrField = new JSlider(MIN, MAX);
        ecrField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getEmissiveColor();
				colore.setX((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setEmissiveColor((ObjectArray.get(nameField.getText())),colore);
				drawingPanel.requestFocusInWindow();
			}
		});
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 3;
        mPanel.add(ecrField, c);  
        
        JSlider ecgField = new JSlider(MIN, MAX);
        ecgField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getEmissiveColor();
				colore.setY((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setEmissiveColor((ObjectArray.get(nameField.getText())),colore);
				drawingPanel.requestFocusInWindow();
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 4;
        mPanel.add(ecgField, c);   
        
        JSlider ecbField = new JSlider(MIN, MAX);
        ecbField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getEmissiveColor();
				colore.setZ((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setEmissiveColor((ObjectArray.get(nameField.getText())),colore);
				drawingPanel.requestFocusInWindow();
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 3;
        c.gridy = 5;
        mPanel.add(ecbField, c);   
        
        JLabel ecrFieldLabel = new JLabel("Emissive Color R: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 2;
        c.gridy = 3;
        ecrFieldLabel.setLabelFor(ecrField);
        ecrFieldLabel.setFocusable(false);
        mPanel.add(ecrFieldLabel,c);
        
        JLabel ecgFieldLabel = new JLabel("Emissive Color G: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 2;
        c.gridy = 4;
        ecgFieldLabel.setLabelFor(ecgField);
        ecgFieldLabel.setFocusable(false);
        mPanel.add(ecgFieldLabel,c);
        
        JLabel ecbFieldLabel = new JLabel("Emissive Color B: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 2;
        c.gridy = 5;
        ecbFieldLabel.setLabelFor(ecbField);
        ecbFieldLabel.setFocusable(false);
        mPanel.add(ecbFieldLabel,c);
        
        //DiffuseColor
        JSlider dcrField = new JSlider(MIN, MAX);
        dcrField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getDiffuseColor();
				colore.setX((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setDiffuseColor((ObjectArray.get(nameField.getText())),colore);
				drawingPanel.requestFocusInWindow();
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 5;
        c.gridy = 0;
        mPanel.add(dcrField, c);  
        
        JSlider dcgField = new JSlider(MIN, MAX);
        dcgField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getDiffuseColor();
				colore.setY((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setDiffuseColor((ObjectArray.get(nameField.getText())),colore);
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 5;
        c.gridy = 1;
        mPanel.add(dcgField, c);   
        
        JSlider dcbField = new JSlider(MIN, MAX);
        dcbField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getDiffuseColor();
				colore.setZ((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setDiffuseColor((ObjectArray.get(nameField.getText())),colore);
				drawingPanel.requestFocusInWindow();
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 5;
        c.gridy = 2;
        mPanel.add(dcbField, c);   
        
        JLabel dcrFieldLabel = new JLabel("Diffuse Color R: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx =0.05;
        c.weighty = 0.1;
        c.gridx = 4;
        c.gridy = 0;
        dcrFieldLabel.setLabelFor(dcrField);
        dcrFieldLabel.setFocusable(false);
        mPanel.add(dcrFieldLabel,c);
        
        JLabel dcgFieldLabel = new JLabel("Diffuse Color G: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 4;
        c.gridy = 1;
        dcgFieldLabel.setLabelFor(dcgField);
        dcgFieldLabel.setFocusable(false);
        mPanel.add(dcgFieldLabel,c);
        
        JLabel dcbFieldLabel = new JLabel("Diffuse Color B: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 4;
        c.gridy = 2;
        dcbFieldLabel.setLabelFor(dcbField);
        dcbFieldLabel.setFocusable(false);
        mPanel.add(dcbFieldLabel,c);
        
        //	SpecularColor
        JSlider scrField = new JSlider(MIN, MAX);
        scrField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getSpecularColor();
				colore.setX((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setSpecularColor((ObjectArray.get(nameField.getText())),colore);
				drawingPanel.requestFocusInWindow();
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 5;
        c.gridy = 3;
        mPanel.add(scrField, c);  
        
        JSlider scgField = new JSlider(MIN, MAX);
        scgField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getSpecularColor();
				colore.setY((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setSpecularColor((ObjectArray.get(nameField.getText())),colore);
				drawingPanel.requestFocusInWindow();
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 5;
        c.gridy = 4;
        mPanel.add(scgField, c);   
        
        JSlider scbField = new JSlider(MIN, MAX);
        scbField.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ObjectData mObj = new ObjectData(ObjectArray.get(nameField.getText())) ;
				Color3f colore = mObj.getSpecularColor();
				colore.setZ((float) ((JSlider)e.getSource()).getValue() /255);
				mObj.setSpecularColor((ObjectArray.get(nameField.getText())),colore);
				drawingPanel.requestFocusInWindow();
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 5;
        c.gridy = 5;
        mPanel.add(scbField, c);   
        
        JLabel scrFieldLabel = new JLabel("Specular Color R: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 4;
        c.gridy = 3;
        scrFieldLabel.setLabelFor(scrField);
        scrFieldLabel.setFocusable(false);
        mPanel.add(scrFieldLabel,c);
        
        JLabel scgFieldLabel = new JLabel("Specular Color G: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 4;
        c.gridy = 4;
        scgFieldLabel.setLabelFor(scgField);
        scgFieldLabel.setFocusable(false);
        mPanel.add(scgFieldLabel,c);
        
        JLabel scbFieldLabel = new JLabel("Specular Color B: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx = 4;
        c.gridy = 5;
        scbFieldLabel.setLabelFor(scbField);
        scbFieldLabel.setFocusable(false);
        mPanel.add(scbFieldLabel,c);
        
      //delete
        JButton delButton = new JButton("Delete solid");
        delButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scene.removeChild(ObjectArray.get(nameField.getText()));
				solidPanel.setVisible(false);
				ExploreBtn.setSelected(true);
			}
		});
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.05;
        c.weighty = 0.1;
        c.gridx =5 ;
        c.gridy =6 ;
        mPanel.add(delButton,c);

        return mPanel;
	}

	/**
	 * Fills all solidPanel fields with selected solid data
	 * @param name the selected solid name
	 */
	private void showSolidProperties(String name){
		//TODO showSolidProperties
		solidPanel.setVisible(true);
		lightPanel.setVisible(false);
		GridBagConstraints c = new GridBagConstraints();
		ObjectData solidData = new ObjectData(ObjectArray.get(name));
				
		//name
		JTextField nameField = (JTextField) solidPanel.getComponent(0);
		nameField.setText(solidData.getName());
		c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 0;
		solidPanel.add(nameField, c, 0);
		
		//position
		//X
		JTextField posxField = (JTextField) solidPanel.getComponent(2);
		posxField.setText(String.valueOf(solidData.getPosition().x));
		c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 1;
		solidPanel.add(posxField, c , 2);
		
		//Y
		JTextField posyField = (JTextField) solidPanel.getComponent(3);
		posyField.setText(String.valueOf(solidData.getPosition().y));
		c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 2;
		solidPanel.add(posyField, c , 3);
		
		//Z
		JTextField poszField = (JTextField) solidPanel.getComponent(4);
		poszField.setText(String.valueOf(solidData.getPosition().z));
		c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 3;
		solidPanel.add(poszField,c,4);
		
		//dimension 
		Vector3d dim = solidData.getDimensions();
		if(solidData.getType()==ObjectData.Box){
			//X
			JTextField dimxField = (JTextField) solidPanel.getComponent(8);
			dimxField.setText(String.valueOf(dim.getX()));
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.weighty = 0.1;
			c.gridx = 1;
			c.gridy = 4;
			solidPanel.add(dimxField,c, 8);

			//Y
			JTextField dimyField = (JTextField) solidPanel.getComponent(9);
			dimyField.setText(String.valueOf(dim.getY()));
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.weighty = 0.1;
			c.gridx = 1;
			c.gridy = 5;
			solidPanel.add(dimyField,c, 9);

			//z
			JTextField dimzField = (JTextField) solidPanel.getComponent(10);
			dimzField.setText(String.valueOf(dim.getZ()));
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.1;
			c.weighty = 0.1;
			c.gridx = 1;
			c.gridy = 6;
			solidPanel.add(dimzField,c, 10);
		}
		else if(solidData.getType()==ObjectData.Sphere){
			//raggio
			JTextField dimxField = (JTextField) solidPanel.getComponent(8);
			dimxField.setText(String.valueOf(dim.getX()));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.weighty = 0.1;
	        c.gridx = 1;
	        c.gridy = 4;
			solidPanel.add(dimxField,c, 8);
			
			//altezza
			JTextField dimyField = (JTextField) solidPanel.getComponent(9);
			dimyField.setText("not exist");
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.weighty = 0.1;
	        c.gridx = 1;
	        c.gridy = 5;
			solidPanel.add(dimyField,c, 9);
			
			//non presente
			JTextField dimzField = (JTextField) solidPanel.getComponent(10);
			dimzField.setText("not exist");
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.weighty = 0.1;
	        c.gridx = 1;
	        c.gridy = 6;
			solidPanel.add(dimzField,c, 10);
		}
		else if((solidData.getType()==ObjectData.Cylinder) || (solidData.getType()==ObjectData.Cone)){
			//raggio
			JTextField dimxField = (JTextField) solidPanel.getComponent(8);
			dimxField.setText(String.valueOf(dim.getX()));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.weighty = 0.1;
	        c.gridx = 1;
	        c.gridy = 4;
			solidPanel.add(dimxField,c, 8);
			
			//altezza
			JTextField dimyField = (JTextField) solidPanel.getComponent(9);
			dimyField.setText(String.valueOf(dim.getY()));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.weighty = 0.1;
	        c.gridx = 1;
	        c.gridy = 5;
			solidPanel.add(dimyField,c, 9);
			
			//non presente
			JTextField dimzField = (JTextField) solidPanel.getComponent(10);
			dimzField.setText("not exist");
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.weighty = 0.1;
	        c.gridx = 1;
	        c.gridy = 6;
			solidPanel.add(dimzField,c, 10);
		}
		if(!solidData.getName().contains("WF")){

			for (int i = 14; i<=40; i++){
				solidPanel.getComponent(i).setEnabled(true);
			}
			
			//AmbientColor
			Color3f colore = solidData.getAmbientColor();


			//R
			JSlider acrField = (JSlider) solidPanel.getComponent(14);
			acrField.setValue((int) (255*(colore.getX())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.gridx = 3;
	        c.gridy = 0;
			solidPanel.add(acrField, c , 14);
			
			//G
			JSlider acgField = (JSlider) solidPanel.getComponent(15);
			acgField.setValue((int) (255*(colore.getY())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.gridx = 3;
	        c.gridy = 1;
			solidPanel.add(acgField, c , 15);
			
			//B
			JSlider acbField = (JSlider) solidPanel.getComponent(16);
			acbField.setValue((int) (255*(colore.getZ())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.gridx = 3;
	        c.gridy = 2;
			solidPanel.add(acbField,c,16);
			
			//shininess
			JTextField shineField = (JTextField) solidPanel.getComponent(20);
			shineField.setText(String.valueOf(solidData.getShininess()));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.gridx = 3;
	        c.gridy = 6;
			solidPanel.add(shineField, c, 20);
			
			//Emissive Color
			Color3f Ecolore = solidData.getEmissiveColor();
			//R
			JSlider ecrField = (JSlider) solidPanel.getComponent(22);
			ecrField.setValue((int) (255*(Ecolore.getX())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.05;
	        c.gridx = 3;
	        c.gridy = 3;
			solidPanel.add(ecrField, c , 22);
			
			//G
			JSlider ecgField = (JSlider) solidPanel.getComponent(23);
			ecgField.setValue((int) (255*(Ecolore.getY())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.05;
	        c.gridx = 3;
	        c.gridy = 4;
			solidPanel.add(ecgField, c , 23);
			
			//B
			JSlider ecbField = (JSlider) solidPanel.getComponent(24);
			ecbField.setValue((int) (255*(Ecolore.getZ())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.05;
	        c.gridx = 3;
	        c.gridy = 5;
			solidPanel.add(ecbField,c,24);
			
			//Diffuse Color
			Color3f Dcolore = solidData.getDiffuseColor();
			//R
			JSlider dcrField = (JSlider) solidPanel.getComponent(28);
			dcrField.setValue((int) (255*(Dcolore.getX())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.gridx = 5;
	        c.gridy = 0;
			solidPanel.add(dcrField, c , 28);
			
			//G
			JSlider dcgField = (JSlider) solidPanel.getComponent(29);
			dcgField.setValue((int) (255*(Dcolore.getY())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.gridx = 5;
	        c.gridy = 1;
			solidPanel.add(dcgField, c , 29);
			
			//B
			JSlider dcbField = (JSlider) solidPanel.getComponent(30);
			dcbField.setValue((int) (255*(Dcolore.getZ())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.gridx = 5;
	        c.gridy = 2;
			solidPanel.add(dcbField,c,30);
			
			// Specular Color
			Color3f Scolore = solidData.getSpecularColor();
			//R
			JSlider scrField = (JSlider) solidPanel.getComponent(34);
			scrField.setValue((int) (255*(Scolore.getX())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.gridx = 5;
	        c.gridy = 3;
			solidPanel.add(scrField, c , 34);
			
			//G
			JSlider scgField = (JSlider) solidPanel.getComponent(35);
			scgField.setValue((int) (255*(Scolore.getY())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.gridx = 5;
	        c.gridy = 4;
			solidPanel.add(scgField, c , 35);
			
			//B
			JSlider scbField = (JSlider) solidPanel.getComponent(36);
			scbField.setValue((int) (255*(Scolore.getZ())));
			c.fill = GridBagConstraints.HORIZONTAL;
	        c.weightx = 0.1;
	        c.gridx = 5;
	        c.gridy = 5;
			solidPanel.add(scbField,c,36);
		}
		else{
			for (int i = 14; i<=40; i++){
				solidPanel.getComponent(i).setEnabled(false);
			}
		}
	}
	
	/**
	 * Creates a toolbar with buttons and buttons listeners
	 * @return a JToolbar object
	 */
	private JToolBar createToolbar() {
		//TODO createToolbar()
		JToolBar tb = new JToolBar();

		ExploreBtn = new JToggleButton("Explore Mode",true);
		ExploreBtn.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED){
					solidPanel.setVisible(false);
					lightPropertiesPanel.setVisible(false);
					lightPanel.setVisible(false);
					LightBtn.setSelected(false);
					SelBtn.setSelected(false);
					drawingPanel.requestFocusInWindow();
					if (ObjectArray.get("WFCube") != null)
						scene.removeChild(ObjectArray.get("WFCube"));
				}
			}
		});
		ExploreBtn.setFocusable(false);
		
		LightBtn = new JToggleButton("Modify Lights");
		LightBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JToggleButton) e.getSource()).isSelected()){
					solidPanel.setVisible(false);
					lightPanel.setVisible(true);
					ExploreBtn.setSelected(false);
					SelBtn.setSelected(false);
					if (ObjectArray.get("WFCube") != null)
						scene.removeChild(ObjectArray.get("WFCube"));
					JList LightList = new JList(LightNames);
					LightList.setLayoutOrientation(JList.VERTICAL);
					LightList.setVisibleRowCount(8);
					LightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					LightList.addListSelectionListener(new ListSelectionListener() {
						
						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							if (!arg0.getValueIsAdjusting()){
								showLightProperties(LightNames[((JList) arg0.getSource()).getSelectedIndex()]);
							}
						}
					});
					scrollLightList.getViewport().setView(LightList);
				}
			}
		});
		LightBtn.setFocusable(false);
		
		SelBtn = new JToggleButton("Selection Mode");
		SelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (((JToggleButton) arg0.getSource()).isSelected()){
					InsertObject(new CreateWireFrameCube(defaultDim).getBG());
					solidPanel.setVisible(true);
					lightPanel.setVisible(false);
					lightPropertiesPanel.setVisible(false);
					ExploreBtn.setSelected(false);
					LightBtn.setSelected(false);
					showSolidProperties("WFCube");
				}
			}
		});
		SelBtn.setFocusable(false);
		
		JButton BoxBtn = new JButton("Box");
		BoxBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {		
				InsertObject(new CreateBox(defaultDim, defaultDim, defaultDim, 
						emissive, realColor, specular, shininess).getBG());
			}
		});
		
		BoxBtn.setFocusable(false);

		JButton SphereBtn = new JButton("Sphere");
		SphereBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InsertObject(new CreateSphere(defaultDim,
						emissive, realColor, specular, shininess).getBG());
			}
		});
		
		SphereBtn.setFocusable(false);
		
		JButton CylinderBtn = new JButton("Cylinder");
		CylinderBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InsertObject(new CreateCylinder(defaultDim,3*defaultDim,
						emissive, realColor, specular, shininess).getBG());
		 }
		});
		
		CylinderBtn.setFocusable(false);
		
		JButton ConeBtn = new JButton("Cone");
		ConeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InsertObject(new CreateCone(defaultDim,3*defaultDim,
						emissive, realColor, specular, shininess).getBG());
			}
		});
		
		ConeBtn.setFocusable(false);
		
		JButton DirLightBtn = new JButton("Directional Light");
		DirLightBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InsertObject(new CreateDirLight(white, lightDirection).getBG());
				JList LightList = new JList(LightNames);
				LightList.setLayoutOrientation(JList.VERTICAL);
				LightList.setVisibleRowCount(8);
				LightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				LightList.addListSelectionListener(new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						if (!arg0.getValueIsAdjusting()){
							showLightProperties(LightNames[((JList) arg0.getSource()).getSelectedIndex()]);
						}
					}
				});
				scrollLightList.getViewport().setView(LightList);
			}
		});
		
		DirLightBtn.setFocusable(false);

		JButton PointLightBtn = new JButton("Point Light");
		PointLightBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InsertObject(new CreatePointLight(white, lightPosition, lightPosition).getBG());
				JList LightList = new JList(LightNames);
				LightList.setLayoutOrientation(JList.VERTICAL);
				LightList.setVisibleRowCount(8);
				LightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				LightList.addListSelectionListener(new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						if (!arg0.getValueIsAdjusting()){
							showLightProperties(LightNames[((JList) arg0.getSource()).getSelectedIndex()]);
						}
					}
				});
				scrollLightList.getViewport().setView(LightList);
			}
		});
		
		PointLightBtn.setFocusable(false);

		JButton SpotLightBtn = new JButton("Spot Light");
		SpotLightBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InsertObject(new CreateSpotLight(white, lightPosition, lightAttenuation, lightDirection, lightSpreadAngle, lightConcentration).getBG());
				JList LightList = new JList(LightNames);
				LightList.setLayoutOrientation(JList.VERTICAL);
				LightList.setVisibleRowCount(8);
				LightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				LightList.addListSelectionListener(new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						if (!arg0.getValueIsAdjusting()){
							showLightProperties(LightNames[((JList) arg0.getSource()).getSelectedIndex()]);
						}
					}
				});
				scrollLightList.getViewport().setView(LightList);
			}
		});
		
		SpotLightBtn.setFocusable(false);
		
		tb.add(ExploreBtn);
		tb.add(LightBtn);
		tb.add(SelBtn);
		
		tb.add(BoxBtn);
		tb.add(SphereBtn);
		tb.add(CylinderBtn);
		tb.add(ConeBtn);
		tb.add(DirLightBtn);
		tb.add(PointLightBtn);
		tb.add(SpotLightBtn);
		
		tb.setFocusable(false);
		tb.setFloatable(false);
		
		return tb;
	}
	

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JPanel drawingPanel;
	private JPanel lowerPanel;
	private JPanel solidPanel;
	private JPanel lightPropertiesPanel;
	private JPanel lightPanel;
	private JScrollPane scrollLightList;
	private JTextField nameField; 
	private JTextField lightnameField;
	private JToggleButton ExploreBtn;
	private JToggleButton LightBtn;
	private JToggleButton SelBtn;
	// End of variables declaration//GEN-END:variables
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if ((o instanceof PickedObjects) && (arg instanceof Map<?,?>)){
			
			if (((Map<String, String>) arg).containsKey(PickUtility.clicked)){
				ExploreBtn.setSelected(false);
				LightBtn.setSelected(false);
				SelBtn.setSelected(false);
				showSolidProperties(((Map<String,String>)arg).get(PickUtility.clicked));
				arg = new HashMap<String,String>(2);
			}
			else if (((Map<String, String>) arg).containsKey(PickUtility.mouseOver)){
				System.out.println("sono nell'update: " + ObjectArray.get(arg).getName());
				arg = new HashMap<String,String>(2);
			}
		}
	}

}
