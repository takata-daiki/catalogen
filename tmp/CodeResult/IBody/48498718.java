/*
 *
 * (c)2010 Lein-Mathisen Digital
 * http://lmdig.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.  
 *
 */


package com.lmdig.android.tutorial.oglbox2dbasics.game;

import static android.opengl.GLES10.glColor4f;
import static android.opengl.GLES10.glEnable;
import static android.opengl.GLES10.GL_MODULATE;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;

import android.util.Log;

import com.kristianlm.robotanks.box2dbridge.Box2DFactory;
import com.kristianlm.robotanks.box2dbridge.IBody;
import com.kristianlm.robotanks.box2dbridge.IWorld;
import com.kristianlm.robotanks.box2dbridge.jbox2d.JBox2DWorld;
import com.kristianlm.robotanks.box2dbridge.jnibox2d.JNIBox2DWorld;
import com.lmdig.android.tutorial.oglbox2dbasics.MainActivity;
import com.lmdig.android.tutorial.oglbox2dbasics.geometry.GLCircle;
import com.lmdig.android.tutorial.oglbox2dbasics.geometry.GLRectangle;
import com.lmdig.android.tutorial.oglbox2dbasics.geometry.GameShape;



public class GameEngine implements IGame {

	
	private static final float TIME_STEP = 1f / 40f;
	private static final int   ITERATIONS = 1;
	
	
	IWorld world = Box2DFactory.newWorld();

	List<GameShape> gsl = new ArrayList<GameShape>();
	List<Joint> jl = new ArrayList<Joint>();

	public void init() {

		// density of dynamic bodies
		float density = 1;
		
		// create world's bounding box. 
		// if objects exceed these borders, they will no longer be
		// animated (body dies). limits imposed for performance reasons.
		AABB aabb = new AABB(	
						new Vec2(-50, -50), 
						new Vec2( 50,  50)
					);
		
		Vec2 gravity = new Vec2(MainActivity.x, MainActivity.y); 

		world.create(
				aabb,
				gravity,
				true);
		
		GameShape gs;
		Joint j;
		
		// Long
		gs = GameShape.create(new GLRectangle(3, 0.5f));
		IBody b1 = gs.attachToNewBody(world, null, density);
		b1.setPosition(new Vec2(0, 2));
		gsl.add(gs);
		
		// Short
		gs = GameShape.create(new GLRectangle(1, 0.5f));
		IBody b2 = gs.attachToNewBody(world, null, density);
		b2.setPosition(new Vec2(-2,2));
		gsl.add(gs);
		
		// Joint them with a Revolute Joint
		// http://www.toolingu.com/definition-470140-101752-revolute-joint.html
		// (Good info, but different (lower level) API calls: 
		//         http://www.iforce2d.net/b2dtut/joints-revolute)
		j = (Joint)world.createRevoluteJoint(b1, b2, -1.5f, 2f);
		jl.add(j);
		
		// An L-shaped ojbect
		// http://www.raywenderlich.com/forums/viewtopic.php?f=2&t=1040
		
		// Objects NOT Bouncing of each other
		// http://www.box2d.org/forum/viewtopic.php?f=8&t=6570
		
		// Circle
		gs = GameShape.create(new GLCircle(0.5f));
		IBody b3 = gs.attachToNewBody(world, null, density);
		b2.setPosition(new Vec2(0,-1));
		gsl.add(gs);
		
		makeFence();
	}
	
	private void makeFence() {
		IBody ground = world.getGroundBody();
		
		// static bodies are defined as those having mass and intertia 0
		// this ensures they are never moved. they only affect positions of
		// other dynamic bodies who collide with them.
		float density = 0;
		GameShape gs;
		gs = GameShape.create(new GLRectangle(50, .1f));
		gs.attachToBody(ground, new Vec2(0, -4), density);
		gsl.add(gs);
		
		gs = GameShape.create(new GLRectangle(50, .1f));
		gs.attachToBody(ground, new Vec2(0, 4), density);
		gsl.add(gs);
		
		gs = GameShape.create(new GLRectangle(.1f, 50f));
		gs.attachToBody(ground, new Vec2(3, 0), density);
		gsl.add(gs);
		
		gs = GameShape.create(new GLRectangle(.1f, 50f));
		gs.attachToBody(ground, new Vec2(-3, 0), density);
		gsl.add(gs);
		
		// A little something in the middle
		gs = GameShape.create(new GLRectangle(.5f, .1f));
		gs.attachToBody(ground, new Vec2(-.75f, -.5f), density);
		gsl.add(gs);
		
		
	}

	public void destroy() {
		// in case we are using JNIBox2D, this
		// is very important! otherwise we end up with memory leaks.
		// world.destroy will recursively destroy all its attached content
		world.destroy();
	}
	
	
	@Override
	public void drawFrame() {

		glEnable(GL_MODULATE);
		glColor4f(1, 0, 0, 0.5f);
		
		for(GameShape gs : gsl) {
			gs.draw();
		}
	}
	
	long nanoTime;
	float fps;
	int frames;
	float gravX, gravY;

	@Override
	public void gameLoop() {
		if(world == null) {
			Log.e("pg", "World not initialized");
			return;
		}
		frames++;
		long elap = System.currentTimeMillis() - nanoTime;
		if(elap > 1000) {
			// update info every second
			fps = frames / ((float)elap / 1000f);
			nanoTime = System.currentTimeMillis();
			frames = 0;
		}
		
		// Do not automatically change gravity
		// In Martin's emulator: 
		setGravity(); // Sensor in MainAcitivity updates regularly
		
		// Present info:
		String engine = (world instanceof JBox2DWorld ? "JBox2D" : world instanceof JNIBox2DWorld ? "JNIBox2D": "unknown");
		MainActivity.setStatus(engine + ", fps: " + String.format("%.3f", fps) + "  G(x,y) = G(" + String.format("%.3f", gravX) + "," + String.format("%.3f", gravY)	+ ")");

		world.step(TIME_STEP, ITERATIONS);
		world.sync();
	}
	
	public void setGravity() {

		if(world instanceof JBox2DWorld) {
//			Log.d("pg", "gravity seet to " + MainActivity.x + ", " + MainActivity.y);
			JBox2DWorld jw = ((JBox2DWorld)world);
			World w = jw.getWorld();
			gravX = MainActivity.x;
			gravY = MainActivity.y;
			w.setGravity(new Vec2(gravX, gravY));
		}

	}

}
