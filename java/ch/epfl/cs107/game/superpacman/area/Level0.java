package ch.epfl.cs107.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level0 extends SuperPacmanArea{
	
	Orientation orientation[]= {Orientation.RIGHT, Orientation.LEFT};
	private final DiscreteCoordinates PLAYER_SPAWN_POSITION= new DiscreteCoordinates(10,1);
	@Override
	public String getTitle() {
		return "superpacman/Level0";
	}

	@Override
	protected void createArea() {
		Key key= new Key(this,new DiscreteCoordinates(3,4));
        super.createArea();
        registerActor( new Door("superpacman/Level1", new DiscreteCoordinates(15,6), Logic.TRUE, this,Orientation.UP, new DiscreteCoordinates(5,9), new DiscreteCoordinates(6,9) ));
        registerActor(key);
        registerActor(new Gate(this,Orientation.RIGHT,new DiscreteCoordinates(5,8),this,key));
        registerActor(new Gate(this,Orientation.LEFT,new DiscreteCoordinates(6,8),this, key));
	}
	
	public DiscreteCoordinates getPlayerSpawnPosition() {
		return PLAYER_SPAWN_POSITION;
	}
}
