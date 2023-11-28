package sim.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import util.ImageLoader;
import util.Util;

//Enemy that shoots at player
public class Enemy extends Spaceship {

	private Rectangle2D hitbox; //enemy hitbox
	private Rectangle2D healthbar;
	private Rectangle2D healthPercent;
	private int fireRate = 0;
	protected BufferedImage img;

	public Enemy(float x, float y, int w, int h, float s) {
		super(x, y, w, h, s);
		vel = Util.randomPVector((int) Util.random(-10, 10), 0);
		health = 2;
		maxHealth = health;
		img = ImageLoader.loadImage("assets/Enemy1.png");
	}

	@Override
	//check collision with walls
	protected void checkCollision(Dimension panelSize) {
		float margin = 150;
		Rectangle2D.Double left = new Rectangle2D.Double(-margin, -margin, margin, panelSize.height + margin * 2);
		Rectangle2D.Double right = new Rectangle2D.Double(panelSize.width, -margin, margin,
				panelSize.height + margin * 2);

		if (getBoundingBox().intersects(left) && vel.x < 0)
			vel.set(-vel.x, 0);
		else if (getBoundingBox().intersects(right) && vel.x > 0)
			vel.set(-vel.x, 0);
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform at = g.getTransform();

		g.translate(pos.x, pos.y);
		g.scale(scale, scale);
		g.setColor(new Color(0, 0, 0, 0));
		g.fill(hitbox);

		if (health < maxHealth) {
			g.setColor(Color.red);
			g.fill(healthbar);

			g.setColor(Color.green);
			
			//For drawing a healthbar
			healthPercent = new Rectangle2D.Double(-dim.width / 2, -dim.height / 2,
					dim.width * ((double) health / (double) maxHealth), 3);
			g.fill(healthPercent);
		}
		g.setTransform(at);

		g.translate(pos.x, pos.y);
		g.scale(scale * 4, scale * 4);

		g.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);

		g.setTransform(at);
		
		for (Missile m : missileList)
			m.draw(g);
	}

	@Override
	protected void setShapeAttributes() {
		hitbox = new Rectangle2D.Double(-dim.width / 2, -dim.height / 2 + scale * 7, dim.width, dim.height);
		healthbar = new Rectangle2D.Double(-dim.width / 2, -dim.height / 2, dim.width, 3);
	}

	@Override
	protected void setOutline() {
		outline = new Area(hitbox);
	}

	public void update(MovingObject player) {
		super.update();
		
		//shooting with set fire rate
		fireRate++;
		if (fireRate >= 30) {
			this.fire();
			fireRate = 0;
		}
		
		//Removes missile if player is hit
		for (int i = 0; i < missileList.size(); i++) {
			if (missileList.get(i).isColliding(player)) {
				((Player) player).hit();
				missileList.remove(i);
			}
		}
	}
	
	//Fire with set missile speed
	public void fire() {
		missileList.add(new enemyMissile(pos.x, pos.y, 10, 10, 1f));
	}

}
