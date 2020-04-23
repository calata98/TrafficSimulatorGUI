package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	private RoadMap roadMap; 
	private List<Event> eventList;
	private int time;
	private List<TrafficSimObserver> observers;
	
	
	public TrafficSimulator() {
		eventList = new SortedArrayList<Event>();
		time = 0;
		roadMap = new RoadMap();
		observers = new ArrayList<>();
	}
	
	
	public void addEvent(Event e) {
		eventList.add(e);
		
		for(TrafficSimObserver obv : observers) {
			obv.onEventAdded(roadMap,eventList,e,time);
		}
		
	}
	
	public void advance() {
		
		time++;
		
		for(TrafficSimObserver obv : observers) {
			obv.onAdvanceStart(roadMap,eventList,time);
		}
		
		while(eventList.size() > 0 && eventList.get(0)._time == time) {  //Se comprueba que haya eventos por ejecutar y que se ejecuten en el tick correspondiente
				eventList.get(0).execute(roadMap);
				eventList.remove(0);
		}
		List<Junction> junctions = roadMap.getJunctions();
		for(int i = 0; i < junctions.size(); i++) {
			junctions.get(i).advance(time);
		}
		
		
		List<Road> roads = roadMap.getRoads();
		for(int i = 0; i < roads.size(); i++) {
			roads.get(i).advance(time);
		}

		for(TrafficSimObserver obv : observers) {
			obv.onAdvanceEnd(roadMap,eventList,time);
		}
		
	}
	
	public void reset() {
		roadMap.reset();
		eventList.clear();
		time = 0;
		for(TrafficSimObserver obv : observers) {
			obv.onReset(roadMap,eventList,time);
		}
	}
	
	public JSONObject report() {
		
		JSONObject aux = new JSONObject();
		aux.put("time", time);
		aux.put("state", roadMap.report());
		
		return aux;
	}


	@Override
	public void addObserver(TrafficSimObserver o) {
		observers.add(o);
		for(TrafficSimObserver obv : observers) {
			obv.onRegister(roadMap,eventList,time);
		}
	}


	@Override
	public void removeObserver(TrafficSimObserver o) {
		observers.remove(o);
	}
	
	
}
