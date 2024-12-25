package ch.epfl.cs107.game.superpacman.area;

import java.util.Queue;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.ImprovedFantome;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

abstract public class SuperPacmanArea extends Area implements Logic {

	public final static float CAMERA_SCALE_FACTOR = 50.f;

	
	
	private SuperPacmanBehavior behavior;
	protected void createArea() {
         
        
        behavior.registerActors(this);
        
	}
	

	public Queue<Orientation> shortestPath(DiscreteCoordinates from, DiscreteCoordinates to){
		return behavior.shortestPath(from, to);
	}
	
	public void setSignal(DiscreteCoordinates coordinates, Logic signal) {
		behavior.setSignal(coordinates, signal);
	}
	public boolean nodeExists(DiscreteCoordinates coordinates){
        return behavior.nodeExists(coordinates);
    }
	public void scareGhosts() {
		for(int i=0; i< behavior.listGhosts.size(); i++) {
			
			
			
			if(behavior.listGhosts.get(i) instanceof ImprovedFantome && ! ((ImprovedFantome)(behavior.listGhosts.get(i))).isAffraid() ) 
			((ImprovedFantome)(behavior.listGhosts.get(i))).setStateTransition(true);
			
			behavior.listGhosts.get(i).setAffraid(Logic.TRUE);
			
		}
	}
	public void unScareGhosts() {
		for(int i=0; i< behavior.listGhosts.size(); i++) {
			
			
			if(behavior.listGhosts.get(i) instanceof ImprovedFantome && ((ImprovedFantome)(behavior.listGhosts.get(i))).isAffraid() )
			((ImprovedFantome)(behavior.listGhosts.get(i))).setStateTransition(true);
			
			behavior.listGhosts.get(i).setAffraid(Logic.FALSE);
		}
		
	}
	
	@Override
    public final float getCameraScaleFactor() {
        return CAMERA_SCALE_FACTOR;
    }
	
	@Override
	 public boolean begin(Window window, FileSystem fileSystem) {
		  if (super.begin(window, fileSystem)) {
	            // Set the behavior map
	        	behavior = new SuperPacmanBehavior(window, getTitle());
	            setBehavior(behavior);
	            createArea();
	            return true;
	        }
	        return false;
	 }
	@Override
	public boolean isOn() {
	if(Diamond.nbrDiamond==0) {
		return true;
	}
	else return false;
	
}
	@Override
	public boolean isOff() {
		if(Diamond.nbrDiamond==0) {
			return false;
		}
		else return true;
		
	}
	@Override
	public float getIntensity() {
		if(isOn()) {
			return 1.0f;
		}
		else return 0.0f;
	}
}