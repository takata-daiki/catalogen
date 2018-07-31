package com.events;

public class MapInfo extends Event {

    private long seed;
    private int clusters;
    private int tilesInCluster;
    private float tileLenght;

    public MapInfo(EventType type) {
//        this(type, 0, 0, 0.0f);
        super(type);
    }

    public MapInfo(EventType type, long seed, int clusters, int tilesInCluster, float tileLength) {
        super(type);
        this.seed = seed;
        this.clusters = clusters;
        this.tilesInCluster = tilesInCluster;
        this.tileLenght = tileLength;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public int getClusters() {
        return clusters;
    }

    public void setClusters(int clusters) {
        this.clusters = clusters;
    }

    public int getTilesInCluster() {
        return tilesInCluster;
    }

    public void setTilesInCluster(int tilesInCluster) {
        this.tilesInCluster = tilesInCluster;
    }

    public float getTileLenght() {
        return tileLenght;
    }

    public void setTileLenght(float tileLenght) {
        this.tileLenght = tileLenght;
    }
}
