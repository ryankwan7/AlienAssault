package sim.objects;

import java.awt.Color;

import processing.core.PVector;

//enemy missile that goes towards the player

public class enemyMissile extends Missile{
	
	public enemyMissile(float x, float y, int w, int h, float s) {
		super(x, y, w, h, s);
		vel = new PVector(0, 10);
		color = Color.red;
	}

}
