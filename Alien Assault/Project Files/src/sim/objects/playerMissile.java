package sim.objects;

import java.awt.Color;

import processing.core.PVector;

//player missile going towards enemies
public class playerMissile extends Missile{
	
	public playerMissile(float x, float y, int w, int h, float s) {
		super(x, y, w, h, s);
		vel = new PVector(0, -30);
		color = Color.yellow;
	}

}
