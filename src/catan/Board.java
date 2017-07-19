package catan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
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
		this.sideLength = sideLength+1;
		initTiles();
		initNodes();
		initEdges();
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

}
