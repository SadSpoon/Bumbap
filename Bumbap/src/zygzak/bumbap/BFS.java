package zygzak.bumbap;

import java.awt.Dimension;
import java.util.Vector;


public class BFS implements Runnable{
	
	public int counter = 0;
	private Vector<Dimension> Queue;
	public Vector<Dimension> OptimalQueue;
	private Dimension Position;
	private Player Doll;
	private MainWindow W;
	public int[][] mapGraph;
	public Dimension Start, End;
	final int PUSTE = 0;
	final int ODWIEDZONE = 2;
	final int LEFT = 3;
	final int RIGHT = 4;
	final int UP = 5;
	final int DOWN = 6;
	
	public BFS(Dimension Start, Dimension End, MapSystem Map, Player Doll, MainWindow W){
		mapGraph = new int[15][20];
		mapCopy(Map.Matrix);
		this.Start = (Dimension) Start.clone();
			this.Start.width /=32;
			this.Start.height /=32;
		this.End = End;
		this.Position = this.Start;
		Queue = new Vector<Dimension>();
		OptimalQueue = new Vector<Dimension>();
		this.Doll = Doll;
		this.W = W;
		

	}
	
	private void mapCopy(int[][] Map){
		for(int i = 0; i < 15; i++)
			for(int j=0;j<20;j++)
				mapGraph[i][j] = Map[i][j];
	}
	
	long now, updateLength, lastLoopTime;
	
	public void Find(){
		mapGraph[Start.height][Start.width] = ODWIEDZONE;
		Queue.add(Start);
		
		while(!Queue.isEmpty()){
			counter++;
			
			Position = Queue.firstElement();
			Queue.removeElementAt(0);
			
			{
				
				//Doll.Position.width = ((Dimension)(Position.clone())).width*32;
				//Doll.Position.height = ((Dimension)(Position.clone())).height*32;
				
			}
			
			//System.out.println("BFS: jestem na pozycji "+Position);
			
	        try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        if(Position.equals(End))   // sprawdzamy, czy doszliœmy do punktu K
	        {
	            System.err.println("BFS: znalazlem punkt docelowy!");
//	        	
//	            for(int i = 0; i < 15; i++){
//	            	System.out.println();
//	            	for(int j = 0; j < 20; j++)
//	            		System.out.print(mapGraph[i][j]+" ");
//	            }
//	            System.out.println();
	            
	        	// cofamy siê do punktu S, wymazuj¹c kroki

	            while(mapGraph[Position.height][Position.width] != ODWIEDZONE)
	            {

	                switch(mapGraph[Position.height][Position.width])
	                {
	                    case LEFT  : 
	                    	mapGraph[Position.height][Position.width++] = ODWIEDZONE;
	                    	OptimalQueue.add(new Dimension(Position.width-1,Position.height)); 
	                    	break;
	                    case UP  : 
	                    	mapGraph[Position.height++][Position.width] = ODWIEDZONE;
	                    	OptimalQueue.add(new Dimension(Position.width,Position.height-1)); 
	                    	break;
	                    case RIGHT : 
	                    	mapGraph[Position.height][Position.width--] = ODWIEDZONE; 
	                    	OptimalQueue.add(new Dimension(Position.width+1,Position.height)); 
	                    	break;
	                    case DOWN   : 
	                    	mapGraph[Position.height--][Position.width] = ODWIEDZONE; 
	                    	OptimalQueue.add(new Dimension(Position.width,Position.height+1)); 
	                    	break;
	                }

	                
	    			//Doll.Position = Position;
	    			//System.err.println("BFS: jestem na pozycji "+Position);
//	    	        try {
//	    				Thread.sleep(100);
//	    			} catch (InterruptedException e) {
//	    				e.printStackTrace();
//	    			}
	            }
	            System.err.println("BFS: utworzono optymalna sciezke.");
	            break;                // wychodzimy z pêtli
	        }


	        // szukamy wszystkich dróg wyjœcia z bie¿¹cej pozycji. Wspó³rzêdne
	        // komórek umieszczamy w kolejce, w komórkach umieszczamy kierunek przejœcia

	        if(mapGraph[Position.height][Position.width-1] == PUSTE)
	        {
	        	mapGraph[Position.height][Position.width-1] = LEFT; 
	            Queue.add(new Dimension(Position.width-1, Position.height));
	            //System.out.println("BFS: moge isc w lewo.");
	        }

	        if(mapGraph[Position.height-1][Position.width] == PUSTE)
	        {
	        	
	        	mapGraph[Position.height-1][Position.width] = UP;
	            Queue.add(new Dimension(Position.width, Position.height-1));
	            //System.out.println("BFS: moge isc w gore.");
	        }

	        if(mapGraph[Position.height][Position.width+1] == PUSTE)
	        {
	        	mapGraph[Position.height][Position.width+1] = RIGHT; 
	            Queue.add(new Dimension(Position.width+1, Position.height));
	            //System.out.println("BFS: moge isc w prawo.");
	        }

	        if(mapGraph[Position.height+1][Position.width] == PUSTE)
	        {
	        	mapGraph[Position.height+1][Position.width] = DOWN;
	            Queue.add(new Dimension(Position.width, Position.height+1));
	            //System.out.println("BFS: moge isc w dol.");
	        }
	        
			
		}
		//System.out.println("BFS: Zakonczono szukanie.");
		move();
	}

	public void run() {
		Find();
	}
	
	public int OptimalBFSSteps = -1;
	
	private void move(){
		Dimension Step;
		Start.width *=32;
		Start.height *=32;
		Doll.Position = Start;
		System.err.println("AIM: Queries: "+OptimalQueue.size());
		OptimalBFSSteps = OptimalQueue.size();
		while(!OptimalQueue.isEmpty()){
			Step = OptimalQueue.lastElement();
			OptimalQueue.remove(OptimalQueue.size()-1);
			Step.width *=32;
			Step.height *=32;
			
			//System.err.println(Step);
			
			while(Doll.Position.width != Step.width)	{
				if(Doll.Position.width < Step.width) Doll.movePlayer(1, 0, 1);
				else if(Doll.Position.width > Step.width) Doll.movePlayer(-1, 0, 1);
				
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			while(Doll.Position.height != Step.height)	{
				if(Doll.Position.height < Step.height) Doll.movePlayer(0, 1, W.delta);
				else if(Doll.Position.height > Step.height) Doll.movePlayer(0, -1, W.delta);
				
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			

		}
		W.isBFS = false;
	}
	
	
}
