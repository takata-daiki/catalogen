/*
 * Copyright (C)  2011  Álvaro Tanarro Santamaría.
 * Permission is granted to copy, distribute and/or modify this document
 * under the terms of the GNU Free Documentation License, Version 1.3
 * or any later version published by the Free Software Foundation;
 * with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 * A copy of the license is included in the section entitled "GNU
 * Free Documentation License".
 */
package tanarro.pfc.demonstrator.view.NodesARViewOverlays;

import tanarro.pfc.demonstrator.utils.ARUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.view.View;

public class DrawTextBox extends View {
	private static final float MARGIN = 2.5f;
	private float text_size;
	private float[] center = { 0, 0 };
	private String text;

	public DrawTextBox(Context context, double x, double y) {
		super(context);

		text_size = ARUtils.transformPixInDip(context, 8);
		setCenter(x, y);
	}

	public void setCenter(double x, double y) {
		this.center[0] = (float) x;

		if (y == 0)
			this.center[1] = text_size + MARGIN;
		else
			this.center[1] = (float) y;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setARGB(150, 10, 10, 10);
		paint.setAntiAlias(true);

		Paint paintText = new Paint();
		paintText.setColor(Color.WHITE);
		paintText.setTextSize(text_size);
		paintText.setAntiAlias(true);
		paintText.setFakeBoldText(true);
		paintText.setTextAlign(Align.CENTER);

		float w_m = paint.measureText(text) + MARGIN;
		float h_m = text_size + MARGIN;

		RectF back = new RectF(center[0] - w_m, center[1] - h_m, center[0]
				+ w_m, center[1] + h_m);

		canvas.drawRoundRect(back, 15, 15, paint);

		if (text != null) {
			Path path = new Path();
			path.moveTo(center[0] - (w_m - MARGIN), center[1] + text_size / 2);
			path.lineTo(center[0] + (w_m - MARGIN), center[1] + text_size / 2);
			canvas.drawTextOnPath(text, path, 0, 0, paintText);
		}

		super.onDraw(canvas);
	}
}