package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver{

	private Controller _ctrl;
	private JButton bCargaFich,bChangeCO2,bChangeW,bPlay,bStop,bExit;
	private boolean _stopped;
	private RoadMap _map;
	private int _time;
	
	public ControlPanel(Controller _ctrl) {
		this._ctrl = _ctrl;
		this.setLayout(new GridLayout(0,2));
		_ctrl.addObserver(this);
		initGUI();
		setName("ControlPanel");
	}
	
	private void initGUI() {
		
		
		JPanel mainP = new JPanel();
		mainP.setLayout(new FlowLayout(FlowLayout.LEFT));

		//Boton Carga del fichero de eventos
		bCargaFich = new JButton();
		bCargaFich.setIcon(new ImageIcon("resources/icons/open.png"));
		bCargaFich.setToolTipText("Open a file");
		bCargaFich.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

            	JDialog dialog = new JDialog();
            	dialog.setTitle("Open");
            	dialog.setBounds(getParent().getWidth(), getParent().getHeight(), 500, 300);
            	JFileChooser fileChooser = new JFileChooser("resources/examples");
            	int returnValue = fileChooser.showOpenDialog(null);
        		if (returnValue == JFileChooser.APPROVE_OPTION) {

        			_ctrl.reset();
        			try {
        				

						_ctrl.loadEvents(new FileInputStream(fileChooser.getSelectedFile()));
						
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
        		}else if(returnValue == JFileChooser.CANCEL_OPTION){
        			fileChooser.setVisible(false);
        		}
            	dialog.add(fileChooser);
            }

        });
		
		mainP.add(bCargaFich);
		
		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		sep.setPreferredSize(new Dimension(2,50));
		sep.setForeground(Color.black);
		sep.setBackground(Color.black);
		mainP.add(sep);
		
		//Panel Botones de Changeco2 y ChangeWeather
		JPanel pChange = new JPanel(); 
		pChange.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		//Boton Cambio de la clase de contaminación de un vehículo
		bChangeCO2 = new JButton();
		bChangeCO2.setIcon(new ImageIcon("resources/icons/co2class.png"));
		bChangeCO2.setToolTipText("Change CO2 class of a Vehicle");
		bChangeCO2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog((JFrame) SwingUtilities.getWindowAncestor(bChangeCO2));
            	
            	int status = dialog.open(_map);
            	
            	if(status == 1) {
            		List<Pair<String, Integer>> cs = new ArrayList<>();
            		cs.add(new Pair<String,Integer>(dialog.getVehicle().getId(),dialog.getContClass()));
            		_ctrl.addEvent(new NewSetContClassEvent(_time + dialog.getTicks(),cs));
            	}
            }
		});
		
		pChange.add(bChangeCO2);
		
		//Boton Cambio de tiempo de una carretera
		bChangeW = new JButton();
		bChangeW.setIcon(new ImageIcon("resources/icons/weather.png"));
		bChangeW.setToolTipText("Change Weather of a Road");
		bChangeW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	ChangeWeatherDialog dialog = new ChangeWeatherDialog((JFrame) SwingUtilities.getWindowAncestor(bChangeCO2));
            	
            	int status = dialog.open(_map);
            	
            	if(status == 1) {
            		List<Pair<String, Weather>> cs = new ArrayList<>();
            		cs.add(new Pair<String,Weather>(dialog.getRoad().getId(),dialog.getWeather()));
            		_ctrl.addEvent(new SetWeatherEvent(_time + dialog.getTicks(),cs));
            	}
            }
		});
		pChange.add(bChangeW);
		mainP.add(pChange);
		
		JSeparator sep2 = new JSeparator(SwingConstants.VERTICAL);
		sep2.setPreferredSize(new Dimension(3,50));
		sep2.setForeground(Color.black);
		sep2.setBackground(Color.black);
		mainP.add(sep2);
		
		
		//Panel de botones play/stop
		JPanel pBPlayStop = new JPanel();
		pBPlayStop.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		
		//Spinner de ticks
		JLabel tTicks = new JLabel("Ticks: ");
		
		JSpinner spTicks = new JSpinner();
		SpinnerModel model =   new SpinnerNumberModel(10, 1, 10000, 1);
		spTicks.setModel(model);
		spTicks.setPreferredSize(new Dimension(60,40));
		
		//Boton Play
		bPlay = new JButton();
		bPlay.setIcon(new ImageIcon("resources/icons/run.png"));
		bPlay.setToolTipText("Run the simulator");
		bPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	_stopped = false;
            	enableToolBar(false);
            	run_sim((int)spTicks.getValue());
            }
		});
		
		//Boton Stop
		bStop = new JButton();
		bStop.setIcon(new ImageIcon("resources/icons/stop.png"));
		bStop.setToolTipText("Stop the simulator");
		bStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	stop();
            }
		});
				
		pBPlayStop.add(bPlay);
		pBPlayStop.add(bStop);
		mainP.add(pBPlayStop);

		mainP.add(tTicks);
		mainP.add(spTicks);
		add(mainP);
		
		JSeparator sep3 = new JSeparator(SwingConstants.VERTICAL);
		sep3.setPreferredSize(new Dimension(3,50));
		sep3.setForeground(Color.black);
		sep3.setBackground(Color.black);
		mainP.add(sep3);
		
		JPanel pExit = new JPanel();
		pExit.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JSeparator sep4 = new JSeparator(SwingConstants.VERTICAL);
		sep4.setPreferredSize(new Dimension (3,50));
		sep4.setBackground(Color.black);
		sep4.setForeground(Color.black);
		pExit.add(sep4);
		bExit = new JButton();
		bExit.setIcon(new ImageIcon("resources/icons/exit.png"));
		bExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	int seleccion = JOptionPane.showOptionDialog( (JFrame) SwingUtilities.getWindowAncestor(bExit),
            			   "Are you sure you want to quit?", 
            			   "Quit",
            			   JOptionPane.YES_NO_OPTION,
            			   JOptionPane.QUESTION_MESSAGE,
            			   null,    
            			   new Object[] { "No", "Yes"},   
            			   null);
            	
            	
            	if(seleccion == 1) {
            		System.exit(0);
            	}
            }
		});
		pExit.add(bExit);
		add(pExit);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

		
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {

		this._time = time;
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {

		
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_map = map;
		_time = time;
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	private void run_sim( int n ) {
		if ( n > 0 && ! _stopped ) {
			try {
			_ctrl .run(1);
			} catch (Exception e ) {
			// TODO show error message
			_stopped = true ;
			return ;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
				run_sim( n - 1);
				}
			});
		} else {
			enableToolBar( true );
			_stopped = true ;
		}
	}
		
	
	private void stop() {
		_stopped = true ;
		enableToolBar(true);
	}
	
	private void enableToolBar(boolean enable) {
		
		bCargaFich.setEnabled(enable);
		bChangeCO2.setEnabled(enable);
		bChangeW.setEnabled(enable);
		bPlay.setEnabled(enable);
	}
	
	
	
	
}
