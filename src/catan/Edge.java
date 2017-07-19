package catan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Edge extends ScreenElement{
	private static int ID = 0;
	private static Map<Integer,Edge> allEdges = new HashMap<Integer,Edge>();
	public int id;
	private GamePiece road;
	private Node[] nodes;
	public Edge(Node... nodes) {
		this.id = ID++;
		allEdges.put(id, this);
		for(Node n: nodes){
			n.addEdge(this);
		}
		this.nodes = nodes;
	}
	public GamePiece getRoad() {
		return road;
	}
	public void setRoad(GamePiece road) {
		this.road = road;
	}
	public Node traverse(Node start){
		return (nodes[0] == start)? nodes[1]:nodes[0];
	}
	
	public Tile[] getTiles(){
		ArrayList<Tile> tmp = new ArrayList<Tile>();
		for(Tile t: nodes[0].getTiles()){
			for(Tile t1: nodes[1].getTiles()){
				if(t == t1) tmp.add(t1);
			}
		}
		Tile [] tmpArr = new Tile[tmp.size()];
		tmp.toArray(tmpArr);
		return tmpArr;
	}
	@Override
	public void click(int playerID) {
		// TODO Auto-generated method stub
		Tile[] tilesArr = getTiles();
		String[] tilesStrArr = new String[tilesArr.length];
		for(int i = 0; i < tilesArr.length; i++){
			tilesStrArr[i] = tilesArr[i] + "";
		}
		System.out.println(nodes[0] +"<-->" +nodes[1] +" edge clicked. Adjacent tiles are: " + String.join(",",tilesStrArr));
	}
	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 2;
	}
}
