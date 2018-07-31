package com.ybi.whoot.singleton;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import com.ybi.whoot.R;
import com.ybi.whoot.dataobjects.AAngle;
import com.ybi.whoot.dataobjects.AColor;
import com.ybi.whoot.dataobjects.ADecalage;
import com.ybi.whoot.dataobjects.AFont;
import com.ybi.whoot.dataobjects.AImage;
import com.ybi.whoot.dataobjects.APosition;
import com.ybi.whoot.dataobjects.ATaille;
import com.ybi.whoot.dataobjects.ATypo;
import com.ybi.whoot.dataobjects.AVaria;
import com.ybi.whoot.dataobjects.AVariaSemaine;
import com.ybi.whoot.dataobjects.AVariaSeparateur;
import com.ybi.whoot.dataobjects.AVariaTexte;

public class ASingletonCanvas
{
	// variables statiques
	private String[] hm;
	private String[] mm;
	private String[] mn;
	private String[] md;

	private String[] se;
	private String[] ms;
	private String[] jr;

	
	private String[] dec_texte;
	private String[] dec_shadow;
	private String[] taille_texte;
	private String[] taille_shadow;
	private String[] typo;
	private String[] varia_semaine;
	private String[] varia_separateur;
	private String[] varia_texte;
	private String[] font_texte;
	private String[] position_texte ;
	private String[] line_texte ;
	private String[] alignement_texte ;
	
	private String heure;
	private String heures;
	private String etUne;
	
	
	// choix utilisateur
	protected final static String PREFS_NAME = "com.ybi.whoot";

	// temporary lifecycle ?
	private Context context;
	
	private ASettings<AColor> colorGroup;
	private ASettings<ATaille> tailleGroup;
	private ASettings<ADecalage> decalageGroup;
	private ASettings<AVaria> typeGroup;
	private ASettings<ATypo> typoGroup;
	private ASettings<AAngle> angleGroup;
	private ASettings<AFont> fontGroup;
	private ASettings<AImage> imageGroup;
	private ASettings<APosition> positionGroup;
	
	// Private constructor prevents instantiation from other classes
	private ASingletonCanvas()
	{
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class ASingletonCanvasHolder
	{
		public static final ASingletonCanvas INSTANCE = new ASingletonCanvas();
	}

	public static ASingletonCanvas getInstance()
	{
		return ASingletonCanvasHolder.INSTANCE;
	}

	public void initTranslations()
	{
		if (hm == null)
		{
			Log.i("YBI", "Traitement de Game");
			Resources res = context.getResources();
			hm = res.getStringArray(R.array.hm);
			mm = res.getStringArray(R.array.mm);
			mn = res.getStringArray(R.array.mn);
			md = res.getStringArray(R.array.md);
			se = res.getStringArray(R.array.se);
			ms = res.getStringArray(R.array.ms);
			jr = res.getStringArray(R.array.jr);
			
			 dec_texte = res.getStringArray(R.array.dec_texte);
			 dec_shadow = res.getStringArray(R.array.dec_shadow);
			 taille_texte = res.getStringArray(R.array.taille_texte);
			 taille_shadow = res.getStringArray(R.array.taille_shadow);
			 typo = res.getStringArray(R.array.typo);
			 varia_semaine = res.getStringArray(R.array.varia_semaine);
			 varia_separateur = res.getStringArray(R.array.varia_separateur);
			 varia_texte = res.getStringArray(R.array.varia_texte);
			 font_texte = res.getStringArray(R.array.font_texte);
			 position_texte = res.getStringArray(R.array.position_liste);
			 line_texte = res.getStringArray(R.array.line_liste);
			 alignement_texte = res.getStringArray(R.array.alignement_liste);
			 
			 
			 heure = res.getString(R.string.heure);
			 heures = res.getString(R.string.heures);
			 etUne = res.getString(R.string.etune);
		}
	}

	public void initSettings()
	{
		if (colorGroup == null)
		{
			colorGroup = new ASettings<AColor>();
			colorGroup.put("sepColor", new AColor(255, 0, 0, 0));
			colorGroup.put("heuresColor", new AColor(255, 255, 255, 255));
			colorGroup.put("minutesColor", new AColor(255, 255, 255, 255));
			colorGroup.put("shadowColor", new AColor(50, 0, 0, 0));
			colorGroup.put("backgroundColor", new AColor(0, 0, 0, 0));
			colorGroup.put("jourColor", new AColor(255, 0, 0, 0));
			colorGroup.put("semaineColor", new AColor(255, 255, 255, 255));
			colorGroup.put("moisColor", new AColor(255, 255, 255, 255));
		}

		
		if (tailleGroup == null)
		{
			tailleGroup = new ASettings<ATaille>();
			tailleGroup.put("heuresTaille", new ATaille(ATaille.GRAND));
			tailleGroup.put("sepTaille", new ATaille(ATaille.PETIT));
			tailleGroup.put("minutesTaille", new ATaille(ATaille.GRAND));
			tailleGroup.put("shadowTaille", new ATaille(ATaille.DIFFUSE));
			tailleGroup.put("jourTaille", new ATaille(ATaille.PETIT));
			tailleGroup.put("semaineTaille", new ATaille(ATaille.TRESPETIT));
			tailleGroup.put("moisTaille", new ATaille(ATaille.TRESPETIT));
			tailleGroup.put("interligneTaille", new ATaille(ATaille.TRESPETIT));
		}

		
		if (decalageGroup == null)
		{
			decalageGroup = new ASettings<ADecalage>();
			decalageGroup.put("heuresDecalage", new ADecalage());
			decalageGroup.put("sepDecalage", new ADecalage());
			decalageGroup.put("minutesDecalage", new ADecalage());
			decalageGroup.put("shadowDecalage", new ADecalage());
			decalageGroup.put("jourDecalage", new ADecalage());
			decalageGroup.put("semaineDecalage", new ADecalage());
			decalageGroup.put("moisDecalage", new ADecalage());
		}
		
		if (typeGroup == null)
		{
			typeGroup = new ASettings<AVaria>();
			typeGroup.put("heuresType", new AVariaTexte(AVariaTexte.NORMAL));
			typeGroup.put("sepType", new AVariaSeparateur(AVariaSeparateur.NORMAL));
			typeGroup.put("minutesType", new AVariaTexte(AVariaTexte.NORMAL));
			typeGroup.put("jourType", new AVariaTexte(AVariaTexte.NORMAL));
			typeGroup.put("semaineType", new AVariaSemaine(AVariaSemaine.NORMAL));
			typeGroup.put("moisType", new AVariaTexte(AVariaTexte.NORMAL));
		}
		
		if (typoGroup == null)
		{
			typoGroup= new ASettings<ATypo>();
			typoGroup.put("sepTypo", new ATypo());
			typoGroup.put("heuresTypo", new ATypo());
			typoGroup.put("minutesTypo", new ATypo());
			typoGroup.put("jourTypo", new ATypo());
			typoGroup.put("semaineTypo", new ATypo());
			typoGroup.put("moisTypo", new ATypo());
		}
		
		if (angleGroup == null)
		{
			angleGroup= new ASettings<AAngle>();
			angleGroup.put("globalAngle", new AAngle(0,0));
		}
		
		if (fontGroup == null)
		{
			fontGroup = new ASettings<AFont>();
			fontGroup.put("globalFont", new AFont(AFont.DEFAULT));
		}
		
		if (imageGroup == null)
		{
			imageGroup = new ASettings<AImage>();
			imageGroup.put("globalImage", new AImage());
		}
		
		if (positionGroup == null)
		{
			positionGroup = new ASettings<APosition>();
			positionGroup.put("heuresPos", new APosition(APosition.LINE_ONE,0,0));
			positionGroup.put("sepPos", new APosition(APosition.LINE_ONE,0,0));
			positionGroup.put("minutesPos", new APosition(APosition.LINE_ONE,0,0));
			positionGroup.put("jourPos", new APosition(APosition.LINE_TWO,0,0));
			positionGroup.put("semainePos", new APosition(APosition.LINE_TWO,0,0));
			positionGroup.put("moisPos", new APosition(APosition.LINE_TWO,0,0));
			positionGroup.put("backgroundPos", new APosition(APosition.LINE_ONE,0,0));
		}
		
	}

	public void saveSettings()
	{

		// cas de figure tordu
		// au cas ou le telephone redemarre et que la personne a fait un clean avant de l'eteindre
		if (colorGroup == null)
			loadSettings();

		SharedPreferences settings = this.context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		colorGroup.save(editor);
		tailleGroup.save(editor);
		decalageGroup.save(editor);
		typeGroup.save(editor);
		typoGroup.save(editor);
		angleGroup.save(editor);
		fontGroup.save(editor);
		imageGroup.save(editor);
		positionGroup.save(editor);

		editor.commit();

	}

	public void loadSettings()
	{
		// cas de figure tordu
		// au cas ou le telephone redemarre et que la personne a fait un clean avant de l'eteindre
		initSettings();

		colorGroup.load(this.context);
		tailleGroup.load(this.context);
		decalageGroup.load(this.context);
		typeGroup.load(this.context);
		typoGroup.load(this.context);
		angleGroup.load(this.context);
		fontGroup.load(this.context);
		imageGroup.load(this.context);
		positionGroup.load(this.context);
	}


	public Context getContext()
	{
		return context;
	}

	public void setContext(Context context)
	{
		this.context = context;
		
		// la flemme de passer systematiquement le contexte en parametre
		if (this.context !=null)
		{
			if (hm == null)
				initTranslations();
			if (colorGroup == null)
				initSettings();
		}
	}


	public String getHm(int i)
	{
		return hm[i];
	}

	public String getMm(int i)
	{
		return mm[i];
	}

	public String getMn(int i)
	{
		return mn[i];
	}

	public String getMd(int i)
	{
		return md[i];
	}

	public String getSe(int i)
	{
		return se[i];
	}

	public String getMs(int i)
	{
		return ms[i];
	}

	public String getJr(int i)
	{
		return jr[i];
	}


	public String getDec_texte(int i)
	{
		return dec_texte[i];
	}

	public String getDec_shadow(int i)
	{
		return dec_shadow[i];
	}

	public String getTaille_texte(int i)
	{
		return taille_texte[i];
	}

	public String getTaille_shadow(int i)
	{
		return taille_shadow[i];
	}

	public String getTypo(int i)
	{
		return typo[i];
	}

	public String getVaria_semaine(int i)
	{
		return varia_semaine[i];
	}

	public String getVaria_separateur(int i)
	{
		return varia_separateur[i];
	}

	public String getVaria_texte(int i)
	{
		return varia_texte[i];
	}

	public String[] getDec_texte()
	{
		return dec_texte;
	}

	public String[] getDec_shadow()
	{
		return dec_shadow;
	}

	public String[] getTaille_texte()
	{
		return taille_texte;
	}

	public String[] getTaille_shadow()
	{
		return taille_shadow;
	}

	public String[] getTypo()
	{
		return typo;
	}

	public String[] getVaria_semaine()
	{
		return varia_semaine;
	}

	public String[] getVaria_separateur()
	{
		return varia_separateur;
	}

	public String[] getVaria_texte()
	{
		return varia_texte;
	}
	
	public String getFont_texte(int i)
	{
		return font_texte[i];
	}

	public String[] getFont_texte()
	{
		return font_texte;
	}

	
	
	public String getHeure()
	{
		return heure;
	}
	
	public String getHeures()
	{
		return heures;
	}
	public String getEtUne()
	{
		return etUne;
	}
	

	public AColor getColor(String s)
	{
		return (AColor)colorGroup.get(s);
	}

	public ATaille getTaille(String s)
	{
		return (ATaille)tailleGroup.get(s);
	}

	public ADecalage getDecalage(String s)
	{
		return (ADecalage)decalageGroup.get(s);
	}

	public AVaria getType(String s)
	{
		return (AVaria)typeGroup.get(s);
	}

	public ATypo getTypo(String s)
	{
		return (ATypo)typoGroup.get(s);
	}	
	
	public AAngle getAngle()
	{
		return (AAngle)angleGroup.get("globalAngle");
	}

	public AFont getFont()
	{
		return (AFont)fontGroup.get("globalFont");
	}
	
	public AImage getImage()
	{
		return (AImage)imageGroup.get("globalImage");
	}
	
	public APosition getPosition(String s)
	{
		return (APosition)positionGroup.get(s);
	}

	public String[] getPosition_texte()
	{
		return position_texte;
	}

	public String[] getLine_texte()
	{
		return line_texte;
	}

	public String[] getAlignement_texte()
	{
		return alignement_texte;
	}

	public String getPosition_texte(int item)
	{
		return position_texte[item];
	}

	public String getLine_texte(int item)
	{
		return line_texte[item];
	}

	public String getAlignement_texte(int item)
	{
		return alignement_texte[item];
	}


}
