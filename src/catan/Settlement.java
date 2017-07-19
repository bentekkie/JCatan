package catan;

public class Settlement extends Occupant {

	public Settlement(int playerID, Node location) {
		super(playerID, GamePieceType.Settlement, location);
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
