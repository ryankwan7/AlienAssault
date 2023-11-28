package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;
import sim.objects.Star;

//Intro screen with title, controls, and play button
public class IntroScreen {
	
	private PVector pos;
	private float scale;
	private Star star;
	
	public IntroScreen(float x, float y, float s) {
		pos = new PVector(x, y);
		scale = s;
		star = new Star(x-400, y+190, 0.2f);
	}
	
	public void draw(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.scale(scale, scale);
		
		g2.setColor(Color.CYAN);
		Font f = new Font("Arial", Font.BOLD, 36);
		g2.setFont(f);
		FontMetrics metrics = g2.getFontMetrics(f);
		
		float textWidth = metrics.stringWidth("ALIEN ASSAULT!");
		g2.drawString("ALIEN ASSAULT!",  -textWidth/2, 0);
		
		g2.setColor(Color.WHITE);
		Font f2 = new Font("Arial", Font.PLAIN, 24);
		FontMetrics metrics2 = g2.getFontMetrics(f2);
		g2.setFont(f2);
		
		float textWidth2 = metrics2.stringWidth("Controls");
		g2.drawString("Controls",  -textWidth2/2, 60);
		g2.drawLine(-(int)textWidth2/2, 60 + 5, (int)textWidth2/2, 60 + 5);
		
		
		Font f3 = new Font("Arial", Font.PLAIN, 14);
		FontMetrics metrics3 = g2.getFontMetrics(f3);
		g2.setFont(f3);
		
		float textWidth3 = metrics3.stringWidth("Arrow Keys: Move");
		g2.drawString("Arrow Keys: Move",  -textWidth3/2, 90);
		
		float textWidth4 = metrics3.stringWidth("Space: Fire");
		g2.drawString("Space: Fire",  -textWidth4/2, 120);
		
		star.draw(g2);
		
		g2.setTransform(at);
	}
}
