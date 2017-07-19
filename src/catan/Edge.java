package catan;

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
	@Override
	public void click(int playerID) {
		// TODO Auto-generated method stub
		System.out.println(nodes[0] +"<-->" +nodes[1] +" edge clicked");
	}
	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 1;
	}
}
