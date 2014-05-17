package zygzak.bumbap;

import java.awt.Dimension;
import java.awt.Image;

public class Player {
	public Dimension Position;
	public int Speed = 4;
	public Image PlayerImage;
	private BFS Movement;
	private Thread AIM;
	private MainWindow W;
	
	public Player(int x, int y, Image PlayerImage, MainWindow W){
		Position = new Dimension();
		this.Position.width = x;
		this.Position.height = y;
		this.PlayerImage = PlayerImage;
		this.W = W;
	}
	
	public void movePlayer(int dx, int dy, double delta){
		Position.width += dx*(Speed*delta);
		Position.height += dy*(Speed*delta);
	}
	
	public void AutoInteligentMovement(MapSystem Map){
		if(AIM == null) Movement = new BFS(Position, new Dimension(18,13), Map, this, W);
		else {
			Movement = null;
			Movement = new BFS(Position, new Dimension(18,13), Map, this, W);
		}
		AIM = new Thread(Movement);
		AIM.start();
	}
	
	public int BFSSteps(){
		return Movement.counter;
	}
	
	public int BFSOptiNodes(){
		return Movement.OptimalBFSSteps;
	}
}
