package com.companyname.blockgame;

import java.awt.*;
import java.util.*;
import java.io.*;

import static com.companyname.blockgame.Constants.*;
import com.companyname.blockgame.BlockTypes.*;

public class Level {
	public static int worldW = CHUNCK_SIZE * NUMBER_OF_CHUNKS_ON_ONE_SIDE, worldH = CHUNCK_SIZE * NUMBER_OF_CHUNKS_ON_ONE_SIDE;
	//public static Chunk[][] chunk = new Chunk [3][3];
//	public int tile[][] = new int [worldW][worldH];
//	public int tileNX[][] = new int [worldW][worldH];
//	public int tileNY[][] = new int [worldW][worldH];
//	public int tileNXY[][] = new int [worldW][worldH];
	public Chunk[][] chunk = new Chunk [NUMBER_OF_CHUNKS_ON_ONE_SIDE][NUMBER_OF_CHUNKS_ON_ONE_SIDE];
	public Block[][] block = new Block [CHUNCK_SIZE * NUMBER_OF_CHUNKS_ON_ONE_SIDE][CHUNCK_SIZE * NUMBER_OF_CHUNKS_ON_ONE_SIDE];
	public Block[][] block2 = new Block [worldW][worldH];
	public Block[][] block3 = new Block [worldW][worldH];
	public static float[][] mapHeight = new float[worldW][worldH]; //To get elevations through perlin noise
	public int chunkOffsetX = 0;
	public int chunkOffsetY = 0;
	public Random rand = new Random();
	public Random rand2 = new Random();
	public Random seed = new Random();
	public int Size = 100;
	public PerlinGenerator perlin;
	public BlockTypess test2 = new BlockTypess();
	public Font font = new Font("Arial", Font.PLAIN, 23);
	public SaveGame test = new SaveGame();
	public boolean testVar = true;
	public Chunk tempChunk;

	public Level() {
//		SaveGame test = new SaveGame();
		
		//Perlin Noise Generator
//		long rgenseed = System.currentTimeMillis();
		Random rgen = new Random();
		//rgen.setSeed(rgenseed);
		rgen.setSeed(0);
		//System.out.println("Random number generator seed is " + rgenseed);
		//perlin = new PerlinGenerator((int) rgenseed);
		perlin = new PerlinGenerator(0);
		
		
		//Get MapHeight
		for(int y=0; y<chunk.length;y++) {
			for(int x=0; x<chunk[0].length;x++) {
				for(int y2=0; y2<CHUNCK_SIZE;y2++) {
					for(int x2=0; x2<CHUNCK_SIZE;x2++) {
						mapHeight[(x*CHUNCK_SIZE)+x2][(y*CHUNCK_SIZE)+y2] = perlin.Noise(4 * ((y*CHUNCK_SIZE)+y2) / (float)Size, 4 * ((x*CHUNCK_SIZE)+x2) / (float)Size, 0);
					}
				}
			}
		}
		
		

		
		//Render air for block2
		for(int y=0; y<block2.length;y++) {
			for(int x=0; x<block2[0].length;x++) {				
				block2[x][y] = new Block(new Rectangle(x * TILE_SIZE + (int) Component.sX, y * TILE_SIZE + (int) Component.sY, TILE_SIZE, TILE_SIZE), AIR);
			}
		}
		
		for(int y=0; y<chunk.length;y++) {
			for(int x=0; x<chunk[0].length;x++) {
				chunk[x][y] = new Chunk(x,y);
				//System.out.println(x+","+y);
			}
		}
		

		
		//Render Chunks
		for(int y=0; y<chunk.length;y++) {
			for(int x=0; x<chunk[0].length;x++) {
				
				for(int y2=0; y2<chunk[x][y].cBlock.length;y2++) {
					for(int x2=0; x2<chunk[x][y].cBlock[0].length;x2++) {
						//System.out.println("Chunk: "+ x+","+y+"   "+ x2+","+y2);
						//System.out.println(chunk[x][y].cBlock[x][y].id[0]+","+chunk[x][y].cBlock[x][y].id[1]);
						//System.out.println(x2+(x*CHUNCK_SIZE) +","+(y2+(y*CHUNCK_SIZE)));
						block[(x*CHUNCK_SIZE)+x2][(y*CHUNCK_SIZE)+y2] = chunk[x][y].cBlock[x2][y2];
						//block2[(x*CHUNCK_SIZE)+x2][(y*CHUNCK_SIZE)+y2] = chunk[x][y].cBlock2[x2][y2];
					}
				}
			}
		}
		

		//AutoTile Check
		for(int y=1; y<block.length-1;y++) {
			for(int x=1; x<block[0].length-1;x++) {
				checkAutoTile(x, y);
			}
		}
		
		
//		for(int y=0; y<chunk.length;y++) {
//			for(int x=0; x<chunk[0].length;x++) {
//				test.saveChunk(x, y);
//				//System.out.println(x+","+y);
//			}
//		}
		
		
		//test.loadSave();

		//Copy chunk 0,0

		
		
		
/*
//		SaveGame test = new SaveGame();
//		test.loadSave();

		long rgenseed = System.currentTimeMillis();
		Random rgen = new Random();
		rgen.setSeed(rgenseed);
		//System.out.println("Random number generator seed is " + rgenseed);
		
		perlin = new PerlinGenerator((int) rgenseed);
		//Perlin = new PerlinGenerator(0);

		
		for(int y=0; y<block.length;y++) {
			for(int x=0; x<block[0].length;x++) {
				//block[x][y].id = Tile.earth;
				
				block2[x][y] = new Block(new Rectangle(x * TILE_SIZE + (int) Component.sX, y * TILE_SIZE + (int) Component.sY, TILE_SIZE, TILE_SIZE), Tile.air);
			}
		}
		
		for(int y=0; y<block.length;y++) {
			for(int x=0; x<block[0].length;x++) {
				block[x][y] = new Grass(new Rectangle(x * TILE_SIZE + (int) Component.sX, y * TILE_SIZE + (int) Component.sY, TILE_SIZE, TILE_SIZE), Tile.grass);
				mapHeight[x][y] = perlin.Noise(4 * y / (float)Size, 4 * x / (float)Size, 0);
			}
		}
		for(int y=0; y<block.length;y++) {
			for(int x=0; x<block[0].length;x++) {
				if(y == 0 || x == 0 || y == block.length-1 || x == block[0].length-1) {
					block2[x][y] = new Bedrock(new Rectangle(x * TILE_SIZE + (int) Component.sX, y * TILE_SIZE + (int) Component.sY, TILE_SIZE, TILE_SIZE), Tile.bedrock);	
				}
			}
		}
		
		
		

//		
		for(int y=0; y<block.length;y++) {
			for(int x=0; x<block[0].length;x++) {
				if(mapHeight[x][y] < -0.2) {
					block[x][y] = new Water(new Rectangle(x * TILE_SIZE + (int) Component.sX, y * TILE_SIZE + (int) Component.sY, TILE_SIZE, TILE_SIZE), Tile.water);
					
				
				}
			}
		}
//TREES
//		for(int y=0; y<block2.length;y++) {
//			for(int x=0; x<block2[0].length;x++) {
//				Random rand = new Random();
//				int  n = rand.nextInt(10) + 1;
//				if(n == 1 && block[x][y].id == Tile.grass && block2[x][y].id != Tile.bedrock) {
//					block2[x][y] = new Tree(new Rectangle(x * TILE_SIZE + (int) Component.sX, y * TILE_SIZE + (int) Component.sY, TILE_SIZE, TILE_SIZE), Tile.tree);
//				}
//			}
//		}

		for(int y=1; y<block.length-1;y++) {
			for(int x=1; x<block[0].length-1;x++) {
					checkAutoTile(x, y);
			}
		}
		
		//TESTING FOR SAVE FILE STUFF...
		//block[14][20].id = Tile.air;
		//checkAutoTile(6, 2);
		//System.out.println(block[6][2].id[0]+","+block[6][2].id[1]);

		//block[5][5] = new Block(new Rectangle(5 * TILE_SIZE + (int) Component.sX, 5 * TILE_SIZE + (int) Component.sY, TILE_SIZE, TILE_SIZE), Tile.bedrock);

		//System.out.println(block[6][2].id[1]);
		BlockType test2 = new BlockType();
		System.out.println(test2.example.get("Wayne"));
		String test15 = "Wayne";
		test2.x1 = 5;
		test2.y1 = 5;
		test2.newBlock();
		block[5][5] = test2.example.get(test15);
//		test2.x1 = 5;
//		test2.y1 = 7;
//		test2.newBlock();
//		block[5][7] = test2.example.get(test15);
 */
	}
	
	public void saveChunk() {
		for(int y=0; y<chunk.length;y++) {
			for(int x=0; x<chunk[0].length;x++) {
				test.saveChunk(x, y,x,y);
				//System.out.println(x+","+y);
			}
		}
	}
	
	public void loadChunk() {
		for(int y=0; y<chunk.length;y++) {
			for(int x=0; x<chunk[0].length;x++) {
				test.loadChunk(x, y);
				//System.out.println(x+","+y);
			}
		}
	}
	
	
	public void checkAutoTile(int x1, int y1) {
		if(block[x1][y1].baseId != GRASS && x1 !=0 && x1 < worldW-1 && y1 != 0 && y1 < worldH-1) {
			boolean top = false;
			boolean left = false;
			boolean right = false;
			boolean bottom = false;
			boolean topLeft = false;
			boolean topRight = false;
			boolean bottomLeft = false;
			boolean bottomRight = false;
			block[x1][y1].autoTile1[0] = 0;
			block[x1][y1].autoTile1[1] = 0;
			block[x1][y1].autoTile2[0] = 0;
			block[x1][y1].autoTile2[1] = 0;
			block[x1][y1].autoTile3[0] = 0;
			block[x1][y1].autoTile3[1] = 0;
			block[x1][y1].autoTile4[0] = 0;
			block[x1][y1].autoTile4[1] = 0;
			
			if(block[x1][y1].baseId == block[x1][y1-1].baseId) {
				top = true;
			}
			if(block[x1][y1].baseId == block[x1-1][y1].baseId) {
				left = true;
			}
			if(block[x1][y1].baseId == block[x1+1][y1].baseId) {
				right = true;
			}
			if(block[x1][y1].baseId == block[x1][y1+1].baseId) {
				bottom = true;
			}
			if(block[x1][y1].baseId == block[x1-1][y1-1].baseId) {
				topLeft = true;
			}
			if(block[x1][y1].baseId == block[x1+1][y1-1].baseId) {
				topRight = true;
			}
			if(block[x1][y1].baseId == block[x1-1][y1+1].baseId) {
				bottomLeft = true;
			}
			if(block[x1][y1].baseId == block[x1+1][y1+1].baseId) {
				bottomRight = true;
			}
			
			
			if(top == false && left == false && right == false && bottom == false) {
			//I don't think I need to do anything because this is the base tile.  
			}
			
			if(top == false && left == false && right == true && bottom == false) {
				block[x1][y1].autoTile1[1] = 1;
				block[x1][y1].autoTile2[1] = 1;
				block[x1][y1].autoTile3[1] = 3;
				block[x1][y1].autoTile4[1] = 3;
			}
			if(top == true && left == false && right == false && bottom == false) {
				block[x1][y1].autoTile1[1] = 3;
				block[x1][y1].autoTile2[0] = 2;
				block[x1][y1].autoTile2[1] = 3;
				block[x1][y1].autoTile3[1] = 3;
				block[x1][y1].autoTile4[0] = 2;
				block[x1][y1].autoTile4[1] = 3;
				
			}
			if(top == true && left == false && right == true && bottom == false) {
				block[x1][y1].autoTile1[1] = 3;
				block[x1][y1].autoTile3[1] = 3;
				block[x1][y1].autoTile4[1] = 3;
				if(topRight == false) {
					block[x1][y1].autoTile2[0] = 2;
					
				} else {
					block[x1][y1].autoTile2[1] = 3;
					
				}
			}
			if(top == false && left == true && right == false && bottom == false) {
				block[x1][y1].autoTile1[0] = 2;
				block[x1][y1].autoTile2[0] = 2;
				block[x1][y1].autoTile3[0] = 2;
				block[x1][y1].autoTile4[0] = 2;
				block[x1][y1].autoTile1[1] = 1;
				block[x1][y1].autoTile2[1] = 1;
				block[x1][y1].autoTile3[1] = 3;
				block[x1][y1].autoTile4[1] = 3;
			}
			if(top == false && left == true && right == true && bottom == false) {
				block[x1][y1].autoTile1[0] = 1;
				block[x1][y1].autoTile2[0] = 1;
				block[x1][y1].autoTile3[0] = 1;
				block[x1][y1].autoTile4[0] = 1;
				block[x1][y1].autoTile1[1] = 1;
				block[x1][y1].autoTile2[1] = 1;
				block[x1][y1].autoTile3[1] = 3;
				block[x1][y1].autoTile4[1] = 3;
			}
			if(top == true && left == true && right == false && bottom == false) {
				block[x1][y1].autoTile2[0] = 2;
				block[x1][y1].autoTile3[0] = 2;
				block[x1][y1].autoTile4[0] = 2;
				block[x1][y1].autoTile2[1] = 3;
				block[x1][y1].autoTile3[1] = 3;
				block[x1][y1].autoTile4[1] = 3;
				if(topLeft == false) {
					block[x1][y1].autoTile1[0] = 2;
					
				} else {
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile1[1] = 3;
					
				}
			}
			if(top == true && left == true && right == true && bottom == false) {
				block[x1][y1].autoTile1[0] = 1;
				block[x1][y1].autoTile2[0] = 1;
				block[x1][y1].autoTile3[0] = 1;
				block[x1][y1].autoTile4[0] = 1;
				block[x1][y1].autoTile1[1] = 3;
				block[x1][y1].autoTile2[1] = 3;
				block[x1][y1].autoTile3[1] = 3;
				block[x1][y1].autoTile4[1] = 3;
				if(topRight == false && topLeft == false) {
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile2[0] = 2;
					block[x1][y1].autoTile1[1] = 0;
					block[x1][y1].autoTile2[1] = 0;
				}else if (topRight == true && topLeft == false) {
					block[x1][y1].autoTile1[1] = 0;
					block[x1][y1].autoTile1[0] = 2;
				}else if (topRight == false && topLeft == true) {
					block[x1][y1].autoTile2[1] = 0;
					block[x1][y1].autoTile2[0] = 2;
					
				}
					
			}
			if(top == false && left == false && right == false && bottom == true) {
				block[x1][y1].autoTile1[1] = 1;
				block[x1][y1].autoTile2[0] = 2;
				block[x1][y1].autoTile2[1] = 1;
				block[x1][y1].autoTile3[1] = 1;
				block[x1][y1].autoTile4[0] = 2;
				block[x1][y1].autoTile4[1] = 1;
			}
			
			if(top == false && left == false && right == true && bottom == true) {
				block[x1][y1].autoTile1[1] = 1;
				block[x1][y1].autoTile2[1] = 1;
				block[x1][y1].autoTile3[1] = 1;
				block[x1][y1].autoTile4[1] = 1;
				
				if(bottomRight == false) {
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
				}
			}
			if(top == true && left == false && right == false && bottom == true) {
				block[x1][y1].autoTile1[1] = 2;
				block[x1][y1].autoTile2[0] = 2;
				block[x1][y1].autoTile2[1] = 2;
				block[x1][y1].autoTile3[1] = 2;
				block[x1][y1].autoTile4[0] = 2;
				block[x1][y1].autoTile4[1] = 2;
			}
			if(top == true && left == false && right == true && bottom == true) {
				block[x1][y1].autoTile1[1] = 2;
				block[x1][y1].autoTile2[1] = 2;
				block[x1][y1].autoTile3[1] = 2;
				block[x1][y1].autoTile4[1] = 2;
				if(topRight == false && bottomRight == false) {
					block[x1][y1].autoTile2[0] = 2;
					block[x1][y1].autoTile2[1] = 0;
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
				} else if (topRight == true && bottomRight == false) {
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
					
				} else if (topRight == false && bottomRight == true) {
					block[x1][y1].autoTile2[0] = 2;
					block[x1][y1].autoTile2[1] = 0;
				}
			}
			if(top == false && left == true && right == false && bottom == true) {
				block[x1][y1].autoTile1[0] = 2;
				block[x1][y1].autoTile1[1] = 1;
				block[x1][y1].autoTile2[0] = 2;
				block[x1][y1].autoTile2[1] = 1;
				block[x1][y1].autoTile3[0] = 2;
				block[x1][y1].autoTile3[1] = 1;
				block[x1][y1].autoTile4[0] = 2;
				block[x1][y1].autoTile4[1] = 1;
				if(bottomLeft == false) {
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
				}
			}
			if(top == false && left == true && right == true && bottom == true) {
				block[x1][y1].autoTile1[0] = 1;
				block[x1][y1].autoTile1[1] = 1;
				block[x1][y1].autoTile2[0] = 1;
				block[x1][y1].autoTile2[1] = 1;
				block[x1][y1].autoTile3[0] = 1;
				block[x1][y1].autoTile3[1] = 1;
				block[x1][y1].autoTile4[0] = 1;
				block[x1][y1].autoTile4[1] = 1;
				if(bottomRight == false && bottomLeft == false) {
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
				} else if (bottomRight == true && bottomLeft == false) {
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
					
				} else if (bottomRight == false && bottomLeft == true) {
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
				}
			}
			if(top == true && left == true && right == false && bottom == true) {
				block[x1][y1].autoTile1[0] = 2;
				block[x1][y1].autoTile1[1] = 2;
				block[x1][y1].autoTile2[0] = 2;
				block[x1][y1].autoTile2[1] = 2;
				block[x1][y1].autoTile3[0] = 2;
				block[x1][y1].autoTile3[1] = 2;
				block[x1][y1].autoTile4[0] = 2;
				block[x1][y1].autoTile4[1] = 2;
				if(topLeft == false && bottomLeft == false) {
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile1[1] = 0;
				} else if (topLeft == true && bottomLeft == false) {
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
					
				} else if (topLeft == false && bottomLeft == true) {
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile1[1] = 0;
				}
			}
			if(top == true && left == true && right == true && bottom == true) {
				block[x1][y1].autoTile1[0] = 1;
				block[x1][y1].autoTile1[1] = 2;
				block[x1][y1].autoTile2[0] = 1;
				block[x1][y1].autoTile2[1] = 2;
				block[x1][y1].autoTile3[0] = 1;
				block[x1][y1].autoTile3[1] = 2;
				block[x1][y1].autoTile4[0] = 1;
				block[x1][y1].autoTile4[1] = 2;
				if(topRight == false && topLeft == false && bottomRight == false && bottomLeft == false) {
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile1[1] = 0;
					block[x1][y1].autoTile2[0] = 2;
					block[x1][y1].autoTile2[1] = 0;
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
				} else if (topRight == true && topLeft == false && bottomRight == false && bottomLeft == false) {
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile1[1] = 0;
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
				} else if (topRight == false && topLeft == true && bottomRight == false && bottomLeft == false) {
					block[x1][y1].autoTile2[0] = 2;
					block[x1][y1].autoTile2[1] = 0;
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
				} else if (topRight == false && topLeft == false && bottomRight == true && bottomLeft == false) {
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile1[1] = 0;
					block[x1][y1].autoTile2[0] = 2;
					block[x1][y1].autoTile2[1] = 0;
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
				} else if (topRight == false && topLeft == false && bottomRight == false && bottomLeft == true) {
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile1[1] = 0;
					block[x1][y1].autoTile2[0] = 2;
					block[x1][y1].autoTile2[1] = 0;
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
				} else if (topRight == true && topLeft == true && bottomRight == false && bottomLeft == false) {
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
					
				} else if (topRight == false && topLeft == true && bottomRight == true && bottomLeft == false) {
					block[x1][y1].autoTile2[0] = 2;
					block[x1][y1].autoTile2[1] = 0;
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
				} else if (topRight == false && topLeft == false && bottomRight == true && bottomLeft == true) {
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile1[1] = 0;
					block[x1][y1].autoTile2[0] = 2;
					block[x1][y1].autoTile2[1] = 0;
				} else if (topRight == true && topLeft == false && bottomRight == false && bottomLeft == true) {
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile1[1] = 0;
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
				} else if (topRight == true && topLeft == false && bottomRight == true && bottomLeft == false) {
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile1[1] = 0;
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
				} else if (topRight == false && topLeft == true&& bottomRight == false && bottomLeft == true) {
					block[x1][y1].autoTile2[0] = 2;
					block[x1][y1].autoTile2[1] = 0;
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
				} else if (topRight == true && topLeft == true && bottomRight == true && bottomLeft == false) {
					block[x1][y1].autoTile3[0] = 2;
					block[x1][y1].autoTile3[1] = 0;
					
				} else if (topRight == false && topLeft == true && bottomRight == true && bottomLeft == true) {
					block[x1][y1].autoTile2[0] = 2;
					block[x1][y1].autoTile2[1] = 0;
					
				} else if (topRight == true && topLeft == false && bottomRight == true && bottomLeft == true) {
					
					block[x1][y1].autoTile1[0] = 2;
					block[x1][y1].autoTile1[1] = 0;
				} else if (topRight == true && topLeft == true && bottomRight == false && bottomLeft == true) {
					block[x1][y1].autoTile4[0] = 2;
					block[x1][y1].autoTile4[1] = 0;
				} 
			}	
		}
	}
	
	public void building(int camX, int camY, int renW, int renH) {
		if(Component.isMouseLeft || Component.isMouseRight) {
			for(int x=(camX /TILE_SIZE)-(chunkOffsetX*CHUNCK_SIZE); x<(camX / TILE_SIZE) + renW-(chunkOffsetX*CHUNCK_SIZE);x++) {
				for(int y=(camY / TILE_SIZE)-(chunkOffsetY*CHUNCK_SIZE); y<(camY / TILE_SIZE) + renH-(chunkOffsetY*CHUNCK_SIZE);y++) {
					if(x >= 0 && y >= 0 && x < worldW && y < worldH) {
						if(block[x][y].contains(new Point((Component.mse.x / Component.pixelSize) + (int) Component.sX, (Component.mse.y / Component.pixelSize) + (int) Component.sY))) {
							block[x][y].isDigAnimationVisible = true;
							int sid = Inventory.invBar[Inventory.selected].item.id;
							if(Component.isMouseLeft) {
								if(Inventory.invBar[Inventory.selected].item.id == SWORD) {
									if(Component.weapon.isInUse == false) {
										Component.weapon.isInUse = true;
										Component.weapon.animationFrame = 0;
										Component.weapon.getAnimationAngle();
									}
								}else if(Inventory.invBar[Inventory.selected].item.id == SHOVEL && block2[x][y].id == AIR && block[x][y].id != DIRT && block[x][y].id != WATER) {
										block[x][y].hitPoints --;
										if(block[x][y].hitPoints < 1) {
											block[x][y].destroy(x,y);
											autoTileCleanUp(x,y);
										}
								}
								
							} else if (Component.isMouseRight) {
								if(block[x][y].id == HOLE) {
									
									if(sid == EARTH_CLUMP) {
										block[x][y] = new Dirt(new Rectangle(x * TILE_SIZE, y * TILE_SIZE,TILE_SIZE, TILE_SIZE),DIRT);
										Inventory.removeFromInventory(sid, 1);
										autoTileCleanUp(x,y);
									} 
									
									
								}
								if(block[x][y].id == DIRT) {
									if(sid == GRASS_SEED) {
										block[x][y] = new Grass(new Rectangle(x * TILE_SIZE, y * TILE_SIZE,TILE_SIZE, TILE_SIZE), GRASS);
										Inventory.removeFromInventory(sid, 1);
										autoTileCleanUp(x,y);
									}
								}
								
							}
							
							break;
							
						}
						block[x][y].isDigAnimationVisible = false;
						
						
					}
					
				}
			}
		} 
	}
	
	public void autoTileCleanUp( int x1, int y1) {
		for(int y=1; y<block.length-1;y++) {
			for(int x=1; x<block[0].length-1;x++) {
				
				//if(block[x][y].heightMap > -5) {
					checkAutoTile(x, y);
				//}
			}
		}
		//System.out.println(block[2][2].autoTile1[0]+","+ block[2][2].autoTile1[1]);
	}
	
	public void tick(int camX, int camY, int renW, int renH) {
		//System.out.println(chunkOffsetX);
		if(!Inventory.isOpen) {
			building(camX, camY, renW, renH);
		}
		
		int mobSpawn = rand.nextInt(30) + 1;
		
		if(mobSpawn == 1) {
//		    int mobLocationX = rand.nextInt(worldW-2)+1;
//		    int mobLocationY = rand.nextInt(worldH-2)+1;
//			int mobID2 = Component.mob.size();
//			Component.mob.add(new Chicken(mobLocationX * TILE_SIZE,mobLocationY * TILE_SIZE,TILE_SIZE,TILE_SIZE * 2, Tile.mobChicken, Component.mobID));
//			Component.mobID = mobID2+1;
		}
		//NOTE!  If you ever decide to have the character start anywhere other than tile 1-1 then you must adjust mapheight somehow...
		//If character is to the left of the middle chunk 9
		if(Component.character.x < block[0][0].x + CHUNCK_SIZE *TILE_SIZE - TILE_SIZE/2) {
			File f = new File("chunks/0-0.sav");
			if(Component.character.x> CHUNCK_SIZE*TILE_SIZE) {
				System.out.println("Character stepped one chunk to the left");
				chunkOffsetX = chunkOffsetX -1;
				
				
	
				test.saveChunk(chunk[2][0].chunkX,chunk[2][0].chunkY,2,0);
				test.saveChunk(chunk[2][1].chunkX,chunk[2][1].chunkY,2,1);
				test.saveChunk(chunk[2][2].chunkX,chunk[2][2].chunkY,2,2);
				
				chunk[2][0] = chunk[1][0];
				chunk[2][1] = chunk[1][1];
				chunk[2][2] = chunk[1][2];
				chunk[1][0] = chunk[0][0];
				chunk[1][1] = chunk[0][1];
				chunk[1][2] = chunk[0][2];
				
				f = new File("chunks/"+chunkOffsetX+"-"+chunkOffsetY +".sav");
				if(f.exists()) {
					test.loadChunk(chunkOffsetX,chunkOffsetY);
					chunk[0][0] = tempChunk;
				} else {
					for(int y2=0; y2<CHUNCK_SIZE;y2++) {
						for(int x2=0; x2<CHUNCK_SIZE;x2++) {
							mapHeight[((chunkOffsetX)*CHUNCK_SIZE)+x2][(chunkOffsetY*CHUNCK_SIZE)+y2] = perlin.Noise(4 * ((chunkOffsetY*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX)*CHUNCK_SIZE)+x2) / (float)Size, 0);
						}
					}
					chunk[0][0] = new Chunk(chunkOffsetX,chunkOffsetY);
				}
				
				f = new File("chunks/"+chunkOffsetX+"-"+(chunkOffsetY +1)+".sav");
				if(f.exists()) {
					test.loadChunk(chunkOffsetX,chunkOffsetY+1);
					chunk[0][1] = tempChunk;
				} else {
					for(int y2=0; y2<CHUNCK_SIZE;y2++) {
						for(int x2=0; x2<CHUNCK_SIZE;x2++) {
							mapHeight[((chunkOffsetX)*CHUNCK_SIZE)+x2][((chunkOffsetY+1)*CHUNCK_SIZE)+y2] = perlin.Noise(4 * (((chunkOffsetY+1)*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX)*CHUNCK_SIZE)+x2) / (float)Size, 0);
						}
					}
					chunk[0][1] = new Chunk(chunkOffsetX,chunkOffsetY+1);
				}
				
				f = new File("chunks/"+chunkOffsetX+"-"+(chunkOffsetY +2)+".sav");
				if(f.exists()) {
					test.loadChunk(chunkOffsetX,chunkOffsetY+2);
					chunk[0][2] = tempChunk;
				} else {
					for(int y2=0; y2<CHUNCK_SIZE;y2++) {
						for(int x2=0; x2<CHUNCK_SIZE;x2++) {
							mapHeight[((chunkOffsetX)*CHUNCK_SIZE)+x2][((chunkOffsetY+2)*CHUNCK_SIZE)+y2] = perlin.Noise(4 * (((chunkOffsetY+2)*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX)*CHUNCK_SIZE)+x2) / (float)Size, 0);
						}
					}
					chunk[0][2] = new Chunk(chunkOffsetX,chunkOffsetY+2);
				}
				
				for(int y=0; y<chunk.length;y++) {
					for(int x=0; x<chunk[0].length;x++) {
						
						for(int y2=0; y2<chunk[x][y].cBlock.length;y2++) {
							for(int x2=0; x2<chunk[x][y].cBlock[0].length;x2++) {
								//System.out.println("Chunk: "+ x+","+y+"   "+ x2+","+y2);
								//System.out.println(chunk[x][y].cBlock[x][y].id[0]+","+chunk[x][y].cBlock[x][y].id[1]);
								//System.out.println(x2+(x*CHUNCK_SIZE) +","+(y2+(y*CHUNCK_SIZE)));
								block[(x*CHUNCK_SIZE)+x2][(y*CHUNCK_SIZE)+y2] = chunk[x][y].cBlock[x2][y2];
							}
						}
					}
				}
				autoTileCleanUp(0,0);
			}
		}
		//If character is to the Top of the middle chunk 9
		if(Component.character.y < block[0][0].y + CHUNCK_SIZE *TILE_SIZE - TILE_SIZE) {
			if(Component.character.y > CHUNCK_SIZE*TILE_SIZE) {
				System.out.println("Character stepped one chunk to the top");
				chunkOffsetY = chunkOffsetY -1;
				
				
	
				test.saveChunk(chunk[0][2].chunkX,chunk[0][2].chunkY, 0,2);
				test.saveChunk(chunk[1][2].chunkX,chunk[1][2].chunkY, 1,2);
				test.saveChunk(chunk[2][2].chunkX,chunk[2][2].chunkY, 2,2);
				
				chunk[0][2] = chunk[0][1];
				chunk[1][2] = chunk[1][1];
				chunk[2][2] = chunk[2][1];
				chunk[0][1] = chunk[0][0];
				chunk[1][1] = chunk[1][0];
				chunk[2][1] = chunk[2][0];
				
				File f = new File("chunks/"+chunkOffsetX+"-"+chunkOffsetY+".sav");
				if(f.exists()) {
					
					test.loadChunk(chunkOffsetX,chunkOffsetY);
					chunk[0][0] = tempChunk;
				} else {
					for(int y2=0; y2<CHUNCK_SIZE;y2++) {
						for(int x2=0; x2<CHUNCK_SIZE;x2++) {
							mapHeight[((chunkOffsetX)*CHUNCK_SIZE)+x2][(chunkOffsetY*CHUNCK_SIZE)+y2] = perlin.Noise(4 * ((chunkOffsetY*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX)*CHUNCK_SIZE)+x2) / (float)Size, 0);
						}
					}
					chunk[0][0] = new Chunk(chunkOffsetX,chunkOffsetY);
				}
				
				f = new File("chunks/"+(chunkOffsetX+1)+"-"+chunkOffsetY+".sav");
				if(f.exists()) {
					test.loadChunk(chunkOffsetX+1,chunkOffsetY);
					chunk[1][0] = tempChunk;
				} else {
					for(int y2=0; y2<CHUNCK_SIZE;y2++) {
						for(int x2=0; x2<CHUNCK_SIZE;x2++) {
							mapHeight[((chunkOffsetX+1)*CHUNCK_SIZE)+x2][(chunkOffsetY*CHUNCK_SIZE)+y2] = perlin.Noise(4 * ((chunkOffsetY*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX+1)*CHUNCK_SIZE)+x2) / (float)Size, 0);
						}
					}
					chunk[1][0] = new Chunk(chunkOffsetX+1,chunkOffsetY);
				}
				
				f = new File("chunks/"+(chunkOffsetX+2)+"-"+chunkOffsetY+".sav");
				if(f.exists()) {
					test.loadChunk(chunkOffsetX+2,chunkOffsetY);
					chunk[2][0] = tempChunk;
				} else {
					for(int y2=0; y2<CHUNCK_SIZE;y2++) {
						for(int x2=0; x2<CHUNCK_SIZE;x2++) {
							mapHeight[((chunkOffsetX+2)*CHUNCK_SIZE)+x2][(chunkOffsetY*CHUNCK_SIZE)+y2] = perlin.Noise(4 * ((chunkOffsetY*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX+2)*CHUNCK_SIZE)+x2) / (float)Size, 0);
						}
					}
					chunk[2][0] = new Chunk(chunkOffsetX+2,chunkOffsetY);
				}
				
				for(int y=0; y<chunk.length;y++) {
					for(int x=0; x<chunk[0].length;x++) {
						for(int y2=0; y2<chunk[x][y].cBlock.length;y2++) {
							for(int x2=0; x2<chunk[x][y].cBlock[0].length;x2++) {
								//System.out.println("Chunk: "+ x+","+y+"   "+ x2+","+y2);
								//System.out.println(chunk[x][y].cBlock[x][y].id[0]+","+chunk[x][y].cBlock[x][y].id[1]);
								//System.out.println(x2+(x*CHUNCK_SIZE) +","+(y2+(y*CHUNCK_SIZE)));
								block[(x*CHUNCK_SIZE)+x2][(y*CHUNCK_SIZE)+y2] = chunk[x][y].cBlock[x2][y2];
							}
						}
					}
				}
				autoTileCleanUp(0,0);
			}
		}
		//If character is to the right of the middle chunk 21

		if(Component.character.x > block[0][0].x + CHUNCK_SIZE * 2 *TILE_SIZE) {
			
			System.out.println("Character stepped one chunk to the right.");
			
			chunkOffsetX = chunkOffsetX +1;
			//public static float[][] mapHeight = new float[worldW][worldH]; //To get elevations through perlin noise
			if(mapHeight.length/CHUNCK_SIZE-3 < chunkOffsetX) {
				float[][] mapHeightNew = new float[mapHeight.length+CHUNCK_SIZE][mapHeight[0].length];
				mapHeight = mapHeightNew;
			}
			
			test.saveChunk(chunk[0][0].chunkX,chunk[0][0].chunkY, 0,0);
			test.saveChunk(chunk[0][1].chunkX,chunk[0][1].chunkY, 0,1);
			test.saveChunk(chunk[0][2].chunkX,chunk[0][2].chunkY, 0,2);
			
			chunk[0][0] = chunk[1][0];
			chunk[0][1] = chunk[1][1];
			chunk[0][2] = chunk[1][2];
			chunk[1][0] = chunk[2][0];
			chunk[1][1] = chunk[2][1];
			chunk[1][2] = chunk[2][2];
			
			File f = new File("chunks/"+(chunkOffsetX+2)+"-"+chunkOffsetY+".sav");
			if(f.exists()) {
				
				test.loadChunk(chunkOffsetX+2,chunkOffsetY);
				chunk[2][0] = tempChunk;
			} else {
				for(int y2=0; y2<CHUNCK_SIZE;y2++) {
					for(int x2=0; x2<CHUNCK_SIZE;x2++) {
						mapHeight[((chunkOffsetX+2)*CHUNCK_SIZE)+x2][(chunkOffsetY*CHUNCK_SIZE)+y2] = perlin.Noise(4 * ((chunkOffsetY*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX+2)*CHUNCK_SIZE)+x2) / (float)Size, 0);
					}
				}
				chunk[2][0] = new Chunk(2+chunkOffsetX,chunkOffsetY);
			}
			
			f = new File("chunks/"+(chunkOffsetX+2)+"-"+(chunkOffsetY+1)+".sav");
			if(f.exists()) {
				test.loadChunk(chunkOffsetX+2,chunkOffsetY+1);
				chunk[2][1] = tempChunk;
			} else {
				for(int y2=0; y2<CHUNCK_SIZE;y2++) {
					for(int x2=0; x2<CHUNCK_SIZE;x2++) {
						mapHeight[((chunkOffsetX+2)*CHUNCK_SIZE)+x2][((chunkOffsetY+1)*CHUNCK_SIZE)+y2] = perlin.Noise(4 * (((chunkOffsetY+1)*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX+2)*CHUNCK_SIZE)+x2) / (float)Size, 0);
					}
				}
				chunk[2][1] = new Chunk(2+chunkOffsetX,chunkOffsetY+1);
			}
			
			f = new File("chunks/"+(chunkOffsetX+2)+"-"+(chunkOffsetY+2)+".sav");
			if(f.exists()) {
				test.loadChunk(chunkOffsetX+2,chunkOffsetY+2);
				chunk[2][2] = tempChunk;
			} else {
				for(int y2=0; y2<CHUNCK_SIZE;y2++) {
					for(int x2=0; x2<CHUNCK_SIZE;x2++) {
						mapHeight[((chunkOffsetX+2)*CHUNCK_SIZE)+x2][((chunkOffsetY+2)*CHUNCK_SIZE)+y2] = perlin.Noise(4 * (((chunkOffsetY+2)*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX+2)*CHUNCK_SIZE)+x2) / (float)Size, 0);
					}
				}
				chunk[2][2] = new Chunk(2+chunkOffsetX,chunkOffsetY+2);
			}
			
	
			
			
			
			for(int y=0; y<chunk.length;y++) {
				for(int x=0; x<chunk[0].length;x++) {
					
					for(int y2=0; y2<chunk[x][y].cBlock.length;y2++) {
						for(int x2=0; x2<chunk[x][y].cBlock[0].length;x2++) {
							//System.out.println("Chunk: "+ x+","+y+"   "+ x2+","+y2);
							//System.out.println(chunk[x][y].cBlock[x][y].id[0]+","+chunk[x][y].cBlock[x][y].id[1]);
							//System.out.println(x2+(x*CHUNCK_SIZE) +","+(y2+(y*CHUNCK_SIZE)));
							block[(x*CHUNCK_SIZE)+x2][(y*CHUNCK_SIZE)+y2] = chunk[x][y].cBlock[x2][y2];
							
						}
					}
				}
			}
			//System.out.println(block[0][0].x);
			autoTileCleanUp(0,0);
			
		}
		//If character is to the bottom of the middle chunk 21
		if(Component.character.y > block[0][0].y + CHUNCK_SIZE * 2 *TILE_SIZE - TILE_SIZE) {
			
			System.out.println("Character stepped one chunk to the bottom.");
			chunkOffsetY = chunkOffsetY +1;
			//public static float[][] mapHeight = new float[worldW][worldH]; //To get elevations through perlin noise
			
			float[][] mapHeightNew = new float[mapHeight.length][mapHeight[0].length+CHUNCK_SIZE];
			mapHeight = mapHeightNew;

					for(int y2=0; y2<CHUNCK_SIZE;y2++) {
						for(int x2=0; x2<CHUNCK_SIZE;x2++) {
							mapHeight[(0*CHUNCK_SIZE)+x2][((2+chunkOffsetY)*CHUNCK_SIZE)+y2] = perlin.Noise(4 * (((2+chunkOffsetY)*CHUNCK_SIZE)+y2) / (float)Size, 4 * ((0*CHUNCK_SIZE)+x2) / (float)Size, 0);
						}
					}
					for(int y2=0; y2<CHUNCK_SIZE;y2++) {
						for(int x2=0; x2<CHUNCK_SIZE;x2++) {
							mapHeight[(0*CHUNCK_SIZE)+x2][((2+chunkOffsetY)*CHUNCK_SIZE)+y2] = perlin.Noise(4 * (((2+chunkOffsetY)*CHUNCK_SIZE)+y2) / (float)Size, 4 * ((0*CHUNCK_SIZE)+x2) / (float)Size, 0);
						}
					}
					for(int y2=0; y2<CHUNCK_SIZE;y2++) {
						for(int x2=0; x2<CHUNCK_SIZE;x2++) {
							mapHeight[(0*CHUNCK_SIZE)+x2][((2+chunkOffsetY)*CHUNCK_SIZE)+y2] = perlin.Noise(4 * (((2+chunkOffsetY)*CHUNCK_SIZE)+y2) / (float)Size, 4 * ((0*CHUNCK_SIZE)+x2) / (float)Size, 0);
						}
					}
			test.saveChunk(chunk[0][0].chunkX,chunk[0][0].chunkY, 0,0);
			test.saveChunk(chunk[1][0].chunkX,chunk[1][0].chunkY, 1, 0);
			test.saveChunk(chunk[2][0].chunkX,chunk[2][0].chunkY,2,0);
			
			chunk[0][0] = chunk[0][1];
			chunk[1][0] = chunk[1][1];
			chunk[2][0] = chunk[2][1];
			chunk[0][1] = chunk[0][2];
			chunk[1][1] = chunk[1][2];
			chunk[2][1] = chunk[2][2];
			
			File f = new File("chunks/"+(chunkOffsetX)+"-"+(chunkOffsetY+2)+".sav");
			if(f.exists()) {
				test.loadChunk(chunkOffsetX,chunkOffsetY+2);
				chunk[0][2] = tempChunk;
			} else {
				for(int y2=0; y2<CHUNCK_SIZE;y2++) {
					for(int x2=0; x2<CHUNCK_SIZE;x2++) {
						mapHeight[((chunkOffsetX)*CHUNCK_SIZE)+x2][((chunkOffsetY+2)*CHUNCK_SIZE)+y2] = perlin.Noise(4 * (((chunkOffsetY+2)*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX)*CHUNCK_SIZE)+x2) / (float)Size, 0);
					}
				}
				chunk[0][2] = new Chunk(chunkOffsetX,2+chunkOffsetY);
			}
			
			f = new File("chunks/"+(chunkOffsetX+1)+"-"+(chunkOffsetY+2)+".sav");
			if(f.exists()) {
				test.loadChunk(chunkOffsetX+1,chunkOffsetY+2);
				chunk[1][2] = tempChunk;
			} else {
				for(int y2=0; y2<CHUNCK_SIZE;y2++) {
					for(int x2=0; x2<CHUNCK_SIZE;x2++) {
						mapHeight[((chunkOffsetX+1)*CHUNCK_SIZE)+x2][((chunkOffsetY+2)*CHUNCK_SIZE)+y2] = perlin.Noise(4 * (((chunkOffsetY+2)*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX+1)*CHUNCK_SIZE)+x2) / (float)Size, 0);
					}
				}
				chunk[1][2] = new Chunk(chunkOffsetX+1,2+chunkOffsetY);
			}
			
			f = new File("chunks/"+(chunkOffsetX+2)+"-"+(chunkOffsetY+2)+".sav");
			if(f.exists()) {
				test.loadChunk(chunkOffsetX+2,chunkOffsetY+2);
				chunk[2][2] = tempChunk;
			} else {
				for(int y2=0; y2<CHUNCK_SIZE;y2++) {
					for(int x2=0; x2<CHUNCK_SIZE;x2++) {
						mapHeight[((chunkOffsetX+2)*CHUNCK_SIZE)+x2][((chunkOffsetY+2)*CHUNCK_SIZE)+y2] = perlin.Noise(4 * (((chunkOffsetY+2)*CHUNCK_SIZE)+y2) / (float)Size, 4 * (((chunkOffsetX+2)*CHUNCK_SIZE)+x2) / (float)Size, 0);
					}
				}
				chunk[2][2] = new Chunk(chunkOffsetX+2,2+chunkOffsetY);
			}
			
			
			
			for(int y=0; y<chunk.length;y++) {
				for(int x=0; x<chunk[0].length;x++) {
					
					for(int y2=0; y2<chunk[x][y].cBlock.length;y2++) {
						for(int x2=0; x2<chunk[x][y].cBlock[0].length;x2++) {
							//System.out.println("Chunk: "+ x+","+y+"   "+ x2+","+y2);
							//System.out.println(chunk[x][y].cBlock[x][y].id[0]+","+chunk[x][y].cBlock[x][y].id[1]);
							//System.out.println(x2+(x*CHUNCK_SIZE) +","+(y2+(y*CHUNCK_SIZE)));
							block[(x*CHUNCK_SIZE)+x2][(y*CHUNCK_SIZE)+y2] = chunk[x][y].cBlock[x2][y2];
						}
					}
				}
			}
			autoTileCleanUp(0,0);
			
		}
	}
	
	public void render(Graphics g, int camX, int camY, int renW, int renH) {
		//Render Area Rectangle
		//RenW (Component.pixel.width /TILE_SIZE) + 2, 
		//RenH (Component.pixel.height / TILE_SIZE) + 2);
		
//		(int) Component.sX, 
//		(int) Component.sY, 
//		(Component.pixel.width /TILE_SIZE) + 2, 
//		(Component.pixel.height / TILE_SIZE) + 2);
		
//		for(int y=0; y<block.length;y++) {
//			for(int x=0; x<block[0].length;x++) {
//				
//					
//					
//					block[x][y].render(g);
//					block2[x][y].render(g);
//					//block3[x][y].render(g);
//					if(block[x][y].id != Tile.air && block[x][y].id != Tile.bedrock && !Inventory.isOpen) {
//						if(block[x][y].contains(new Point((Component.mse.x / Component.pixelSize) + (int) Component.sX, (Component.mse.y / Component.pixelSize) + (int) Component.sY))) {
//							g.setColor(new Color(255,255,255, 40));
//							g.drawRect(block[x][y].x - camX, block[x][y].y - camY, block[x][y].width-1, block[x][y].height-1);
//						}
//					}
//				
//			}
//		}
		for(int x=(camX /TILE_SIZE)-(chunkOffsetX*CHUNCK_SIZE); x<(camX / TILE_SIZE) + renW-(chunkOffsetX*CHUNCK_SIZE);x++) {
			for(int y=(camY / TILE_SIZE)-(chunkOffsetY*CHUNCK_SIZE); y<(camY / TILE_SIZE) + renH-(chunkOffsetY*CHUNCK_SIZE);y++) {
				if(x >= 0 && y >= 0 && x < worldW && y < worldH) {
					block[x][y].render(g);
					//block2[x][y].render(g);
					//block3[x][y].render(g);
					if(block[x][y].id != AIR && block[x][y].id != BEDROCK && !Inventory.isOpen) {
						if(block[x][y].contains(new Point((Component.mse.x / Component.pixelSize) + (int) Component.sX, (Component.mse.y / Component.pixelSize) + (int) Component.sY))) {
							g.setColor(new Color(255,255,255, 40));
							g.drawRect(block[x][y].x - camX, block[x][y].y - camY, block[x][y].width-1, block[x][y].height-1);
						}
					}
				}
			}
		}
//		g.setColor(new Color(255,255,255, 255));
//		//g.drawRect (Component.cameraX-(int)Component.sX,Component.cameraY,Component.cameraW,Component.cameraH);
//		g.drawRect (camX-(chunkOffsetX*CHUNCK_SIZE),camY-(chunkOffsetY*CHUNCK_SIZE*TILE_SIZE),renW*TILE_SIZE,renH*TILE_SIZE);
//		
//		for(int x=(Component.cameraX/TILE_SIZE)-(chunkOffsetX* CHUNCK_SIZE); x<(Component.cameraX / TILE_SIZE) + (Component.cameraW/TILE_SIZE)-(chunkOffsetX*CHUNCK_SIZE);x++) {
//			for(int y=(Component.cameraY / TILE_SIZE)-(chunkOffsetY* CHUNCK_SIZE); y<(Component.cameraY / TILE_SIZE) + (Component.cameraH/TILE_SIZE)-(chunkOffsetY* CHUNCK_SIZE);y++) {
//				if(x >= 0 && y >= 0 && x < worldW && y < worldH) {
//					block[x][y].render(g);
//					if(block[x][y].id != Tile.air && block[x][y].id != Tile.bedrock && !Inventory.isOpen) {
//						if(block[x][y].contains(new Point((Component.mse.x / Component.pixelSize) + (int) Component.sX, (Component.mse.y / Component.pixelSize) + (int) Component.sY))) {
//							g.setColor(new Color(255,255,255, 40));
//							g.drawRect(block[x][y].x - camX, block[x][y].y - camY, block[x][y].width-1, block[x][y].height-1);
//						}
//					}
//				}
//			}
//		}
		

//		
//		
//		
		for(int y=0; y<chunk.length;y++) {
			for(int x=0; x<chunk[0].length;x++) {
				chunk[x][y].render(g);
			}
		}
		
		//Character Health Bar
//		g.setColor(new Color(255,0,0, 255));
//		g.fillRect(18, 18,Component.character.HP * Component.pixelSize,8); 
		//g.setFont();
		
		g.drawImage(ImageAssets.TERRAIN_IMAGE, 
				//Where to place the character
				(int) 16, 
				(int) 16, 
				(int) 19, 
				(int) 26, 
				/*Where it's cut out*/
				0, 
				11 * 16, 
				3, 
				11 * 16 + 10, 
				null);
		for(int y=0; y<Component.character.maxHP-2;y++) {
			g.drawImage(ImageAssets.TERRAIN_IMAGE, 
					//Where to place the character
					(int) 19 + y, 
					(int) 16, 
					(int) 20 + y, 
					(int) 26, 
					/*Where it's cut out*/
					3, 
					11 * 16, 
					4, 
					11 * 16 + 10, 
					null);
		}
		g.drawImage(ImageAssets.TERRAIN_IMAGE, 
				//Where to place the character
				(int) 19 + Component.character.maxHP-2, 
				(int) 16, 
				(int) 22 + Component.character.maxHP-2, 
				(int) 26, 
				/*Where it's cut out*/
				4, 
				11 * 16, 
				7, 
				11 * 16 + 10, 
				null);
		if(Component.character.HP > 0) {
			g.drawImage(ImageAssets.TERRAIN_IMAGE, 
					//Where to place the character
					(int) 18, 
					(int) 16, 
					(int) 19, 
					(int) 26, 
					/*Where it's cut out*/
					7, 
					11 * 16, 
					8, 
					11 * 16 + 10, 
					null);
		}
		for(int y=1; y<Component.character.HP-1d;y++) {
			g.drawImage(ImageAssets.TERRAIN_IMAGE, 
					//Where to place the character
					(int) 18 + y, 
					(int) 16, 
					(int) 19 + y, 
					(int) 26, 
					/*Where it's cut out*/
					8, 
					11 * 16, 
					9, 
					11 * 16 + 10, 
					null);
		}
		if(Component.character.HP == Component.character.maxHP) {
			g.drawImage(ImageAssets.TERRAIN_IMAGE, 
					//Where to place the character
					(int) 19 + Component.character.maxHP-2, 
					(int) 16, 
					(int) 20 + Component.character.maxHP-2, 
					(int) 26, 
					/*Where it's cut out*/
					9, 
					11 * 16, 
					10, 
					11 * 16 + 10, 
					null);
		}
		File test4 = new File("res/squaredance00.TTF");
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, test4);
		} catch(Exception e) {
			System.out.println("Font did not load for some reason...");
		}


		//Font font = new Font(font, Font.PLAIN, 23);
		Font derivedFont = font.deriveFont(Font.BOLD, 9f);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		
		g2.setFont(derivedFont);
		g2.setColor(Color.WHITE);
		g2.drawString("HP",3, 24);
		
	}
	//For overhead graphics...
//	public void render2(Graphics g, int camX, int camY, int renW, int renH) {
//		
//	}
}
