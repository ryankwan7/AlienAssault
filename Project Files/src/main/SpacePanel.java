package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import sim.objects.Player;
import ui.DeathMessage;
import ui.IntroScreen;
import ui.PlayButton;
import ui.RestartButton;
import ui.PlayerInfo;
import util.MinimHelper;
import util.Util;
import sim.objects.Enemy;
import sim.objects.Enemy2;
import sim.objects.Enemy3;
import sim.objects.Star;

public class SpacePanel extends JPanel implements ActionListener {

	/**
	 * Main Panel
	 */
	private static final long serialVersionUID = 1L;
	public final static Dimension PAN_SIZE = new Dimension(800, 600);
	private Timer t;

	private Player p;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private IntroScreen intro;
	private PlayButton playButton;
	private DeathMessage deathMessage;
	private RestartButton restart;
	private PlayerInfo score;
	
	private ArrayList<Star> stars = new ArrayList<Star>();

	private boolean up, down, left, right, fire;
	
	private int playerFireRate = 0;

	
	private final static int INTRO = 0;
	private final static int LEVEL = 1;
	private final static int DEATH = -1;
	private int state = INTRO;
	private JFrame frame;
	
	private Minim minim;
	private AudioPlayer shoot;
	private AudioPlayer bkmusic;
	
	public SpacePanel(JFrame frame) {
		super();
		this.frame = frame;
		this.setPreferredSize(PAN_SIZE);
		
		//adds player
		p = new Player(PAN_SIZE.width / 2, PAN_SIZE.height - 50, 20, 20, 1);
		
		//starting enemies
		for (int i = 0; i < 1; i++) {
			enemies.add(new Enemy(Util.random(100, PAN_SIZE.width - 100), 100, 50, 50, 1));
		}
		
		//creates background of stars
		for(int i = 0; i < 50; i++) {
			stars.add(new Star(Util.random(PAN_SIZE.width*2), Util.random(PAN_SIZE.height*2), 0.1f));
		}
		
		intro = new IntroScreen(PAN_SIZE.width/2, PAN_SIZE.height/2-150, 1);
		playButton = new PlayButton(PAN_SIZE.width/2, PAN_SIZE.height/2+100, 100, 50, 1);
		deathMessage = new DeathMessage(PAN_SIZE.width/2, PAN_SIZE.height/2-100, 1);
		restart = new RestartButton(PAN_SIZE.width/2, PAN_SIZE.height/2+100, 100, 50, 1);
		score = new PlayerInfo(20, 20 ,1);
		
		t = new Timer(33, this);
		t.start();
		
		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		
		minim = new Minim(new MinimHelper());
		shoot = minim.loadFile("playerShoot.wav");
		bkmusic = minim.loadFile("BackgroundMusic.mp3"); //the .wav file had issues for some reason
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Intro Screen
		if (state == INTRO) {
			intro.draw(g2);
			playButton.draw(g2);
		}
		//main level
		else if (state == LEVEL) {
			for(int i = 0; i < stars.size(); i++) {
				stars.get(i).draw(g2);
			}
			
			p.draw(g2);

			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).draw(g2);
			}
			
			score.draw(g2, p);
		}
		//GAME OVER
		else if(state == DEATH) {
			deathMessage.draw(g2);
			restart.draw(g2);
			score.draw(g2, p);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (state > 0) {
				//movement controls
				if (right)
					p.right();
				if (left)
					p.left();
				if (up)
					p.up();
				if (down)
					p.down();
				//shooting controls with a set fire rate
				if (fire) {
					if(playerFireRate >= 3) {
						shoot.play();
						p.fire();
						shoot.rewind();
						playerFireRate = 0;
					}
					playerFireRate++;
				}

				p.update(enemies);
			
			//Code for removing emeies
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).update(p);
				if (enemies.get(i).getHealth() <= 0) {
					enemies.remove(enemies.get(i));
					p.incScore();
				}
			}
			
			//Code for respawning enemies, difficulty increases with score
			while(enemies.size() < p.getScore() && enemies.size() < 50) {
				if(p.getScore() < 25)
					enemies.add(new Enemy(Util.random(100, PAN_SIZE.width - 100), 100, 50, 50, 1));
				else if(p.getScore() < 50)
					enemies.add(new Enemy2(Util.random(100, PAN_SIZE.width - 100), 100, 50, 50, 1));
				else
					enemies.add(new Enemy3(Util.random(100, PAN_SIZE.width - 100), 100, 50, 50, 1));
			}
			
			//GAME OVER if player loses all health
			if(p.getHealth() <= 0) {
				state = DEATH;
			}
		}

		repaint();
	}
	
	public class MyMouseListener extends MouseAdapter {
		
		//Button Controls
		public void mouseClicked(MouseEvent e) {
			double mouseX = e.getX();
			double mouseY = e.getY();
			
			if(state == INTRO && playButton.clicked(mouseX, mouseY)) {
				state = LEVEL;
				bkmusic.loop();
			}
			if(state == DEATH && restart.clicked(mouseX, mouseY)) {
				bkmusic.pause();
				frame.dispose();
				frame = new SpaceApp("Alien Assault!");
			}
		}
	}

	private class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				right = true;
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				left = true;
			if (e.getKeyCode() == KeyEvent.VK_UP)
				up = true;
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				down = true;
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
				fire = true;
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				right = false;
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				left = false;
			if (e.getKeyCode() == KeyEvent.VK_UP)
				up = false;
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				down = false;
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				fire = false;
				playerFireRate = 0;
			}
		}
	}
}
