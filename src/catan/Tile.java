package catan;

import java.util.HashMap;
import java.util.Map;

public class Tile extends ScreenElement{
	private static int ID = 0;
	private static Map<Integer,Tile> allTiles = new HashMap<Integer,Tile>();
	public int id;
	private Terrain type;

	public static Tile getTile(int id){
		return allTiles.get(id);
	}
	
	public Tile(Terrain type) {
		this.id = ID++;
		allTiles.put(this.id, this);
		this.type = type;
	}
	public Terrain getType() {
		return type;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Integer.toString(id) +":"+ type.toString();
	}

	@Override
	public void click(int playerID) {
		// TODO Auto-generated method stub
		System.out.println("Tile clicked");
		
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 2;
	}

}
