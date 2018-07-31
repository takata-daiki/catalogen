package com.bwj.aage.map;

import com.bwj.aage.MapType;
import com.bwj.aage.Point;
import com.bwj.aage.Tile;
import com.bwj.aage.object.DownStair;
import com.bwj.aage.object.UpStair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * A cave map, which uses a cellular automata to create cave shaped maps.
 */
public class CaveMap extends UndergroundMap {

    public CaveMap(long seed) {
        super(seed, 80, 21);
    }

    public CaveMap() {
        super(0, 80, 21);
    }

    private final int INITIALFILLCHANCE = 40;
    private final int CELLULARREPETITIONS = 5; // TODO Make into parameter objects


    @Override
    public void generate(HashMap<String, Integer> param) {
        type = MapType.CAVE;
        Random r = new Random(getSeed());
        System.out.println("---");
        //int width = spawnerRandom.nextInt(20) + 80;
        //int height = spawnerRandom.nextInt(10) + 21;
        //setSize(width, height);

        //First, fill the map with random noise
        for(int i = 0; i < this.getWidth(); i++) {
            for(int j = 0; j < this.getHeight(); j++) {
                //Fill edges with walls
                if(i == 0 || j == 0 || i == getWidth() - 1 || j == getHeight() - 1);
                int toFill = r.nextInt(100);
                if(toFill > INITIALFILLCHANCE) {
                    //Fill with floor
                    terrain[i][j] = Tile.GroundTile;
                } else {
                    terrain[i][j] = Tile.WallTile;
                }
            }
        }
        //Perform cellular automata program a number of times

        for(int a = 0; a < 7; a++) {
            CaveMap newMap = new CaveMap();
            newMap.setSize(getWidth(), getHeight());
            newMap.fill(Tile.WallTile);
            for(int x = 1; x < getWidth() - 1; x++) {
                for(int y = 1; y < getHeight() - 1; y++) {
                    //Count the number of walls around (x,y)
                    int wallTile1 = countWalls(x,y, 1);
                    int wallTile2 = countWalls(x,y,2);
                    if(a < 4) {
                        if(!(wallTile1 >= 5 || wallTile2 <= 2)) {
                            newMap.terrain[x][y] = Tile.GroundTile;
                        }
                    }
                    else  {
                        if(!(wallTile1 >= 5)) {
                            newMap.terrain[x][y] = Tile.GroundTile;
                        }
                    }
                }
            }
            this.terrain = newMap.terrain;
        }

        //Place the character in a random ground tile
        int upx, upy;
        boolean success = false;
        while(!success) {
            //Generate the location of the upstair
            upx = r.nextInt(getWidth());
            upy = r.nextInt(getHeight());
            if(terrain[upx][upy].equals(Tile.GroundTile)) {
                UpStair stair = new UpStair(upx,upy, 0);
                //objects.add(stair);
                //break;
                int attempts = 0;
                //Then generate the downstair
                while(true) {
                    int x = r.nextInt(getWidth());
                    int y = r.nextInt(getHeight());
                    if(terrain[x][y].equals(Tile.GroundTile)) {
                        //Check there is a path between the points
                        ArrayList<Point> path = getPath(new Point(upx, upy), new Point(x, y));
                        if(path == null) {
                            attempts ++;
                            continue;
                        }
                        if(attempts >= 3) {
                            break;
                        }
                        //If there is, check the stairs are a decent distance apart
                        int distance = path.size();
                        if(distance >= 25) {
                            DownStair dstair = new DownStair(x,y, 0);
                            objects.add(dstair);
                            objects.add(stair);
                            success = true;
                            break;
                        }
                        else {
                            attempts++;
                        }
                    }
                }
                
            }
        }
    }

    /**
     * Counts the number of wall tiles around the given point in a given radius.
     * If search == 2, the diagonal points are not counted.
     * @param x Position of the point to search
     * @param y Position to search
     * @param search Radius around the point to search
     * @return The number of wall tiles in this radius
     */
    private int countWalls(int x, int y, int search) {
        int wallTile = 0;
        for(int i = x - search; i <= x + search; i++) {
            for(int j = y - search; j <= y + search; j++) {
                if(!(search == 2 && Math.abs(i - x) == 2 && Math.abs(j - y) == 2) && getTile(i, j) == Tile.WallTile)
                    wallTile++;
            }
        }
        return wallTile;
    }

    @Override
    public void update() {
        super.update();
        
    }

    /**
     * The player begins by default in the location of the first UpStair element.
     */
    public Point getStartingPosition() {
        ArrayList<UpStair> s = getObjects(UpStair.class);
        if(s.size() > 0) {
            int x = s.get(0).getX();
            int y = s.get(0).getY();

            return new Point(x,y);
        } else {
            return new Point(0,0);
        }
    }

	public Tile[][] getTerrain() {
		return terrain;
	}
}
