package sim.objects;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import processing.core.PVector;

//Abstract class for all moving objects including enemies, players, and missiles
public abstract class MovingObject {
	protected PVector pos, vel;
	protected Dimension dim;
	protected float scale;
	protected Area outline;
	
	public MovingObject(float x, float y, int w, int h, float s) {
		this.pos = new PVector(x, y);
		this.dim = new Dimension(w,h);
		this.scale = s;
		setShapeAttributes();
		setOutline();
	}
	
	public void move() {
		pos.add(vel);
	}
	
	public Rectangle2D getBoundingBox() {
		return getOutline().getBounds2D();
	}
	
	protected Shape getOutline() {
		AffineTransform at = new AffineTransform();
		at.translate(pos.x, pos.y);
		at.rotate(vel.heading());
		at.scale(scale, scale);
		return at.createTransformedShape(outline);
	}
	
	protected boolean isColliding(MovingObject other) {
		return (getOutline().intersects(other.getBoundingBox()) &&
				other.getOutline().intersects(getBoundingBox()) );
	}
	
	public abstract void draw(Graphics2D g2);
	public abstract void update();
	protected abstract void setShapeAttributes();
	protected abstract void setOutline();
}
