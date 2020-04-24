package simulator.view;


import java.util.List;

import javax.swing.table.AbstractTableModel;


import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private String[] _colNames = {"Id", "Green", "Queues"};
	private List<Junction> junctions;
	private Controller _ctrl;
	
	public JunctionsTableModel(Controller _ctrl) {
		junctions=null;
		this._ctrl = _ctrl;
		_ctrl.addObserver(this);
	}
	
	public void update() {
		fireTableDataChanged();
	}
	
	public void setJunctionsList(List<Junction> junctions) {
		this.junctions = junctions;
		update();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	
	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return junctions == null ? 0 : junctions.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = junctions.get(rowIndex).getId();
			break;
		case 1:
			if(junctions.get(rowIndex).getGreenLightIndex() != -1) {
				s = junctions.get(rowIndex).getInRoads().get(junctions.get(rowIndex).getGreenLightIndex());
			}else {
				s = "None";
			}
			
			break;
		case 2:
			
			s = "";
			
			for(int i = 0; i < junctions.get(rowIndex).getInRoads().size(); i++) {
				s += junctions.get(rowIndex).getInRoads().get(i).getId() + ": [";
				
				for(int j = 0; j < junctions.get(rowIndex).getQueueList().get(i).size(); j++) {
					s += junctions.get(rowIndex).getQueueList().get(i).get(j).toString();
					
					if(j != junctions.get(rowIndex).getQueueList().get(i).size() - 1){
						s += ",";
					}
				}
				
				s += "]";
				
			}
			
			break;
		}
		
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setJunctionsList(map.getJunctions());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {

		setJunctionsList(map.getJunctions());
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}

