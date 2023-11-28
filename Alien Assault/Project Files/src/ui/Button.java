package ui;

import java.awt.Dimension;
import java.awt.Graphics2D;

import processing.core.PVector;

public abstract class Button {
	protected PVector pos;
	protected double scale;
	protected Dimension dim;
	
	public Button(float x, float y, int w, int h, float s) {
		this.pos = new PVector(x, y);
		this.dim = new Dimension(w,h);
		this.scale = s;
	}
	
	public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if (x > (pos.x - ((double) dim.width) / 2 * scale) && x < (pos.x + ((double) dim.width)/2*scale) && y > (pos.y - ((double) dim.height)/2*scale) && y < (pos.y + ((double) dim.height)/2*scale)) 
			clicked = true;
		
		return clicked;
	}
	
	public abstract void draw(Graphics2D g2);
}
