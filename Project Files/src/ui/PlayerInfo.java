package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;
import sim.objects.MovingObject;
import sim.objects.Player;

//Player info including health and score
public class PlayerInfo {
	private PVector pos;
	private float scale;
	
	public PlayerInfo(float x, float y, float s) {
		pos = new PVector(x, y);
		scale = s;
	}
	public void draw(Graphics2D g2, MovingObject player) {
		AffineTransform at = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.scale(scale, scale);
		
		g2.setColor(Color.WHITE);
		Font f = new Font("Arial", Font.PLAIN, 12);
		g2.setFont(f);
		
		g2.drawString("Score: " + ((Player) player).getScore(),  0, 0);
		g2.drawString("Health: " + ((Player) player).getHealth(),  0, 20);
		
		g2.setTransform(at);
	}
}
