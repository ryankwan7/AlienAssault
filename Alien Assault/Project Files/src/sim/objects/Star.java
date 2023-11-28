package sim.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

import processing.core.PVector;

//Background image
public class Star {
	private PVector pos;
	private float scale;
	private GeneralPath path;
	
	public Star(float x, float y, float scale) {
		pos = new PVector(x, y);
		this.scale = scale;
		this.path = createPath();
	}
	
	private GeneralPath createPath() {
		GeneralPath path = new GeneralPath();

        path.moveTo(0, 0);
        path.lineTo(100, 0);
        path.lineTo(100, 100);
        path.lineTo(0, 100);
        path.closePath();

        return path;
	}
	
	public void draw(Graphics2D g2) {
	        int numPoints = 5;
	        
	        double angle = 2.0 * Math.PI / numPoints;
	        
	        AffineTransform at = g2.getTransform();
	        
	        g2.translate(pos.x / 2, pos.y / 2);

			g2.rotate(angle);
			
			g2.scale(scale, scale);
			
			g2.setColor(new Color(0, 255, 255, 60));
			
	        for (int i = 0; i < numPoints; i++) {
	            g2.rotate(angle);
	            g2.fill(path);
	        }
	        g2.setTransform(at);
	}
}
