package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Bonus extends AreaEntity implements CollectableAreaEntity{

	Sprite[] sprite;
	Animation animation;
	private static final int ANIMATION_DURATION= 5;
	public Bonus(Area area, DiscreteCoordinates position) {
		super(area, Orientation.DOWN, position);
		sprite= Sprite.extractSprites("superpacman/coin", 4, 1, 1,
				this , 16, 16);

		animation=new Animation(ANIMATION_DURATION/2, sprite);
	}
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		animation.update(deltaTime);
	}
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
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
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor)v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		if(animation != null)
			animation.draw(canvas);
	}
	public void collect() {
		getOwnerArea().unregisterActor(this);
	}
	
}
