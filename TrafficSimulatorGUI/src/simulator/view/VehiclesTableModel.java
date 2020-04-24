package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import extra.jtable.EventEx;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{

	private String[] _colNames = {"id", "Location", "Itinerary", "CO2 Class" , "Max.Speed", "Speed" , "Total CO2", "Distance" };
	private List<Vehicle> vehicles;
	private Controller _ctrl;
	
	public VehiclesTableModel(Controller _ctrl) {
		vehicles=null;
		this._ctrl = _ctrl;
		_ctrl.addObserver(this);
	}
	
	public void update() {
		fireTableDataChanged();
	}
	
	public void setVehiclesList(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
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
		return vehicles == null ? 0 : vehicles.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = vehicles.get(rowIndex).getId();
			break;
		case 1:
			
			switch(vehicles.get(rowIndex).getStatus()) {
				case PENDING: 
					s = "Pending";
					break;
				case TRAVELING:
					s = vehicles.get(rowIndex).getRoad() + ":" + vehicles.get(rowIndex).getLocation();
					break;
				case WAITING:
					s = "Waiting";
					break;
				case ARRIVED:
					s = "Arrived";
					break;
			}
			
			break;
		case 2:
			s = vehicles.get(rowIndex).getItinerary();
			break;
		case 3:
			s = vehicles.get(rowIndex).getContClass();
			break;
		case 4:
			s = vehicles.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = vehicles.get(rowIndex).getSpeed();
			break;
		case 6:
			s = vehicles.get(rowIndex).getContTotal();
			break;
		case 7:
			s = vehicles.get(rowIndex).getDistanciaR();
			break;
		}
		
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setVehiclesList(map.getVehicles());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {

		setVehiclesList(map.getVehicles());
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}

