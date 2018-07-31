package code.guicomponents;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import code.core.Game;
import code.core.managers.ImageManager;
import code.core.managers.OptionManager;

public class Background{

	private static final String				ero					= "res/images/backgrounds/ero/ero (%d).jpg";
	private static final String				reg					= "res/images/backgrounds/regular/reg (%d).jpg";

	private static final String				eroResize			= "res/images/backgrounds/ero/ero_resize (%d).jpg";
	private static final String				regResize			= "res/images/backgrounds/regular/reg_resize (%d).jpg";

	private static final ArrayList<Integer>	eroResizeIndexes	= new ArrayList<Integer>(20);
	private static final ArrayList<Integer>	regResizeIndexes	= new ArrayList<Integer>(20);

	private static int						regCount;
	private static int						eroCount;

	private static final Random				rnd					= new Random();

	private static Image					prevImg;
	private static Image					curImg;
	private static Image					nextImg;

	public static int						counter;

	// y-value of current image
	private static float					y;
	// y-value of next image
	private static float					y2;

	// get methods
	private static int getAppropriateCount(){

		return OptionManager.Flags.ERO.isEnabled() ? Background.eroCount : Background.regCount;
	}

	// void methods
	private static void generateNextImg(){

		if(OptionManager.Flags.ERO.isEnabled()){
			Background.counter++;
			if(Background.counter > Background.eroCount){
				Background.counter = 1;
			}
			if(Background.eroResizeIndexes.contains(Background.counter)){
				Background.nextImg = ImageManager.get(String.format(Background.eroResize,
					Background.counter));
			}else{
				Background.nextImg = ImageManager.get(String.format(Background.ero,
					Background.counter));
			}
		}else{
			int ri;
			do{
				// + 1 as to avoid looking for reg (0).jpg as it shouldn't
				// exist
				ri = Background.rnd.nextInt(Background.getAppropriateCount()) + 1;
				if(Background.regResizeIndexes.contains(ri)){
					Background.nextImg = ImageManager.get(String.format(Background.regResize,
						ri));
				}else{
					Background.nextImg = ImageManager.get(String.format(Background.reg, ri));
				}
			}while(Background.nextImg == Background.curImg
				|| Background.nextImg == Background.prevImg);
		}
	}

	public static void init(){

		System.out.println("Background.init(): started");
		Background.prevImg = null;
		Background.curImg = null;
		Background.nextImg = null;

		/*
		 * Adds all ero backgrounds to LoadingList and ImageManager's HashMap
		 * the actual loading can be skipped in the loading screen before the
		 * MainMenu.
		 */
		int i = 1;
		while(true){
			if(new File(String.format(Background.ero, i)).exists()){
				ImageManager.get(String.format(Background.ero, i));
			}else if(new File(String.format(Background.eroResize, i)).exists()){
				ImageManager.addAndResize(String.format(Background.eroResize, i));
				Background.eroResizeIndexes.add(i);
			}else{
				System.out
					.println(String.format(Background.ero, i)
						+ " does not exist (applys to file with _resize suffix as well): eroCount = "
						+ (i - 1));
				Background.eroCount = i - 1;
				break;
			}
			i++;
		}

		/*
		 * Adds all regular backgrounds to LoadingList and ImageManager's
		 * HashMap, the actual loading occurs in the loading screen before the
		 * MainMenu.
		 */
		i = 1;
		while(true){
			if(new File(String.format(Background.reg, i)).exists()){
				ImageManager.get(String.format(Background.reg, i));
			}else if(new File(String.format(Background.regResize, i)).exists()){
				ImageManager.addAndResize(String.format(Background.regResize, i));
				Background.regResizeIndexes.add(i);
			}else{
				System.out
					.println(String.format(Background.reg, i)
						+ " does not exist (applys to file with _resize suffix as well): regCount = "
						+ (i - 1));
				Background.regCount = i - 1;
				break;
			}
			i++;
		}

		System.out.println("Background.init(): finished");
	}

	public static void scroll(final float change){

		Background.y += change;
		Background.y2 += change;

		if(Background.y > Game.HEIGHT){
			Background.prevImg = Background.curImg;
			Background.curImg = Background.nextImg;
			Background.generateNextImg();

			Background.y = -Background.curImg.getHeight() + 640;
			Background.y2 = Background.y - Background.nextImg.getHeight();
		}
	}

	public static void render(final Graphics g){

		if(Background.curImg == null){
			if(OptionManager.Flags.ERO.isEnabled()){
				Background.counter = 1;
				if(Background.eroResizeIndexes.contains(Background.counter)){
					Background.curImg = ImageManager.get(String.format(Background.eroResize,
						Background.counter));
				}else{
					Background.curImg = ImageManager.get(String.format(Background.ero,
						Background.counter));
				}
			}else{
				// + 1 as to avoid looking for reg (0).jpg as it shouldn't
				// exist
				final int ri = Background.rnd.nextInt(Background.getAppropriateCount()) + 1;
				if(Background.regResizeIndexes.contains(ri)){
					Background.curImg = ImageManager.get(String.format(Background.regResize,
						ri));
				}else{
					Background.curImg = ImageManager.get(String.format(Background.reg, ri));
				}
				Background.curImg = ImageManager.get(String.format(
					"res/images/backgrounds/regular/reg (%d).jpg", ri));
			}
			Background.generateNextImg();

			Background.y = -Background.curImg.getHeight() + 640;
			Background.y2 = Background.y - Background.nextImg.getHeight();
		}

		Background.curImg.draw(0, Background.y);
		Background.nextImg.draw(0, Background.y2);
	}
}