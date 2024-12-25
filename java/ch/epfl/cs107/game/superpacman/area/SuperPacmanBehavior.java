package ch.epfl.cs107.game.superpacman.area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.BlinkyLeLunatique;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.Fantome;
import ch.epfl.cs107.play.game.superpacman.actor.InkyLePrudent;
import ch.epfl.cs107.play.game.superpacman.actor.PinkyLeFuyant;
import ch.epfl.cs107.play.game.superpacman.actor.Wall;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class SuperPacmanBehavior extends AreaBehavior {
	
	private AreaGraph areaGraph;
	
	List<Fantome> listGhosts= new ArrayList<Fantome>();
	private static HashMap<Fantome,DiscreteCoordinates> celluleOrigine = new HashMap<Fantome,DiscreteCoordinates>();
	
	public Queue<Orientation> shortestPath(DiscreteCoordinates from, DiscreteCoordinates to){
		return areaGraph.shortestPath(from, to);
		
	}

	public enum SuperPacmanCellType{
		
		NONE(0), // never used as real content
		WALL ( -16777216), //black
		FREE_WITH_DIAMOND(-1), //white
		FREE_WITH_BLINKY (-65536), //red
		FREE_WITH_PINKY ( -157237), //pink
		FREE_WITH_INKY ( -16724737), //cyan
		FREE_WITH_CHERRY (-36752), //light red
		FREE_WITH_BONUS ( -16478723), //light blue
		FREE_EMPTY ( -6118750); // sort of gray

		final int type;
		

		SuperPacmanCellType(int type){
			this.type = type;
			
		}

		public static SuperPacmanCellType toType(int type){
			for(SuperPacmanCellType ict : SuperPacmanCellType.values()){
				if(ict.type == type)
					return ict;
			}
			// When you add a new color, you can print the int value here before assign it to a type
			System.out.println(type);
			return null;
		}
	}

	/**
	 * Default Tuto2Behavior Constructor
	 * @param window (Window), not null
	 * @param name (String): Name of the Behavior, not null
	 */
	public SuperPacmanBehavior(Window window, String name){
		super(window, name);
		areaGraph=new AreaGraph();
		int height = getHeight();
		int width = getWidth();
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width ; x++) {
				SuperPacmanCellType color = SuperPacmanCellType.toType(getRGB(height-1-y, x));
				setCell(x,y, new SuperPacmanCell(x,y,color));
			}
		}
	}
	
	private void addAllNodes() {
		int height = getHeight();
		int width = getWidth();
		
		
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				if(((SuperPacmanCell)getCell(x,y)).type != SuperPacmanCellType.WALL)
				areaGraph.addNode(new DiscreteCoordinates(x,y), hasLeftEdge(x,y), hasUpEdge(x,y), hasRightEdge(x,y), hasDownEdge(x,y));
				
			}
		}
	}
	public void setSignal(DiscreteCoordinates coordinates, Logic signal) {
		areaGraph.setSignal(coordinates, signal);
	}
	 public boolean nodeExists(DiscreteCoordinates coordinates){
	        return areaGraph.nodeExists(coordinates);
	    }
	void registerActors(Area area) {
		
		int height = getHeight();
		int width = getWidth();
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width ; x++) {
				switch (((SuperPacmanCell)getCell(x,y)).type) 
				{
				case WALL: area.registerActor(new Wall(area,  new DiscreteCoordinates(x,y), neighborhood(x,y) ));
				break;
				case FREE_WITH_DIAMOND: area.registerActor(new Diamond(area,  new DiscreteCoordinates(x,y) ));
				break;
				case FREE_WITH_CHERRY: area.registerActor(new Cherry(area,  new DiscreteCoordinates(x,y) ));
				break;
				case FREE_WITH_BONUS: area.registerActor(new Bonus(area,  new DiscreteCoordinates(x,y) ));
				break;
				case FREE_WITH_BLINKY: listGhosts.add(new BlinkyLeLunatique(area, Orientation.UP,new DiscreteCoordinates(x,y)));
				celluleOrigine.put(listGhosts.get(listGhosts.size()-1), new DiscreteCoordinates(x,y));
					area.registerActor(listGhosts.get(listGhosts.size()-1));
					break;
				case FREE_WITH_INKY: listGhosts.add(new InkyLePrudent(area, Orientation.UP,new DiscreteCoordinates(x,y)));
				celluleOrigine.put(listGhosts.get(listGhosts.size()-1), new DiscreteCoordinates(x,y));
					area.registerActor(listGhosts.get(listGhosts.size()-1));
					break;
				case FREE_WITH_PINKY: listGhosts.add(new PinkyLeFuyant(area, Orientation.UP,new DiscreteCoordinates(x,y)));
				celluleOrigine.put(listGhosts.get(listGhosts.size()-1), new DiscreteCoordinates(x,y));
					area.registerActor(listGhosts.get(listGhosts.size()-1));
					break;
				default : ;
				}
				
			}
		}
		addAllNodes();
	}
	private boolean hasLeftEdge(int x, int y) {
		return x > 0 && ( ((SuperPacmanCell)getCell(x-1,y)).type ) != SuperPacmanCellType.WALL ;
	}
	private boolean hasRightEdge(int x, int y) {
		return x < getWidth()-1 && ( ((SuperPacmanCell)getCell(x+1,y)).type ) != SuperPacmanCellType.WALL ;
	}
	private boolean hasDownEdge(int x, int y) {
		return y > 0 && ( ((SuperPacmanCell)getCell(x,y-1)).type ) != SuperPacmanCellType.WALL ;
	}
	private boolean hasUpEdge(int x, int y) {
		return y < getHeight()-1 && ( ((SuperPacmanCell)getCell(x,y+1)).type ) != SuperPacmanCellType.WALL ;
	}
		public static DiscreteCoordinates GhostsOrigineDiscreteCooridinates(Fantome ghost) {
		return celluleOrigine.get(ghost);
	}
	
	private  boolean[][] neighborhood(int x, int y){
		boolean[][] tab = new boolean[3][3];
		if(x==0|| y==0 || x+1 == getWidth() || y+1 == getHeight()) {
		
			tab=borderNeighborhoodCases(x,y,tab);
		}
		else {for(int i=x-1; i<=x+1; i++) {
			for(int j=y+1; j>=y-1; j--) {
				
				if(((SuperPacmanCell)getCell(i,j)).type==SuperPacmanCellType.WALL) {
					tab[i-(x-1)][-(j-(y+1))]=true;
				}
				else { tab[i-(x-1)][-(j-(y+1))]=false; }
			}
		}
		}
		
		return tab;
	}

	private boolean[][] borderNeighborhoodCases(int x, int y, boolean[][] tab){
		if(x==0) {
			tab[0][0]=true;
			tab[1][0]=true;
			tab[2][0]=true;}
			if(y==0) {
				tab[0][0]=true;
				tab[0][1]=true;
				tab[0][2]=true;
				
			}
		
		
			if(x+1==getWidth()) {
				tab[0][2]=true;
				tab[1][2]=true;
				tab[2][2]=true;}
				if(y+1== getHeight()) {
					tab[2][0]=true;
					tab[2][1]=true;
					tab[2][2]=true;
					
				}
				return tab;
	}
	
	
	/**
	 * Cell adapted to the Tuto2 game
	 */
	public class SuperPacmanCell extends AreaBehavior.Cell {
		/// Type of the cell following the enum
		private final SuperPacmanCellType type;
		
		/**
		 * Default Tuto2Cell Constructor
		 * @param x (int): x coordinate of the cell
		 * @param y (int): y coordinate of the cell
		 * @param type (EnigmeCellType), not null
		 */
		public  SuperPacmanCell(int x, int y, SuperPacmanCellType type){
			super(x, y);
			this.type = type;
		}
	
		@Override
		protected boolean canLeave(Interactable entity) {
			return true;
		}

		@Override
		protected boolean canEnter(Interactable entity) {
			if(hasNonTraversableContent())
			return false;
			else return true;
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
		}

	}
}
