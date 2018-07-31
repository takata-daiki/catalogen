package com.mrsamsh.sliderPuzzle.game;

import java.awt.image.BufferedImage;

public class Bitmap implements Renderable {
	protected int width, height;
	protected int pixels[];
	
	protected BufferedImage image;

	public Bitmap(BufferedImage image) {
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
	}
	
	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void render(Renderable renderable, int x, int y) {
		Bitmap canvas = (Bitmap) renderable;
		int cw = canvas.getWidth();
		int ch = canvas.getHeight();
		int x0 = x, x1 = x + width, y0 = y, y1 = y + height;
		int index = 0;
		
		for (int yy = y0; yy < y1; yy++) {
			for (int xx = x0; xx < x1; xx++) {
				if (pixels[index] == 0) {
					index++;
					continue;
				}
				if(xx < 0 || xx >= cw || yy < 0 || yy >= ch) {
					index++;
					continue;
				}
				canvas.pixels[xx + yy * cw] = pixels[index++];
			}
		}
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	@Override
	public void render() {
		
	}
}
