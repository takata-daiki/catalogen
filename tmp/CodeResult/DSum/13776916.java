/*$$
 * Copyright (c) 2007, Centre of Informatics and Systems of the University of Coimbra 
 * All rights reserved.
 *
 * Rui Lopes
 *$$*/
package mater.agents;

import uchicago.src.sim.gui.*;
import uchicago.src.sim.space.MooreNeighborhooder;

import java.awt.*;
import java.util.*;

import mater.MaterModel;
import mater.agents.Patch;
import mater.data.Definitions;
import mater.paths.PathFinderI;
/**
 * 
 */

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;

/**
 * @author Rui Miguel
 *
 */
public abstract class MaterAgent implements Drawable, Definitions{

	/*
	 * Agent attributes
	 */
	protected static final int _ENERGY_BOOST_FOUNDERS = 50;
	protected int x, y;
	protected int age = 0;
	protected int energy = 200;
	//protected int energyLostWaiting = 0;
	protected int timeOfGuest = 0;
	protected City currentCity = null;

	/*
	 * Auxiliar vars
	 */
	protected MaterModel model;
	protected City lastCity = null;
	protected City destinationCity = null;
	//key -> city; value -> Integer
	protected Hashtable<City,Integer> cityCount = new Hashtable<City,Integer>();
	protected ArrayList<Patch> path = new ArrayList<Patch>();
	protected PathFinderI pf = null;
	protected boolean onRoad = false;
	//protected ArrayList<City> cities = new ArrayList<City>();
	private int roadIndex = 0;
	//protected CityManager cityManager;

	//private ArrayList<City> ctemp = new ArrayList<City>(); 
	private MersenneTwister cityScoreDistGen;
	private Uniform cityScoreDist;
	private boolean reverseDirection = false;
	private int nRadius = 1;
	private PriorityQueue<Patch> queue = 
		new PriorityQueue<Patch>( 100, new City.PatchCapacityComparator() );
		//new PriorityQueue<Patch>(100 , new City.PatchCapacityComparator());
		//new ArrayList();
	private boolean guest = false;
//	private int rejections = 0;
	private int unhappiness = 0;
	private double last_e_inc;
	//private boolean forceToLeave = false;
	//para saber os que nao tiveram lugar na cidadee sao forçados a sair de imediato
	//private boolean parasite = false;

	public MaterAgent( MaterModel model , int x, int y , City current){
		this.model = model;
		//cityManager = new CityManager( this );
		//this.world = model.getWorld();
		//this.worldSize = model.getWorldSize();
		this.x = x;
		this.y = y;
		this.pf =  model.getPathFinder();
		//model.getPatchAt( this.x, this.y ).addAgent( this );
		//cities = model.getObs().getCities();
		this.cityScoreDistGen = new MersenneTwister( (int) System.currentTimeMillis() );
		cityScoreDist = new Uniform( cityScoreDistGen );
		this.nRadius = model.getNeighborhoodRadius();
		//FIXME: this must be done city by city
		//current.addGuest( this );
		this.age = current.getAgeDist().nextIntFromTo(1, model.getDeathAge());
		this.energy = getInitialEnergy( current );
		//this.energy = model.getRepThreshold() - 1 ;
		//this.energy = 100;
		//this.energyLostWaiting = energyLostDist.nextIntFromTo( 0, _MAX_ENERGY_LOST );
		timeOfGuest = current.getEnergyLostDist().nextIntFromTo( 0, model.getMaxGuestTime() );
		unhappiness = current.getUnhappinessDist().nextIntFromTo( 0, model.getUnhappinessThreshold());
	}

	protected int getInitialEnergy( City current ) {
		return current.getEnergyDist().nextIntFromTo( model.getDeathThreshold(), model.getRepThreshold() );
	}

	/*
	 * For reproduction of the agents 
	 */
	public MaterAgent( MaterAgent a ){
		this.x = a.x;
		this.y = a.y;
		this.model = a.model;
		//cityManager = new CityManager( this );
		this.pf = model.getPathFinder();
		//model.getPatchAt( x, y ).addAgent( this );
		this.cityScoreDistGen = new MersenneTwister( (int) System.currentTimeMillis() );
		cityScoreDist = new Uniform( cityScoreDistGen );
		this.currentCity = a.currentCity;
		this.nRadius = model.getNeighborhoodRadius();

		if( a.isOnRoad() ){
			this.onRoad = true;
			this.path = a.path;
			this.roadIndex = a.roadIndex;
			this.destinationCity = a.destinationCity;
			this.reverseDirection = a.reverseDirection;
		}
		/*
		 * ELSE
		 * agent is added to guestHouse in class MaterModel
		 */

		//FIXME
		this.energy = a.energy / 2;
		a.energy -= this.energy;
	}

	public boolean isReproducting(){
		if( getEnergy() >= model.getRepThreshold() )
			return true;		
		return false;
	}

	public boolean isOnRoad(){
		return onRoad;
	}

	public void draw(SimGraphics arg0 ) {

		arg0.drawFastRect( Definitions._AGENT_MOVE_COLOR );
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setXY( Point p ) {
		model.getPatchAt( this.x, this.y ).removeAgent( this );
		this.x = p.x;
		this.y = p.y;
		model.getPatchAt( this.x, this.y ).addAgent( this );
	}

	public void preStep( /*ArrayList<MaterAgent> toreproduce*/ ){
		/*if( !isOnRoad() ){
			double d = model.getBirthRateDistr().nextDoubleFromTo(0, 1);
			if( d < model.getBirthRate() ){
				toreproduce.add(this);
				model.incrementBirthCounter();
			}
		}*/
		//cityManager.removeDeadCities();
		//updateEnergy();
	}

	public void postStep( /*ArrayList<MaterAgent> todie*/ ){

		if( isOnRoad() ){
			//FIXME still testing
			energy -= model.getEnergyDec();
			//energy -= model.getEnergyDec()*model.getMoveSpeed();
		}
		else if( isGuest() ){
			//int energydec = (int)(model.getEnergyDec() * currentCity.getOccupation());
			//System.out.println("Agent Loosing " + energydec);
			//energy -= model.getEnergyDec() * currentCity.getOccupation();
			timeOfGuest++;
			energy -= model.getEnergyDec()*model.getMoveSpeed();
			// because the agents live to much and that makes cities of one pixel only live much time
		}		
		else{
			double e_inc = 
				//model.getEnergyInc() - Math.pow( currentCity.getOccupation(),2 );
				model.getEnergyInc() / currentCity.getOccupation();
			energy += e_inc;
			//System.out.println("Agent Winning " + e_inc);
			updateUnhappiness(e_inc);
		}

		age++;
		/*
		if( !isOnRoad() && age > 0){
			double d = model.getDeathRateDistr().nextDoubleFromTo(0, 1);
			if( d < model.getDeathRate() )
				this.die( todie );
		}*/
	}

	protected void updateUnhappiness( double e_inc ){
		double delta_e = e_inc - last_e_inc;
		last_e_inc = e_inc;
		if( delta_e < 0 )
			unhappiness ++;
		else{
			unhappiness --;
			if( unhappiness < 0 )
				unhappiness=0;
		}
	}

	/*
	private void die( ArrayList<MaterAgent> todie ){
		todie.add(this);
		model.incrementDeathCounter();
		//model.getAgentSpace().removeObjectAt(x, y, this);
		model.getPatchAt( this.x, this.y ).removeAgent( this );
	}
	 */

	public void step(){	
		if( !isOnRoad() )
			stepInCity();
		/*
		else if( model.getPatchAt( this.getXY() ).getCity() == null
				&& ( d  = model.getSettleRateDistr().nextDoubleFromTo(0, 1) ) < model.getSettleRate() )
			//System.out.println("Agent is funding city!");
			//TODO: ENERGY BOOST foR these agents
			fundCity();
		 */
		else{	
			if( !path.isEmpty() && this.isOnRoad() )
				stepOnRoad();
			//System.out.print("1... ")
			else{
				MaterModel.pError("Error: Agent isOnRoad and path isEmpty!");
			}
		}	
	}

	private boolean fundCity() {
		queue.clear();
		Patch newCityP = model.getCityNeighborhooder().getNewCityCenter(x, y, new int[]{ nRadius,nRadius});
		
		if( newCityP != null ){
			currentCity.removeAgent( this );
			City c = model.getAgentFactory().createCity( newCityP.getXY(), model, new MaterAgent[]{ this });
			c.populateCity(new MaterAgent[]{this});
			//this.energy += _ENERGY_BOOST_FOUNDERS;
			System.out.println("An agent created a new city!");
			return true;
		}
		else{
			System.out.println("Agent could not find a valid patch!");
			return false;
		}
	}

	private void stepOnRoad() {
		int finalIndex = roadIndex + (reverseDirection? -1 : 1)* model.getMoveSpeed();

		if( finalIndex > path.size() )
			finalIndex = path.size();
		else if( finalIndex < -1 )
			finalIndex = -1;

		//System.out.println("Agent OnRoad: roadindex = "+roadIndex+" ; finalIndex = "+finalIndex);
		while( roadIndex != finalIndex ){

			Patch _next_patch = ( Patch )path.get( roadIndex );
			if( _next_patch.getCity()!=null && _next_patch.getCity().equals( destinationCity ) ){
				destinationCity.addGuest( this );
				//this.setXY( _next_patch.getXY() );
				return;
			}
			else{
				//this.setXY( _next_patch.getXY() );
				//_next_patch.incrementRoadUse();
				if( (!reverseDirection && roadIndex == finalIndex - 1) ||
						( reverseDirection && roadIndex == finalIndex + 1 )){
					this.setXY( _next_patch.getXY() );
					//System.out.print(" Setting agent on patch! ");
				}
			}
			//System.out.println("3... ");
			//FIXME como esta condicao surge no fim, dispara a condicao final deste metodo, quando atingir o final do caminho.
			//por isso essa condicao tem +/- 1 para descartar a ultima iteracao desta iteracao
			if( !reverseDirection )
				roadIndex++;
			else
				roadIndex--;

		}

		//FIXME a primeira condicao tem apenas > e nao >= because it was buggy... testing
		if( roadIndex >= path.size() + 1 || roadIndex < 0 - 1 ){
			//System.out.println("Agent going to nearest City because destination city died! ");
			destinationCity = getNearestCity();
			//FIXME repor ou nao??
			nRadius = model.getNeighborhoodRadius();
			//System.out.println("Going from "+ x +","+y+" to city at "+destinationCity.getCenter());
			path = pf.search( destinationCity, new Point( x, y ) );
			if( path == null )
				System.out.println("Going from "+ x +","+y+" to city at "+destinationCity.getCenter());
			for( Patch p : path ) p.incrementRoadUse();
			roadIndex = 0;
			reverseDirection = false;
		}

		//System.out.println(" Final roadIndex = "+ roadIndex );
	}

	public City getNearestCity() {
		City toreturn = null;


		ArrayList<City> v =null;
		int i = 0;
		do{
			i++;
			v = model.getCityNeighborhooder().getNeighbors( 
					x, 
					y, 
					model.getNeighborhoodRadius()*i, 
					model.getNeighborhoodRadius()*i );
		}
		while( v.isEmpty() );

		double best_score = 100000000;
		double distance = 0;
		for( City c : v ){
			distance = Math.sqrt( Math.pow( c.getCenter().x - x ,2) + Math.pow( c.getCenter().y - y ,2) );
			if( distance < best_score ){
				toreturn = c;
				best_score = distance;
			}				
		}				
		return toreturn;
	}


	private void stepInCity() {		
		/*
		if( forceToLeave ){
			go();
		}
		 */
		if( isGuest() ){
			if( timeOfGuest >= model.getMaxGuestTime()  || currentCity.getAgentCapacity() < 1 )
				go( null );
		}
		else{
			double d = currentCity.getLeaveRateDist().nextDoubleFromTo(0,1);
			double cityLeaveRate = model.getBaseLeaveRate()*currentCity.getOccupation(); 
			//double cityLeaveRate = model.getBaseLeaveRate() / currentCity.getAgentCapacity();
			if( d < cityLeaveRate )
				go( null );
		}
	}

	public void go( City current ) {
		if( current == null )
			current = this.currentCity;
		destinationCity = chooseDestinationCity( current );
		if( destinationCity == null ){
			//FIXME é provável que os agentes fiquem na cidade para sempre
			fundCity();
		}
		else{
			//System.out.println("Guest going to city "+ destinationCity.getId() + " at ");
			//model.getProfiler().startProfiling("SetAgentLeaving");
			reverseDirection = false;
			//if(!cityManager.isEmpty() ){
			//cityManager.debug();
			if( (path = current.getCachedPath( destinationCity )) == null ){
				path = destinationCity.getCachedPath(current);
				if( path == null){
					path = pf.search( destinationCity, current.getCenter());
					destinationCity.addDistance( current, path.size() );
					current.addPath( destinationCity, path );
				}		
				else
					reverseDirection = true;
			}
			setOnRoad( true );
			model.setGajosQueMigraram( model.getGajosQueMigraram() + 1 );

			for( Patch p : path ) p.incrementRoadUse();
			//}
			//model.getProfiler().stopProfiling("SetAgentLeaving");
		}

	}

	private Point getXY() {
		return new Point( x, y );
	}

	private City chooseDestinationCity( City current ){
		CityManager cm = current.getNeighbors();
		PriorityQueue<City> cQueue = cm.getCitiesProbabilitiesQueue(); 
		double scoreSum = cm.getScoreSum();
		double d = cityScoreDist.nextDoubleFromTo(0, scoreSum );
		//MaterModel.pError("\nscoreSum = "+scoreSum);
		//MaterModel.pError("d = "+d);
		double dsum = 0;
		for(City c : cQueue ){
			dsum += cm.getScore( c );
			//MaterModel.pError("dsum is: "+dsum);
			if( d <= dsum ){
				return c;
			}			
		}

		return null;
	}

	private void setOnRoad( boolean b ) {
		this.onRoad = b;
		if( b ){
			if( currentCity != null )
				currentCity.removeAgent( this );
			destinationCity.addFlowFrom( lastCity, 1 );
			roadIndex = reverseDirection? path.size() - 1 : 0 ;
		}
		//else
		//path.clear();

	}

	public City getLastCity() {
		return lastCity;
	}

	public City getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(City c) {		
		this.currentCity = c;
	}

	public City getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(City destinationCity) {
		this.destinationCity = destinationCity;
	}

	/*
	class CityChooseComp implements Comparator<City>{

		private City origin = null ;

		public CityChooseComp(City o){
			this.origin = o;
		}
		public int compare(City c1, City c2) {
			//double score1 = origin.getFlowsTo( c1 ) / c1.getDistanceTo( origin );
			//double score2 = origin.getFlowsTo( c2 ) / c2.getDistanceTo( origin );
			double score1 = c1.getCitizensNum() / Math.pow( c1.getDistanceTo( origin ),2);
			c1.setTempScore( score1 );
			double score2 = c2.getCitizensNum() / Math.pow( c2.getDistanceTo( origin ),2);
			c2.setTempScore( score2 );
			if( score1 < score2 )
				return 1;
			else if( score1 > score2 )
				return -1;
			return 0;
		}

	}*/


	public int getEnergy() {
		return energy;
	}

	public int getAge() {
		return age;
	}

	public boolean isGuest() {
		return guest;
	}

	public void setGuest(boolean guest ) {
		this.guest = guest;
		if( !guest )
			this.timeOfGuest = 0;
	}

	public String toString(){
		String s = "" + super.toString() + ":\n" +
		"\t age = " + age + "\n" +
		"\t x = " + x + "\n" +
		"\t y = " + y + "\n" +
		"\t currentCity = " + ( currentCity == null? null: currentCity.getId() ) + "\n" +
		"\t destinationCity = " + ( destinationCity == null? null: destinationCity.getId() ) + "\n" +
		"\t energy = " + energy + "\n" +
		"\t guest = " + guest + "\n" +
		"\t nRadius = " + nRadius + "\n" +
		"\t onRoad = " + onRoad + "\n";

		return s;
	}

	/*
	public void setTempXY(Point center) {
		model.getPatchAt(x, y).removeAgent( this );
		x = center.x;
		y = center.y;
	}
	 */
	public void resetUnhappinness() {
		unhappiness = 0;
	}

	public int getUnhappiness() {
		return unhappiness;
	}

	public void setUnhappiness(int unhappiness) {
		this.unhappiness = unhappiness;
	}

	/*
	public void setForceToLeave(boolean forceToLeave) {
		this.forceToLeave = forceToLeave;
		setUnhappiness(0);
	}

	public boolean isForceToLeave() {
		return forceToLeave;
	}
	 */

	/*
	public boolean isParasite() {
		return parasite;
	}

	public void setParasite(boolean parasite) {
		this.parasite = parasite;
	}
	 */
	public void increaseGuestTime(){
		this.timeOfGuest ++;
	}
	/*
	public int getRejections() {
		return rejections;
	}

	public void setRejections(int rejections) {
		this.rejections = rejections;
	}
	 */
}
