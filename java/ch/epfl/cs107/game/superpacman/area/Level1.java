package ch.epfl.cs107.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level1 extends SuperPacmanArea{

	private final DiscreteCoordinates PLAYER_SPAWN_POSITION= new DiscreteCoordinates(15,6);
	@Override
	public String getTitle() {
		return "superpacman/Level1";
	}

	@Override
	protected void createArea() {
		super.createArea();
		 registerActor( new Door("superpacman/Level2",  new DiscreteCoordinates(15,29), Logic.TRUE, this,Orientation.UP, new DiscreteCoordinates(14,0), new DiscreteCoordinates(15,0) ));
		 
		 registerActor(new Gate(this,Orientation.RIGHT,new DiscreteCoordinates(14,3),this));
	        registerActor(new Gate(this,Orientation.LEFT,new DiscreteCoordinates(15,3),this));
	}
	public DiscreteCoordinates getPlayerSpawnPosition() {
		return PLAYER_SPAWN_POSITION;
	}
}
