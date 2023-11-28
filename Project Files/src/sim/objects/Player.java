package sim.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

import processing.core.PVector;
import util.ImageLoader;


//Player character 
public class Player extends Spaceship {
	
	private Ellipse2D hitbox; //hitbox to determine of player is hit by missile
	private BufferedImage img;
	private float dampen = 0.8f;
	private float maxSpeed = 10f;
	private Fumes fumes;
	private int score = 0;

	public Player(float x, float y, int w, int h, float s) {
		super(x, y, w, h, s);
		health = 5;
		maxHealth = health;
		vel = new PVector(0, 0);
		fumes = new Fumes(w/4, 30);
		img = ImageLoader.loadImage("assets/Player.png");
	}

	@Override
	public void draw(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		
		g2.translate(pos.x, pos.y);
		g2.scale(scale*3, scale*3);
		
		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
		
		g2.setTransform(at);
		
		//for drawing fumes
		g2.translate(pos.x, pos.y+8*scale);
		g2.scale(scale, scale);
		fumes.drawFumes(g2);

		g2.setColor(new Color(0, 0, 0, 0));
		g2.fill(hitbox);
		g2.setTransform(at);
		
	
		for (Missile m : missileList)
			m.draw(g2);		
	}

	@Override
	//check collision with walls
	protected void checkCollision(Dimension panelSize) {
		float margin = 150;
		Rectangle2D.Double top = new Rectangle2D.Double(-margin, -margin, panelSize.width + margin * 2, margin);
		Rectangle2D.Double bottom = new Rectangle2D.Double(-margin, panelSize.height, panelSize.width + margin * 2,
				margin);
		Rectangle2D.Double left = new Rectangle2D.Double(-margin, -margin, margin, panelSize.height + margin * 2);
		Rectangle2D.Double right = new Rectangle2D.Double(panelSize.width, -margin, margin,
				panelSize.height + margin * 2);

		if ((getBoundingBox().intersects(left) && vel.x < 0) || (getBoundingBox().intersects(right) && vel.x > 0)
				|| (getBoundingBox().intersects(top) && vel.y < 0)
				|| (getBoundingBox().intersects(bottom) && vel.y > 0))
			vel.set(0, 0);
	}

	@Override
	protected void setShapeAttributes() {
		hitbox = new Ellipse2D.Double(-dim.width / 2, -dim.height / 2, dim.width, dim.height);

	}

	@Override
	protected void setOutline() {
		outline = new Area(hitbox);

	}

	public void right() {
		vel.set(maxSpeed, vel.y);
	}

	public void left() {
		vel.set(-maxSpeed, vel.y);
	}

	public void up() {
		vel.set(vel.x, -maxSpeed);
	}

	public void down() {
		vel.set(vel.x, maxSpeed);
	}

	public void move() {
		vel.mult(dampen);
		pos.add(vel);
	}

	public void fire() {
		missileList.add(new playerMissile(pos.x, pos.y, 10, 10, 1f));
	}

	public void update(ArrayList<Enemy> enemies) {
		super.update();
		for (Enemy e : enemies) {
			for (int i = 0; i < missileList.size(); i++) {
				if (missileList.get(i).isColliding(e)) {
					e.hit();
					missileList.remove(i);
				}
			}
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public void incScore() {
		score++;
	}

}
