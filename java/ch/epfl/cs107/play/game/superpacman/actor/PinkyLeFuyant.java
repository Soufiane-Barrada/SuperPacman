package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import ch.epfl.cs107.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.game.superpacman.area.SuperPacmanBehavior;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class PinkyLeFuyant extends ImprovedFantome{


	private Orientation desiredOrientation;
	private Queue<Orientation> path;

	private Path graphicPath;
	private int MAX_RAND_ATTEMPT= 200;
	private int MIN_AFRAID_DISTANCE= 5;
	DiscreteCoordinates targetedCase;
	
	
	
	
	
	Animation[] animations;
	private Sprite[][] sprite;
	public PinkyLeFuyant(Area area,Orientation orientation,DiscreteCoordinates position) {
		super(area,orientation,position);
		sprite = RPGSprite.extractSprites("superpacman/ghost.pinky",
				2, 1, 1,
				this , 16, 16, new Orientation[] {Orientation.UP ,
				Orientation.RIGHT , Orientation.DOWN, Orientation.LEFT});
				// crée un tableau de 2 animations
		 animations =Animation.createAnimations(ANIMATION_DURATION/2, sprite);
	}

	@Override
	public void draw(Canvas canvas) {
		if(!isAffraid()) {
			 animations[desiredOrientation.ordinal()].draw(canvas);	
			 }else {
				 animationAffraid.draw(canvas);
			 }
		graphicPath.draw(canvas);
	}

	@Override
	Orientation getNextOrientation() {
		
		
		if(path ==null ||path.peek()==null  ) {
			setTargetReached(true);
			
			
		}
		
		
		if(getStateTransition()|| getTargetReached() || getMemoryState()) {
			if(isAffraid()) {
				if(getMemoire()!=null) {
					targetedCase = randomReachableCase(getMemoire().getCurrentCells().get(0),MIN_AFRAID_DISTANCE,false);
					
				path=((SuperPacmanArea)getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),targetedCase
						 );
				
				System.out.println("Path changed");}
				else {
					targetedCase=randomReachableCase(null,0,true);
					path=((SuperPacmanArea)getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
							targetedCase);
				System.out.println("Path changed");
				}
			}
			else {
				if(getMemoire()!=null) {
					path=((SuperPacmanArea)getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
							getMemoire().getCurrentCells().get(0) );
					System.out.println("Path changed");
				}
				else {
					path=((SuperPacmanArea)getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
							randomReachableCase(null,0,false) );
					System.out.println("Path changed");
				}
			}
				
				setStateTransition(false);
				setTargetReached(false);
				setMemoryState(false);
		}
		if(path==null) {
			path=((SuperPacmanArea)getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
					randomReachableCase(null,0,false) );
		}
		graphicPath= new Path(this.getPosition(),new LinkedList<Orientation>(path));
		
		return path.poll();
		
	}

	@Override
	public  void collect() {
		 
		
		 getOwnerArea().leaveAreaCells(this , getEnteredCells());

		 setCurrentPosition(SuperPacmanBehavior.GhostsOrigineDiscreteCooridinates(this).toVector());
		 
		 getOwnerArea().enterAreaCells(this , getCurrentCells());
		 resetMotion();

	 }

	 public void update(float deltaTime) {
		 if(!isDisplacementOccurs() ) {
			 desiredOrientation=getNextOrientation(); 
			 if(!isAffraid() && getMemoire()!=null) {
				 setMemoryState(true);
			 }
			 if(isAffraid() && getMemoire()!= null ) {
		 if((Math.abs(DiscreteCoordinates.distanceBetween(targetedCase,getMemoire().getCurrentCells().get(0))))<MIN_AFRAID_DISTANCE) 
			 {	 
				 setMemoryState(true);
			 }
			 }
		 }
		 
		 if(!isAffraid()) {
		 if(!isDisplacementOccurs()) {
			
	        	if(getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates()
	        			.jump(desiredOrientation.toVector()))))   {
	        		this.orientate(desiredOrientation);
	        		move(18);
	        		animations[desiredOrientation.ordinal()].reset();
	        	}
	        }else {
	        	animations[desiredOrientation.ordinal()].update(deltaTime);
	        }
	 }else {
		 if(!isDisplacementOccurs()) {
			 
	        	if(getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates()
	        			.jump(desiredOrientation.toVector()))))   {
	        		this.orientate(desiredOrientation);
	        		move(18);
	        		animationAffraid.reset();
	        	}
	        }else {
	        	animationAffraid.update(deltaTime);
	        }
	 }
		 super.update(deltaTime);
	 }
	 
}
