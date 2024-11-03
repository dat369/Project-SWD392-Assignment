package pkproject.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import pkproject.controller.AGVController;
import pkproject.model.AGV;
import pkproject.model.MapObject;

import javax.swing.*;
import java.awt.*;




public class MapView extends JPanel{
	private int width;
	private int height;
	public List<MapObject> objects = new ArrayList<MapObject>();
	private int squareSize = 8;
	private List<AGV> agvList;
	AGVController agvController = new AGVController();

	public MapView(int width, int height) {
		this.width = width * squareSize;
		this.height = height * squareSize;
		setPreferredSize(new Dimension(this.width, this.height));
	}

	public void setAGVList(List<AGV> agvList) {
		this.agvList = agvList;
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    // Set background color
	    g.setColor(Color.LIGHT_GRAY);
	    for (int x = 0; x <= width; x += squareSize) {
	        for (int y = 0; y <= height; y += squareSize) {
	            g.fillRect(x, y, squareSize, squareSize);
	        }
	    }

	    // Draw paths for AGVs (both to and from the warehouse)
	    for (AGV agv : agvList) {
	        g.setColor(Color.RED); // Color for paths
	        drawPath(g, agv.getWarehouse().getPath()); // Outbound path
	        drawPath(g, agv.getWarehouse().getBackPath()); // Return path
	    }

	    // Draw all objects
	    for (MapObject obj : objects) {
	        int x = (int) (obj.getPosition().getPositionX() * squareSize);
	        int y = (int) (obj.getPosition().getPositionY() * squareSize);

	        Image image = obj.getImage();

	        if (image != null) {
	            g.drawImage(image, x, y, 40, 40, this);
	        } else {
	            g.setColor(Color.BLUE);
	            g.fillOval(x, y, 10, 10);
	        }

	        g.drawString(obj.getName(), x, y + 50);
	    }
	}

	// Helper method to draw a path
	private void drawPath(Graphics g, List<double[]> path) {
	    if (path != null && path.size() > 1) {
	        for (int i = 0; i < path.size() - 1; i++) {
	            int x1 = (int) (path.get(i)[0] * squareSize);
	            int y1 = (int) (path.get(i)[1] * squareSize);
	            int x2 = (int) (path.get(i + 1)[0] * squareSize);
	            int y2 = (int) (path.get(i + 1)[1] * squareSize);
	            g.drawLine(x1, y1, x2, y2);
	        }
	    }
	}



	// Helper function to find AGV by name
	private AGV findAGVByName(String name) {
		for (AGV agv : agvList) {
			if (name.equals("AGV " + agv.getId())) {
				return agv;
			}
		}
		return null;
	}

	public void updateObjects(List<MapObject> newObjects) {
		this.objects = newObjects; // Update the list of objects
		repaint(); // Redraw the panel
	}
}

