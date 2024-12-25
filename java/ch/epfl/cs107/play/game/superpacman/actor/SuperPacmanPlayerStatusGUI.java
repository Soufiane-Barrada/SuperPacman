package ch.epfl.cs107.play.game.superpacman.actor;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SuperPacmanPlayerStatusGUI implements Graphics {
	private int hp;
	private int score;
	final float DEPTH=1.f;
	public void setHp(int hp) {
		this.hp = hp;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	SuperPacmanPlayerStatusGUI(int hp, int score){
		this.hp=hp;
		this.score=score;
	}
	@Override
	public void draw(Canvas canvas) {
		int m;
		ImageGraphics life;
		TextGraphics scoreText;
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2, height/2));
		scoreText = new 
				TextGraphics("Score: "+score, 1.5F, Color.BLACK, Color.YELLOW, 0.1f, false , false ,  anchor.add(new Vector(0.2f, 0.5f)));
			scoreText.draw(canvas);
		
	for(int i=0; i< SuperPacmanPlayer.HP_MAX; i++) {
		
		if(i>= hp) {m=64;} else {m=0;}
		
		 life = new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"),
				1.f, 1.f, new RegionOfInterest(m, 0, 64, 64),
				anchor.add(new Vector(i, height - 1.375f)), 1, DEPTH);
				life.draw(canvas);

	}
	}
}
