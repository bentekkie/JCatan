package catan;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args){
		Board b = new Board(3);
		debugPrint(b);
		JFrame f = new JFrame("Test");
		GamePanel gp = new GamePanel(b,f);
		f.setSize(500, 500);
		f.add(gp);
		gp.setVisible(true);
		gp.setSize(500,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	public static void debugPrint(Board b){
		String n = "";
		for(int i = 0; i < b.tiles.length; i++){
			System.out.print(n);
			for(int j = 0; j < b.tiles[i].length; j++){
				System.out.printf("%-14s",b.tiles[i][j]);
			}
			System.out.println();
			n += "       ";
		}
		for(int i = 0; i < b.nodes.length; i++){
			for(int j = 0; j < b.nodes[i].length; j++){
				if(b.nodes[i][j] != null){
					System.out.print(b.nodes[i][j] + ":  ");
					for(Tile t: b.nodes[i][j].getTiles()){
						System.out.print(t + " ");
					}
					System.out.print(" :  ");
					for(Edge e: b.nodes[i][j].getEdges()){
						System.out.print(e.traverse(b.nodes[i][j]) + ", ");
					}
					System.out.println();
				}
			}
		}
	}
}
