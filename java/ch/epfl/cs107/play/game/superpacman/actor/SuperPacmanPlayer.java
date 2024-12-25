package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.rpg.handler.FantomeInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class SuperPacmanPlayer extends Player  {
	private int score =0;
	private int hp= HP_START;
	private SuperPacmanPlayerStatusGUI SPPSG=new SuperPacmanPlayerStatusGUI(hp,score);
	private Sprite[][] sprite;
	private SuperPacmanPlayerHandler handler= new SuperPacmanPlayerHandler();
	private static final int ANIMATION_DURATION= 5;
	protected static final int HP_MAX = 5;
	protected static final int HP_START = 3;
    private float timer;
    private boolean wasEaten=false;
	public boolean s =false;
	
	
	Animation[] animations;
	 Orientation desiredOrientation=Orientation.UP;
	public SuperPacmanPlayer(Area area, DiscreteCoordinates posIni) {
		super(area,Orientation.UP,posIni);
		sprite = RPGSprite.extractSprites("superpacman/pacman",
				4, 1, 1,
				this , 64, 64, new Orientation[] {Orientation.DOWN ,
				Orientation.LEFT , Orientation.UP, Orientation.RIGHT});
				// crée un tableau de 4 animation
		 animations =Animation.createAnimations(ANIMATION_DURATION/2, sprite);

	}
	public boolean vulnerable() {
		return (timer <= 0.f);
	}
	public void makeInvulnerable() {
		timer = 10;
	}

	
	  public void update(float deltaTime) {
			
		 
				Keyboard keyboard= getOwnerArea().getKeyboard();
		        if(keyboard.get(Keyboard.LEFT).isDown())   desiredOrientation= Orientation.LEFT;
		        if(keyboard.get(Keyboard.RIGHT).isDown())   desiredOrientation= Orientation.RIGHT;
		        if(keyboard.get(Keyboard.UP).isDown())   desiredOrientation= Orientation.UP;
		        if(keyboard.get(Keyboard.DOWN).isDown())   desiredOrientation= Orientation.DOWN;
		        if(keyboard.get(Keyboard.S).isDown())   s=true;
		        if(!isDisplacementOccurs()) {
		        	if(getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates()
		        			.jump(desiredOrientation.toVector()))))   {
		        		this.orientate(desiredOrientation);
		        		move(6);
		        		animations[desiredOrientation.ordinal()].reset();
		        	}
		        }else {
		        	animations[desiredOrientation.ordinal()].update(deltaTime);
		        }
		        
		        
		        super.update(deltaTime);
		        if(!vulnerable()) {
		        timer-=deltaTime;
		        }
		       
		    }

		   
		    /**
		     * Leave an area by unregister this player
		     */
		    public void leaveArea(){
		        getOwnerArea().unregisterActor(this);
		        
		    }

		    /**
		     *
		     * @param area (Area): initial area, not null
		     * @param position (DiscreteCoordinates): initial position, not null
		     */
		
		@Override
		public void draw(Canvas canvas) {
			
			animations[desiredOrientation.ordinal()].draw(canvas);	
		SPPSG.draw(canvas);
		}

		@Override
		public boolean takeCellSpace() {
			return false;
		}

		@Override
		public boolean isCellInteractable() {
			return false;
		}

		@Override
		public boolean isViewInteractable() {
			return true;
		}
		@Override
		public List<DiscreteCoordinates> getCurrentCells() {
			return Collections.singletonList(getCurrentMainCellCoordinates());
		}
		@Override
		public List<DiscreteCoordinates> getFieldOfViewCells(){
			return null;
		}

		@Override
		public void acceptInteraction(AreaInteractionVisitor v) {
			if(v.getClass().getName().equals("ch.epfl.cs107.play.game.superpacman.actor.Fantome$FantomeHandler")) {
				((FantomeInteractionVisitor)v).interactWith(this);
			}
			else {
			((SuperPacmanInteractionVisitor)v).interactWith(this);}

		}
		@Override
	    public boolean wantsCellInteraction() {
	    	return true;
	    }
		@Override
	    public boolean wantsViewInteraction() {
	    	return false;
	    }
		@Override
	    public void interactWith(Interactable other){
	    	other.acceptInteraction(handler);
	    }
		public boolean wasEaten() {
			return wasEaten;
		}
		public void setWasEatenToFalse() {
			wasEaten=false;
		}
		
		private class SuperPacmanPlayerHandler implements SuperPacmanInteractionVisitor{
			@Override
			public void interactWith(Door door) {
				setIsPassingADoor(door);
			}
			
			@Override
			public void interactWith(Bonus bonus) {
				bonus.collect();
				makeInvulnerable();
				
			}
			@Override
			public void interactWith(Cherry cherry) {
				score+=200;
				SPPSG.setScore(score);
				cherry.collect();
			}
			@Override
			public void interactWith(Diamond diamond) {
				score+=10;
				SPPSG.setScore(score);
				diamond.collect();
			}
			@Override
			public void interactWith(Key key) {
				
				key.collect();
			}
			@Override
			public void interactWith(Fantome ghost) {
				if(vulnerable()) {
					if(hp>0) {
					hp--;
					SPPSG.setHp(hp);
					wasEaten=true;}
					else {
						System.out.println("vous avez perdu");
						System.exit(0);
					}
				}else {
					
					score+=Fantome.GHOST_SCORE;
					SPPSG.setScore(score);
					ghost.collect();
					ghost.setWasEaten(true);
				}
			}
		}

}
