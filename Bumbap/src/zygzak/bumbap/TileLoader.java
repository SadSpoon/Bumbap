package zygzak.bumbap;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;

public class TileLoader {
	
	
	Vector<Image> Tiles;
	Image Player;
	
	public void load(){
		
		if(Tiles != null) Tiles.clear();
			else Tiles = new Vector<Image>();
		
		URL ImageURI = getClass().getResource("/images/gmb.png");
		if(ImageURI == null) { System.err.println("File not found."); return; }
		
		BufferedImage TileSet = null;
		try {
			TileSet = ImageIO.read(ImageURI);
		} catch (IllegalArgumentException | IOException e) {
			System.err.println("File not found: /images/gmb.png");
		}
		
		if(TileSet == null) return;
		
		final int offset = 2;
		final int margin = 1;
		final int size = 16;
		
		for(int j = 0; j < 22; j++)
			for(int i = 0; i < 20; i++){
				Tiles.add(TileSet.getSubimage((size*i)+(margin*(i+1)), (size*j)+(margin*(j+1)), size, size));
			}
			
		try {
			Player = ImageIO.read(getClass().getResource("/images/p1.png"));
		} catch (IOException e) {
			System.err.println("File not found: /images/bom1.png");
		}
		
	}
	
	
	
	
	public TileLoader(){}

	
}
