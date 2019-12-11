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

	
	public void makeMove() {
		int row = randomRow();//(A-F) y axis
		int col = randomCol();//(1-10) x axis
		//default random
		row = randomRow();
		col = randomCol();
		//not starting move
		int previousMove = this.numMoves-1;
		int secondPreviousMove = previousMove-1;
		int thirdPreviousMove = secondPreviousMove-1;
		int fourthPreviousMove = thirdPreviousMove-1;
		int fifthPreviousMove = fourthPreviousMove-1;
		int checkRow;
		int checkCol;

		if (this.numMoves > 0) {
			//restore previous round values
			tempTrack = this.moveList.get(String.valueOf(numMoves-1))+10;
			@SuppressWarnings("unused")
			boolean trackPreviousMove = hitTracking.get(previousMove);

			if (tempTrack == 110) {
				row = 10;
				col = 10;
			} else {
				row1 = Integer.valueOf(String.valueOf(tempTrack).substring(0, 1));
				col1 = Integer.valueOf(String.valueOf(tempTrack).substring(1, 2));
			}
			
			if (this.numMoves > 1) {
				@SuppressWarnings("unused")
				boolean trackSecondPreviousMove = hitTracking.get(secondPreviousMove);
				//if true true
				//read 2 previous coordinates
				//check which value (row or col) is the same
				//increment one that is not the same
				if (hitTracking.get(previousMove) == true && hitTracking.get(secondPreviousMove) == true) {
					//if previous row hit and previous col hit, track right

					col = col1+1;
					row = row1;
					
					
				}
				if (hitTracking.get(previousMove) == false && hitTracking.get(secondPreviousMove) == true) {
					
					int shipSize = 0;
					
					for (int i = hitTracking.size()-1; i >= 0; i--) {
						
						if (i>0&&hitTracking.get(i-1) == true) {
							shipSize+=1;
						} else if (i == 0 || hitTracking.get(i-1) == false) {
							break;
						}
					}
					
					col=col2-(shipSize+1);
					row=row2;
					col2=col;
				

				}
				if (hitTracking.get(previousMove) == true && hitTracking.get(secondPreviousMove) == false) {
					
					col=col1+1;
					row=row1;						
					
				}
				if (this.numMoves > 2 ) {
					@SuppressWarnings("unused")
					boolean trackThirdPreviousMove = hitTracking.get(thirdPreviousMove);
					//if hit previous round false and hit previous previous round false and hit previous previous previous round true
					//must try vertical down
					//increment row variable
					if (hitTracking.get(previousMove) == false && hitTracking.get(secondPreviousMove) == false && hitTracking.get(thirdPreviousMove) == true) {
						col=col2+1;
						row=row2+1;
					}
					
					if (this.numMoves > 3) {
						@SuppressWarnings("unused")
						boolean trackFourthPreviousMove = hitTracking.get(fourthPreviousMove);
						//if hit previous round false and hit previous previous round false and
						//hit previous previous previous round false but hit 4th previous round
						//must try vertical up
						//increment row variable
						if (hitTracking.get(previousMove) == false && hitTracking.get(secondPreviousMove) == false
								&& hitTracking.get(thirdPreviousMove) == false && hitTracking.get(fourthPreviousMove) == true) {
							int shipSize = 0;
							
							for (int i = hitTracking.size()-3; i >= 0; i--) {
								
								if (i>0&&hitTracking.get(i-1) == true) {
									shipSize+=1;
								} else if (i == 0 || hitTracking.get(i-1) == false) {
									break;
								}
							}
							
							col=col2;
							row=row2-(shipSize+2);
						}
						if (hitTracking.get(previousMove) == false && hitTracking.get(secondPreviousMove) == false
								&& hitTracking.get(thirdPreviousMove) == true && hitTracking.get(fourthPreviousMove) == false) {
							int shipSize = 0;
							
							for (int i = hitTracking.size()-4; i >= 0; i--) {
								
								if (i>0 && mod(i, 3) && hitTracking.get(i-1) == true) {
									shipSize+=1;
								} else if (i == 0 || hitTracking.get(i-1) == false) {
									break;
								}
							}
							
							col=col1+1;
							row=row1-(shipSize+1);
							
						}


					}

				}
				
				
				//loop through move list to ensure current move is valid, if not reroll random				
				for (int i = 0; i < moveList.size(); i++) {
					checkRow = Integer.valueOf(String.valueOf(this.moveList.get(String.valueOf(i))+10).substring(0, 1));
					checkCol = Integer.valueOf(String.valueOf(this.moveList.get(String.valueOf(i))+10).substring(1, 2));
					if ((row == checkRow &&	col == checkCol)||(col>10)||(col<1)||(row>10)||(row<1)) {
						row = randomCol();
						col = randomRow();
						i = 0;
					}
					
				}	
				
			} else //if hit previous
				//increment col variable
			if (hitTracking.get(previousMove) == true) {
				col = col1+1;
				row = row1;
			}		
				
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
	public static boolean mod(int a, int b){
	    if ( a < 0 ){
	        return false;
	    }else if (a==b){ 
	        return true;
	    }else{ 
	        return mod( a-b, b );
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