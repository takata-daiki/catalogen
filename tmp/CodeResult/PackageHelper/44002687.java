package com.android.internal.content;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.storage.IMountService;
import android.os.storage.IMountService.Stub;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import libcore.io.IoUtils;

public class PackageHelper
{
    public static final int APP_INSTALL_AUTO = 0;
    public static final int APP_INSTALL_EXTERNAL = 2;
    public static final int APP_INSTALL_INTERNAL = 1;
    public static final int RECOMMEND_FAILED_ALREADY_EXISTS = -4;
    public static final int RECOMMEND_FAILED_INSUFFICIENT_STORAGE = -1;
    public static final int RECOMMEND_FAILED_INVALID_APK = -2;
    public static final int RECOMMEND_FAILED_INVALID_LOCATION = -3;
    public static final int RECOMMEND_FAILED_INVALID_URI = -6;
    public static final int RECOMMEND_INSTALL_EXTERNAL = 2;
    public static final int RECOMMEND_INSTALL_INTERNAL = 1;
    public static final int RECOMMEND_MEDIA_UNAVAILABLE = -5;
    private static final String TAG = "PackageHelper";
    private static final boolean localLOGV = true;

    private static void copyZipEntry(ZipEntry paramZipEntry, ZipFile paramZipFile, ZipOutputStream paramZipOutputStream)
        throws IOException
    {
        byte[] arrayOfByte = new byte[4096];
        if (paramZipEntry.getMethod() == 0);
        InputStream localInputStream;
        for (ZipEntry localZipEntry = new ZipEntry(paramZipEntry); ; localZipEntry = new ZipEntry(paramZipEntry.getName()))
        {
            paramZipOutputStream.putNextEntry(localZipEntry);
            localInputStream = paramZipFile.getInputStream(paramZipEntry);
            try
            {
                while (true)
                {
                    int i = localInputStream.read(arrayOfByte);
                    if (i <= 0)
                        break;
                    paramZipOutputStream.write(arrayOfByte, 0, i);
                }
            }
            finally
            {
                IoUtils.closeQuietly(localInputStream);
            }
        }
        paramZipOutputStream.flush();
        IoUtils.closeQuietly(localInputStream);
    }

    public static String createSdDir(int paramInt1, String paramString1, String paramString2, int paramInt2, boolean paramBoolean)
    {
        String str;
        try
        {
            IMountService localIMountService = getMountService();
            Log.i("PackageHelper", "Size of container " + paramInt1 + " MB");
            if (localIMountService.createSecureContainer(paramString1, paramInt1, "ext4", paramString2, paramInt2, paramBoolean) != 0)
            {
                Log.e("PackageHelper", "Failed to create secure container " + paramString1);
                str = null;
            }
            else
            {
                str = localIMountService.getSecureContainerPath(paramString1);
                Log.i("PackageHelper", "Created secure container " + paramString1 + " at " + str);
            }
        }
        catch (RemoteException localRemoteException)
        {
            Log.e("PackageHelper", "MountService running?");
            str = null;
        }
        return str;
    }

    public static boolean destroySdDir(String paramString)
    {
        boolean bool = false;
        try
        {
            Log.i("PackageHelper", "Forcibly destroying container " + paramString);
            if (getMountService().destroySecureContainer(paramString, true) != 0)
                Log.i("PackageHelper", "Failed to destroy container " + paramString);
            while (true)
            {
                return bool;
                bool = true;
            }
        }
        catch (RemoteException localRemoteException)
        {
            while (true)
                Log.e("PackageHelper", "Failed to destroy container " + paramString + " with exception " + localRemoteException);
        }
    }

    // ERROR //
    public static int extractPublicFiles(String paramString, java.io.File paramFile)
        throws IOException
    {
        // Byte code:
        //     0: aload_1
        //     1: ifnonnull +138 -> 139
        //     4: aconst_null
        //     5: astore_2
        //     6: aconst_null
        //     7: astore_3
        //     8: iconst_0
        //     9: istore 4
        //     11: new 55	java/util/zip/ZipFile
        //     14: dup
        //     15: aload_0
        //     16: invokespecial 157	java/util/zip/ZipFile:<init>	(Ljava/lang/String;)V
        //     19: astore 5
        //     21: aload 5
        //     23: invokevirtual 161	java/util/zip/ZipFile:entries	()Ljava/util/Enumeration;
        //     26: invokestatic 167	java/util/Collections:list	(Ljava/util/Enumeration;)Ljava/util/ArrayList;
        //     29: invokevirtual 173	java/util/ArrayList:iterator	()Ljava/util/Iterator;
        //     32: astore 9
        //     34: aload 9
        //     36: invokeinterface 179 1 0
        //     41: ifeq +119 -> 160
        //     44: aload 9
        //     46: invokeinterface 183 1 0
        //     51: checkcast 41	java/util/zip/ZipEntry
        //     54: astore 13
        //     56: aload 13
        //     58: invokevirtual 79	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
        //     61: astore 14
        //     63: ldc 185
        //     65: aload 14
        //     67: invokevirtual 191	java/lang/String:equals	(Ljava/lang/Object;)Z
        //     70: ifne +23 -> 93
        //     73: ldc 193
        //     75: aload 14
        //     77: invokevirtual 191	java/lang/String:equals	(Ljava/lang/Object;)Z
        //     80: ifne +13 -> 93
        //     83: aload 14
        //     85: ldc 195
        //     87: invokevirtual 198	java/lang/String:startsWith	(Ljava/lang/String;)Z
        //     90: ifeq -56 -> 34
        //     93: iload 4
        //     95: i2l
        //     96: aload 13
        //     98: invokevirtual 202	java/util/zip/ZipEntry:getSize	()J
        //     101: ladd
        //     102: l2i
        //     103: istore 4
        //     105: aload_1
        //     106: ifnull -72 -> 34
        //     109: aload 13
        //     111: aload 5
        //     113: aload_3
        //     114: invokestatic 204	com/android/internal/content/PackageHelper:copyZipEntry	(Ljava/util/zip/ZipEntry;Ljava/util/zip/ZipFile;Ljava/util/zip/ZipOutputStream;)V
        //     117: goto -83 -> 34
        //     120: astore 6
        //     122: aload 5
        //     124: invokevirtual 207	java/util/zip/ZipFile:close	()V
        //     127: aload 6
        //     129: athrow
        //     130: astore 8
        //     132: aload_3
        //     133: invokestatic 75	libcore/io/IoUtils:closeQuietly	(Ljava/lang/AutoCloseable;)V
        //     136: aload 8
        //     138: athrow
        //     139: new 209	java/io/FileOutputStream
        //     142: dup
        //     143: aload_1
        //     144: invokespecial 212	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
        //     147: astore_2
        //     148: new 50	java/util/zip/ZipOutputStream
        //     151: dup
        //     152: aload_2
        //     153: invokespecial 215	java/util/zip/ZipOutputStream:<init>	(Ljava/io/OutputStream;)V
        //     156: astore_3
        //     157: goto -149 -> 8
        //     160: aload 5
        //     162: invokevirtual 207	java/util/zip/ZipFile:close	()V
        //     165: aload_1
        //     166: ifnull +35 -> 201
        //     169: aload_3
        //     170: invokevirtual 218	java/util/zip/ZipOutputStream:finish	()V
        //     173: aload_3
        //     174: invokevirtual 85	java/util/zip/ZipOutputStream:flush	()V
        //     177: aload_2
        //     178: invokestatic 224	android/os/FileUtils:sync	(Ljava/io/FileOutputStream;)Z
        //     181: pop
        //     182: aload_3
        //     183: invokevirtual 225	java/util/zip/ZipOutputStream:close	()V
        //     186: aload_1
        //     187: invokevirtual 230	java/io/File:getAbsolutePath	()Ljava/lang/String;
        //     190: sipush 420
        //     193: bipush 255
        //     195: bipush 255
        //     197: invokestatic 234	android/os/FileUtils:setPermissions	(Ljava/lang/String;III)I
        //     200: pop
        //     201: aload_3
        //     202: invokestatic 75	libcore/io/IoUtils:closeQuietly	(Ljava/lang/AutoCloseable;)V
        //     205: iload 4
        //     207: ireturn
        //     208: astore 10
        //     210: goto -45 -> 165
        //     213: astore 7
        //     215: goto -88 -> 127
        //
        // Exception table:
        //     from	to	target	type
        //     21	117	120	finally
        //     11	21	130	finally
        //     122	127	130	finally
        //     127	130	130	finally
        //     160	165	130	finally
        //     169	201	130	finally
        //     160	165	208	java/io/IOException
        //     122	127	213	java/io/IOException
    }

    public static boolean finalizeSdDir(String paramString)
    {
        boolean bool = false;
        try
        {
            if (getMountService().finalizeSecureContainer(paramString) != 0)
                Log.i("PackageHelper", "Failed to finalize container " + paramString);
            while (true)
            {
                return bool;
                bool = true;
            }
        }
        catch (RemoteException localRemoteException)
        {
            while (true)
                Log.e("PackageHelper", "Failed to finalize container " + paramString + " with exception " + localRemoteException);
        }
    }

    public static boolean fixSdPermissions(String paramString1, int paramInt, String paramString2)
    {
        boolean bool = false;
        try
        {
            if (getMountService().fixPermissionsSecureContainer(paramString1, paramInt, paramString2) != 0)
                Log.i("PackageHelper", "Failed to fixperms container " + paramString1);
            while (true)
            {
                return bool;
                bool = true;
            }
        }
        catch (RemoteException localRemoteException)
        {
            while (true)
                Log.e("PackageHelper", "Failed to fixperms container " + paramString1 + " with exception " + localRemoteException);
        }
    }

    public static IMountService getMountService()
        throws RemoteException
    {
        IBinder localIBinder = ServiceManager.getService("mount");
        if (localIBinder != null)
            return IMountService.Stub.asInterface(localIBinder);
        Log.e("PackageHelper", "Can't get mount service");
        throw new RemoteException("Could not contact mount service");
    }

    public static String getSdDir(String paramString)
    {
        try
        {
            String str2 = getMountService().getSecureContainerPath(paramString);
            str1 = str2;
            return str1;
        }
        catch (RemoteException localRemoteException)
        {
            while (true)
            {
                Log.e("PackageHelper", "Failed to get container path for " + paramString + " with exception " + localRemoteException);
                String str1 = null;
            }
        }
    }

    public static String getSdFilesystem(String paramString)
    {
        try
        {
            String str2 = getMountService().getSecureContainerFilesystemPath(paramString);
            str1 = str2;
            return str1;
        }
        catch (RemoteException localRemoteException)
        {
            while (true)
            {
                Log.e("PackageHelper", "Failed to get container path for " + paramString + " with exception " + localRemoteException);
                String str1 = null;
            }
        }
    }

    public static String[] getSecureContainerList()
    {
        try
        {
            String[] arrayOfString2 = getMountService().getSecureContainerList();
            arrayOfString1 = arrayOfString2;
            return arrayOfString1;
        }
        catch (RemoteException localRemoteException)
        {
            while (true)
            {
                Log.e("PackageHelper", "Failed to get secure container list with exception" + localRemoteException);
                String[] arrayOfString1 = null;
            }
        }
    }

    public static boolean isContainerMounted(String paramString)
    {
        try
        {
            boolean bool2 = getMountService().isSecureContainerMounted(paramString);
            bool1 = bool2;
            return bool1;
        }
        catch (RemoteException localRemoteException)
        {
            while (true)
            {
                Log.e("PackageHelper", "Failed to find out if container " + paramString + " mounted");
                boolean bool1 = false;
            }
        }
    }

    public static String mountSdDir(String paramString1, String paramString2, int paramInt)
    {
        Object localObject = null;
        try
        {
            int i = getMountService().mountSecureContainer(paramString1, paramString2, paramInt);
            if (i != 0)
            {
                Log.i("PackageHelper", "Failed to mount container " + paramString1 + " rc : " + i);
            }
            else
            {
                String str = getMountService().getSecureContainerPath(paramString1);
                localObject = str;
            }
        }
        catch (RemoteException localRemoteException)
        {
            Log.e("PackageHelper", "MountService running?");
        }
        return localObject;
    }

    public static boolean renameSdDir(String paramString1, String paramString2)
    {
        boolean bool = false;
        try
        {
            int i = getMountService().renameSecureContainer(paramString1, paramString2);
            if (i != 0)
                Log.e("PackageHelper", "Failed to rename " + paramString1 + " to " + paramString2 + "with rc " + i);
            while (true)
            {
                return bool;
                bool = true;
            }
        }
        catch (RemoteException localRemoteException)
        {
            while (true)
                Log.i("PackageHelper", "Failed ot rename    " + paramString1 + " to " + paramString2 + " with exception : " + localRemoteException);
        }
    }

    public static boolean unMountSdDir(String paramString)
    {
        boolean bool = false;
        try
        {
            int i = getMountService().unmountSecureContainer(paramString, true);
            if (i != 0)
                Log.e("PackageHelper", "Failed to unmount " + paramString + " with rc " + i);
            while (true)
            {
                return bool;
                bool = true;
            }
        }
        catch (RemoteException localRemoteException)
        {
            while (true)
                Log.e("PackageHelper", "MountService running?");
        }
    }
}

/* Location:                     /home/lithium/miui/chameleon/2.11.16/framework_dex2jar.jar
 * Qualified Name:         com.android.internal.content.PackageHelper
 * JD-Core Version:        0.6.2
 */