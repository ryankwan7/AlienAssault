package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;

//Death message
public class DeathMessage {
	private PVector pos;
	private float scale;
	public DeathMessage(float x, float y, float s) {
		pos = new PVector(x, y);
		scale = s;
	}
	
	public void draw(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.scale(scale, scale);
		
		g2.setColor(Color.RED);
		Font f = new Font("Arial", Font.BOLD, 36);
		g2.setFont(f);
		FontMetrics metrics = g2.getFontMetrics(f);
		
		float textWidth = metrics.stringWidth("YOU DIED");
		g2.drawString("YOU DIED",  -textWidth/2, 0);
		
		g2.setTransform(at);
	}
}
