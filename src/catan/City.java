package catan;

import catan.Board.Node;
import catan.Board.Player;
import catan.Board.Tile;

public class City extends Occupant {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6600597686563994529L;

	public City(Player player, Node location) {
		super(player, GamePieceType.City, location);
	}

	@Override
	public ResourcePacket generateResources() {
		ResourcePacket rp = new ResourcePacket();
		for(Tile t : location.getTiles()){
			rp = rp.add(t.getType().getResourcePacket());
		}
		return rp.mult(2);
	}

}
