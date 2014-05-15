package zygzak.bumbap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JPanel implements Runnable{

	JFrame Window;
	public String Title;
	
	private boolean IsRunning = true;
	private KeyboardSupport Keyboard;
	
	
	public MainWindow(){
		
		Keyboard = new KeyboardSupport();
		
		Title = "Bumbap";
		
		this.setPreferredSize(new Dimension(800, 600));
		this.setMaximumSize(new Dimension(800, 600));
		this.setMinimumSize(new Dimension(800, 600));
		
		Window = new JFrame();
		Window.setTitle(Title);
		Window.setDefaultCloseOperation(3);
		Window.setLocationRelativeTo(null);
		Window.addKeyListener(Keyboard); // TODO
		Window.getContentPane().add(this);
		Window.pack();
		Window.setFocusable(true);
		Window.setVisible(true);
		
	}
	
	
	public void start(){
		Thread Finite = new Thread(this);
		Finite.start();
	}

	public double delta;
	public int fps;
	
	public void run() {
		// TODO Auto-generated method stub
		long lastLoopTime = System.nanoTime();
		long now, updateLength, lastFpsTime = 0;
		long OPTIMAL_TIME = 1000000000/60;
		
		while (IsRunning)
	      {
	    	 
		         // work out how long its been since the last update, this
		         // will be used to calculate how far the entities should
		         // move this loop
		         now = System.nanoTime();
		         updateLength = now - lastLoopTime;
		         lastLoopTime = now;
		         delta = updateLength / (double)OPTIMAL_TIME;
		
		         // update the frame counter
		         lastFpsTime += updateLength;
		         fps++;
		         
		         // update our FPS counter if a second has passed since
		         // we last recorded
		         
		         // update the game logic
		         Update(delta);
		         
		         // draw everyting
		         repaint();
				   if (lastFpsTime >= 1000000000)
			         {
				       
					   //TITLE_FPS = " | fps: "+fps;
					   lastFpsTime = 0;
					   fps = 0;
			         }
				   
		         // we want each frame to take 10 milliseconds, to do this
		         // we've recorded when we started the frame. We add 10 milliseconds
		         // to this and then factor in the current time to give 
		         // us our final value to wait for
		         // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
		         try{Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );}catch (Exception e) {}
	    	  
	      }
	}	
	
	private void Update(double delta){}
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		g.setColor(Color.WHITE);
		g.drawString("F1 - Start new game", (this.getWidth()/2)-50, (this.getHeight()/2)-50);
		g.drawString("F2 - Join game", (this.getWidth()/2)-50, (this.getHeight()/2)-35);
		g.drawString("ESC - Quit", (this.getWidth()/2)-50, (this.getHeight()/2)-20);
		
	}
}
