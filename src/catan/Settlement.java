package catan;

import catan.Board.Node;
import catan.Board.Player;
import catan.Board.Tile;

public class Settlement extends Occupant {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7072394377387759219L;

	public Settlement(Player player, Node location) {
		super(player, GamePieceType.Settlement, location);
	}

	@Override
	public ResourcePacket generateResources() {
		ResourcePacket rp = new ResourcePacket();
		for(Tile t : location.getTiles()){
			rp = rp.add(t.getType().getResourcePacket());
		}
		return rp;
	}

}
