package catan;

import java.awt.Color;

public enum Terrain {
	Field(1,0,0,0,0, Color.YELLOW),
	Forest(0,1,0,0,0, Color.GREEN),
	Pasture(0,0,1,0,0, Color.CYAN),
	Mountain(0,0,0,1,0, Color.LIGHT_GRAY),
	Hills(0,0,0,0,1, Color.DARK_GRAY),
	Desert(0,0,0,0,0, Color.decode("#ECD540")),
	Ocean(0,0,0,0,0, new Color(0,0,0,0));
	
	private final ResourcePacket resource;
	private final Color c;
	
	public Color getColor() {
		return c;
	}

	Terrain(int grain, int lumber, int wool, int ore, int brick, Color c){
		this.resource = new ResourcePacket(grain, lumber, wool, ore, brick);
		this.c = c;
	}
	
	public ResourcePacket getResourcePacket(){
		return resource;
	}
	
	
}
