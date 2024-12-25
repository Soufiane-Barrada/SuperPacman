package ch.epfl.cs107.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level2 extends SuperPacmanArea{

	private Key key1 = new Key(this, new DiscreteCoordinates(3,16));
	private Key key2 = new Key(this, new DiscreteCoordinates(26,16));
	private Key key3 = new Key(this, new DiscreteCoordinates(2,8));
	private Key key4 = new Key(this, new DiscreteCoordinates(27,8));
	private Orientation[] orientation = { Orientation.RIGHT,Orientation.DOWN,Orientation.RIGHT,Orientation.RIGHT,Orientation.RIGHT,
			Orientation.DOWN,Orientation.RIGHT,Orientation.RIGHT,Orientation.RIGHT,
			Orientation.RIGHT,Orientation.RIGHT,Orientation.RIGHT,Orientation.RIGHT
			,Orientation.RIGHT};
	private DiscreteCoordinates[] position= { new DiscreteCoordinates(8,14),new DiscreteCoordinates(5,12),new DiscreteCoordinates(8,10),
			new DiscreteCoordinates(8,8),new DiscreteCoordinates(21,14),new DiscreteCoordinates(24,12),new DiscreteCoordinates(21,10),
			new DiscreteCoordinates(21,8),new DiscreteCoordinates(10,2),new DiscreteCoordinates(19,2),new DiscreteCoordinates(12,8),
			new DiscreteCoordinates(17,8),new DiscreteCoordinates(14,3),new DiscreteCoordinates(15,3)};
	private Key[][] keys= {{key1},{key1},{key1},{key1},{key2},{key2}
	,{key2},{key2},{key3, key4},{key3, key4},{key3, key4},{key3, key4},{null},{null}		
	};
	private final DiscreteCoordinates PLAYER_SPAWN_POSITION= new DiscreteCoordinates(15,29);
	@Override
	public String getTitle() {
		return "superpacman/Level2";
	}
	private void registerGatesPro() {
		
		
		for(int i=0; i< orientation.length; i++) {
			int r = keys[i].length;
			if(keys[i][0]==null) {
				registerActor( new Gate(this, orientation[i], position[i],this));
			}else{
				registerActor(new Gate(this, orientation[i], position[i], keys[i]));
			
			}
			
		}
		
	}
	@Override
	protected void createArea() {
		super.createArea();
		registerActor(key1);
		registerActor(key2);
		registerActor(key3);
		registerActor(key4);
		registerGatesPro();
	}
	public DiscreteCoordinates getPlayerSpawnPosition() {
		return PLAYER_SPAWN_POSITION;
	}
}
