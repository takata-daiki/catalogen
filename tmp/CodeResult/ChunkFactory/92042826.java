package com.x2a.spacegame.world;

import com.x2a.math.AxisAlignedBox;
import com.x2a.math.Vector2;
import com.x2a.scene.Camera;
import com.x2a.scene.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ethan on 1/1/2015.
 */
public class ChunkWorld<E extends Chunk> extends Node{
    private ChunkFactory chunkFactory;

    private Camera camera;

    private Map<String, E> chunks;

    public ChunkWorld(ChunkFactory<E> chunkFactory, Camera camera) {
        this.chunkFactory = chunkFactory;
        this.camera = camera;

        chunks = new HashMap<String, E>();
    }


    public void clearWorld() {
        chunks.clear();
    }


    @Override
    public void update(float timeElapsed) {
        AxisAlignedBox cameraView = camera.getView();

        Vector2 tileAtBottomRight = new Vector2((float)Math.ceil(cameraView.getBottomRight().x/chunkFactory.getChunkWidth())*chunkFactory.getChunkWidth(),
                (float)Math.ceil(cameraView.getBottomRight().y/(float)chunkFactory.getChunkHeight())*chunkFactory.getChunkHeight());

        for (Vector2 tileLoc = tileAtBottomRight; tileLoc.x > cameraView.getTopLeft().x-chunkFactory.getChunkWidth(); tileLoc.sub(new Vector2(chunkFactory.getChunkWidth(), 0))) {
            for (Vector2 tileLoc2 = new Vector2(tileLoc); tileLoc2.y > cameraView.getTopLeft().y-chunkFactory.getChunkHeight(); tileLoc2.sub(new Vector2(0, chunkFactory.getChunkHeight()))) {
                float xTransform = tileLoc2.x - (int)(chunkFactory.getChunkWidth()/2.0f);
                float yTransform = tileLoc2.y - (int)(chunkFactory.getChunkHeight()/2.0f);

                E chunk = chunks.get(tileLoc2.toString());

                if (chunk == null) {
                    chunk = (E)chunkFactory.newChunk(tileLoc2);
                    chunks.put(tileLoc2.toString(), chunk);
                }
                chunk.update(timeElapsed);
            }
        }
    }

    public Collection<E> getChunks() {
        return chunks.values();
    }

}
