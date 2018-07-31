/* Child (<15) Agent Class
 * Josh King
 * MIA Model
 * Last Modified: Nov 18, 2008
 */

/* Adult (15+) Agent Class
 * Josh King
 * MIA Model
 * Last Modified: Nov 18, 2008
 */

package ca.uwaterloo.fes.mia.v1.iquitos;
import uchicago.src.sim.util.Random;

public class ChildAgent{
	
	
	  
	  private int x;
	  private int y;
	  private int birthday;
	  private int age = 0;
	  private int death = 0;
	  private int homeX;
	  private int homeY;
	  private int sex;  //1 Female 2 Male
	  private int occupation;   //1 Agricultural 2 Fishing 3 City Work
	  private int stepsSinceInfection = 0;
	  private boolean hasMalaria;
	  private boolean isInfectious;
	  private boolean isSymptomatic;
	  private int stepsToLive;
	  private int tick = 0;
	  private static int IDNumber = 0;
	  private int ID;
	  


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
		ChildAgent other = (ChildAgent) obj;
		if (ID != other.ID)
			return false;
		return true;
	}


	private MiaSpace miaSpace;

	  
	  public ChildAgent(HouseholdAgent household, int newAge, int newDeath){
			homeX = household.homeX;
			homeY = household.homeY;
			birthday = Random.uniform.nextIntFromTo(1, 365);
			x = homeX;
			y = homeY;
			age = newAge;
			death = newDeath;
			hasMalaria = false;
		
		    stepsToLive = ((death-age)*356)+ Random.uniform.nextIntFromTo(-182, 182);
		    sex = Random.uniform.nextIntFromTo(1, 2);
		    occupation = Random.uniform.nextIntFromTo(1, 3);
		    IDNumber++;
		    ID = IDNumber;
		
		  }
	  
	
	  
	  public void setXY(int newX, int newY){
		    x = newX;
		    y = newY;
		  }
	  public int getDeathAge(){
		  return death;
	  }
	  
	  public void setMiaSpace(MiaSpace cds){
		    miaSpace = cds;
		  }
	  
	  public String getID(){
		    return "Human-" + ID;
		  }

	  public boolean getMalaria(){
		    return hasMalaria;
		  }
	  
	  public boolean getInfectious(){
		    return isInfectious;
		  }
	  
	  public MiaSpace getcurrenSpace(){
		  return miaSpace;
	  }
	  
	  public boolean getSymptomatic(){
		  return isSymptomatic;
	  }
	  
      public int getOccupation(){
    	  return occupation;
      }
      
	  public int getStepsToLive(){
		    return stepsToLive;
		  }

	  public void report(){
		    System.out.println(getID() +
		                       " at " +
		                       x + ", " + y +
		                       " is " +
		                       getAge() + " and will die at " + getDeathAge() + " and has " +
		                       getStepsToLive() + " steps to live.");
		  }
	  
	  public int getSex(){
		  return sex;
	  }
	  
	  public int getX(){
			    return x;
			  }

	  public int getY(){
			    return y;
			  }
     public void infect(){
    	 hasMalaria = true;
     }
     public boolean checkbirthday(){
    	 return true;
     }
     public int getAge(){
    	 return age;
     }
     
	  
	  public void step(){
		  tick++;
		  int date = (tick/356)+(tick%365);
		  if (date == birthday) {
			  age++;
		  }
		  
		  if (hasMalaria){
			  stepsSinceInfection++;
			  if (stepsSinceInfection>=14){
				  isInfectious = true;
			  }
			  if(Random.uniform.nextDoubleFromTo(0, 1)>=0.97){
				  hasMalaria = false;
				  isInfectious = false;
				  stepsSinceInfection = 0;
			  }
		  }

		  stepsToLive--;

	  		}
}