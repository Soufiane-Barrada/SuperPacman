package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;

import ch.epfl.cs107.game.superpacman.area.SuperPacmanBehavior;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class BlinkyLeLunatique extends Fantome implements CollectableAreaEntity{
	 private Orientation desiredOrientation=Orientation.UP;
	
	Animation[] animations;
	private Sprite[][] sprite;
	public BlinkyLeLunatique(Area area,Orientation orientation,DiscreteCoordinates position) {
		super(area,orientation,position);
		sprite = RPGSprite.extractSprites("superpacman/ghost.blinky",
				2, 1, 1,
				this , 16, 16, new Orientation[] {Orientation.UP ,
				Orientation.RIGHT , Orientation.DOWN, Orientation.LEFT});
				// crée un tableau de 2 animations
		 animations =Animation.createAnimations(ANIMATION_DURATION/2, sprite);
	}

	 @Override
	 public void update(float deltaTime) {
		 if(!isDisplacementOccurs())
			 desiredOrientation=getNextOrientation();
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
	
	 @Override
		public void draw(Canvas canvas) {
		 if(!isAffraid()) {
		 animations[desiredOrientation.ordinal()].draw(canvas);	
		 }else {
			 animationAffraid.draw(canvas);
		 }
	 }
	 public  void collect() {
		 
		
		 getOwnerArea().leaveAreaCells(this , getEnteredCells());

		 setCurrentPosition(SuperPacmanBehavior.GhostsOrigineDiscreteCooridinates(this).toVector());
		 
		 getOwnerArea().enterAreaCells(this , getCurrentCells());
		 resetMotion();

	 }
	 Orientation getNextOrientation() {
		 int randomInt = RandomGenerator.getInstance().nextInt(4);
		 return Orientation.fromInt(randomInt);
	 }
}
