package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

//Restart button for replaying after death
public class RestartButton extends Button{
	public RestartButton(float x, float y, int w, int h, float s) {
		super(x, y, w, h , s);
	}

	@Override
	public void draw(Graphics2D g2) {
		
		AffineTransform at = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.scale(scale, scale);
		
		g2.setColor(Color.YELLOW);
		g2.fillRect(-dim.width/2, -dim.height/2, dim.width, dim.height);
		
		g2.setColor(Color.BLACK);
		Font f = new Font("Arial", Font.BOLD, 24);
		g2.setFont(f);
		FontMetrics metrics = g2.getFontMetrics(f);
		
		float textWidth = metrics.stringWidth("Restart");
		g2.drawString("Restart", -textWidth / 2, 8);
		
		g2.setTransform(at);
	}
}
