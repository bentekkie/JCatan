package catan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node {
	private static int ID = 0;
	private static Map<Integer,Node> allNodes = new HashMap<Integer,Node>();
	public int id;
	private Occupant occ;
	private ArrayList<Edge> edges;
	private Tile[] tiles;
	public int x;
	public int y;
	public double realX;
	public double realY;
	public int orrientation;
	
	public static Node getNode(int id){
		return allNodes.get(id);
	}
	
	public Node(int x, int y, int orrientation, Tile... tiles) {
		this.id = ID++;
		allNodes.put(this.id, this);
		this.edges = new ArrayList<Edge>();
		this.tiles = tiles;
		this.x = x;
		this.y = y;
		this.orrientation = orrientation;
	}
	public Occupant getOccupant() {
		return occ;
	}
	public void setOccupant(Occupant occ) {
		this.occ = occ;
	}
	public void addEdge(Edge edge){
		this.edges.add(edge);
	}
	public Edge[] getEdges() {
		Edge[] tmp = new Edge[edges.size()];
		edges.toArray(tmp);
		return tmp;
	}
	public Tile[] getTiles() {
		return tiles;
	}
	@Override
	public String toString() {
		return Integer.toString(x) + "," + Integer.toString(y) + "," + Integer.toString(orrientation);
	}

}
