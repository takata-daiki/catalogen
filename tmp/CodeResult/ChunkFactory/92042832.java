package com.x2a.spacegame.world;

import com.x2a.math.Vector2;

/**
 * Created by Ethan on 1/1/2015.
 */
public interface ChunkFactory<E extends Chunk> {
    public E newChunk(Vector2 location);

    public void resetFactory();

    public int getChunkWidth();
    public int getChunkHeight();
}
