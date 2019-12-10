// This player is as rudimentary as it gets.  It simply puts the ships in a static 
// location, and makes random moves until one sticks.  Your player can use this 
// as a base to expand upon. It is a good idea to play against this player until yours
// gets good enough to beat it regularly.
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class Player1 extends Player {
	boolean hitThisRound = false;
	boolean hitPvsRound = false;
	ArrayList<Boolean> hitTracking = new ArrayList<Boolean>();
//	int row1,col1,row2,col2;
	int[][] trackingGrid = new int[10][10];
	Map<String, Integer> moveList = new HashMap<String, Integer>();
	public static final int[][] shipSize = {{ 2, 1 }, { 3, 1 }, { 3, 1 },{ 4, 1 }, { 5, 1 }};
	public static int[][] gridState = new int[10][10];
	public static boolean[] shipSank = new boolean[5];
	public static int[][] kGrid;
	Integer tempTrack,row1,col1,row2,col2;
	// You must call the super to establish the necessary game variables
	// and register the game.
	public Player1(int playerNum) {
		super(playerNum);
		setName("Matt Tse");

	}

	public void hunt(){
		
		//declare array
		double[][] prob = new double[9][9];
		
		//Array Initialization
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				prob[i][j] = 0;
		
		/*
		 * 	Goes through each cell, and then iterates through all the possible
		 * 	ship placements. Then increments 1 for the possible locations, forming
		 * 	the final ship probability grid.
		 */
		for (int cX = 0; cX < 10; cX++){
			for (int cY = 0; cY < 10; cY++){
				
				for (int p = 0; p < 5; p++){
					//horizontal placement
					int lX = cX + shipSize[p][0] - 1, 
							lY = cY + shipSize[p][1] - 1;
					
					//check boundaries
					if (lX >= 10 || lY >= 10) continue;
					
					//check if the selected area is occupied or not
					boolean areaOccupied = false;

					for (int i = cX; i <= lX; i++)
						if (gridState[i][cY] != 0)
							areaOccupied = true;
					
					if (areaOccupied) continue;
					
					// add probability if the area has never been fired
					for (int i = cX; i <= lX; i++)
						prob[i][cY] += 1;
				}
				
				//vertical placement
				for (int p = 0; p < 5; p++){
					int lX = cX + shipSize[p][1] - 1, 
							lY = cY + shipSize[p][0] - 1;
					
					//check boundaries
					if (lX >= 10 || lY >= 10) continue;
					
					//check if the selected area is occupied or not
					boolean areaOccupied = false;
					
					for (int i = cY; i <= lY; i++)
						if (gridState[cX][i] != 0)
							areaOccupied = true;
					
					if (areaOccupied) continue;
					
					// add probability if the area has never been fired
					for (int i = cY; i <= lY; i++)
						prob[cX][i] += 1;
				}
				
			}			
			
		}
	
	}

		
	public void target(){
		
		kGrid = (int[][])gridState.clone();
		
		double[][] prob = new double[10][10];
		
		//Array Initialization
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				prob[i][j] = 0;
		
		/*
		 * 	Goes through each cell, and then iterates through all the possible
		 * 	ship placements. Then increments 1 for the possible locations, forming
		 * 	the final ship probability grid.
		 */
		
		// loop through all the points
		for (int cX = 0; cX < 10; cX++){
			for (int cY = 0; cY < 10; cY++){
				
				
				for (int p = 0; p < 5; p++){
					if (!shipSank[p]){// if the ship has not been sank
						
						//horizontal placement
						int lX = cX + shipSize[p][0] - 1, 
								lY = cY + shipSize[p][1] - 1;
						
						//check out of bound
						if (lX >= 10 || lY >= 10) continue;
						
						//declare boolean conditions to determine if the grid probability is viable
						boolean hitIncluded = false;
						boolean missIncluded = false;
						
						int nHitIncluded = 0;
						
						//check if the selected area has successful hit on the ship
						for (int i = cX; i <= lX; i++){
							if (kGrid[i][cY] == 1){
								hitIncluded = true;
								nHitIncluded += 1;
							}
							
						//check if the selected area has previous miss or sank ship
							if (kGrid[i][cY] == 2 || kGrid[i][cY] == 3){
								missIncluded = true;
								break;
							}
						}
						
						
						if (!hitIncluded || missIncluded) continue;
						
						// if the selected area contains successful hit on the ship but not previous hit or sank, then update
						for (int i = cX; i <= lX; i++)
							prob[i][cY] += nHitIncluded;
					}
				}
				
				for (int p = 0; p < 5; p++){
					if (!shipSank[p]){
						//vertical placement
						int lX = cX + shipSize[p][1] - 1, 
								lY = cY + shipSize[p][0] - 1;
						
						if (lX >= 10 || lY >= 10) continue;
						
						//declare boolean conditions to determine if the grid probability is viable
						boolean hitIncluded = false;
						boolean missIncluded = false;
						
						int nHitIncluded = 0;
						
						//check if the selected area has successful hit on the ship
						for (int i = cY; i <= lY; i++){
							if (kGrid[cX][i] == 1){
								hitIncluded = true;
								nHitIncluded += 1;
							}
							
							//check if the selected area has previous miss or sank ship
							if (kGrid[cX][i] == 2 || kGrid[cX][i] == 3){
								missIncluded = true;
								break;
							}
						}
						
						if ((!hitIncluded) || missIncluded) continue;
						// if the selected area contains successful hit on the ship but not previous hit or sank, then update
						for (int i = cY; i <= lY; i++)
							prob[cX][i] += nHitIncluded;
					}
				}				
			}
			
		}
	}
		
	

	
	public void makeMove() {
		int row = randomRow();//(A-F) y axis
		int col = randomCol();//(1-10) x axis
		//default random
		row = randomRow();
		col = randomCol();
		//not starting move
		int previousMove = this.numMoves-1;
		int secondPreviousMove = previousMove-1;

		if (this.numMoves > 0) {
			//restore previous round values
			tempTrack = this.moveList.get(String.valueOf(numMoves-1))+10;
			boolean trackPreviousMove = hitTracking.get(previousMove);

			if (tempTrack == 110) {
				row = 10;
				col = 10;
			} else {
				row1 = Integer.valueOf(String.valueOf(tempTrack).substring(0, 1));
				col1 = Integer.valueOf(String.valueOf(tempTrack).substring(1, 2));
			}
			
			if (this.numMoves > 1) {
				boolean trackSecondPreviousMove = hitTracking.get(secondPreviousMove);
				//if true true
				//read 2 previous coordinates
				//check which value (row or col) is the same
				//increment one that is not the same
				if (hitTracking.get(previousMove) == true && hitTracking.get(secondPreviousMove) == true) {
					//if previous row hit and previous col hit, track right

					col = col1+1;
					row = row1;

					if ((col==10&&row==10)||(col==1&&col==1)||(col==1&&col==10)||(col==10&&col==1)) {
						col = randomCol();
						row = randomRow();
					}
					
					
				}
				if (hitTracking.get(previousMove) == false && hitTracking.get(secondPreviousMove) == true) {
					if ((col==10&&row==10)||(col==1&&col==1)||(col==1&&col==10)||(col==10&&col==1)) {
						col = randomCol();
						row = randomRow();
					} else {
						int shipSize = 0;
						for (int i = 0; i < hitTracking.size(); i++) {
							if (hitTracking.get(i) == true) {
								shipSize+=1;
							}
						}
						col=col1-(shipSize+1);
						row=row2;						
					}

				}
				if (hitTracking.get(previousMove) == true && hitTracking.get(secondPreviousMove) == false) {
					if ((col==10&&row==10)||(col==1&&col==1)||(col==1&&col==10)||(col==10&&col==1)) {
						col = randomCol();
						row = randomRow();
					} else {
						col=col2+1;
						row=row2;						
					}
				}
			} else 
			if (hitTracking.get(previousMove) == true) {
				col = col1+1;
				row = row1;
			}
			
			
//					 //previous hit and previous previous hit was false
//					//move random
//					if (hitTracking.get(numMoves) == false && hitTracking.get(numMoves-1) == false) {
//						row = randomRow();
//						col = randomCol();
//									
//
//					}
//					//if hit previous round and not hit previous previous round
//					if (hitTracking.get(numMoves) == true && hitTracking.get(numMoves-1) == false) {
//						if (col < 10) {
//							col=col+1;//increment horizontally	
//						} else {
//							col=col-1;
//						}
//						
//					}
				//if hit previous round x axis and hit previous previous round x axis and next digit is < than 10 and > 1
				//increment col variable

				
				//if hit previous round y axis and hit previous previous round y axis and next digit is < than 10 and > 1
				//increment row variable
				
				
		}
		//if first move	
		if (this.numMoves == 0) {
			row = 5;	
			col = 5;
		} 		

		while(!game.makeMove(hisShips, myMoves, row, col)) {

			
			

		}
		//store current move
		tempTrack = Integer.valueOf(String.valueOf(row).concat(String.valueOf(col)))-10;

		//if row and col is equal to 10, tempTrack == 100
		if (row == 10 && col == 10) {
			tempTrack = 100;
		}

		moveList.put(String.valueOf(this.numMoves), tempTrack);
		//increment move counter
		numMoves++;
		
		//log if move was a hit
		if(game.getMoveBoardValue(BSGame.BOARD_P1MOVES, row, col) == BSGame.PEG_HIT) {
			hitTracking.add(true);
		} else {
			hitTracking.add(false);
			row2=row;
			col2=col;
		}
		
		
	}

	public boolean addShips() {
		int carrierY = ThreadLocalRandom.current().nextInt(9, 10);
		int subY = ThreadLocalRandom.current().nextInt(6, 8);
		int cruiserY = ThreadLocalRandom.current().nextInt(7, 8);
		int btX = ThreadLocalRandom.current().nextInt(4, 6);
		
		game.putShip(myShips, Ships.SHIP_CARRIER, 10, carrierY, Ships.SHIP_NORTH);//5 slots
		game.putShip(myShips, Ships.SHIP_BATTLESHIP, btX, 3, Ships.SHIP_SOUTH);//4 slots
		game.putShip(myShips, Ships.SHIP_CRUISER, 7, cruiserY, Ships.SHIP_SOUTH);//3 slots
		game.putShip(myShips, Ships.SHIP_DESTROYER, 10, 1, Ships.SHIP_NORTH);//2 slots
		game.putShip(myShips, Ships.SHIP_SUBMARINE, 4, subY, Ships.SHIP_EAST);//3 slots

		return true;
	}
	
	public void sankCarrier() {
		addDbgText("You Sank my Carrier(p" + myPlayerNum + ")");

	}

	public void sankBattleShip() {
		addDbgText("You Sank my Battleship(p" + myPlayerNum + ")");

	}

	public void sankCruiser() {
		addDbgText("You Sank my Cruiser(p" + myPlayerNum + ")");

	}

	public void sankDestroyer() {
		addDbgText("You Sank my Destroyer(p" + myPlayerNum + ")");

	}

	public void sankSubmarine() {
		addDbgText("You Sank my Submarine(p" + myPlayerNum + ")");
	}

}