package com.discreteit.interactiveimageview;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.discreteit.interactiveimageview.ShaderHelper;
import com.discreteit.interactiveimageview.ScriptLoaderHelper;
import android.content.Context;

/**
 * Created by erikb on 7/18/13.
 */
public class ImageRenderer implements Renderer {

    //Storage for the ids
    public int mProgramId;
    public int mVertexId;
    public int mFragmentId;
    public String mVertexCode;
    public String mFragmentCode;

    private Context mContext;

    //TODO:  Need to find a way to intake the imageview image, grab its bounds, and convert image to bitmap (if it isn't already,
    //possibly via override?
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    public void onDrawFrame(GL10 unused) {

    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        //TODO:  Need to retain EGLContext if it is lost.
        mVertexCode = ScriptLoaderHelper.loadScriptFromResource(R.raw.base_vertex_shader, mContext);
        mFragmentCode = ScriptLoaderHelper.loadScriptFromResource(R.raw.base_fragment_shader, mContext);

        //Link program, compile shaders here.  Probably need ints for programid, vertex attributes, fragment attributes, etc.
        mVertexId = ShaderHelper.compileVertexShader(mVertexCode);
        mFragmentId = ShaderHelper.compileFragmentShader(mFragmentCode);

        //compile program.
        mProgramId = ShaderHelper.linkProgram(mVertexId, mFragmentId);

    }

    //doesn't really set a bitmap, but sets the bitmap to be 'texellated'
    public void setBitmap(Bitmap b) {

    }

    public ImageRenderer(Context context, Bitmap b) {
       this.mContext = context;
        //TODO:  Render the bitmap
    }
}
