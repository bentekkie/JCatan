package catan;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Tile[][] tiles;
	Node[][] nodes;
	Tile ocean;
	private int totalTiles;
	private int sideLength;
	public int getSideLength() {
		return sideLength;
	}

	public Board(int sideLength) {
		this.totalTiles = 3*sideLength*(sideLength-1)+1;
		this.sideLength = sideLength;
		initTiles();
		initNodes();
		initEdges();
		initListeners();
	}
	
	private void initTiles(){
		List<Tile> tmp = new ArrayList<Tile>();
		ocean = new Tile(Terrain.Ocean);
		tmp.add(new Tile(Terrain.Desert));
		int ratio1 = 4*(totalTiles-1)/18;
		int ratio2 = 3*(totalTiles-1)/18;
		for(int x = 0; x < ratio1; x++){
			tmp.add(new Tile(Terrain.Field));
		}
		for(int x = 0; x < ratio1; x++){
			tmp.add(new Tile(Terrain.Forest));
		}
		for(int x = 0; x < ratio1; x++){
			tmp.add(new Tile(Terrain.Pasture));
		}
		for(int x = 0; x < ratio2; x++){
			tmp.add(new Tile(Terrain.Mountain));
		}
		for(int x = 0; x < ratio2; x++){
			tmp.add(new Tile(Terrain.Hills));
		}
		Collections.shuffle(tmp);
		int d = 2*sideLength+1;
		tiles = new Tile[d][d];
		int offset = sideLength-1;
		for(int i = 0; i < d; i++){
			tiles[0][i] = ocean;
			tiles[d-1][i]= ocean;
			tiles[i][0] = ocean;
			tiles[i][d-1] = ocean;
		}
		for(int x = 1; x < d-1; x++){
			if(offset > 0){
				for(int y = 1; y < offset+1; y++) tiles[x][y] = ocean;
				for(int y = offset+1; y < d-1; y++) tiles[x][y] = tmp.remove(0);
			}else{
				for(int y = 1; y < -offset+1; y++) tiles[x][d-1-y] = ocean;
				for(int y = -offset+1; y < d-1; y++) tiles[x][d-1-y] = tmp.remove(0);
			}
			offset--;
		}
	}
	
	private void initNodes(){
		int d = 2*sideLength+1;
		nodes = new Node[d*2][d];
		for(int y = 1; y < d; y++){
			if(tiles[0][y].getType() != Terrain.Ocean || tiles[1][y-1].getType() != Terrain.Ocean || tiles[1][y].getType() != Terrain.Ocean ){
				nodes[1][y] = new Node(0,y,1,tiles[0][y],tiles[1][y-1],tiles[1][y]);
			}
		}
		for(int y = 0; y < d-1; y++){
			if(tiles[d-2][y].getType() != Terrain.Ocean || tiles[d-2][y+1].getType() != Terrain.Ocean || tiles[d-2][y].getType() != Terrain.Ocean ){
				nodes[2*(d-1)][y] = new Node(d-1,y,0,tiles[d-2][y],tiles[d-2][y+1],tiles[d-2][y]);
			}
		}
		for(int x = 1; x < d-1; x++){
			for(int y = 0; y < d-1; y++){
				if(tiles[x-1][y].getType() != Terrain.Ocean || tiles[x-1][y+1].getType() != Terrain.Ocean || tiles[x][y].getType() != Terrain.Ocean){
					nodes[2*x][y] = new Node(x,y,0,tiles[x-1][y],tiles[x-1][y+1],tiles[x][y]);
				}
			}
			for(int y = 1; y < d; y++){
				if(tiles[x][y].getType() != Terrain.Ocean || tiles[x+1][y-1].getType() != Terrain.Ocean || tiles[x+1][y].getType() != Terrain.Ocean){
					nodes[2*x+1][y] = new Node(x,y,1,tiles[x][y],tiles[x+1][y-1],tiles[x+1][y]);
				}
			}
		}
	}
	
	private void initEdges(){
		int d = 2*sideLength+1;
		for(int x = 1; x < d; x++){
			for(int y = 0; y < d-1; y++){
				if(nodes[2*x][y] != null){
					if(2*x-3 > 0 && nodes[2*x-3][y+1] != null){
						new Edge(nodes[2*x][y],nodes[2*x-3][y+1]);
					}
					if(nodes[2*x-1][y] != null){
						new Edge(nodes[2*x][y],nodes[2*x-1][y]);
					}
					if(nodes[2*x-1][y+1] != null){
						new Edge(nodes[2*x][y],nodes[2*x-1][y+1]);
					}
				};
			}
		}
	}
	
	private void initListeners(){
		for(Node n:allNodes.values()){
			n.addActionListener(new SerializableActionListener() {
				private static final long serialVersionUID = 6293653573069804422L;

				@Override
				public void actionPerformed(ActionEvent e) {
					if(n.getOccupant() != null && n.getOccupant().getPlayer().getId() == Main.playerID){
						n.setOccupant(null);
					}else{
						n.setOccupant(new City(getPlayer(Main.playerID), n));
					}
				}
			});
		}
		for(Edge n:allEdges.values()){
			n.addActionListener(new SerializableActionListener() {
				private static final long serialVersionUID = -878740968296562926L;

				@Override
				public void actionPerformed(ActionEvent e) {
					if(n.getRoad() != null && n.getRoad().getPlayer().getId() == Main.playerID){
						n.setRoad(null);
					}else{
						n.setRoad(new GamePiece(getPlayer(Main.playerID),GamePieceType.Road));
					}
				}
			});
		}
	}
	private Map<Integer,Node> allNodes = new HashMap<Integer,Node>();
	private int nodeMaxID = 0;
	public Node getNode(int id){
		return allNodes.get(id);
	}
	public class Node extends ScreenElement{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1377377934634091655L;
		public int id;
		private Occupant occ;
		private ArrayList<Edge> edges;
		private Tile[] tiles;
		public int x;
		public int y;
		public Point2D.Double realCoord;
		public double realX;
		public double realY;
		public int orrientation;
		
		public Node(int x, int y, int orrientation, Tile... tiles) {
			this.id = nodeMaxID++;
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
			return Integer.toString(id) +":"+ Integer.toString(x) + "," + Integer.toString(y) + "," + ((orrientation == 0)?"top":"bot");
		}

		@Override
		public int priority() {
			// TODO Auto-generated method stub
			return 3;
		}

	}
	
	private Map<Integer,Edge> allEdges = new HashMap<Integer,Edge>();
	private int edgeMaxID = 0;
	public class Edge extends ScreenElement{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4942208033759747758L;
		public int id;
		private GamePiece road;
		private Node[] nodes;
		public Edge(Node... nodes) {
			this.id = edgeMaxID++;
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
		public int priority() {
			// TODO Auto-generated method stub
			return 2;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return Integer.toString(id) +":"+ nodes[0] + "<-->" + nodes[1];
		}
	}

	public Tile getTile(int id){
		return allTiles.get(id);
	}
	

	private Map<Integer,Tile> allTiles = new HashMap<Integer,Tile>();
	private int tileMaxID = 0;
	public class Tile extends ScreenElement{
		/**
		 * 
		 */
		private static final long serialVersionUID = -7131370096924389993L;
		public int id;
		private Terrain type;

		public Tile(Terrain type) {
			this.id = tileMaxID++;
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
		public int priority() {
			// TODO Auto-generated method stub
			return 1;
		}

	}
	private Map<Integer,Player> allPlayers = new HashMap<Integer,Player>();
	public void createPlayer(int id,Color c){
		allPlayers.put(id, new Player(id,c));
	}
	
	public Player getPlayer(int id){
		return allPlayers.get(id);
	}
	public class Player implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 130542026828487035L;
		private Color c;
		private int id;
		private Player(int id, Color c) {
			this.c = c;
			this.id = id;
		}
		public Color getColor() {
			return c;
		}
		public int getId() {
			return id;
		}

	}


}
