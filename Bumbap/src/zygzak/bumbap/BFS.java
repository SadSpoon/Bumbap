package zygzak.bumbap;

import java.awt.Dimension;
import java.lang.reflect.Array;
import java.util.Vector;


public class BFS implements Runnable{
	
	public int counter = 0;
	private Vector<Dimension> Queue;
	private Dimension Position;
	private Player Doll;
	
	public int map[][]={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,1,1,0,1,1,1,0,1,1,1,1,1,1,0,1,1},
			{1,1,0,1,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1,1},
			{1,1,0,1,0,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1},
			{1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1},
			{1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,1,0,1,0,0,1,1,0,1,1,1,1},
			{1,0,1,0,1,1,1,1,1,0,1,1,0,0,0,0,0,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
				
		};
	
	public int[][] mapGraph;

	
	public Dimension Start, End;
	
	final int PUSTE = 0;
	final int ODWIEDZONE = -1;
	final int LEFT = -2;
	final int RIGHT = -3;
	final int UP = -4;
	final int DOWN = -5;
	
	public BFS(Player Doll){
		mapGraph = new int[15][20];
		System.arraycopy(map, 0, mapGraph, 0, map.length);
		
		Start = new Dimension(1,1);
		End = new Dimension(18,13);
		Position = new Dimension(1,1);
		Queue = new Vector<Dimension>();
		this.Doll = Doll;
		
		mapGraph[1][1] = 0;
		mapGraph[13][18] = 0;
		

	}
	
	long now, updateLength, lastLoopTime;
	
	public void Find(){
		mapGraph[1][1] = ODWIEDZONE;
		Queue.add(new Dimension(1,1));
		
		while(!Queue.isEmpty()){
			counter++;
			
			Position = Queue.firstElement();
			Queue.removeElementAt(0);
			
			Doll.Position = Position;
			
			System.out.println("BFS: jestem na pozycji "+Doll.Position);
			
	        try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        if(Position.equals(End))
	        	System.err.println("BFS: znalazlem wyjscie!");

	        // szukamy wszystkich dróg wyjœcia z bie¿¹cej pozycji. Wspó³rzêdne
	        // komórek umieszczamy w kolejce, w komórkach umieszczamy kierunek przejœcia

	        if(mapGraph[Position.height][Position.width-1] == PUSTE)
	        {
	        	mapGraph[Doll.Position.height][Doll.Position.width-1] = LEFT; 
	            Queue.add(new Dimension(Position.width-1, Position.height));
	            System.out.println("BFS: moge isc w lewo.");
	        }

	        if(mapGraph[Position.height-1][Position.width] == PUSTE)
	        {
	        	
	        	mapGraph[Position.height-1][Position.width] = UP;
	            Queue.add(new Dimension(Position.width, Position.height-1));
	            System.out.println("BFS: moge isc w gore.");
	        }

	        if(mapGraph[Position.height][Position.width+1] == PUSTE)
	        {
	        	mapGraph[Position.height][Position.width+1] = RIGHT; 
	            Queue.add(new Dimension(Position.width+1, Position.height));
	            System.out.println("BFS: moge isc w prawo.");
	        }

	        if(mapGraph[Position.height+1][Position.width] == PUSTE)
	        {
	        	mapGraph[Position.height+1][Position.width] = DOWN;
	            Queue.add(new Dimension(Position.width, Position.height+1));
	            System.out.println("BFS: moge isc w dol.");
	        }
	        
			
		}
		System.out.println("BFS: Zakonczono szukanie.");
	}

	public void run() {
		Find();
	}
	
	
}
