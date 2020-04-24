package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {

	
	private static final String HELPMSG = "Schedule an event to change the weather of a road after a given number of simulation ticks from now.";
	private final JPanel contentPanel = new JPanel();
	private RoadMap map;
	private JSpinner spTicks;
	private int _status;
	private JComboBox<Road> roads;
	private DefaultComboBoxModel<Road> roadsModel;
	private JComboBox<Weather> cbWeather;
	
	public ChangeWeatherDialog(JFrame parent) {
		super(parent,true);

		initGUI();
	}
	
	private void initGUI() {
		
		_status = 0;
		setTitle("Change CO2 Class");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 450, 160);
        
        //Se le añade un layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setBackground(Color.white);
        
        //Se le añade el texto
        JTextArea texto = new JTextArea(HELPMSG);
        texto.setBounds(20, 20, 440, 220);
        texto.setWrapStyleWord(true);
        texto.setLineWrap(true);
        texto.setEditable(false);
        contentPanel.add(texto);
        
        JPanel panelC = new JPanel();
        getContentPane().add(panelC, BorderLayout.CENTER);
        panelC.setBackground(Color.white);
        
        JPanel panelCMain = new JPanel();
        panelC.add(panelCMain);
        panelCMain.setLayout(new FlowLayout());
        panelCMain.setBackground(Color.white);
        
        JLabel tRoad = new JLabel(" Road: " );
        panelCMain.add(tRoad);
        
        roadsModel = new DefaultComboBoxModel<Road>();
        
        roads = new JComboBox();
        
        roads.setModel(roadsModel);
        panelCMain.add(roads);
        
        JLabel tWeather = new JLabel(" Weather: " );
        panelCMain.add(tWeather);   
        
        cbWeather = new JComboBox<Weather> (Weather.values());
        panelCMain.add(cbWeather);
        
        JLabel tTicks = new JLabel(" Ticks: " );
        panelCMain.add(tTicks); 
        
        spTicks = new JSpinner();
        SpinnerModel model =   new SpinnerNumberModel(1, 0, 20, 1);
        spTicks.setModel(model);
        panelCMain.add(spTicks); 
        
        JPanel panelB = new JPanel();
        getContentPane().add(panelB, BorderLayout.SOUTH);
        panelB.setBackground(Color.white);
        
        JButton bCancel = new JButton("Cancel");
        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	_status = 0;
            	setVisible(false);
            }
		});
        
        JButton bOk = new JButton("OK");
        bOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	_status = 1;
            	setVisible(false);
            }
		});
        
        panelB.add(bCancel);
        panelB.add(bOk);
        
        this.pack();
    	this.setLocationRelativeTo(null);
	}
	
	protected int open(RoadMap map) {
		this.map = map;
		
		for(Road r : map.getRoads()) {
	        roadsModel.addElement(r);
	    }
		
		setVisible(true);
		return _status;
	}
	
	protected Road getRoad() {
		return (Road) roads.getSelectedItem();
	}
	
	protected Weather getWeather() {
		return (Weather) cbWeather.getSelectedItem();
	}
	
	protected int getTicks() {
		return (int) spTicks.getValue();
	}
	
}
