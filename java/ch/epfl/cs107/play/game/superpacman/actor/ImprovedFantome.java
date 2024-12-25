package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;

public abstract class ImprovedFantome extends Fantome{

	private  boolean stateTransition= false;
	private boolean targetReached= true;
	private  boolean memoryState= false;
	final static int velocityAffraid = 20;
	final static int velocity = 18;
	
	public boolean getStateTransition() {
		return stateTransition;
	}


	public  void setStateTransition(boolean stateTransition) {
		this.stateTransition = stateTransition;
	}


	public  boolean getTargetReached() {
		return targetReached;
	}


	public  void setTargetReached(boolean targetReached) {
		this.targetReached = targetReached;
	}


	public  boolean getMemoryState() {
		return memoryState;
	}


	public  void setMemoryState(boolean memoryState) {
		this.memoryState = memoryState;
	}


	
	
	public ImprovedFantome(Area area,Orientation orientation,DiscreteCoordinates position) {
		super(area, orientation, position);
		
	}
	

	@Override
	abstract Orientation getNextOrientation();
	
	DiscreteCoordinates randomReachableCase(DiscreteCoordinates dc, int rayon, boolean wantInside) {
		//System.out.println("fzfs ");
		int height= getOwnerArea().getHeight();
		int width= getOwnerArea().getWidth();
		int randIntX;
		int randIntY;
		boolean condition= false;
		if( dc !=null && rayon!=0) {
			if(wantInside) {
				do {
					condition=false;
			randIntX= -rayon + (int)(Math.random() * (2*rayon + 1));
			randIntY= -rayon + (int)(Math.random() * (2*rayon + 1));
			if(!((SuperPacmanArea)getOwnerArea()).nodeExists(dc.jump(randIntX, randIntY))) {
				condition=true;
			}
			
				}while(condition);
				return dc.jump(randIntX, randIntY);
				}
			else {
				do {
					condition=false;
				randIntX= RandomGenerator.getInstance().nextInt(width);
				randIntY= RandomGenerator.getInstance().nextInt(height);
				if(!(Math.abs(DiscreteCoordinates.distanceBetween(new DiscreteCoordinates(randIntX,randIntY),dc))>rayon)) {
					condition=true;
				}
				}while(condition);
				return new DiscreteCoordinates(randIntX,randIntY);
			}
		}

			else {
				do {
					condition=false;
					randIntX= RandomGenerator.getInstance().nextInt(width);
					randIntY= RandomGenerator.getInstance().nextInt(height);
					if(!((SuperPacmanArea)getOwnerArea()).nodeExists(new DiscreteCoordinates(randIntX,randIntY))) {
						condition=true;
					}
					}while(condition);
				
				return new DiscreteCoordinates(randIntX,randIntY);
					}
				
				
			}
		
	
@Override
	public void update(float deltaTime) {
		
		if(wasEaten()) {
			
			setMemoire(null);
			setMemoryState(true);
			setWasEaten(false);
				
		}
		
		super.update(deltaTime);
	}
}
