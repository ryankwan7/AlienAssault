package sim.objects;

import util.ImageLoader;

//strongest enemy with even more health and bigger bullets
public class Enemy3 extends Enemy{
	public Enemy3(float x, float y, int w, int h, float s) {
		super(x, y, w, h, s);
		health = 5;
		maxHealth = health;
		img = ImageLoader.loadImage("assets/Enemy3.png");
	}
	
	//bullets are size 20 now
	public void fire() {
		missileList.add(new enemyMissile(pos.x, pos.y, 20, 20, 1f));
	}
}
