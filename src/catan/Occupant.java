package catan;

import catan.Board.Node;
import catan.Board.Player;

public abstract class Occupant extends GamePiece {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5408471002967363514L;
	Node location;
	public Occupant(Player player, GamePieceType type, Node location){
		super(player,type);
		this.location = location;
	}
	public abstract ResourcePacket generateResources();
}
