package zygzak.bumbap;

import java.awt.Dimension;
import java.awt.Image;

public class Player {
	public Dimension Position;
	public int Speed = 4;
	public Image PlayerImage;
	
	public Player(int x, int y, Image PlayerImage){
		Position = new Dimension();
		this.Position.width = x;
		this.Position.height = y;
		this.PlayerImage = PlayerImage;
	}
	
	public void movePlayer(int dx, int dy, double delta){
		Position.width += dx*(Speed*delta);
		Position.height += dy*(Speed*delta);
	}
}
