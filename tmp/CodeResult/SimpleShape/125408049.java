package com.jebonicus.scene.geometry;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import org.w3c.dom.Element;

import com.jebonicus.scene.rendering.IRenderContext;

public class SimpleShape implements IGeometry {
    
    private int vertexCount;
    private FloatBuffer vertices;
    private FloatBuffer texCoords;

    private int[] vboVertices = new int[1];
    private int[] vboTexCoords = new int[1];

    public SimpleShape() {
        vertexCount = 3;

        vertices = FloatBuffer.allocate(vertexCount*3);
        texCoords = FloatBuffer.allocate(vertexCount*2);
        
        vertices.put(-5.0f).put(-5.0f).put( 0.0f);
        texCoords.put(0.0f).put(1.0f);
        vertices.put( 0.0f).put( 5.0f).put( 0.0f);
        texCoords.put(0.5f).put(0.0f);
        vertices.put( 5.0f).put(-5.0f).put( 0.0f);
        texCoords.put(1.0f).put(1.0f);
        //vertices.flip();
    }

    @Override
    public IGeometry initGeometry(final IRenderContext renderContext) {
        if(vertices != null && texCoords != null) {
            GL2 gl = renderContext.getDrawable().getGL().getGL2();
            gl.glGenBuffers(vboVertices.length, vboVertices, 0);
            gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboVertices[0]);
            gl.glBufferData(GL.GL_ARRAY_BUFFER, vertices.limit()*Float.SIZE, vertices.rewind(), GL.GL_STATIC_DRAW);

            gl.glGenBuffers(vboTexCoords.length, vboTexCoords, 0);
            gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboTexCoords[0]);
            gl.glBufferData(GL.GL_ARRAY_BUFFER, texCoords.limit()*Float.SIZE, texCoords.rewind(), GL.GL_STATIC_DRAW);
            vertices = null;
            texCoords = null;
        }
        return this;
    }

    public static IGeometry createSimpleShape(Element childElem) {
        SimpleShape shape = new SimpleShape();
        
        return shape;
    }

    @Override
    public int getVBO() {
        return vboVertices[0];
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public int getTextureCoordVBO() {
        return vboTexCoords[0];
    }

}
