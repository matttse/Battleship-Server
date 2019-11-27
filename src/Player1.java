// This player is as rudimentary as it gets.  It simply puts the ships in a static 
// location, and makes random moves until one sticks.  Your player can use this 
// as a base to expand upon. It is a good idea to play against this player until yours
// gets good enough to beat it regularly.
import java.util.concurrent.ThreadLocalRandom;
public class Player1 extends Player {

	// You must call the super to establish the necessary game variables
	// and register the game.
	public Player1(int playerNum) {
		super(playerNum);
		setName("Matt Tse");
	}

	@Override
	public void makeMove() {
		int row = randomRow();
		int col = randomCol();
		boolean hitInd = false;
		boolean hitIndFirst = false;
		boolean hitIndSecond = false;
		// Try making a move until successful
		while(!game.makeMove(hisShips, myMoves, row, col)) {
			/*
			//original code
			row = randomRow();
			col = randomCol();*/
			
			if (game.getMoveBoardValue(BSGame.BOARD_P1MOVES, row, col) == BSGame.PEG_HIT) {
				int cnt = 0;
				hitIndFirst = true;
				if (hitIndFirst = true) {
					col = col+1;
				} else {
					row = row+1;
				}
			} else {
				row = randomRow();
				col = randomCol();
			}
			

				
				

			
		}
		numMoves++;
		addDbgText("row = " + row + " col = " + col);
		addDbgText("Player " + myPlayerNum + " (" + row + ", " + col + ") num Moves = " + numMoves);
		if (game.getMoveBoardValue(BSGame.BOARD_P1MOVES, row, col) == BSGame.PEG_HIT) {
			addDbgText("HIT!!!");
			
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