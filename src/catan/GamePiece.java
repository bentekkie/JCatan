package catan;

import java.io.Serializable;

import catan.Board.Player;

public class GamePiece implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8813683152982709100L;
	private Player player;
	private GamePieceType type;
	public GamePiece(Player player, GamePieceType type) {
		this.player = player;
		this.type = type;
	}
	public Player getPlayer(){
		return player;
	}
	public GamePieceType getType(){
		return type;
	}

}
