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
	private TileLoader T;
	private Player P1;
	private BFS D;
	private Thread Pathfinding;
	
	public MainWindow(){
		
		Keyboard = new KeyboardSupport();
		
		Title = "Bumbap";
		
		this.setPreferredSize(new Dimension(640, 480));
		this.setMaximumSize(new Dimension(640, 480));
		this.setMinimumSize(new Dimension(640, 480));
		
		Window = new JFrame();
		Window.setTitle(Title);
		Window.setDefaultCloseOperation(3);
		Window.addKeyListener(Keyboard); // TODO
		Window.getContentPane().add(this);
		Window.setResizable(false);
		Window.pack();
		Window.setLocationRelativeTo(null);
		Window.setFocusable(true);
		Window.setVisible(true);
		
		System.out.println("Window size: "+Window.getSize());
		
		
		
		
		T = new TileLoader();
		T.load();
		
		P1 = new Player(32, 32, T.Player);
		D = new BFS(P1);
		Pathfinding = new Thread(D);
	}
	
	
	public void start(){
		Thread Finite = new Thread(this);
		Finite.start();
	}

	public double delta;
	public int frames;
	public int FPS;
	
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
		         frames++;
		         
		         // update our FPS counter if a second has passed since
		         // we last recorded
		         
		         // update the game logic
		         Update(delta);
		         
		         // draw everyting
		         repaint();
				   if (lastFpsTime >= 1000000000)
			         {
				       
					   //TITLE_FPS = " | fps: "+fps;
					   FPS = frames;
					   lastFpsTime = 0;
					   frames = 0;
			         }
				   
		         // we want each frame to take 10 milliseconds, to do this
		         // we've recorded when we started the frame. We add 10 milliseconds
		         // to this and then factor in the current time to give 
		         // us our final value to wait for
		         // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
		         try{Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );}catch (Exception e) {}
	    	  
	      }
	}	
	
	int moveH = 0;
	long now, lastTime=-1, updateTime = 1000;
	int addVal = 64;
	boolean isBFS = false;
	
	private void Update(double delta){
		if(lastTime == -1) lastTime = System.currentTimeMillis();
		now = System.currentTimeMillis();
		if((now-lastTime) >= updateTime) {
			
//			if(moveH >= 64*30) 	addVal = -1*addVal;
//			else if(moveH <= -1) addVal = Math.abs(addVal);
//			moveH+=addVal;
//			System.out.println("MoveH: "+moveH);
			
			lastTime = now;

			
		}
		
		if(P1 != null && Keyboard != null){
			if(Keyboard.W.isPressed) P1.movePlayer(0, -1, delta);
			if(Keyboard.S.isPressed) P1.movePlayer(0, 1, delta);
			if(Keyboard.A.isPressed) P1.movePlayer(-1, 0, delta);
			if(Keyboard.D.isPressed) P1.movePlayer(1, 0, delta);
			if(Keyboard.F1.isPressed) {
				if(isBFS==false){
					Pathfinding.start();
					isBFS=true;
				}
			}
		}
		

		
		
	}
	

	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
//		g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
//		g.setColor(Color.WHITE);
//		g.drawString("F1 - Start new game", (this.getWidth()/2)-50, (this.getHeight()/2)-50);
//		g.drawString("F2 - Join game", (this.getWidth()/2)-50, (this.getHeight()/2)-35);
//		g.drawString("ESC - Quit", (this.getWidth()/2)-50, (this.getHeight()/2)-20);

		
		if(T == null){
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
			g.setColor(Color.WHITE);
			g.drawString("LOADING", (this.getWidth()/2)-50, (this.getHeight()/2)-50);
			return;
		}
		try{
			drawMap(g);
			drawPlayer(g);
			drawDebugInfo(g);
		}catch(java.lang.ArrayIndexOutOfBoundsException | NullPointerException c){}

	}
	
	
	
	private void drawPlayer(Graphics g){
		if(P1 != null)
		if(isBFS)g.drawImage(P1.PlayerImage, P1.Position.width*32, P1.Position.height*32, 30, 30, null);
		else	g.drawImage(P1.PlayerImage, P1.Position.width, P1.Position.height, 30, 30, null);
	}
	
	private void drawDebugInfo(Graphics g){
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		g.setColor(Color.WHITE);
		g.drawString("FPS: "+FPS, 50, 20);
		g.drawString("Liczba krokow BFS: "+D.counter, 50, 40);
	}
	

	
	final int size = 32;
	public void drawMap(Graphics g){
			g.setColor(Color.white);
			for(int i = 0; i < 15; i++)
				for (int j = 0 ; j<20; j++){
					if(D.map[i][j] == 0)	g.drawImage(T.Tiles.elementAt(10), size*j, size*i, size, size, null);
					else if(D.map[i][j] == 1)	g.drawImage(T.Tiles.elementAt(12), size*j, size*i, size, size, null);
					else if(D.map[i][j] == 2)	g.drawImage(T.Tiles.elementAt(63), size*j, size*i, size, size, null);
					else if(D.map[i][j] == 3)	g.drawImage(T.Tiles.elementAt(83), size*j, size*i, size, size, null);
					else if(D.map[i][j] == -1)	g.drawString("O", size*j, size*i+16);
					else if(D.map[i][j] == -2)	g.drawString("A", size*j, size*i+16);
					else if(D.map[i][j] == -3)	g.drawString("D", size*j, size*i+16);
					else if(D.map[i][j] == -4)	g.drawString("W", size*j, size*i+16);
					else if(D.map[i][j] == -5)	g.drawString("S", size*j, size*i+16);
					else if(D.map[i][j] == 0)	g.drawString("E", size*j, size*i+16);
				}

	}
	
	
}
