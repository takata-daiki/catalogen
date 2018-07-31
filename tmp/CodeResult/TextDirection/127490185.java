/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.helloworld;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HelloWorld implements ApplicationListener {
	private OrthographicCamera camera;
	SpriteBatch spriteBatch;
	Texture texture;
	BitmapFont font;
	Vector2 textPosition = new Vector2(100, 100);
	Vector2 textDirection = new Vector2(1, 1);
	float sizeCoef = 1;	
	final int baseWidth = 480;
	final int baseHeight = 320;

	@Override
	public void create () {
		font = new BitmapFont();
		font.setColor(Color.RED);
		texture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
		spriteBatch = new SpriteBatch();
	}

	@Override
	public void render () {
		int centerX = Gdx.graphics.getWidth() / 2;
		int centerY = Gdx.graphics.getHeight() / 2;

		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);

		// more fun but confusing :)
		// textPosition.add(textDirection.tmp().mul(Gdx.graphics.getDeltaTime()).mul(60));
		textPosition.x += textDirection.x * Gdx.graphics.getDeltaTime() * 60;
		textPosition.y += textDirection.y * Gdx.graphics.getDeltaTime() * 60;

		if (textPosition.x < 0) {
			textDirection.x = -textDirection.x;
			textPosition.x = 0;
		}
		if (textPosition.x > Gdx.graphics.getWidth()) {
			textDirection.x = -textDirection.x;
			textPosition.x = Gdx.graphics.getWidth();
		}
		if (textPosition.y < 0) {
			textDirection.y = -textDirection.y;
			textPosition.y = 0;
		}
		if (textPosition.y > Gdx.graphics.getHeight()) {
			textDirection.y = -textDirection.y;
			textPosition.y = Gdx.graphics.getHeight();
		}

		spriteBatch.begin();
		spriteBatch.setColor(Color.WHITE);
		
		spriteBatch.draw(texture, 0f, 0f, 480 * sizeCoef, 320 * sizeCoef, 0, 0,
	            texture.getWidth(), texture.getHeight(), false, false);
		
		font.draw(spriteBatch, "Hello World!", (int)textPosition.x, (int)textPosition.y);
		spriteBatch.end();
	}

	@Override
	public void resize (int width, int height) {
		float widthCoef = (float) width / (float) baseWidth;
		float heightCoef = (float) height / (float) baseHeight;
		
		if (widthCoef < heightCoef) {
			sizeCoef = widthCoef;
		} else {
			sizeCoef = heightCoef;
		}
		
		float aspectRatio = (float) width / (float) height;
        if (height > width) {
        	aspectRatio = (float) height / (float) width;
        	camera = new OrthographicCamera(width, height * aspectRatio);
        } else {
        	camera = new OrthographicCamera(width * aspectRatio, height);
        }
        
//        Gdx.gl10.glOrthof(-1f, 1f, -1f, 1f, 1f, -1f);
//        Gdx.gl10.glViewport(10, 10, 200, 200);
         
//        Gdx.gl10.glMatrixMode(Gdx.graphics.getGL10().GL_PROJECTION);
//        Gdx.gl10.glLoadIdentity();
//        Gdx.gl10.glFrustumf(-2f, 2f, -2f, 2f, 2f, 20f);
//        Gdx.gl10.glMatrixMode(Gdx.graphics.getGL10().GL_MODELVIEW);
        
        
        
//        camera.update();
//		spriteBatch.setProjectionMatrix(camera.projection);
		
		textPosition.set(0, 0);
	}

	@Override
	public void pause () {

	}

	@Override
	public void resume () {

	}

	@Override
	public void dispose () {

	}

}
