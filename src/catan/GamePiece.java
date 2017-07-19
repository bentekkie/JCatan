package catan;

public class GamePiece {
	private int playerID;
	private GamePieceType type;
	public GamePiece(int playerID, GamePieceType type) {
		this.playerID = playerID;
		this.type = type;
	}
	public int getPlayerID(){
		return playerID;
	}
	public GamePieceType getType(){
		return type;
	}

}
