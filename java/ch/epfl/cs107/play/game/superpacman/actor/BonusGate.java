package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;

import ch.epfl.cs107.play.game.rpg.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class BonusGate extends AreaEntity implements CollectableAreaEntity {
	Sprite sprite;
	
	boolean ballcollected=false;
		
		public BonusGate(Area area, DiscreteCoordinates position) {
			super(area, Orientation.DOWN, position);
			sprite= new Sprite("superpacman/gate", 1, 1,this, new RegionOfInterest(0,0,64,64));

			
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
			if(sprite != null)
				sprite.draw(canvas);
		}
		public void collect() {
			getOwnerArea().unregisterActor(this);
			ballcollected=true;
		}
		
		
}
