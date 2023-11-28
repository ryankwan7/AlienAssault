package sim.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import processing.core.PVector;

//abstract class for missiles
public abstract class Missile extends MovingObject{
	
	protected Color color;
	protected Ellipse2D circle;
	
	public Missile(float x, float y, int w, int h, float s) {
		super(x, y, w, h, s);
	}
	
	public PVector getPos() {
		return pos;
	}
	
	@Override
	public void update() {
		move();		
	}
	
	public void draw(Graphics2D g) {
		AffineTransform at = g.getTransform();
		g.translate(pos.x, pos.y);
		g.scale(scale, scale);
		g.setColor(color);
		g.fill(circle);
		g.setTransform(at);
		
	}

	protected void setShapeAttributes() {
		circle = new Ellipse2D.Double(-dim.width / 2, -dim.height / 2, dim.width, dim.height);
	}

	protected void setOutline() {
		outline = new Area(circle);
	}
}
