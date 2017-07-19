package catan;

public class ResourcePacket {
	private int grain, lumber, wool, ore, brick;

	public ResourcePacket(int grain, int lumber, int wool, int ore, int brick) {
		this.grain = grain;
		this.lumber = lumber;
		this.wool = wool;
		this.ore = ore;
		this.brick = brick;
	}
	public ResourcePacket() {
		this.grain = 0;
		this.lumber = 0;
		this.wool = 0;
		this.ore = 0;
		this.brick = 0;
	}
	
	public ResourcePacket add(ResourcePacket rp){
		return new ResourcePacket(grain + rp.grain, lumber + rp.lumber, wool + rp.wool, ore + rp.ore, brick + rp.brick);
	}
	public ResourcePacket mult(int n){
		return new ResourcePacket(grain*n, lumber*n, wool*n, ore*n, brick*n);
	}
	
	public int getGrain() {
		return grain;
	}
	public int getLumber() {
		return lumber;
	}
	public int getWool() {
		return wool;
	}
	public int getOre() {
		return ore;
	}
	public int getBrick() {
		return brick;
	}

}
