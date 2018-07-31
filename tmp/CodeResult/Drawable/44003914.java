package android.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.NinePatch;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import com.android.internal.R.styleable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class Drawable
{
    private static final Rect ZERO_BOUNDS_RECT = new Rect();
    private Rect mBounds = ZERO_BOUNDS_RECT;
    private WeakReference<Callback> mCallback = null;
    private int mChangingConfigurations = 0;
    private int mLevel = 0;
    private int[] mStateSet = StateSet.WILD_CARD;
    private boolean mVisible = true;

    public static Drawable createFromPath(String paramString)
    {
        Drawable localDrawable = null;
        if (paramString == null);
        while (true)
        {
            return localDrawable;
            Bitmap localBitmap = BitmapFactory.decodeFile(paramString);
            if (localBitmap != null)
                localDrawable = drawableFromBitmap(null, localBitmap, null, null, null, paramString);
        }
    }

    public static Drawable createFromResourceStream(Resources paramResources, TypedValue paramTypedValue, InputStream paramInputStream, String paramString)
    {
        return createFromResourceStream(paramResources, paramTypedValue, paramInputStream, paramString, null);
    }

    public static Drawable createFromResourceStream(Resources paramResources, TypedValue paramTypedValue, InputStream paramInputStream, String paramString, BitmapFactory.Options paramOptions)
    {
        Drawable localDrawable = null;
        if (paramInputStream == null);
        while (true)
        {
            return localDrawable;
            Rect localRect1 = new Rect();
            if (paramOptions == null)
                paramOptions = new BitmapFactory.Options();
            paramOptions.inScreenDensity = DisplayMetrics.DENSITY_DEVICE;
            Bitmap localBitmap = BitmapFactory.decodeResourceStream(paramResources, paramTypedValue, paramInputStream, localRect1, paramOptions);
            if (localBitmap != null)
            {
                byte[] arrayOfByte = localBitmap.getNinePatchChunk();
                if ((arrayOfByte == null) || (!NinePatch.isNinePatchChunk(arrayOfByte)))
                {
                    arrayOfByte = null;
                    localRect1 = null;
                }
                int[] arrayOfInt = localBitmap.getLayoutBounds();
                Rect localRect2 = null;
                if (arrayOfInt != null)
                    localRect2 = new Rect(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]);
                localDrawable = drawableFromBitmap(paramResources, localBitmap, arrayOfByte, localRect1, localRect2, paramString);
            }
        }
    }

    public static Drawable createFromStream(InputStream paramInputStream, String paramString)
    {
        return createFromResourceStream(null, null, paramInputStream, paramString, null);
    }

    public static Drawable createFromXml(Resources paramResources, XmlPullParser paramXmlPullParser)
        throws XmlPullParserException, IOException
    {
        AttributeSet localAttributeSet = Xml.asAttributeSet(paramXmlPullParser);
        int i;
        do
            i = paramXmlPullParser.next();
        while ((i != 2) && (i != 1));
        if (i != 2)
            throw new XmlPullParserException("No start tag found");
        Drawable localDrawable = createFromXmlInner(paramResources, paramXmlPullParser, localAttributeSet);
        if (localDrawable == null)
            throw new RuntimeException("Unknown initial tag: " + paramXmlPullParser.getName());
        return localDrawable;
    }

    public static Drawable createFromXmlInner(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet)
        throws XmlPullParserException, IOException
    {
        String str = paramXmlPullParser.getName();
        Object localObject;
        if (str.equals("selector"))
            localObject = new StateListDrawable();
        while (true)
        {
            ((Drawable)localObject).inflate(paramResources, paramXmlPullParser, paramAttributeSet);
            return localObject;
            if (str.equals("level-list"))
            {
                localObject = new LevelListDrawable();
            }
            else if (str.equals("layer-list"))
            {
                localObject = new LayerDrawable();
            }
            else if (str.equals("transition"))
            {
                localObject = new TransitionDrawable();
            }
            else if (str.equals("color"))
            {
                localObject = new ColorDrawable();
            }
            else if (str.equals("shape"))
            {
                localObject = new GradientDrawable();
            }
            else if (str.equals("scale"))
            {
                localObject = new ScaleDrawable();
            }
            else if (str.equals("clip"))
            {
                localObject = new ClipDrawable();
            }
            else if (str.equals("rotate"))
            {
                localObject = new RotateDrawable();
            }
            else if (str.equals("animated-rotate"))
            {
                localObject = new AnimatedRotateDrawable();
            }
            else if (str.equals("animation-list"))
            {
                localObject = new AnimationDrawable();
            }
            else if (str.equals("inset"))
            {
                localObject = new InsetDrawable();
            }
            else if (str.equals("bitmap"))
            {
                localObject = new BitmapDrawable(paramResources);
                if (paramResources != null)
                    ((BitmapDrawable)localObject).setTargetDensity(paramResources.getDisplayMetrics());
            }
            else
            {
                if (!str.equals("nine-patch"))
                    break;
                localObject = new NinePatchDrawable();
                if (paramResources != null)
                    ((NinePatchDrawable)localObject).setTargetDensity(paramResources.getDisplayMetrics());
            }
        }
        throw new XmlPullParserException(paramXmlPullParser.getPositionDescription() + ": invalid drawable tag " + str);
    }

    private static Drawable drawableFromBitmap(Resources paramResources, Bitmap paramBitmap, byte[] paramArrayOfByte, Rect paramRect1, Rect paramRect2, String paramString)
    {
        if (paramArrayOfByte != null);
        for (Object localObject = new NinePatchDrawable(paramResources, paramBitmap, paramArrayOfByte, paramRect1, paramRect2, paramString); ; localObject = new BitmapDrawable(paramResources, paramBitmap))
            return localObject;
    }

    public static int resolveOpacity(int paramInt1, int paramInt2)
    {
        if (paramInt1 == paramInt2);
        while (true)
        {
            return paramInt1;
            if ((paramInt1 == 0) || (paramInt2 == 0))
                paramInt1 = 0;
            else if ((paramInt1 == -3) || (paramInt2 == -3))
                paramInt1 = -3;
            else if ((paramInt1 == -2) || (paramInt2 == -2))
                paramInt1 = -2;
            else
                paramInt1 = -1;
        }
    }

    public void clearColorFilter()
    {
        setColorFilter(null);
    }

    public final Rect copyBounds()
    {
        return new Rect(this.mBounds);
    }

    public final void copyBounds(Rect paramRect)
    {
        paramRect.set(this.mBounds);
    }

    public abstract void draw(Canvas paramCanvas);

    public final Rect getBounds()
    {
        if (this.mBounds == ZERO_BOUNDS_RECT)
            this.mBounds = new Rect();
        return this.mBounds;
    }

    public Callback getCallback()
    {
        if (this.mCallback != null);
        for (Callback localCallback = (Callback)this.mCallback.get(); ; localCallback = null)
            return localCallback;
    }

    public int getChangingConfigurations()
    {
        return this.mChangingConfigurations;
    }

    public ConstantState getConstantState()
    {
        return null;
    }

    public Drawable getCurrent()
    {
        return this;
    }

    public int getIntrinsicHeight()
    {
        return -1;
    }

    public int getIntrinsicWidth()
    {
        return -1;
    }

    public Insets getLayoutInsets()
    {
        return Insets.NONE;
    }

    public final int getLevel()
    {
        return this.mLevel;
    }

    public int getMinimumHeight()
    {
        int i = getIntrinsicHeight();
        if (i > 0);
        while (true)
        {
            return i;
            i = 0;
        }
    }

    public int getMinimumWidth()
    {
        int i = getIntrinsicWidth();
        if (i > 0);
        while (true)
        {
            return i;
            i = 0;
        }
    }

    public abstract int getOpacity();

    public boolean getPadding(Rect paramRect)
    {
        paramRect.set(0, 0, 0, 0);
        return false;
    }

    public int getResolvedLayoutDirectionSelf()
    {
        Callback localCallback = getCallback();
        if ((localCallback == null) || (!(localCallback instanceof Callback2)));
        for (int i = 0; ; i = ((Callback2)localCallback).getResolvedLayoutDirection(this))
            return i;
    }

    public int[] getState()
    {
        return this.mStateSet;
    }

    public Region getTransparentRegion()
    {
        return null;
    }

    public void inflate(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet)
        throws XmlPullParserException, IOException
    {
        TypedArray localTypedArray = paramResources.obtainAttributes(paramAttributeSet, R.styleable.Drawable);
        inflateWithAttributes(paramResources, paramXmlPullParser, localTypedArray, 0);
        localTypedArray.recycle();
    }

    void inflateWithAttributes(Resources paramResources, XmlPullParser paramXmlPullParser, TypedArray paramTypedArray, int paramInt)
        throws XmlPullParserException, IOException
    {
        this.mVisible = paramTypedArray.getBoolean(paramInt, this.mVisible);
    }

    public void invalidateSelf()
    {
        Callback localCallback = getCallback();
        if (localCallback != null)
            localCallback.invalidateDrawable(this);
    }

    public boolean isStateful()
    {
        return false;
    }

    public final boolean isVisible()
    {
        return this.mVisible;
    }

    public void jumpToCurrentState()
    {
    }

    public Drawable mutate()
    {
        return this;
    }

    protected void onBoundsChange(Rect paramRect)
    {
    }

    protected boolean onLevelChange(int paramInt)
    {
        return false;
    }

    protected boolean onStateChange(int[] paramArrayOfInt)
    {
        return false;
    }

    public void scheduleSelf(Runnable paramRunnable, long paramLong)
    {
        Callback localCallback = getCallback();
        if (localCallback != null)
            localCallback.scheduleDrawable(this, paramRunnable, paramLong);
    }

    public abstract void setAlpha(int paramInt);

    public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        Rect localRect = this.mBounds;
        if (localRect == ZERO_BOUNDS_RECT)
        {
            localRect = new Rect();
            this.mBounds = localRect;
        }
        if ((localRect.left != paramInt1) || (localRect.top != paramInt2) || (localRect.right != paramInt3) || (localRect.bottom != paramInt4))
        {
            this.mBounds.set(paramInt1, paramInt2, paramInt3, paramInt4);
            onBoundsChange(this.mBounds);
        }
    }

    public void setBounds(Rect paramRect)
    {
        setBounds(paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
    }

    public final void setCallback(Callback paramCallback)
    {
        this.mCallback = new WeakReference(paramCallback);
    }

    public void setChangingConfigurations(int paramInt)
    {
        this.mChangingConfigurations = paramInt;
    }

    public void setColorFilter(int paramInt, PorterDuff.Mode paramMode)
    {
        setColorFilter(new PorterDuffColorFilter(paramInt, paramMode));
    }

    public abstract void setColorFilter(ColorFilter paramColorFilter);

    public void setDither(boolean paramBoolean)
    {
    }

    public void setFilterBitmap(boolean paramBoolean)
    {
    }

    public final boolean setLevel(int paramInt)
    {
        if (this.mLevel != paramInt)
            this.mLevel = paramInt;
        for (boolean bool = onLevelChange(paramInt); ; bool = false)
            return bool;
    }

    public boolean setState(int[] paramArrayOfInt)
    {
        if (!Arrays.equals(this.mStateSet, paramArrayOfInt))
            this.mStateSet = paramArrayOfInt;
        for (boolean bool = onStateChange(paramArrayOfInt); ; bool = false)
            return bool;
    }

    public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
    {
        if (this.mVisible != paramBoolean1);
        for (boolean bool = true; ; bool = false)
        {
            if (bool)
            {
                this.mVisible = paramBoolean1;
                invalidateSelf();
            }
            return bool;
        }
    }

    public void unscheduleSelf(Runnable paramRunnable)
    {
        Callback localCallback = getCallback();
        if (localCallback != null)
            localCallback.unscheduleDrawable(this, paramRunnable);
    }

    public static abstract class ConstantState
    {
        public abstract int getChangingConfigurations();

        public abstract Drawable newDrawable();

        public Drawable newDrawable(Resources paramResources)
        {
            return newDrawable();
        }
    }

    public static abstract interface Callback2 extends Drawable.Callback
    {
        public abstract int getResolvedLayoutDirection(Drawable paramDrawable);
    }

    public static abstract interface Callback
    {
        public abstract void invalidateDrawable(Drawable paramDrawable);

        public abstract void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong);

        public abstract void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable);
    }
}

/* Location:                     /home/lithium/miui/chameleon/2.11.16/framework_dex2jar.jar
 * Qualified Name:         android.graphics.drawable.Drawable
 * JD-Core Version:        0.6.2
 */