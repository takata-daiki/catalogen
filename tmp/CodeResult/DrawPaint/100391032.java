package com.lejingw.apps.scatchcard.index;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.lejingw.apps.scatchcard.R;
import com.lejingw.apps.scatchcard.util.CustomDialog;
import com.lejingw.apps.scatchcard.util.DisplayUtil;
import com.lejingw.apps.scatchcard.util.ImageUtil;

import java.io.IOException;
import java.io.InputStream;

public class ScratchCardView extends ImageView {
    private final String PACKAGE_NAME = "com.lejingw.apps.scatchcard";
    private final String RAW_DIRECTORY = "raw";
    private final Context context;
    private final View popWinView;
    private final PopupWindow popWindow;

    private ScratchData scratchData;
    private ScratchCover scratchCover;
    private final boolean rewardFlag;

    private Button tryScratchBtn;
    private Button buyScratchCardBtn;

    private boolean scratchEnabled = false;

    private final float scale;

    private int imageViewWidth = -1, imageViewHeight = -1;
    private int bgWidth, bgHeight;
    private float sx, sy;

    private int preClickX = 0, preClickY = 0;

    //测试机器MI1S,480*854分辨率
//    private int canvasStartX;
//    private int canvasStartY;


    private Path drawPath = new Path();
    private Paint drawPaint = null;
    private Bitmap drawBitmap = null;
    private Canvas drawCanvas = null;

    public ScratchCardView(Context context, View popWinView, PopupWindow popWindow, ScratchData scratchData, boolean rewardFlag) {
        super(context);
        this.context = context;
        this.popWinView = popWinView;
        this.popWindow = popWindow;
        this.scratchData = scratchData;
        this.rewardFlag = rewardFlag;
        this.scratchCover = scratchData.getScratchCover();

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.scale = displayMetrics.density;
        Log.d("msg", displayMetrics.widthPixels + "---------------" + displayMetrics.heightPixels);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, DisplayUtil.dip2px(context, 40));
        setLayoutParams(lp);
        setScaleType(ScaleType.FIT_XY);
        //根据名称获取资源id
        int backgroundResId = context.getResources().getIdentifier(scratchData.getRawPicName(), RAW_DIRECTORY, PACKAGE_NAME);
        setImageResource(backgroundResId);
        Log.d("msg", "-------------"+backgroundResId);

//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), backgroundResId);
        Bitmap bitmap = ImageUtil.readBitMap(context, backgroundResId);
        //下句无效，不能有效的支持图片拉伸
//        setImageBitmap(bitmap);
        Log.d("msg", bitmap.getWidth() + "---------------" + bitmap.getHeight());
        bgWidth = bitmap.getWidth();
        bgHeight = bitmap.getHeight();

        buyScratchCardBtn = (Button)popWinView.findViewById(R.id.buyScratchCardBtn);
        buyScratchCardBtn.setOnClickListener(new BuyScratchCardClickListener(this));

        tryScratchBtn = (Button)popWinView.findViewById(R.id.tryScratchBtn);
        tryScratchBtn.setOnClickListener(new TryScratchClickListener());

        initPaint();
    }

    private void initPaint() {
        drawPaint = new Paint();
        drawPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        drawPaint.setAntiAlias(true);
        drawPaint.setDither(true);
        drawPaint.setStyle(Style.STROKE);
        drawPaint.setStrokeWidth(10 * scale);
        drawPaint.setStrokeCap(Cap.ROUND);
        drawPaint.setStrokeJoin(Join.ROUND);
        drawPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        drawPaint.setAlpha(0);

//        canvasStartX = scratchCover.getCanvasStartX();
//        canvasStartY = scratchCover.getCanvasStartY();
//        canvasStartX = 0;
//        canvasStartY = 134;
    }

    private void initCover(){
		InputStream in = null;
		try {
			in = context.getAssets().open(scratchCover.getPicName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap orignalBitmap = BitmapFactory.decodeStream(in);
//		Bitmap coverBitmap = orignalBitmap.copy(Bitmap.Config.ARGB_8888, true);
//      Matrix matrix=new Matrix();
//      matrix.postScale(sx, sy);
//		drawBitmap = Bitmap.createBitmap(coverBitmap, 0, 0, coverBitmap.getWidth(), coverBitmap.getHeight(), matrix, true);

        int width = (int)(sx * orignalBitmap.getWidth());
        int height = (int)(sy * orignalBitmap.getHeight());
		drawBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);

		drawCanvas = new Canvas(drawBitmap);
		Rect dst = new Rect(0, 0, width, height);
		drawCanvas.drawBitmap(orignalBitmap, null, dst, null);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (imageViewWidth < 0) {
            synchronized (this) {
                imageViewWidth = canvas.getWidth();
                imageViewHeight = canvas.getHeight();
                sx = (float)imageViewWidth / bgWidth;
                sy = (float)imageViewHeight / bgHeight;
                initCover();
            }
        }

        drawCanvas.drawPath(drawPath, drawPaint);
//        canvas.drawBitmap(drawBitmap, canvasStartX, canvasStartY, null);
//        canvas.drawBitmap(drawBitmap, scratchCover.getCanvasStartX(), scratchCover.getCanvasStartY(), null);
        canvas.drawBitmap(drawBitmap, scratchCover.getCanvasStartXRate()* imageViewWidth, scratchCover.getCanvasStartYRate()* imageViewHeight, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!scratchEnabled)
            return true;
        int action = event.getAction();
//        int currClickX = (int) event.getX() - canvasStartX;
//        int currClickY = (int) event.getY() - canvasStartY;
//        int currClickX = (int) event.getX() - scratchCover.getCanvasStartX();
//        int currClickY = (int) event.getY() - scratchCover.getCanvasStartY();
        int currClickX = (int) event.getX() - (int)(scratchCover.getCanvasStartXRate() * imageViewWidth);
        int currClickY = (int) event.getY() - (int)(scratchCover.getCanvasStartYRate() * imageViewHeight);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                drawPath.reset();
                preClickX = currClickX;
                preClickY = currClickY;
                drawPath.moveTo(preClickX, preClickY);
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                drawPath.quadTo(preClickX, preClickY, currClickX, currClickY);
                postInvalidate();
                preClickX = currClickX;
                preClickY = currClickY;
            }
            break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                drawPath.reset();
            }
            break;
        }
        return true;
    }

    class BuyScratchCardClickListener implements OnClickListener {
        BuyScratchCardClickListener(ScratchCardView scratchCardView) {
        }

        @Override
        public void onClick(View view) {
            CustomDialog.Builder builder = new CustomDialog.Builder(context);
            builder.setMessage("敬请期待...^ ^").setBackgroundResId(R.drawable.custom_dialog_zwkf);
            builder.setPositiveButton("试试其他的", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    popWindow.dismiss();
                }
            });

            builder.setNegativeButton("再玩一次",
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            initCover();
                            tryScratchBtn.setText("试刮兑奖");
                            ScratchCardView.this.scratchEnabled = true;
                        }
                    });

            builder.create2().show();
        }
    }

    class TryScratchClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            if("免费试刮".equals(tryScratchBtn.getText())){
                tryScratchBtn.setText("试刮兑奖");
                ScratchCardView.this.scratchEnabled = true;
            }else if("试刮兑奖".equals(tryScratchBtn.getText())){
                CustomDialog.Builder builder = new CustomDialog.Builder(ScratchCardView.this.context);
                if(rewardFlag){
                    builder.setMessage("奖金20元").setBackgroundResId(R.drawable.custom_dialog_zj);
                }else{
                    builder.setMessage("很遗憾,没有中奖").setBackgroundResId(R.drawable.custom_dialog_mzj);
                }
                builder.setPositiveButton("查看奖券", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //清空画布内容
                        drawCanvas.drawPaint(drawPaint);
                        ScratchCardView.this.invalidate();

                        tryScratchBtn.setText("再玩一次");
                        ScratchCardView.this.scratchEnabled = false;

                    }
                });

                builder.setNegativeButton("再玩一次",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                initCover();
                                tryScratchBtn.setText("试刮兑奖");
                                ScratchCardView.this.scratchEnabled = true;
                            }
                        });

                builder.create2().show();
            }else if("再玩一次".equals(tryScratchBtn.getText())){
                initCover();
                tryScratchBtn.setText("试刮兑奖");
                ScratchCardView.this.scratchEnabled = true;
            }
        }
    }
}
