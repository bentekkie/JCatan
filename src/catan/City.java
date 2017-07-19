package catan;

public class City extends Occupant {

	public City(int playerID, Node location) {
		super(playerID, GamePieceType.City, location);
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
