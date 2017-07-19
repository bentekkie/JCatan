package catan;

public abstract class Occupant extends GamePiece {
	Node location;
	public Occupant(int playerID, GamePieceType type, Node location){
		super(playerID,type);
		this.location = location;
	}
	public abstract ResourcePacket generateResources();
}
