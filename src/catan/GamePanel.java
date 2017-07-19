package catan;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private double hexWidth;
	private double r;
	private static final long serialVersionUID = 5722903008506660750L;
	Board b;
	JFrame f;
	public GamePanel(Board b, JFrame f) {
		this.b = b;
		this.f = f;
		this.setOpaque(true);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int dim = getWidth();
		hexWidth = dim/((b.tiles.length-2));
		r = hexWidth / Math.sqrt(3);
		System.out.println((2*b.getSideLength()+b.getSideLength()-1));
		long desiredHeight = Math.round((2*b.getSideLength()+b.getSideLength()-1)*r);
		if(getHeight() != desiredHeight){
			f.setSize(f.getWidth(), (int) desiredHeight+f.getHeight()-this.getHeight());
			setSize(getWidth(), (int) desiredHeight);
		}
		Graphics2D g2 = (Graphics2D) g;
		drawTiles(g2);
		drawNodes(g2);
		drawEdges(g2);
	}
	
	private void drawTiles(Graphics2D g2){
		int offset = 0;
		for(int x = 0; x < b.tiles.length; x++){
			for(int y = 0; y < b.tiles[x].length; y++){
				if(b.tiles[x][y].getType() != null)
				drawHex(g2,y*hexWidth+ offset- (b.getSideLength()+1)*hexWidth/2,x*r*3/2+r - r*3/2,b.tiles[x][y].getType().getColor());
				g2.setColor(Color.BLACK);
				g2.drawString(Integer.toString(b.tiles[x][y].id), (int)Math.round(y*hexWidth+ offset- (b.getSideLength()+1)*hexWidth/2),(int)Math.round(x*r*3/2+r - r*3/2));
			}
			offset += hexWidth/2;
		}
	}
	
	private void drawNodes(Graphics g2){
		int offset = 0;
		g2.setColor(Color.BLACK);
		for(int i = 0; i < b.nodes.length; i++){
			for(int j = 0; j < b.nodes[i].length; j++){
				if(b.nodes[i][j] != null){
					double yVal = b.nodes[i][j].x*r*3/2+r - r*3/2 + ((b.nodes[i][j].orrientation == 0)?-r:r);
					double xVal = b.nodes[i][j].y*hexWidth+ offset- (b.getSideLength()+1)*hexWidth/2;
					g2.fillOval((int)Math.round(xVal)-5, (int)Math.round(yVal)-5, 10, 10);
					b.nodes[i][j].realX = xVal;
					b.nodes[i][j].realY = yVal;
				}
			}
			if(i % 2 == 1)offset += hexWidth/2;
		}
			
	}
	
	private void drawEdges(Graphics2D g2){
		int d = b.nodes.length/2;
		g2.setStroke(new BasicStroke(5));
		g2.setColor(Color.darkGray);
		for(int x = 1; x < d; x++){
			for(int y = 0; y < d-1; y++){
				if(b.nodes[2*x][y] != null){
					for(Edge e:b.nodes[2*x][y].getEdges()){
						Node other = e.traverse(b.nodes[2*x][y]);
						g2.drawLine((int)b.nodes[2*x][y].realX, (int)b.nodes[2*x][y].realY, (int)other.realX, (int)other.realY);
					}
				};
			}
		}
	}
	
	private void drawHex(Graphics2D g2, double x, double y, Color c){
		Polygon p = new Polygon();
		for(int i=0; i<7; i++) {
			double a = Math.PI / 3.0 * i;
			p.addPoint((int)(Math.round(x + Math.sin(a) * r)), (int)(Math.round(y + Math.cos(a) * r)));
		}
		g2.setColor(c);
		g2.fillPolygon(p);
	}
}
