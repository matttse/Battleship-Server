// This player is as rudimentary as it gets.  It simply puts the ships in a static 
// location, and makes random moves until one sticks.  Your player can use this 
// as a base to expand upon. It is a good idea to play against this player until yours
// gets good enough to beat it regularly.

public class Player2 extends Player {

	// You must call the super to establish the necessary game variables
	// and register the game.
	public Player2(int playerNum) {
		super(playerNum);
		setName("Simple 2");
	}

	@Override
	public void makeMove() {
		int row = randomRow();
		int col = randomCol();
		
		// Try making a move until successful
		while(!game.makeMove(hisShips, myMoves, row, col)) {
			row = randomRow();
			col = randomCol();
		}

		numMoves++;
		addDbgText("Player " + myPlayerNum + " (" + row + ", " + col + ") num Moves = " + numMoves);
	}

	public boolean addShips() {
		
		game.putShip(myShips, Ships.SHIP_CARRIER, 2, 2, Ships.SHIP_SOUTH);
		game.putShip(myShips, Ships.SHIP_BATTLESHIP, 5, 5, Ships.SHIP_EAST);
		game.putShip(myShips, Ships.SHIP_CRUISER, 6, 7, Ships.SHIP_EAST);
		game.putShip(myShips, Ships.SHIP_DESTROYER, 8, 3, Ships.SHIP_EAST);
		game.putShip(myShips, Ships.SHIP_SUBMARINE, 9, 9, Ships.SHIP_NORTH);

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