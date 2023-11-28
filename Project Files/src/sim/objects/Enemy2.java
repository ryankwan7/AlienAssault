package sim.objects;

import util.ImageLoader;

//stronger enemy with more health and bigger bullets
public class Enemy2 extends Enemy{

	public Enemy2(float x, float y, int w, int h, float s) {
		super(x, y, w, h, s);
		health = 3;
		maxHealth = health;
		img = ImageLoader.loadImage("assets/Enemy2.png");
	}
	
	//bullets are bigger now, size 15
	public void fire() {
		missileList.add(new enemyMissile(pos.x, pos.y, 15, 15, 1f));
	}

}
