package sim.objects;

import java.awt.Dimension;
import java.util.ArrayList;

import main.SpacePanel;

//abstract class for players and enemies
public abstract class Spaceship extends MovingObject {
	protected int health;
	protected int maxHealth;
	protected ArrayList<Missile> missileList = new ArrayList<Missile>();

	public Spaceship(float x, float y, int w, int h, float s) {
		super(x, y, w, h, s);
	}
	
	public void update() {
		checkCollision(SpacePanel.PAN_SIZE);
		move();
		
		//if missiles are off screen, remove them for optimization
		for(int i = 0; i < missileList.size(); i++) {
			missileList.get(i).update();
			if(missileList.get(i).getPos().y < -50 || missileList.get(i).getPos().y > SpacePanel.PAN_SIZE.height + 50) {
				missileList.remove(missileList.get(i));
			}
		}
	}
	
	public void hit() {
		health--;
	}
	
	public int getHealth() {
		return health;
	}
	
	protected abstract void checkCollision(Dimension panelSize);
}
