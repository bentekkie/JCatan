package catan;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import catan.Board.*;
import javax.swing.JFrame;

public class Main {

	public static int dim = 1000;
	public static int boardMaxID = 0;
	private static Map<Integer,JFrame> allFrames = new HashMap<Integer,JFrame>();
	private static Map<Integer,Board> allBoards = new HashMap<Integer,Board>();
	public static int playerID= 0;
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		String cmd = s.nextLine();
		while(!cmd.equals("quit")){
			String[] cmdSplit = cmd.split(" ");
			switch(cmdSplit[0]){
				case "new":
					newBoard(Integer.parseInt(cmdSplit[1]));
					break;
				case "save":
					saveBoard(Integer.parseInt(cmdSplit[1]),cmdSplit[2]);
					break;
				case "load":
					loadBoard(cmdSplit[1]);
					break;
			}
			cmd = s.nextLine();	
		}
		s.close();
	}
	
	public static void newBoard(int sideLength){
		Board b = new Board(sideLength);
		b.createPlayer(playerID, Color.orange);
		int id = boardMaxID++;
		JFrame f = new JFrame("Board " + id);
		GamePanel gp = new GamePanel(b,f);
		f.setSize(dim, dim);
		f.add(gp);
		f.setBackground(Color.BLUE);
		gp.setVisible(true);
		gp.setSize(dim,dim);
		f.setVisible(true);
		allBoards.put(id, b);
		allFrames.put(id, f);
	}
	
	public static void saveBoard(int id, String fileName){
		FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(allBoards.get(id));
            out.close();
            JFrame f = allFrames.get(id);
            f.setVisible(false);
            f.dispose();
            allBoards.remove(id);
            allFrames.remove(id);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
	}
	
	public static void loadBoard(String fileName){
		FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            Board b = (Board) in.readObject();
            in.close();
            int id = boardMaxID++;
    		JFrame f = new JFrame("Board " + id);
    		GamePanel gp = new GamePanel(b,f);
    		f.setSize(dim, dim);
    		f.add(gp);
    		f.setBackground(Color.BLUE);
    		gp.setVisible(true);
    		gp.setSize(dim,dim);
    		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		f.setVisible(true);
    		allFrames.put(id, f);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
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
