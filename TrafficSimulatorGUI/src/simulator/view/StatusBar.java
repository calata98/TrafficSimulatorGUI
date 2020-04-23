package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{

	private Controller _ctrl;
	private RoadMap map;
	private JLabel lTime,lMsg;
	private int time;
	
	StatusBar(Controller _ctrl){
		this._ctrl = _ctrl;
		_ctrl.addObserver(this);
		initGUI();
	}
	
	
	private void initGUI() {
		
		setLayout(new BorderLayout());
		
		JPanel pTime = new JPanel();
		pTime.setPreferredSize(new Dimension(100,25));
		pTime.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		lTime = new JLabel("Time: " + time);
		pTime.add(lTime);
		
		add(pTime,BorderLayout.WEST);
		
		JPanel pMsg = new JPanel();
		pMsg.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		lMsg = new JLabel("Welcome!");
		pMsg.add(lMsg);
		
		add(pMsg,BorderLayout.CENTER);
		
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {

		this.time = time;
		lTime.setText("Time: " + time );
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

		lMsg.setText("Event added (" + events.get(events.size() - 1).toString() + ")" );
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {

		this.time = time;
		this.map = map;
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
