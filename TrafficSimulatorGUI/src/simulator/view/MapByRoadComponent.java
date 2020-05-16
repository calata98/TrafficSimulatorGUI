package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

	private Controller _ctrl;
	private RoadMap map;
	private Image car;
	private static final Color _BG_COLOR = Color.WHITE;
	private final Color BLUE_COLOR = Color.BLUE;
	private final Color BLACK_COLOR = Color.BLACK;
	private final String CAR_IMAGE_URL = "car.png";
	private final String SUN_IMAGE_URL = "sun.png";
	private final String STORM_IMAGE_URL = "storm.png";
	private final String WIND_IMAGE_URL = "wind.png";
	private final String RAIN_IMAGE_URL = "rain.png";
	private final String CLOUD_IMAGE_URL = "cloud.png";
	private final int cRadius = 14;
	private final int carSize = 20;
	private final int estandarSize = 32;
	
	public MapByRoadComponent(Controller _ctrl) {
		this._ctrl = _ctrl;
		_ctrl.addObserver(this);
		this.setPreferredSize(new Dimension (300, 200));
		car = loadImage(CAR_IMAGE_URL);
	}
	
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		
		Graphics2D g = (Graphics2D) graphics;
		g.setBackground(_BG_COLOR);
		
		if (map == null || map.getRoads().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			drawMapByRoad(g);
		}
		
		
	}
	
	private void drawMapByRoad(Graphics g) {
		int x1 = 50;
		int x2 = getWidth()-100;
		int y;
		
		for(int i = 0; i < map.getRoads().size(); i++) {
			y = (i+1)*50;
			
			Road currRoad = map.getRoads().get(i);
			
			//Lineas
			g.setColor(BLACK_COLOR);
			g.drawLine(x1, y, x2, y);
			
			//Correspondientes id
			g.setColor(BLACK_COLOR);
			g.drawString(currRoad.getId(), x1 - 30, y);
			
			//Circulos de srcJunctions
			g.setColor(BLUE_COLOR);
			g.fillOval(x1 - cRadius / 2, y - cRadius / 2, cRadius, cRadius);
			
			//Correspondientes id
			g.setColor(Color.MAGENTA);
			g.drawString(currRoad.getSrc().getId(), x1, y - cRadius);
			
			//Circulos de destJunctions
			
			//Se compara la carretera actual con la que tiene el semaforo en verde en la lista de carreteras entrantes del cruce destino de la misma
			if(currRoad == map.getJunction(currRoad.getDest().getId()).getInRoads().get(map.getJunction(currRoad.getDest().getId()).getGreenLightIndex())) {
				g.setColor(Color.GREEN);
			}else {
				g.setColor(Color.RED);
			}
			
			g.fillOval(x2 - cRadius / 2, y - cRadius / 2, cRadius, cRadius);
			
			
			//Correspondientes id
			g.setColor(Color.MAGENTA);
			g.drawString(currRoad.getDest().getId(), x2, y - cRadius);
			
			//Imagenes de coches
			for(Vehicle v : currRoad.getVehicles()) {
				int x = x1 + ( int ) ((x2 - x1) * (( double ) v.getLocation() / ( double ) currRoad.getLength()));
				g.drawImage(car, x, y - carSize / 2, carSize, carSize, this);
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));
				g.drawString(v.getId(), x, y - carSize / 2);
			}
			
			switch(currRoad.getWeather()) {
			
			case SUNNY:
				g.drawImage(loadImage(SUN_IMAGE_URL), x2 + estandarSize / 2, y - estandarSize, estandarSize, estandarSize, this);
				break;
			case CLOUDY:
				g.drawImage(loadImage(CLOUD_IMAGE_URL), x2 + estandarSize / 2, y - estandarSize, estandarSize, estandarSize, this);
				break;
			case RAINY:
				g.drawImage(loadImage(RAIN_IMAGE_URL), x2 + estandarSize / 2, y - estandarSize, estandarSize, estandarSize, this);
				break;
			case STORM:
				g.drawImage(loadImage(STORM_IMAGE_URL), x2 + estandarSize / 2, y - estandarSize, estandarSize, estandarSize, this);
				break;
			case WINDY:
				g.drawImage(loadImage(WIND_IMAGE_URL), x2 + estandarSize / 2, y - estandarSize, estandarSize, estandarSize, this);
				break;
			}
			
			
			int C = ( int ) Math.floor(Math.min(( double ) currRoad.getTotalCO2() /(1.0 + ( double ) currRoad.getCO2Limit()),1.0) / 0.19);
			
			g.drawImage(loadImage("cont_" + C + ".png"), x2 + estandarSize * 2, y - estandarSize, estandarSize, estandarSize, this);
			
		}
		
		
	}
	
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	public void update(RoadMap map) {
		this.map = map;
		repaint();
	}
	
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}
