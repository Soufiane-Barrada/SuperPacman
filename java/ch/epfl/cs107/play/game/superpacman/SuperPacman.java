package ch.epfl.cs107.play.game.superpacman;

import ch.epfl.cs107.game.superpacman.area.Level0;
import ch.epfl.cs107.game.superpacman.area.Level1;
import ch.epfl.cs107.game.superpacman.area.Level2;
import ch.epfl.cs107.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class SuperPacman extends RPG{
	
	private Area currentArea;
	private SuperPacmanPlayer player;
	private final String[] areas = {"superpacman/Level0", "superpacman/Level1","superpacman/Level2","superpacman/level1Random"};
	private int areaIndex;
	private boolean wasAffraid=true;
	
	 public String getTitle() {
		 return "Super Pac-Man";
	 }
	
	 @Override
	 public void update(float deltaTime) {
		 
		 if(player.isPassingADoor()) {
			 
			for(int i=0; i<areas.length; i++) {
			if( player.passedDoor().getDestination().equals(areas[i])) {
				areaIndex=i;
			}
			}
			player.leaveArea();
			currentArea = setCurrentArea(areas[areaIndex],false);
			player.enterArea(currentArea, areaSpawnPosition(currentArea));
		 }
		 if(player.s==true) {
			 player.s=false;
			 if(areaIndex<3)
			 areaIndex++;
			 else {
				 areaIndex=0;
			 }
			 player.leaveArea();
				 currentArea = setCurrentArea(areas[areaIndex],false);
				player.enterArea(currentArea, areaSpawnPosition(currentArea));
		 }
		 if(player.wasEaten()) {
			 player.leaveArea();
				 currentArea = setCurrentArea(areas[areaIndex],false);
				player.enterArea(currentArea, areaSpawnPosition(currentArea));
				player.setWasEatenToFalse();
	        }
		 if(!player.vulnerable()  ) {
			 
			 ((SuperPacmanArea)currentArea).scareGhosts();
			
		 }
		 if(player.vulnerable() ){
			 ((SuperPacmanArea)currentArea).unScareGhosts();
			 
		 }
		 super.update(deltaTime);
		 
		 
	 }
	 
	 private void createAreas(){

			addArea(new Level0());
			addArea(new Level1());

			addArea(new Level2());
		}
	 
	 @Override
		public boolean begin(Window window, FileSystem fileSystem) {


			if (super.begin(window, fileSystem)) {

				createAreas();
				areaIndex=0;
				currentArea = setCurrentArea(areas[areaIndex], true);
				player = new SuperPacmanPlayer(currentArea, areaSpawnPosition(currentArea) );
				initPlayer(player);
			
				return true;
			}
			return false;
		}
	 private DiscreteCoordinates areaSpawnPosition(Area area) {
		 switch(areaIndex) {
		 case 0: return ((Level0)area).getPlayerSpawnPosition();
		 case 1: return ((Level1)area).getPlayerSpawnPosition();
		 case 2: return ((Level2)area).getPlayerSpawnPosition();	
		 default: return null;}
			 
	 }
}
