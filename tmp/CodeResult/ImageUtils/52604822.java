package com.google.android.apps.unveil.env;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.FloatMath;
import com.google.android.apps.unveil.env.media.ImageLoader.Image;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class ImageUtils
{
  private static final UnveilLogger LOGGER = new UnveilLogger();
  protected static final int MAX_JPEG_QUALITY = 95;
  protected static final int MIN_JPEG_QUALITY = 60;

  static
  {
    ResourceUtils.loadNativeLibrary("imageutils");
  }

  public static Rect adjustCropForUnexpectedPictureSize(Rect paramRect, Size paramSize1, Size paramSize2)
  {
    if (paramRect == null)
      return null;
    UnveilLogger localUnveilLogger1 = LOGGER;
    Object[] arrayOfObject1 = new Object[4];
    arrayOfObject1[0] = Integer.valueOf(paramRect.left);
    arrayOfObject1[1] = Integer.valueOf(paramRect.top);
    arrayOfObject1[2] = Integer.valueOf(paramRect.width());
    arrayOfObject1[3] = Integer.valueOf(paramRect.height());
    localUnveilLogger1.v("Original crop area: left=%d, top=%d, width=%d, height=%d", arrayOfObject1);
    LOGGER.v("Expected picture size: %s", new Object[] { paramSize1 });
    LOGGER.v("Actual picture size: %s", new Object[] { paramSize2 });
    Rect localRect = makeRectSafe(paramSize2, convertCropRectAspectRatio(paramRect, paramSize1, paramSize2));
    if (localRect == null)
    {
      LOGGER.e("Could not make adjustedCropArea safe.", new Object[0]);
      return null;
    }
    UnveilLogger localUnveilLogger2 = LOGGER;
    Object[] arrayOfObject2 = new Object[4];
    arrayOfObject2[0] = Integer.valueOf(localRect.left);
    arrayOfObject2[1] = Integer.valueOf(localRect.top);
    arrayOfObject2[2] = Integer.valueOf(localRect.width());
    arrayOfObject2[3] = Integer.valueOf(localRect.height());
    localUnveilLogger2.v("Final crop area: left=%d, top=%d, width=%d, height=%d", arrayOfObject2);
    return localRect;
  }

  public static byte[] compressBitmap(Bitmap paramBitmap, int paramInt)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, paramInt, localByteArrayOutputStream);
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    ResourceUtils.closeStream(localByteArrayOutputStream);
    return arrayOfByte;
  }

  protected static byte[] compressImage(Picture paramPicture1, Picture paramPicture2, Bitmap paramBitmap1, Bitmap paramBitmap2)
  {
    byte[] arrayOfByte = compressBitmap(paramBitmap2, getJpegQualityRecommendation(paramBitmap2.getWidth(), paramBitmap2.getHeight()));
    UnveilLogger localUnveilLogger1 = LOGGER;
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Size.dimensionsAsString(paramBitmap1.getWidth(), paramBitmap1.getHeight());
    arrayOfObject1[1] = Integer.valueOf(paramPicture1.getByteSize());
    localUnveilLogger1.d("Size of photo before recompress: %s %d bytes", arrayOfObject1);
    UnveilLogger localUnveilLogger2 = LOGGER;
    Object[] arrayOfObject2 = new Object[2];
    arrayOfObject2[0] = Size.dimensionsAsString(paramBitmap2.getWidth(), paramBitmap2.getHeight());
    arrayOfObject2[1] = Integer.valueOf(arrayOfByte.length);
    localUnveilLogger2.d("Size of photo after recompress: %s %d bytes", arrayOfObject2);
    if (paramBitmap2 != paramBitmap1)
      paramBitmap2.recycle();
    if (paramPicture2 != paramPicture1)
      paramPicture2.recycle();
    return arrayOfByte;
  }

  public static native void computeEdgeBitmap(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public static ImageStatistics computeImageStatistics(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    ImageUtils localImageUtils = new ImageUtils();
    localImageUtils.getClass();
    return new ImageStatistics(computeImageStatisticsNative(paramInt1, paramInt2, paramArrayOfByte));
  }

  private static native float[] computeImageStatisticsNative(int paramInt1, int paramInt2, byte[] paramArrayOfByte);

  public static native int[] computeSignatureNative(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static native void convertARGB8888ToYUV420SP(int[] paramArrayOfInt, byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  protected static Rect convertCropRectAspectRatio(Rect paramRect, Size paramSize1, Size paramSize2)
  {
    double d1 = paramSize2.width / paramSize1.width;
    double d2 = paramSize2.height / paramSize1.height;
    if (Math.abs(1.0D - d1) < Math.abs(1.0D - d2));
    for (double d3 = d1; ; d3 = d2)
    {
      double d4 = (d3 * paramSize1.width - paramSize2.width) / 2.0D;
      double d5 = (d3 * paramSize1.height - paramSize2.height) / 2.0D;
      return new Rect((int)(d3 * paramRect.left - d4), (int)(d3 * paramRect.top - d5), (int)(d3 * paramRect.right - d4), (int)(d3 * paramRect.bottom - d5));
    }
  }

  public static native void convertRGB565ToYUV420SP(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2);

  public static native void convertYUV420SPToARGB8888(byte[] paramArrayOfByte, int[] paramArrayOfInt, int paramInt1, int paramInt2, boolean paramBoolean);

  public static native void convertYUV420SPToRGB565(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2);

  public static Bitmap cropBitmap(Bitmap paramBitmap, Rect paramRect)
  {
    if (paramRect == null)
      return paramBitmap;
    if (paramBitmap == null)
    {
      LOGGER.e("Unable to decode camera image for processing.", new Object[0]);
      return null;
    }
    return Bitmap.createBitmap(paramBitmap, paramRect.left, paramRect.top, paramRect.width(), paramRect.height());
  }

  public static native byte[] decodeJpegToYUV420SP(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt);

  public static native int diffSignatureNative(int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static native void downsampleImageNative(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, int paramInt3, byte[] paramArrayOfByte2);

  public static native byte[] encodeJpegFromYUV420SP(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3);

  public static Matrix generateUndistortTransformer(Size paramSize1, Size paramSize2)
  {
    float f1 = paramSize1.width / paramSize2.width;
    float f2 = paramSize1.height / paramSize2.height;
    float f4;
    float f3;
    if (f1 < f2)
    {
      f4 = f2 / f1;
      f3 = 1.0F;
    }
    while (true)
    {
      Matrix localMatrix = new Matrix();
      localMatrix.setScale(f3, f4, paramSize2.width / 2, paramSize2.height / 2);
      return localMatrix;
      f3 = f1 / f2;
      f4 = 1.0F;
    }
  }

  public static native int[] getBucketDistributionNative(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public static int getJpegQualityRecommendation(int paramInt1, int paramInt2)
  {
    double d1 = paramInt1 * paramInt2 / 1000000.0D;
    double d2 = 3681.0D + 55382.0D * d1;
    int i;
    if (d2 >= 44999.999900000003D)
      i = 60;
    do
    {
      return i;
      double d3 = Math.sqrt(2000.0D * d1);
      i = (int)(11.63659D * Math.log(45000.0D - d2) + (-12.19872D + -0.8533439D * d3));
      if (i < 60)
        return 60;
    }
    while (i <= 95);
    return 95;
  }

  public static int getJpegQualityRecommendation(Size paramSize)
  {
    return getJpegQualityRecommendation(paramSize.width, paramSize.height);
  }

  public static Picture getPicture(ContentResolver paramContentResolver, Uri paramUri, int paramInt)
  {
    LOGGER.v(paramUri.toString(), new Object[0]);
    try
    {
      Picture localPicture = PictureFactory.createBitmap(paramContentResolver, paramUri, 1635840, paramInt);
      return localPicture;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      LOGGER.e(localFileNotFoundException, "Failed to read imageUri", new Object[0]);
      return null;
    }
    catch (PictureFactory.ImageDecodingException localImageDecodingException)
    {
      LOGGER.e(localImageDecodingException, "Image decoding failed.", new Object[0]);
    }
    return null;
  }

  public static Picture getPicture(ContentResolver paramContentResolver, ImageLoader.Image paramImage)
  {
    return getPicture(paramContentResolver, paramImage.imageUri, paramImage.orientation);
  }

  public static Matrix getTransformationMatrix(Size paramSize1, Size paramSize2, int paramInt)
  {
    int i = 1;
    if (Math.abs(paramInt) % 90 != 0)
    {
      UnveilLogger localUnveilLogger = LOGGER;
      Object[] arrayOfObject = new Object[i];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      localUnveilLogger.w("Angle that is not multiple of 90! %d", arrayOfObject);
    }
    Matrix localMatrix = new Matrix();
    if (paramInt != 0)
    {
      localMatrix.postTranslate(-paramSize1.width / 2.0F, -paramSize1.height / 2.0F);
      localMatrix.postRotate(paramInt);
    }
    int j;
    if ((90 + Math.abs(paramInt)) % 180 == 0)
    {
      if (i == 0)
        break label192;
      j = paramSize1.height;
      label108: if (i == 0)
        break label201;
    }
    label192: label201: for (int k = paramSize1.width; ; k = paramSize1.height)
    {
      if ((j != paramSize2.width) || (k != paramSize2.height))
        localMatrix.postScale(paramSize2.width / j, paramSize2.height / k);
      if (paramInt != 0)
        localMatrix.postTranslate(paramSize2.width / 2.0F, paramSize2.height / 2.0F);
      return localMatrix;
      i = 0;
      break;
      j = paramSize1.width;
      break label108;
    }
  }

  public static int getYUVByteSize(int paramInt1, int paramInt2)
  {
    return paramInt1 * paramInt2 + 2 * ((paramInt1 + 1) / 2 * ((paramInt2 + 1) / 2));
  }

  public static native boolean isBlurredNative(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public static Rect makeRectSafe(Size paramSize, Rect paramRect)
  {
    Rect localRect = new Rect(paramRect);
    if (localRect.left < 0)
    {
      localRect.right += localRect.left;
      localRect.left = 0;
    }
    if (localRect.top < 0)
    {
      localRect.bottom += localRect.top;
      localRect.top = 0;
    }
    if (localRect.right > paramSize.width)
    {
      double d2 = localRect.right - paramSize.width;
      localRect.right = ((int)(localRect.right - d2));
      localRect.left = ((int)(d2 + localRect.left));
    }
    if (localRect.bottom > paramSize.height)
    {
      double d1 = localRect.bottom - paramSize.height;
      localRect.bottom = ((int)(localRect.bottom - d1));
      localRect.top = ((int)(d1 + localRect.top));
    }
    if (localRect.left >= localRect.right)
    {
      LOGGER.e("Crop area has nonpositive width", new Object[0]);
      return null;
    }
    if (localRect.top >= localRect.bottom)
    {
      LOGGER.e("Crop area has nonpositive height", new Object[0]);
      return null;
    }
    localRect.left = Math.max(0, localRect.left);
    localRect.top = Math.max(0, localRect.top);
    localRect.right = Math.min(paramSize.width, localRect.right);
    localRect.bottom = Math.min(paramSize.height, localRect.bottom);
    return localRect;
  }

  public static native void mirrorX(int paramInt1, int paramInt2, byte[] paramArrayOfByte);

  public static byte[] rotateAndCompressImage(Picture paramPicture, int paramInt1, int paramInt2)
  {
    Picture localPicture = paramPicture.getCroppedPicture();
    Bitmap localBitmap = localPicture.peekBitmap();
    if (localBitmap == null)
      return null;
    return compressImage(paramPicture, localPicture, localBitmap, rotateImage(paramInt1, paramInt2, localBitmap));
  }

  public static Bitmap rotateImage(int paramInt1, int paramInt2, Bitmap paramBitmap)
  {
    float f = 1.0F;
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    if (paramBitmap.getWidth() * paramBitmap.getHeight() > paramInt2)
    {
      i = (int)FloatMath.sqrt(i / j * paramInt2);
      f = i / paramBitmap.getWidth();
      j = (int)(f * j);
    }
    UnveilLogger localUnveilLogger1 = LOGGER;
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Float.valueOf(f);
    localUnveilLogger1.v("scaleFactor %f", arrayOfObject1);
    UnveilLogger localUnveilLogger2 = LOGGER;
    Object[] arrayOfObject2 = new Object[2];
    arrayOfObject2[0] = Integer.valueOf(i);
    arrayOfObject2[1] = Integer.valueOf(j);
    localUnveilLogger2.v("outputMajorAxis: %d outputMinorAxis: %d", arrayOfObject2);
    Bitmap localBitmap = paramBitmap;
    if ((paramInt1 != 0) || (i != paramBitmap.getWidth()) || (j != paramBitmap.getHeight()))
      if ((paramInt1 != 90) && (paramInt1 != 270))
        break label287;
    label287: for (localBitmap = Bitmap.createBitmap(j, i, Bitmap.Config.RGB_565); ; localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565))
    {
      Matrix localMatrix = new Matrix();
      localMatrix.postTranslate(-(1 + paramBitmap.getWidth()) / 2, -(1 + paramBitmap.getHeight()) / 2);
      localMatrix.postRotate(paramInt1);
      localMatrix.postScale(f, f);
      localMatrix.postTranslate(localBitmap.getWidth() / 2, localBitmap.getHeight() / 2);
      Paint localPaint = new Paint();
      localPaint.setFilterBitmap(true);
      new Canvas(localBitmap).drawBitmap(paramBitmap, localMatrix, localPaint);
      return localBitmap;
    }
  }

  public static native void rotateYuvFrame(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public class ImageStatistics
  {
    public final float averageContrast;
    public final float averageintensity;
    public final float contrastStdDev;
    public final float intensityStdDev;
    public final float maxContrast;
    public final float maxintensity;
    public final float minContrast;
    public final float minIntensity;

    protected ImageStatistics(float[] arg2)
    {
      int i = 0 + 1;
      Object localObject;
      this.minIntensity = localObject[0];
      int j = i + 1;
      this.averageintensity = localObject[i];
      int k = j + 1;
      this.maxintensity = localObject[j];
      int m = k + 1;
      this.intensityStdDev = localObject[k];
      int n = m + 1;
      this.minContrast = localObject[m];
      int i1 = n + 1;
      this.averageContrast = localObject[n];
      int i2 = i1 + 1;
      this.maxContrast = localObject[i1];
      (i2 + 1);
      this.contrastStdDev = localObject[i2];
    }

    public String toString()
    {
      return "Int: " + this.minIntensity + ", " + this.averageintensity + ", " + this.maxintensity + ", " + this.intensityStdDev + "  Grad: " + this.minContrast + ", " + this.averageContrast + ", " + this.maxContrast + ", " + this.contrastStdDev;
    }
  }

  public static class RotatePhotoTask extends AsyncTask<Void, Void, Picture>
  {
    private final UnveilLogger logger = new UnveilLogger();
    private final int outputMaxPixels;
    private final Picture picture;
    private final int rotationAngle;
    private long timeTaken;

    public RotatePhotoTask(Picture paramPicture, int paramInt)
    {
      this.rotationAngle = paramPicture.getOrientation();
      this.outputMaxPixels = paramInt;
      this.picture = paramPicture;
    }

    protected Picture doInBackground(Void[] paramArrayOfVoid)
    {
      Thread.currentThread().setName("RotatePhotoTask");
      long l = SystemClock.uptimeMillis();
      try
      {
        byte[] arrayOfByte = ImageUtils.rotateAndCompressImage(this.picture, this.rotationAngle, this.outputMaxPixels);
        if (arrayOfByte == null)
        {
          this.logger.e("Failed to rotate and compress image.", new Object[0]);
          return null;
        }
        this.timeTaken = (SystemClock.uptimeMillis() - l);
        this.logger.v("Returning rotated and compressed JpegPicture.", new Object[0]);
        Picture localPicture = PictureFactory.createJpeg(arrayOfByte, this.picture.getOrientation(), this.picture.getSource());
        return localPicture;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        this.logger.e("OutOfMemoryError when rotating photo.", new Object[0]);
        this.timeTaken = -1L;
      }
      return null;
    }

    protected void onPostExecute(Picture paramPicture)
    {
      UnveilLogger localUnveilLogger = this.logger;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(this.timeTaken);
      localUnveilLogger.d("Time taken to rencode and rotate: %d", arrayOfObject);
    }
  }
}

/* Location:           D:\Jervis\Documents\Programming\Research\Android\apks\com.google.android.apps.translate-141\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.unveil.env.ImageUtils
 * JD-Core Version:    0.6.2
 */