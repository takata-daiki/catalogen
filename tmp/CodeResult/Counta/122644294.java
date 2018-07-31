package surgeroom;

import java.io.File;
import java.io.IOException;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import processing.opengl.*;
import surgeroom.Gesture.Implementation.MultiHistoryMovement;
import surgeroom.Gesture.Interface.MultiMovement;
// import sun.net.www.content.audio.wav;
import wblut.hemesh.core.*;
import wblut.hemesh.creators.*;
import wblut.hemesh.modifiers.*;
import wblut.hemesh.processing.VertexBufferRender;
import wblut.processing.WB_Render;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

import KinectAbstractionFramework.Core.PreparedApplet.SkeletonApplet;

public class ProjectScene extends SkeletonApplet implements Surge {
	Texture texture;
	private GLU glu = new GLU();

	HE_Mesh mesh;

	UserModel user_model_a;
	UserModel user_model_b;

	WB_Render render;
	HEM_WaveDeform wave_modifier;
	HEM_Extrude extrude_modifier;
	static final int resolution = Parameters.cageMeshResolution;

	double[] water_map_a;
	double[] water_map_b;
	double[] diff_map;

	private int zoom = Parameters.cameraZoom;
	private PFont font;

	private int oldWidth;

	private PGraphicsOpenGL pgl;
	private GL gl;

	private UserPosition userA;
	private UserPosition userB;

	private MultiMovement userAMovement;
	private MultiMovement userBMovement;

	// wall collision detection stuff
	private static final double minSurgeHeightOfCollision = Parameters.minSurgeHeightOfCollision;
	private static final int countMax = Parameters.countMax;

	private int countA = 0;
	private int countB = 0;

	private Minim minim = new Minim(this);
	private AudioPlayer backgroundMusicLoop;

	private AudioPlayer waveStartSoundA;
	private AudioPlayer stampingSoundA;
	private AudioPlayer waveCollisionSoundA;
	private AudioPlayer endSoundPathA;

	private AudioPlayer waveStartSoundB;
	private AudioPlayer stampingSoundB;
	private AudioPlayer waveCollisionSoundB;
	private AudioPlayer endSoundPathB;

	private Texture[] gameStateTextures = new Texture[100];

	public ProjectScene() {
		super("SurgeRoom", Parameters.simulationFile, Parameters.useRealKinect,
				UNDECORATED);
		// GamePlayZweiPersonen1 einePersonStampft3
	}

	AudioPlayer loadSound(String p) {
		if (!p.isEmpty()) {
			System.out.println("loading: " + p);
			return minim.loadFile(p);
		}
		System.out.println("Empty soundPath!");
		return null;
	}

	void playSound(AudioPlayer a) {
		if (a == null)
			return;
		a.rewind();
		a.play();
	}

	void playSoundLoop(AudioPlayer a) {
		if (a == null)
			return;
		playSound(a);
		a.loop();
	}

	@Override
	public void functionalSetup() {
		try {
			Thread.sleep(1000 * 0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long startSetupTime = System.currentTimeMillis();
		System.out.printf("Start setup: %d\n", startSetupTime);

		backgroundMusicLoop = loadSound(Parameters.backgroundMusicLoopPath);
		playSoundLoop(backgroundMusicLoop);

		waveStartSoundA = loadSound(Parameters.waveStartSoundPath);
		stampingSoundA = loadSound(Parameters.stampingSoundPath);
		waveCollisionSoundA = loadSound(Parameters.waveCollisionSoundPath);
		endSoundPathA = loadSound(Parameters.endSoundPath);

		waveStartSoundB = loadSound(Parameters.waveStartSoundPath);
		stampingSoundB = loadSound(Parameters.stampingSoundPath);
		waveCollisionSoundB = loadSound(Parameters.waveCollisionSoundPath);
		endSoundPathB = loadSound(Parameters.endSoundPath);

		userAMovement = MultiHistoryMovement.valueOf(15, kinect()
				.groundPositionOne());
		userBMovement = MultiHistoryMovement.valueOf(15, kinect()
				.groundPositionTwo());

		// size(1040, 600, OPENGL);
		// hint(ENABLE_OPENGL_4X_SMOOTH);
		hint(DISABLE_OPENGL_ERROR_REPORT);
		hint(ENABLE_NATIVE_FONTS);
		kinect().setMirror(true);
		pgl = (PGraphicsOpenGL) this.g;
		gl = pgl.gl;
		// smooth();
		// width, height, depth

		final boolean VBOsupported = gl.isFunctionAvailable("glGenBuffersARB")
				&& gl.isFunctionAvailable("glBindBufferARB")
				&& gl.isFunctionAvailable("glBufferDataARB")
				&& gl.isFunctionAvailable("glDeleteBuffersARB");

		System.out.println("VertexBufferObject supported: " + VBOsupported);

		HEC_Creator creator = new HEC_Cage(Parameters.cageWidth,
				Parameters.cageHeight, Parameters.cageDepth, 2 * resolution,
				resolution, 2 * resolution);

		long meshGenerateStartTime = System.currentTimeMillis();
		mesh = new HE_Mesh(creator);

		System.out.printf("Duration mesh generate: %d ms\n",
				System.currentTimeMillis() - meshGenerateStartTime);

		meshFaces = mesh.getFacesAsArray().length;

		wave_modifier = new HEM_WaveDeform(this, resolution * 6,
				1 + 2 * resolution);
		wave_modifier.setDistance(1);

		water_map_a = new double[resolution * 6 * (1 + 2 * resolution)];
		water_map_b = new double[resolution * 6 * (1 + 2 * resolution)];
		diff_map = new double[resolution * 6 * (1 + 2 * resolution)];

		for (int n = 0; n < water_map_a.length; n++) {
			water_map_a[n] = 0;
			water_map_b[n] = 0;
		}

		for (String name : PFont.list()) {
			// System.out.printf("Available Font: %s\n", name);
		}
		font = createFont("Skia-Regular", 15, false);
		render = new VertexBufferRender(this, meshFaces);

		user_model_a = new UserModel(render, new PVector(0, -1500, -3500));
		user_model_a.setColor(Parameters.particleColorUserA);
		user_model_b = new UserModel(render, new PVector(0, -1500, -3500));
		user_model_b.setColor(Parameters.particleColorUserB);

		frameRate(Parameters.maximalFramerate);
		System.out.printf("End setup: %d\n", System.currentTimeMillis());
		System.out.printf("Duration setup: %d ms\n", System.currentTimeMillis()
				- startSetupTime);

		oldWidth = width();

		userA = new UserPosition(600, 600);
		userA.setRealXRange(-700, 900);
		userA.setRealZRange(1400, 3900);

		userB = new UserPosition(600, 600);
		userB.setRealXRange(-500, 900);
		userB.setRealZRange(1400, 3900);

		loadTexture();
		try {
			loadGameInfoTextures();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// fullscreen();
	}

	int meshFaces;

	private int frame = 0;
	private boolean current_map = false;
	private boolean ownRendering = true;

	double meshCalculationTime = 0;
	double meshRenderingTime = 0;
	double particleCalculationTime = 0;
	double particleDrawingTime = 0;
	double setSkeletonTime = 0;

	private double[] map_now;
	private double[] map_before;

	HE_Face[] faces;
	HE_Vertex[][] tmpVertices;

	/**
	 * 11-14 := game_over 1
	 * 0 - 1 := count down 
	 * 0 := game waiting for players 
	 * -1 := game running
	 */
	int game_state = 4;
	int dead_seconds = 0;
	long last_tick = 0;

	@Override
	public void postSkeletonDraw() {
		if (last_tick == 0 || System.currentTimeMillis() - last_tick >= 1000) {
			last_tick = System.currentTimeMillis();
			if (game_state > 0) {
				if (game_state > 3 || (kinect().isTrackingSkeleton(kinect().realUserTwoId()) && kinect().isTrackingSkeleton(kinect().realUserOneId()))) {
					game_state--;
				}
			}

			if (game_state == -1 && (user_model_a.isDead() || user_model_b.isDead())) {
				dead_seconds++;

				if (dead_seconds > 3) {
					dead_seconds = 0;
					user_model_a.reset();
					user_model_b.reset();
					game_state = 4;
				}
			}
		}

		if (frame == 0) {
			System.out.printf("First draw: %d\n", System.currentTimeMillis());
		}
		if (oldWidth != width()) {
			oldWidth = width();
			font = createFont(Parameters.screenFont, 15, true);
		}
		frame = frame + 1;
		background(Parameters.backgroundColor.R, Parameters.backgroundColor.G,
				Parameters.backgroundColor.B);
		lights();
		stroke(0, 0, 0);
		fill(0, 0, 0, 200);

		if (keyPressed) {
			if (key == '+') {
				zoom = zoom + 5;
			} else if (key == '-') {
				zoom = zoom - 5;
			} else if (key == 'r') {
				ownRendering = !ownRendering;
				if (ownRendering) {
					render = new VertexBufferRender(this, meshFaces);
				} else {
					render = new WB_Render(this);
				}
			} else if (key == 'b') {
				user_model_a.blowUp(UserModel.Target.All, 0.1);
			} else if (key == 'n') {
				user_model_a.blowUp(UserModel.Target.All, -0.1);
			} else if (key == 'h') {
				// stretch arms
				user_model_a.expand(UserModel.Target.LeftArm, 0.1);
			} else if (key == 'j') {
				// stretch arms
				user_model_a.expand(UserModel.Target.LeftArm, -0.1);
			} else if (key == 'u') {
				// stretch arms
				user_model_a.addDeathRate(0.0001);
			} else if (key == 'i') {
				// stretch arms
				user_model_a.addDeathRate(-0.0001);
			} else if (key == 'y') {
				user_model_a.startEndPhase();
				user_model_b.startEndPhase();
			} else if (key == 'm') {
				user_model_a.reset();
				user_model_b.reset();
			}
		}

		translate(width() / 2, height() / 2, 120 + zoom);

		if (Parameters.enableMouseRotation) {
			rotateY(mouseX * 1.0f / width() * TWO_PI);
			rotateX(mouseY * 1.0f / height() * TWO_PI);
		} else {
			rotateY(Parameters.cameraRotateY);
			rotateX(Parameters.cameraRotateX);
		}

		double damping = 0.03;// 0.01;

		if (current_map) {
			// a is current map
			map_now = water_map_a;
			map_before = water_map_b;
		} else {
			// b is current map
			map_now = water_map_b;
			map_before = water_map_a;
		}

		if (game_state == 0 && kinect().isTrackingSkeleton(kinect().realUserTwoId())
				&& kinect().isTrackingSkeleton(kinect().realUserOneId())) {
			game_state = -1;
		}

		if (kinect().isTrackingSkeleton(kinect().realUserOneId())) {
			long startSetSkeleton = System.currentTimeMillis();
			user_model_a.setSkeleton(kinect().skeletonOne());
			setSkeletonTime += System.currentTimeMillis() - startSetSkeleton;

			if (game_state == -1) {
				double collA = checkCollisionA(map_now);
				if (collA > 2) {
					playSound(waveCollisionSoundA);
					// System.out.printf("Wall collision player 1. ( %f )\n",
					// collA );
					if (kinect().isTrackingSkeleton(kinect().realUserTwoId())) {
						user_model_a.expand(UserModel.Target.LeftArm,
								Parameters.armEnlargmentFactor * collA);
						user_model_a.expand(UserModel.Target.RightArm,
								Parameters.armEnlargmentFactor * collA);
						// user_model_a.blowUp(UserModel.Target.All, collA /
						// 400);

						user_model_a.checkGameOver();
					}
				}
			}
		}

		if (kinect().isTrackingSkeleton(kinect().realUserTwoId())) {
			long startSetSkeleton = System.currentTimeMillis();
			user_model_b.setSkeleton(kinect().skeletonTwo());
			setSkeletonTime += System.currentTimeMillis() - startSetSkeleton;

			if (game_state == -1) {
				double collB = checkCollisionB(map_now);
				if (collB > 2) {
					playSound(waveCollisionSoundB);
					// System.out.printf("Wall collision player 2. ( %f )\n",
					// collB);
					if (kinect().isTrackingSkeleton(kinect().realUserOneId())) {
						user_model_b.expand(UserModel.Target.LeftArm,
								Parameters.armEnlargmentFactor * collB);
						user_model_b.expand(UserModel.Target.RightArm,
								Parameters.armEnlargmentFactor * collB);
						// user_model_b.blowUp(UserModel.Target.All, collB /
						// 400);

						user_model_b.checkGameOver();
					}
				}
			}
		}

		// user_model_a.moveTo(user_x, user_h, user_y);

		// wave_modifier.setMouse(user_x, user_y);

		// if (frame % 1 == 0) {

		// based on http://freespace.virgin.net/hugo.elias/graphics/x_water.htm

		int mesh_x = -1;
		int mesh_y  = -1;

		// detect stamping
		if (game_state == -1) {
			manageMovement();
		}

		long startMeshCalculation = System.currentTimeMillis();
		for (int x = 0; x < resolution * 6; x++) {
			for (int y = 0; y < 2 * resolution; y++) {
				int i = y * resolution * 6 + x;
				int left = (map_now.length + (i - 1)) % map_now.length;
				int right = i + 1;
				int top = i + (resolution * 6) % map_now.length;
				int buttom = (map_now.length + (i - (resolution * 6)))
						% map_now.length;
				map_now[i] = (map_before[left] + map_before[right]
						+ map_before[buttom] + map_before[top])
						/ 2 - map_now[i];

				map_now[i] -= map_now[i] * damping;

				diff_map[i] = map_now[i] - map_before[i];
			}
		}

		wave_modifier.setHeightMap(diff_map);
		wave_modifier.apply(mesh);

		current_map = !current_map;
		meshCalculationTime += System.currentTimeMillis()
				- startMeshCalculation;

		// }

		// current_pos.moveTo(wave_modifier.pos.get());

		stroke(0, 0, 0);
		fill(0, 0, 0, 50);

		// render.drawEdges(user_model_a);

		long startMeshDrawing = System.currentTimeMillis();
		gl.glColor3f(Parameters.backgroundColor.Rf(),
				Parameters.backgroundColor.Gf(),
				Parameters.backgroundColor.Bf());
		render.reset(false);
		render.drawFaces(mesh);
		render.finish();

		gl.glColor3f(Parameters.cageColor.Rf(), Parameters.cageColor.Gf(),
				Parameters.cageColor.Bf());
		render.reset(true);
		render.drawFaces(mesh);
		render.finish();
		long stopMeshDrawing = System.currentTimeMillis();

		meshRenderingTime += (stopMeshDrawing - startMeshDrawing);

		// System.out.println("Mesh rendering time: " + (stopMeshDrawing -
		// startMeshDrawing) + " ms");
		// render.reset(false);
		if (kinect().isTrackingSkeleton(kinect().realUserOneId())) {
			long startTime = System.currentTimeMillis();
			user_model_a.calcParticles();
			particleCalculationTime += (System.currentTimeMillis() - startTime);

			startTime = System.currentTimeMillis();
			user_model_a.draw();
			particleDrawingTime += (System.currentTimeMillis() - startTime);
		} else {
			showStartScene(100, 150, new PVector(85, -145));
		}

		if (kinect().isTrackingSkeleton(kinect().realUserTwoId())) {
			long startTime = System.currentTimeMillis();
			user_model_b.calcParticles();
			particleCalculationTime += (System.currentTimeMillis() - startTime);

			startTime = System.currentTimeMillis();
			user_model_b.draw();
			particleDrawingTime += (System.currentTimeMillis() - startTime);
		} else {
			showStartScene(100, 150, new PVector(-185, -145));
		}

		if (game_state > 0) {
			drawGameInfo();
		}

		if (frame % 300 == 0) {
			System.out
					.printf("Frame: %d, setSkeleton: %f, Grid calculation: %f ms, Grid rendering: %f ms, Particle calculation: %f ms, Particle drawing: %f ms\n",
							frame, setSkeletonTime / frame, meshCalculationTime
									/ frame, meshRenderingTime / frame,
							particleCalculationTime / frame,
							particleDrawingTime / frame);
		}

		if (Parameters.debugScreenInfo) {
			stroke(255, 0, 0);
			line(0, 0, 0, 1000, 0, 0);

			stroke(0, 255, 0);
			line(0, 0, 0, 0, 1000, 0);

			stroke(0, 0, 255);
			line(0, 0, 0, 0, 0, 1000);

			// camera(); //reset camera
			// perspective(); // reset perspective

			// textMode(SCREEN);
			stroke(Parameters.textColor.R, Parameters.textColor.G,
					Parameters.textColor.B, 150);
			fill(Parameters.textColor.R, Parameters.textColor.G,
					Parameters.textColor.B, 150);
			rotateY(-TWO_PI);
			rotateX(-TWO_PI / 2);

			textFont(font);
			String txtOut = "FPS: " + (int) frameRate;
			text(txtOut, 0, 0);
		} else {

			stroke(Parameters.textColor.R, Parameters.textColor.G,
					Parameters.textColor.B, 0);
			fill(Parameters.textColor.R, Parameters.textColor.G,
					Parameters.textColor.B, 0);

			textFont(font);
			text("FOO", 0, 0);
		}
	}

	final private float gameinfo_z = -100;
	final private float gameinfo_size = 100;

	private void loadGameInfoTextures() throws GLException, IOException {
		for (int n = 0; n < 3; n++) {
			String filename = (n + 1) + ".png";
			gameStateTextures[n] = TextureIO.newTexture(
					ImageIO.read(new File("texture/countdown/" + filename)),
					true);
		}
		for (int n = 3; n <= 14; n++) {
			gameStateTextures[n] = TextureIO.newTexture(
				ImageIO.read(new File("texture/countdown/gameover.png")), true);
		}
		for (int n = 0; n < 15; n++) {
			Texture t = gameStateTextures[n];
			t.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
			t.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
			t.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			t.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER,
					GL.GL_LINEAR_MIPMAP_LINEAR);
			t.bind();
		}
	}

	private void drawGameInfo() {

		// Really basic and most common alpha blend function
		gl.glEnable(GL.GL_BLEND);
		gl.glEnable(GL.GL_TEXTURE_2D);

		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

		gameStateTextures[game_state - 1].bind();

		gl = pgl.beginGL();
		gl.glBegin(GL.GL_QUADS);

		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-gameinfo_size, gameinfo_size, gameinfo_z);

		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(gameinfo_size, gameinfo_size, gameinfo_z);

		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(gameinfo_size, -gameinfo_size, gameinfo_z);

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-gameinfo_size, -gameinfo_size, gameinfo_z);

		gl.glEnd();
		pgl.endGL();

		gl.glDisable(GL.GL_TEXTURE_2D);

	}

	private void loadTexture() {
		try {
			texture = TextureIO.newTexture(
					ImageIO.read(new File("texture/Man2.png")), true);
			texture.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
			texture.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
			texture.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			texture.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER,
					GL.GL_LINEAR_MIPMAP_LINEAR);
			texture.bind();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showStartScene(int width, int height, PVector start) {
		gl.glEnable(GL.GL_BLEND);
		gl.glEnable(GL.GL_TEXTURE_2D);

		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

		texture.bind();

		gl = pgl.beginGL();
		gl.glBegin(GL.GL_QUADS);

		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(start.x, start.y + height, 0);

		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(start.x + width, start.y + height, 0);

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(start.x + width, start.y, 0);

		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(start.x, start.y, 0);

		gl.glEnd();
		pgl.endGL();

		gl.glDisable(GL.GL_TEXTURE_2D);
	}

	private void manageMovement() {
		if (kinect().isTrackingSkeleton(kinect().realUserOneId())
				&& user_model_a.getSkeleton().id() > 20) {
			userAMovement.add(user_model_a.getSkeleton());
		}
		if (kinect().isTrackingSkeleton(kinect().realUserTwoId())
				&& user_model_b.getSkeleton().id() > 20) {
			userBMovement.add(user_model_b.getSkeleton());
		}
		float l;
		l = userAMovement.footStampingIntensity();
		if (l > Parameters.stampingDetectionThreshold) {
			onStamping(kinect().realUserOneId(), l);
			onGroundContact(kinect().realUserOneId());
		}

		l = userBMovement.footStampingIntensity();
		if (l > Parameters.stampingDetectionThreshold) {
			onStamping(kinect().realUserTwoId(), l);
			onGroundContact(kinect().realUserTwoId());
		}

	}

	static public void main(String[] args) {
		System.out.println("Program start");
		new ProjectScene();
	}

	@Override
	public void preSkeletonDraw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawIfTrackingSkeleton(int userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onOtherSkeletonContact(int targetUserId) {
		// TODO Auto-generated method stub

	}

	public void generateWave(UserModel model, double[] heightMap,
			double intesity) {
		int x = model.getHeightMapX();
		int y = model.getHeightMapY();

		// System.out.append("x: " + x + ", y: " + y );
		if (y * resolution * 6 + x < heightMap.length) {
			// map_now[y * resolution * 6 + x] += 100 + sin((float) frame / 4f)
			// * 130;
			heightMap[y * resolution * 6 + x] = intesity
					* Parameters.waveAplification;
			// ----wave = new Wave(x, y, 0.1, 5f);
		}
	}

	@Override
	public void onGroundContact(int userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStamping(int userId, float intensity) {
		if (userId == kinect().realUserOneId()) {
			playSound(stampingSoundA);
			playSound(waveStartSoundA);
			countA = countMax;
			System.out.println("stamping for user A");
			generateWave(user_model_a, map_now, intensity);
		}
		if (userId == kinect().realUserTwoId()) {
			playSound(stampingSoundB);
			playSound(waveStartSoundB);
			countB = countMax;
			System.out.println("stamping for user B");
			generateWave(user_model_b, map_now, intensity);
		}
	}

	private double checkCollisionA(double[] map) {
		int x = user_model_a.getHeightMapX();
		int y = user_model_a.getHeightMapY();

		if (y * resolution * 6 + x < map.length) {
			double surgeHeight = map[y * resolution * 6 + x];

			if (countA > 0) {
				System.out.println("countA " + countA);
				// countA -= 1;
				// return 0;
				if (surgeHeight > minSurgeHeightOfCollision / 2) {
					countA -= 1;
					return 0;
				} else {
					countA = 0;
				}
			} else if (surgeHeight > minSurgeHeightOfCollision
					&& !userAMovement.wasStamped()) {
				System.out.println("collision: " + surgeHeight);
				return surgeHeight;
			}
		}
		return 0;
	}

	private double checkCollisionB(double[] map) {

		int x = user_model_b.getHeightMapX();
		int y = user_model_b.getHeightMapY();

		if (y * resolution * 6 + x < map.length) {
			double surgeHeight = map[y * resolution * 6 + x];

			if (countB > 0) {
				System.out.println("countB " + countB);
				// countB -= 1;
				// return 0;
				if (surgeHeight > minSurgeHeightOfCollision / 2) {
					countB -= 1;
					return 0;
				} else {
					countB = 0;
				}
			} else if (surgeHeight > minSurgeHeightOfCollision
					&& !userBMovement.wasStamped()) {

				return surgeHeight;
			}
			// Abstand zum Boden prfen
		}
		return 0;
	}
	
	final public void draw() {
		// update the cam
		kinect().update();

		// if you want do some settings before skeleton is drawn
		preSkeletonDraw();

		kinect().saveSkeleton();
		if (kinect().isTrackingSkeleton(kinect().realUserOneId())) {
			drawIfTrackingSkeleton(kinect().realUserOneId());
		}
		if (kinect().isTrackingSkeleton(kinect().realUserTwoId())) {
			drawIfTrackingSkeleton(kinect().realUserTwoId());
		}

		// if you want do some settings after skeleton is drawn
		postSkeletonDraw();

	}
	
	public void onEndCalibration(int userId, boolean successfull) {
		println("onEndCalibration - userId: " + userId + ", successfull: "
				+ successfull);
		if (successfull) {
			println("  User calibrated");
			kinect().saveCalibrationDataSkeleton(userId,
					userskeletonClibrationPath + userId);
			kinect().startTrackingSkeleton(userId);
			if (kinect().realUserOneId() == userId || kinect().realUserTwoId() == userId)
				return;
			if (!kinect().isUserOneIdSetted()){
				kinect().setRealUserOneId(userId);
				println("onNewUser ->> a - realUserId: " + userId);
				return;
			}
			if (!kinect().isUserTwoIdSetted()){
				kinect().setRealUserTwoId(userId);
				println("onNewUser ->> b - realUserId: " + userId);
				return;
			}
		} else {
			println("  Failed to calibrate user");
			println("  Start pose detection");
			kinect().startPoseDetection("Psi", userId);
			onLostUser(userId);			
		}
	}
	
	public void onNewUser(int userId) {
		if (!kinect().isUserOneIdSetted()){
//			kinect().setRealUserOneId(userId);
			println("onNewUser -> a - realUserId: " + userId);
			println("  start pose detection");
			kinect().startPoseDetection("Psi", userId);
			return;
		}
		if (!kinect().isUserTwoIdSetted()){
//			kinect().setRealUserTwoId(userId);
			println("onNewUser -> b - realUserId: " + userId);
			println("  start pose detection");
			kinect().startPoseDetection("Psi", userId);
			return;
		}
		System.out.println("No free place for new user");
	}
	
	public void onLostUser(int userId) {
		if (userId == kinect().realUserOneId()){
			kinect().setRealUserOneId(0);
			println("userModel a lost user: " + userId);
//			dead_seconds = 0;
//			user_model_a.reset();
//			user_model_b.reset();
//			game_state = 3;
		}
		else if (userId == kinect().realUserTwoId()){
	
			kinect().setRealUserTwoId(0);
			println("userModel b lost user: " + userId);
//			dead_seconds = 0;
//			user_model_a.reset();
//			user_model_b.reset();
//			game_state = 3;

		}
		else
			println("onLostUser without place - userId: " + userId);
	}

}