package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private String[] _colNames = {"Time", "Description" };
	private List<Event> _events;
	private Controller _ctrl;
	
	public EventsTableModel(Controller _ctrl) {
		_events=null;
		this._ctrl = _ctrl;
		_ctrl.addObserver(this);
	}
	
	public void update() {
		fireTableDataChanged();
	}
	
	public void setEventsList(List<Event> events) {
		_events = events;
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
		return _events == null ? 0 : _events.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _events.get(rowIndex).getTime();
			break;
		case 1:
			s = _events.get(rowIndex).toString();
			break;
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		setEventsList(events);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		List<Event> eventsAux = new ArrayList<>();
		for(Event e : events) {
			if(e.getTime() > time) {
				eventsAux.add(e);
			}
		}
		setEventsList(eventsAux);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		setEventsList(events);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
