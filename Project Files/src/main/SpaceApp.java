package main;

import javax.swing.JFrame;

public class SpaceApp extends JFrame{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpaceApp(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SpacePanel panel = new SpacePanel(this);

		this.add(panel);
		this.pack();
		
		// displaying the frame
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new SpaceApp("Alien Assault!");
	}

}