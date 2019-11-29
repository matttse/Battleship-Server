// This player is as rudimentary as it gets.  It simply puts the ships in a static 
// location, and makes random moves until one sticks.  Your player can use this 
// as a base to expand upon. It is a good idea to play against this player until yours
// gets good enough to beat it regularly.
import java.util.concurrent.ThreadLocalRandom;
public class Player1 extends Player {
	boolean hitThisRound = false;
	boolean hitPvsRound = false;
	int row1,col1,row2,col2;
	// You must call the super to establish the necessary game variables
	// and register the game.
	public Player1(int playerNum) {
		super(playerNum);
		setName("Matt Tse");

	}

	@Override
	public void makeMove() {
		int row = randomRow();//(A-F)
		int col = randomCol();//(1-10)
		//if hit 2nd previous round but not the round before this one, track other way
		if (hitPvsRound == true && hitThisRound == false) {	
			row = row2-1;		
			col = col2; 
		}
		//if hit previous round, track downwards
		if (hitThisRound == true) {	
			row = row1+1;	
			col = col1;	
		}
		//testing first move
		if (this.numMoves == 0) {row = 9;	col = 9;}
		// Try making a move until successful
		while(!game.makeMove(hisShips, myMoves, row, col)) {			
			
			
		}
		//increment number of moves
		numMoves++;
		addDbgText("row = " + row + " col = " + col);
		addDbgText("Player " + myPlayerNum + " (" + row + ", " + col + ") num Moves = " + numMoves);
		//check previous round and store it
		if (hitThisRound == true) {	
			hitPvsRound = true;	
		}
		//check current round and store it
		if (game.getMoveBoardValue(BSGame.BOARD_P1MOVES, row, col) == BSGame.PEG_HIT) {
			addDbgText("HIT!!!");
			hitThisRound = true;
		} else {
			hitThisRound = false;
		}
		//update tracker for previous round
		if (hitThisRound == true) {	
			row1=row;
			col1=col; 
		} 
		//if hit previous round but not this round, update previous tracker 2
		if (hitPvsRound == true && hitThisRound == false) {	row2 = row1;	col2 = col1; }
		
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