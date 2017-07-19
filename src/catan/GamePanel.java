package catan;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.PriorityQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private double hexWidth;
	private double r;
	private static final long serialVersionUID = 5722903008506660750L;
	private Board b;
	private JFrame f;
	private double margin;
	private PriorityQueue<ScreenElement> elements;
	public GamePanel(Board b, JFrame f) {
		this.b = b;
		this.f = f;
		this.setOpaque(true);
		elements = new PriorityQueue<ScreenElement>(10,(e1,e2) ->  e2.priority() - e1.priority());
		setBackground(Color.BLUE);
		addMouseListener(this);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int dim = getWidth();
		hexWidth = (dim-margin)/((b.tiles.length-2));
		r = hexWidth / Math.sqrt(3);
		margin = r;
		long desiredHeight = Math.round((2*b.getSideLength()+b.getSideLength()-1)*r+margin);
		if(getHeight() != desiredHeight){
			f.setSize(f.getWidth(), (int) desiredHeight+f.getHeight()-this.getHeight());
			setSize(getWidth(), (int) desiredHeight);
		}
		Graphics2D g2 = (Graphics2D) g;
		drawTiles(g2);
		drawNodes(g2);
		drawEdges(g2);
		drawNodes(g2);
	}
	
	private void drawTiles(Graphics2D g2){
		int offset = 0;
		for(int x = 0; x < b.tiles.length; x++){
			for(int y = 0; y < b.tiles[x].length; y++){
				if(b.tiles[x][y].getType() != null) {
					double xVal = y*hexWidth+ offset- (b.getSideLength()+1)*hexWidth/2 +margin/2;
					double yVal = x*r*3/2+r - r*3/2 + margin/2;
					b.tiles[x][y].setArea(drawHex(g2,xVal,yVal,b.tiles[x][y].getType().getColor()));
					g2.setColor(Color.BLACK);
					g2.drawString(Integer.toString(b.tiles[x][y].id), (int)Math.round(xVal),(int)Math.round(yVal));
					
				}
			}
			offset += hexWidth/2;
		}
	}
	
	private void drawNodes(Graphics2D g2){
		int offset = 0;
		g2.setColor(Color.BLACK);
		double nodeDiameter = (r/7);
		for(int i = 0; i < b.nodes.length; i++){
			for(int j = 0; j < b.nodes[i].length; j++){
				if(b.nodes[i][j] != null){
					double yVal = b.nodes[i][j].x*r*3/2+r - r*3/2 + ((b.nodes[i][j].orrientation == 0)?-r:r)+margin/2;
					double xVal = b.nodes[i][j].y*hexWidth+ offset- (b.getSideLength()+1)*hexWidth/2+margin/2;
					Ellipse2D p = new Ellipse2D.Double(xVal - nodeDiameter/2, yVal - nodeDiameter/2, nodeDiameter, nodeDiameter);
					g2.fill(p);
					b.nodes[i][j].setArea(p);
					b.nodes[i][j].realCoord = new Point2D.Double(xVal,yVal);
					b.nodes[i][j].realY = yVal;
					if(!elements.contains(b.nodes[i][j])) {
						elements.add(b.nodes[i][j]);
					}	
				}
			}
			if(i % 2 == 1)offset += hexWidth/2;
		}
			
	}
	
	private void drawEdges(Graphics2D g2){
		int d = b.nodes.length/2;
		Stroke s = new BasicStroke((int)(r/14));
		g2.setStroke(s);
		g2.setColor(Color.BLACK);
		for(int x = 1; x < d; x++){
			for(int y = 0; y < d-1; y++){
				if(b.nodes[2*x][y] != null){
					for(Edge e:b.nodes[2*x][y].getEdges()){
						Node other = e.traverse(b.nodes[2*x][y]);
						Line2D p = new Line2D.Double(b.nodes[2*x][y].realCoord, other.realCoord);
						g2.draw(p);
						e.setArea(s.createStrokedShape(p));
						if(!elements.contains(e)) {
							elements.add(e);
						}
					}
				};
			}
		}
	}
	
	private Polygon drawHex(Graphics2D g2, double x, double y, Color c){
		Polygon p = new Polygon();
		for(int i=0; i<7; i++) {
			double a = Math.PI / 3.0 * i;
			p.addPoint((int)(Math.round(x + Math.sin(a) * r)), (int)(Math.round(y + Math.cos(a) * r)));
		}
		g2.setColor(c);
		g2.fillPolygon(p);
		return p;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		elements.stream()
				.filter(e1 -> e1.getArea().contains(e.getX(), e.getY()))
				.findFirst()
				.ifPresent(e1 -> e1.click(0));

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
