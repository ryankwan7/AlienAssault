package sim.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import processing.core.PApplet;
import util.Util;

//smoke coming from player spaceship
public class Fumes {
	private int width, height;

	private float xstart;
	private float xnoise;
	private float ynoise;
	private PApplet pa;

	public Fumes(int w, int h) {
		width = w;
		height = h;
		xstart = Util.random(10);
		xnoise = xstart;
		ynoise = Util.random(10);
		pa = new PApplet();
	}
	
	public void drawFumes(Graphics2D g2) {
		float noiseFactor;
		AffineTransform at = g2.getTransform();
		g2.translate(-width/2, 20);
		for(int y=0; y <=height; y += 5) {
			ynoise += 0.1;
			xnoise = xstart;
			for(int x= 0; x<=width; x+=5) {
				xnoise+= 0.1;
				noiseFactor = pa.noise(xnoise,ynoise);

				AffineTransform at1 = g2.getTransform();
				g2.translate(x, y);
				g2.rotate(noiseFactor*Math.toRadians(540));
				float edgeSize = noiseFactor * 35;
				int grey = (int) (150 + (noiseFactor*105));
				int alph = (int) (80 +(noiseFactor*105));
				g2.setColor(new Color(grey,grey,grey,alph));
				g2.fill(new Ellipse2D.Float(-edgeSize/2, -edgeSize/4, edgeSize, edgeSize/2*noiseFactor));
				g2.setTransform(at1);
			}

		}
		g2.setTransform(at);
	}

}