package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements TrafficSimObserver{

	private RoadMap roadMap; 
	private List<Event> eventList;
	private int time;
	private TrafficSimObserver o;
	
	
	public TrafficSimulator() {
		eventList = new SortedArrayList<Event>();
		time = 0;
		roadMap = new RoadMap();
	}
	
	
	public void addEvent(Event e) {
		eventList.add(e);
		onEventAdded(roadMap,eventList,e,time);
	}
	
	public void advance() {
		
		time++;
		
		onAdvanceStart(roadMap,eventList,time);
		
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
		
		onAdvanceEnd(roadMap,eventList,time);
		
	}
	
	public void reset() {
		roadMap.reset();
		eventList.clear();
		time = 0;
		onReset(roadMap,eventList,time);
	}
	
	public JSONObject report() {
		
		JSONObject aux = new JSONObject();
		aux.put("time", time);
		aux.put("state", roadMap.report());
		
		return aux;
	}
	
	public void addObserver(TrafficSimObserver o) {
		this.o = o;
	}
	
	public void removeObserver(TrafficSimObserver o) {
		this.o = null;
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	//No se si se puede
	public RoadMap getRoadMap() {
		return roadMap;
	}
	
	
}
