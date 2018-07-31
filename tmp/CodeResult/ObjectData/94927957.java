package it.polimi.aasi;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

/**
 * Contains informations about an object (a solid, child of Primitive; or 
 * a light, child of Light), given the branchgroup it belongs to.
 * Has getters and setters for the principal characteristics of a solid
 * and a light and permits translation and scaling operation on the solid.
 * @author massimo
 *
 */
public class ObjectData {
	public static final int Box = 0;
	public static final int Cone = 1;
	public static final int Cylinder = 2;
	public static final int Sphere = 3;
	public static final int DirectionalLight = 4;
	public static final int PointLight = 5;
	public static final int SpotLight = 6;

	private TransformGroup tg = null;
	private Transform3D t3d = null;
	private String name = null;

	private Box box = null;
	private Cone cone = null;
	private Cylinder cylinder = null;
	private Sphere sphere = null;

	private Color3f ambientColor = null;
	private Color3f emissiveColor = null;
	private Color3f diffuseColor = null;
	private Color3f specularColor = null;
	private float shininess;

	private DirectionalLight dirLight = null;
	private SpotLight spotLight = null;
	private PointLight pointLight = null;

	private Color3f lightColor = null;
	private Vector3f lightDirection = null;
	private Point3f lightPosition = null;
	private Point3f lightAttenuation = null;
	private float lightSpreadAngle = -1;
	private float lightConcentration = -1;

	public ObjectData(BranchGroup bg){			

		if (bg.getChild(0) instanceof TransformGroup){
			tg = (TransformGroup) bg.getChild(0);

			if (tg.getChild(0) instanceof Box){
				//retrieve the TransformGroup (it's always the first, and only, child)
				//get the Transform3D so we know the position etc.
				t3d = new Transform3D();
				tg.getTransform(t3d);
				//finally retrieve the solid itself, and all of its properties
				box = (Box) tg.getChild(0);
				name = box.getName();

				if(!name.contains("WF")){

					ambientColor = new Color3f();
					box.getAppearance().getMaterial().getAmbientColor(ambientColor);

					emissiveColor = new Color3f();
					box.getAppearance().getMaterial().getEmissiveColor(emissiveColor);

					diffuseColor = new Color3f();
					box.getAppearance().getMaterial().getDiffuseColor(diffuseColor);

					specularColor = new Color3f();
					box.getAppearance().getMaterial().getSpecularColor(specularColor);

					shininess = box.getAppearance().getMaterial().getShininess();
				}
			}

			else if (tg.getChild(0) instanceof Cone){
				t3d = new Transform3D();
				tg.getTransform(t3d);
				cone = (Cone) tg.getChild(0);
				name = cone.getName();

				ambientColor = new Color3f();
				cone.getAppearance().getMaterial().getAmbientColor(ambientColor);

				emissiveColor = new Color3f();
				cone.getAppearance().getMaterial().getEmissiveColor(emissiveColor);

				diffuseColor = new Color3f();
				cone.getAppearance().getMaterial().getDiffuseColor(diffuseColor);

				specularColor = new Color3f();
				cone.getAppearance().getMaterial().getSpecularColor(specularColor);

				shininess = cone.getAppearance().getMaterial().getShininess();
			}

			else if (tg.getChild(0) instanceof Cylinder){
				t3d = new Transform3D();
				tg.getTransform(t3d);
				cylinder = (Cylinder) tg.getChild(0);
				name = cylinder.getName();

				ambientColor = new Color3f();
				cylinder.getAppearance().getMaterial().getAmbientColor(ambientColor);

				emissiveColor = new Color3f();
				cylinder.getAppearance().getMaterial().getEmissiveColor(emissiveColor);

				diffuseColor = new Color3f();
				cylinder.getAppearance().getMaterial().getDiffuseColor(diffuseColor);

				specularColor = new Color3f();
				cylinder.getAppearance().getMaterial().getSpecularColor(specularColor);

				shininess = cylinder.getAppearance().getMaterial().getShininess();
			}

			else if (tg.getChild(0) instanceof Sphere){
				t3d = new Transform3D();
				tg.getTransform(t3d);
				sphere = (Sphere) tg.getChild(0);
				name = sphere.getName();

				ambientColor = new Color3f();
				sphere.getAppearance().getMaterial().getAmbientColor(ambientColor);

				emissiveColor = new Color3f();
				sphere.getAppearance().getMaterial().getEmissiveColor(emissiveColor);

				diffuseColor = new Color3f();
				sphere.getAppearance().getMaterial().getDiffuseColor(diffuseColor);

				specularColor = new Color3f();
				sphere.getAppearance().getMaterial().getSpecularColor(specularColor);

				shininess = sphere.getAppearance().getMaterial().getShininess();
			}
		}
		// Lights part
		else if (bg.getChild(0) instanceof DirectionalLight){
			dirLight = (DirectionalLight) bg.getChild(0);
			name = dirLight.getName();

			lightColor = new Color3f();
			dirLight.getColor(lightColor);

			lightDirection = new Vector3f();
			dirLight.getDirection(lightDirection);
		}
		else if ((bg.getChild(0) instanceof SpotLight)&&(bg.getChild(0).getName().contains("Spot"))){
			spotLight = (SpotLight) bg.getChild(0);
			name = spotLight.getName();

			lightColor = new Color3f();
			spotLight.getColor(lightColor);

			lightDirection = new Vector3f();
			spotLight.getDirection(lightDirection);

			lightPosition = new Point3f();
			spotLight.getPosition(lightPosition);

			lightAttenuation = new Point3f();
			spotLight.getAttenuation(lightAttenuation);

			lightSpreadAngle = spotLight.getSpreadAngle();

			lightConcentration = spotLight.getConcentration();
		}
		else if ((bg.getChild(0) instanceof PointLight)&&(bg.getChild(0).getName().contains("Point"))){
			pointLight = (PointLight) bg.getChild(0);
			name = pointLight.getName();

			lightColor = new Color3f();
			pointLight.getColor(lightColor);

			lightPosition = new Point3f();
			pointLight.getPosition(lightPosition);

			lightAttenuation = new Point3f();
			pointLight.getAttenuation(lightAttenuation);
		}
	}

	/**
	 * returns the Object type contained in this class instance
	 * @return a unique identifier of this class object
	 */
	public int getType(){
		if (box!=null)
			return ObjectData.Box;
		else if (cone!=null)
			return ObjectData.Cone;
		else if (cylinder!=null)
			return ObjectData.Cylinder;
		else if (sphere!=null)
			return ObjectData.Sphere;
		else if (dirLight != null)
			return ObjectData.DirectionalLight;
		else if (pointLight!=null)
			return ObjectData.PointLight;
		else if (spotLight != null)
			return ObjectData.SpotLight;
		else return -1;
	}

	public void delete(BranchGroup bg){
		bg.removeAllChildren();
	}

	/**
	 * returns the name of the object
	 * @return the name of the object
	 */
	public String getName(){
		return name;
	}

	/**
	 * returns the position of a solid
	 * @return a Vector3f object, pointing the position of the solid
	 */
	public Vector3f getPosition(){
		Vector3f v3f = new Vector3f();
		t3d.get(v3f);
		return v3f;
	}
	/**
	 * sets the position of a solit to the given coordinates
	 * @param x the new x coordinate of the solid
	 */
	public void setPosition(BranchGroup bg, Vector3f trans){
		t3d.setTranslation(trans);
		((TransformGroup)bg.getChild(0)).setTransform(t3d);
	}

	/**
	 * returns the dimension of the solid, depending on which
	 * solid this instance has been initialized with
	 * @return an array of float indicating the dimensions of the solid
	 */
	public Vector3d getDimensions(){
		Vector3d scale = new Vector3d();
		t3d.getScale(scale);
		return scale;
	}

	/**
	 * setsDimension of a solid, using Transform3D.setScale(Vector3d v)
	 * @param bg th BrachGruop of the solid to interact with
	 * @param scale the Vector3d object to apply as a scale
	 */
	public void setDimensions(BranchGroup bg, Vector3d scale){
		t3d.setScale(scale);
		((TransformGroup)bg.getChild(0)).setTransform(t3d);
	}

	/**
	 * 
	 * @return a Color3f containing the AmbientColor of the solid
	 */
	public Color3f getAmbientColor(){
		return ambientColor;
	}

	/**
	 * sets the AmbientColor of the solid to the given color
	 * @param color the new color of the solid
	 */
	public void setAmbientColor(BranchGroup bg, Color3f color){
		ambientColor = color;
		Material material = new Material(ambientColor, emissiveColor, diffuseColor, specularColor, shininess);
		((Primitive) ((TransformGroup)bg.getChild(0)).getChild(0)).getAppearance().setMaterial(material);
	}

	/**
	 * 
	 * @return a Color3f containing the EmissiveColor of the solid
	 */
	public Color3f getEmissiveColor(){
		return emissiveColor;
	}

	/**
	 * sets the EmissiveColor of the solid to the given color
	 * @param color the new color of the solid
	 */
	public void setEmissiveColor(BranchGroup bg, Color3f color){
		emissiveColor = color;
		Material material = new Material(ambientColor, emissiveColor, diffuseColor, specularColor, shininess);
		((Primitive) ((TransformGroup)bg.getChild(0)).getChild(0)).getAppearance().setMaterial(material);
	}

	/**
	 * 
	 * @return a Color3f containing the DiffuseColor of the solid
	 */
	public Color3f getDiffuseColor(){
		return diffuseColor;
	}

	/**
	 * sets the DiffuseColor of the solid to the given color
	 * @param color the new color of the solid
	 */
	public void setDiffuseColor(BranchGroup bg, Color3f color){
		diffuseColor = color;
		Material material = new Material(ambientColor, emissiveColor, diffuseColor, specularColor, shininess);
		((Primitive) ((TransformGroup)bg.getChild(0)).getChild(0)).getAppearance().setMaterial(material);
	}

	/**
	 * 
	 * @returna Color3f containing the SpecularColor of the solid
	 */
	public Color3f getSpecularColor(){
		return specularColor;
	}

	/**
	 * sets the SpecularColor of the solid to the given color
	 * @param color the new color of the solid
	 */
	public void setSpecularColor(BranchGroup bg, Color3f color){
		specularColor = color;
		Material material = new Material(ambientColor, emissiveColor, diffuseColor, specularColor, shininess);
		((Primitive) ((TransformGroup)bg.getChild(0)).getChild(0)).getAppearance().setMaterial(material);
	}

	/**
	 * 
	 * @return the shininess factor of the solid
	 */
	public float getShininess(){
		return shininess;
	}

	/**
	 * sets the shinines factor of the solid to the given one
	 * @param shininess the new value of shininess factor
	 */
	public void setShininess(BranchGroup bg, float shininess){
		this.shininess = shininess;
		Material material = new Material(ambientColor, emissiveColor, diffuseColor, specularColor, shininess);
		((Primitive) ((TransformGroup)bg.getChild(0)).getChild(0)).getAppearance().setMaterial(material);
	}

	/**
	 * 
	 * @return a Colo3f object containing the light color values
	 */
	public Color3f getLightColor(){
		return lightColor;
	}
	/**
	 * sets the light color value to the given one
	 * @param color a Color3f object containing new values for light color
	 */
	public void setLightColor(BranchGroup bg, Color3f color){
		lightColor = color;
		((Light) bg.getChild(0)).setColor(color);
	}

	/**
	 * 
	 * @return a Vector3f containing direction values for the light
	 */
	public Vector3f getLightDirection(){
		return lightDirection;
	}

	/**
	 * sets the direction of a directional light or a spot light to the
	 * specified value
	 * @param direction the new direction
	 */
	public void setLightDirection(BranchGroup bg, Vector3f direction){
		this.lightDirection = direction;

		if(dirLight!=null)
			((DirectionalLight) bg.getChild(0)).setDirection(direction);
		else if (spotLight!=null)
			((SpotLight) bg.getChild(0)).setDirection(direction);
	}

	/**
	 * 
	 * @return a Point3f object indicating the position of a point or a spot light
	 */
	public Point3f getLightPosition(){
		return lightPosition;
	}

	/**
	 * sets the position of a point or spot light to the given value
	 * @param position a Point3f object indicating new position
	 */
	public void setLightPosition(BranchGroup bg, Point3f position){
		this.lightPosition = position;
		if(pointLight!=null){
			((PointLight) bg.getChild(0)).setPosition(position);
		}
		else if(spotLight!=null)
			((SpotLight) bg.getChild(0)).setPosition(position);
	}

	/**
	 * 
	 * @return a Point3f object indicating the Attenuation for this light
	 */
	public Point3f getLightAttenuation(){
		return lightAttenuation;
	}
	/**
	 * sets the Attenuation for a point or spot light to the given value
	 * @param attenuation a Point3f object indicating new attenuation value
	 */
	public void setLightAttenuation(BranchGroup bg, Point3f attenuation){
		this.lightAttenuation = attenuation;
		if(pointLight!=null)
			((PointLight) bg.getChild(0)).setAttenuation(attenuation);
		else if (spotLight!=null)
			((SpotLight) bg.getChild(0)).setAttenuation(attenuation);
	}

	/**
	 * gets the Spread Angle for this SpotLight object
	 * @return
	 */
	public float getLightSpreadAngle(){
		return lightSpreadAngle;
	}

	/**
	 * sets the SpreadAngle for this SpotLight to the given value
	 * @param spreadAngle
	 */
	public void setLightSpreadAngle(BranchGroup bg, float spreadAngle){
		this.lightSpreadAngle = spreadAngle;
		((SpotLight) bg.getChild(0)).setSpreadAngle(spreadAngle);
	}

	/**
	 * gets the Concentration for this SpotLight object
	 * @return
	 */
	public float getLightConcentration(){
		return lightConcentration;
	}

	public void setLightConcentration(BranchGroup bg, float concentration){
		this.lightConcentration = concentration;
		((SpotLight) bg.getChild(0)).setConcentration(concentration);
	}
}