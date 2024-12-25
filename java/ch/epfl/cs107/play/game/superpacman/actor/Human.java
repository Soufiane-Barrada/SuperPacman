package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Human extends MovableAreaEntity{
	private Orientation desiredOrientation=Orientation.UP;
	 static final int ANIMATION_DURATION= 5;
	Animation[] animations;
	private Sprite[][] sprite;
	public Human(Area area,Orientation orientation,DiscreteCoordinates position) {
		
		super(area,orientation,position);
		sprite = RPGSprite.extractSprites("superpacman/ghost.boy",
				2, 1, 1,
				this , 16, 16, new Orientation[] {Orientation.UP ,
				Orientation.RIGHT , Orientation.DOWN, Orientation.LEFT});
				// crée un tableau de 2 animations
		 animations =Animation.createAnimations(ANIMATION_DURATION/2, sprite);
		
		
		
	}
	
	

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean takeCellSpace() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		animations[desiredOrientation.ordinal()].draw(canvas);	
	}

}
