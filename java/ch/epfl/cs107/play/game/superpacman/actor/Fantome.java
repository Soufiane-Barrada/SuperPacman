package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.rpg.handler.FantomeInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public abstract class Fantome extends MovableAreaEntity implements Interactor, CollectableAreaEntity {
	private  Logic affraid= Logic.FALSE;
	
	 static final int ANIMATION_DURATION= 5;
	private FantomeHandler handler = new FantomeHandler();
	public final static int GHOST_SCORE = 500;
	Sprite[] spriteAffraid;
	protected Animation animationAffraid;
	private SuperPacmanPlayer memoire;
	private boolean wasEaten=false;
	public Fantome(Area area,Orientation orientation,DiscreteCoordinates position){
        super(area, orientation, position);
        spriteAffraid= Sprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1,
				this , 16, 16);

		animationAffraid=new Animation(ANIMATION_DURATION/2, spriteAffraid);
        
    }
	public SuperPacmanPlayer getMemoire() {
		return memoire;
	}
	void setMemoire(SuperPacmanPlayer spp) {
		memoire=spp;
	}
	 public void leaveArea(){
	        getOwnerArea().unregisterActor(this);
	    }
	 public void enterArea(Area area, DiscreteCoordinates position){
	        area.registerActor(this);
	        
	        setOwnerArea(area);
	        setCurrentPosition(position.toVector());
	       
	        resetMotion();
	    }

	

	public boolean wasEaten() {
		return wasEaten;
	}
	public void setWasEaten(boolean b) {
		wasEaten=b;
	}
	
	abstract Orientation getNextOrientation();
public boolean isAffraid() {
	return affraid.isOn();
}
public  void setAffraid(Logic b) {
	affraid=b;
}
@Override
public boolean takeCellSpace() {
	return false;
}

@Override
public boolean isCellInteractable() {
	return true;
}

@Override
public boolean isViewInteractable() {
	return false;
}
@Override
public List<DiscreteCoordinates> getCurrentCells() {
	return Collections.singletonList(getCurrentMainCellCoordinates());
}
@Override
public List<DiscreteCoordinates> getFieldOfViewCells(){
	DiscreteCoordinates O = getCurrentMainCellCoordinates();
	List<DiscreteCoordinates> fieldOfViewCells = new ArrayList<DiscreteCoordinates>();
	for(int i= O.y - 5; i<= O.y+5; i++) {
		for(int j=O.x-5; j<=O.x+5; j++) {
			fieldOfViewCells.add(new DiscreteCoordinates(j,i));
		}
	}
	return fieldOfViewCells;
}

@Override
public void acceptInteraction(AreaInteractionVisitor v) {
	if(v.getClass().getName().equals("ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer$SuperPacmanPlayerHandler")) {
		((SuperPacmanInteractionVisitor)v).interactWith(this);
	}
	else {
	
	((FantomeInteractionVisitor)v).interactWith(this);}

}
@Override
public boolean wantsCellInteraction() {
	return false;
}
@Override
public boolean wantsViewInteraction() {
	return true;
}
@Override
public void interactWith(Interactable other){
	
	other.acceptInteraction(handler);

}
private void setMemoryStatetoTrue() {
	if(this instanceof ImprovedFantome)
		((ImprovedFantome)this).setMemoryState(true);
}
public abstract void collect() ;
 

	

private class FantomeHandler implements FantomeInteractionVisitor{
	@Override
 public void interactWith(SuperPacmanPlayer spp) {
		
		memoire=spp;
		if(!isAffraid()) {
		setMemoryStatetoTrue();
		}
	}
	
	
	
	
	
}
	
	
	
}
