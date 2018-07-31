// Vector Agent Class
package ca.uwaterloo.fes.mia.v1.iquitos;

import uchicago.src.sim.util.Random;


import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import uchicago.src.sim.gui.ColorMap;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import ca.uwaterloo.fes.mia.v1.utility.WeightedSelector;


public class VectorAgent implements Drawable{
	//  private int gCycle = 0;
	  private double Pr;
	  private double ddSinceInfection;
	  private int moves = 0;
	  private Color color;
	  
	  private boolean bit = false;
	  private boolean laidegg = false;
	  private boolean idle = true;

	  private boolean isOviposit = false;
	  private boolean hasBitten = false;
	  private boolean isInfectious = false;
	  private boolean isInfected = false;
	  private int x;
	  private int y;
	  private int vX;
	  private int vY;
	  //private int malaria = 1;
	  private int eggs;
	  private static int IDNumber = 0;
	  private int ID;
	  private int maxMove = 40;
	  
	  @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VectorAgent other = (VectorAgent) obj;
		if (ID != other.ID)
			return false;
		return true;
	}

	private MiaSpace miaSpace;

	  public VectorAgent(){
	    x = -1;
	    y = -1;
	    Pr = 0.0;
	    IDNumber++;
	    ID = IDNumber;
	  }
	  
	  public VectorAgent(int newX, int newY){			
		    x = newX;
		    y = newY;
		    Pr = 0.0;
		    IDNumber++;
		    ID = IDNumber;
		  }
	  
	  public void setXY(int newX, int newY){
		    x = newX;
		    y = newY;
		  }
	  

	  public void setColor() {
		  ColorMap ramp = miaSpace.getColourRamp();

			//System.out.println(miaSpace.adultVectorCount(x, y));
			  Color newColor = ramp.getColor(miaSpace.adultVectorCount(x, y)-1);
			 
	    color = newColor;
	  }
	  
	  public void setMiaSpace(MiaSpace cds){
		  miaSpace = cds;
		  }
	  

	  public void setPr(double newPr){
		  Pr = newPr;
	  }
	  
	  public boolean getIdle(){
		  return idle;
	  }
	  
	  public boolean getLaidEgg(){
		  return laidegg;
	  }
	  
	  public boolean getBit(){
		  return bit;
	  }

	  
	  public boolean getHasBitten(){
		  return hasBitten;
	  }
	  
	  public boolean getIsOviposit(){
		  return isOviposit;
	  }
	  
	  public void infect(){
		  isInfected = true;
	  }
	  
	  public int getID(){
		    return ID;
		  }


		  public void report(){
		    System.out.println(getID() +
		                       " at " +
		                       x + ", " + y +
		                       " has " );
		  }
		  public int getX(){
			    return x;
			  }

			  public int getY(){
			    return y;
			  }


	public void searchForHuman(){

		
		List dir = new LinkedList(); 
		dir = miaSpace.weightedDirection(x, y);

		
		while (miaSpace.isCellOccupied(x,y) == false && moves <= maxMove){
			dir = miaSpace.weightedDirection(x, y);
			
			if (vX < 0 || vX > (miaSpace.getCurrentVectorSpace().getSizeX()-1)) System.out.println("wtf");
			if (vY < 0 || vY > (miaSpace.getCurrentVectorSpace().getSizeY()-1)) System.out.println("wtf");
			vX = x + Integer.parseInt(dir.get(0).toString());
			vY = y + Integer.parseInt(dir.get(1).toString());
			

			miaSpace.moveVectorAt(x,y,vX,vY,this);
			setXY(vX,vY);
			moves++;
		}
		//System.out.println("found meal in " + moves + " moves");
			bite();
	
	}
	
	public void bite(){
		if (miaSpace.isCellOccupied(x,y)){
			miaSpace.bite(x, y, this);
			hasBitten = true;   //This is a human bite
			bit = true;
			//System.out.println(this.isInfectious);
		//	if(isInfectious){
		//		System.out.println("Infectious Bite");
		//	}
		}
		
		hasBitten = true;
		bit = true;
		//It is assumed that the mosquito finds a meal within a step which is non humnan, does not count to ABR
		 
	}
	
	
	public void searchForWater(){
		while (miaSpace.isCellWater(x,y)==false && moves <= maxMove){
			List dir = new LinkedList(); 
			dir = miaSpace.weightedWaterDirection(x, y);
			

			
			//if (vX < 0 || vX > (miaSpace.getCurrentVectorSpace().getSizeX()-1)) System.out.println("wtf");
			//if (vY < 0 || vY > (miaSpace.getCurrentVectorSpace().getSizeY()-1)) System.out.println("wtf");
			vX = x + Integer.parseInt(dir.get(0).toString());
			vY = y + Integer.parseInt(dir.get(1).toString());
			
			
			/*
			vX =-1;
			vY =-1;
			while(vX < 0 || vX > (miaSpace.getCurrentVectorSpace().getSizeX()-1)){
				vX = x +  Random.uniform.nextIntFromTo(-1, 1);
			}
			while(vY < 0 || vY > (miaSpace.getCurrentVectorSpace().getSizeY()-1)){
				vY = y + Random.uniform.nextIntFromTo(-1, 1);
			}
		*/
		
			miaSpace.moveVectorAt(x,y,vX,vY,this);
			setXY(vX,vY);
			moves++;
		}
		
		if (Pr >= 1){
		if (miaSpace.isCellWater(x,y)){
			Oviposit();  //if 70 moves pass and they have not found water they wait
			hasBitten = false;
		}
		}
		else
		{
			waiting();
		}
	}
	
	public void Oviposit(){
		Pr = 0;
		//Random.normal.nextInt();
		eggs = (int) Math.round(Random.normal.nextInt() * 0.5);
		miaSpace.vectorLayEggs(this.x,this.y,eggs);
		laidegg = true;
		hasBitten = false;

	}
	
	public void waiting(){
		//TODO: waiting? what happens here????? 1. waiting 2. waiting 3. ? 4. profit
	}
	
	  public void draw(SimGraphics G){

		 // System.out.println(color);
		 // G.drawFastRoundRect(color);
		  G.drawFastRoundRect(Color.red);
		  }
			  
	public void step(double u, double currentMeanTemp){
		 bit = false;
		 laidegg = false;
		 idle = true;
		moves = 0;
		
	
		/*
		 * Checking development of plasmodium withinin the vector
		 */
		if (isInfected && isInfectious == false){
			if (currentMeanTemp >= 16){
			ddSinceInfection = ddSinceInfection + currentMeanTemp;
			
			}
			if (ddSinceInfection >= 111) {
				
				isInfectious = true;
			}
		}
		
		if(hasBitten){
			Pr = Pr + (1/u);
			if (Pr >= 1){	
			searchForWater();
			isOviposit = true;
			}
		}
		
		//else if (!hasBitten && !isOviposit) {
		else if (!hasBitten) {
			searchForHuman();
			
		}
		
		
		if (bit || laidegg){
			idle = false;
		}
		
		if (!bit && !laidegg && !idle){
			System.out.println("WTF");
		}
	}


	public boolean getIsInfectious() {
		 return isInfectious;
		// TODO Auto-generated method stub
		
	}
	
	public boolean getIsInfected() {
		 return isInfected;
		// TODO Auto-generated method stub
		
	}
		
}