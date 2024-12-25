package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Gate extends AreaEntity {

	Logic[] key;
	
	Logic signal;
	Sprite sprite;
	public Gate(Area area, Orientation orientation, DiscreteCoordinates position, Logic signal, Key... key) {
		super(area, orientation, position);
		if(orientation==Orientation.UP || orientation==Orientation.DOWN) {
		sprite= new Sprite("superpacman/gate", 1, 1,this, new RegionOfInterest(0,0,64,64));
		
		}else {
			sprite= new Sprite("superpacman/gate", 1, 1,this, new RegionOfInterest(0,64,64,64));
		}
		this.signal=signal;
		this.key=key;
		
	}
	public Gate(Area area, Orientation orientation, DiscreteCoordinates position, Key... key) {
		super(area, orientation, position);
		if(orientation==Orientation.UP || orientation==Orientation.DOWN) {
		sprite= new Sprite("superpacman/gate", 1, 1,this, new RegionOfInterest(0,0,64,64));
		
		}else {
			sprite= new Sprite("superpacman/gate", 1, 1,this, new RegionOfInterest(0,64,64,64));
		}
		this.signal=Logic.TRUE;
		this.key=key;
		
	}
	
	public Gate(Area area, Orientation orientation, DiscreteCoordinates position, Logic signal) {
		super(area, orientation, position);
		if(orientation==Orientation.UP || orientation==Orientation.DOWN) {
		sprite= new Sprite("superpacman/gate", 1, 1,this, new RegionOfInterest(0,0,64,64));
		
		}else {
			sprite= new Sprite("superpacman/gate", 1, 1,this, new RegionOfInterest(0,64,64,64));
		}
		this.signal=signal;
		key=new Logic[1];
		key[0]=Logic.TRUE;
		
	}
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		
		if(signal.isOff() || keysState())
		return true;
		else return false;
	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		//((SuperPacmanInteractionVisitor)v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		
		if(sprite != null) {
			if( signal.isOff() || keysState())
			sprite.draw(canvas);
		}
	}
	private boolean keysState() {
		boolean keysstate=false;
		int l = key.length;
		for(int i=0; i<l;i++) {
			if(keysstate) {}
			else {
				keysstate=key[i].isOff();
			}
		}
		return keysstate;
	}
}
