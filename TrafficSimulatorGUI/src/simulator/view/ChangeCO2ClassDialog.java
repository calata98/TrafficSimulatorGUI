package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {
	
	private static final String HELPMSG = "Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.";

	private final JPanel contentPanel = new JPanel();
	private int _status;
	private RoadMap map;
	private JSpinner spTicks;
	private JComboBox<Vehicle> vehicles;
	private JComboBox<Integer> cbCO2;
	private DefaultComboBoxModel<Vehicle> vehiclesModel;
	private final Integer [] co2Class = {0,1,2,3,4,5,6,7,8,9,10};
	
	public ChangeCO2ClassDialog(JFrame parent) {
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
        
        JLabel tVehicle = new JLabel(" Vehicle: " );
        panelCMain.add(tVehicle);        
        
        
        vehiclesModel = new DefaultComboBoxModel<Vehicle>();
        
        vehicles = new JComboBox();
        
        vehicles.setModel(vehiclesModel);
        panelCMain.add(vehicles);
        
        JLabel tCO2 = new JLabel(" CO2 Class: " );
        panelCMain.add(tCO2);   
        
        cbCO2 = new JComboBox<Integer> (co2Class);
        panelCMain.add(cbCO2);
        
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
		
		for(Vehicle v : map.getVehicles()) {
	        vehiclesModel.addElement(v);
	    }
		
		setVisible(true);
		return _status;
	}
	
	protected Vehicle getVehicle() {
		return (Vehicle) vehicles.getSelectedItem();
	}
	
	protected int getContClass() {
		return (int) cbCO2.getSelectedItem();
	}
	
	protected int getTicks() {
		return (int) spTicks.getValue();
	}
	
	
}
