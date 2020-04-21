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

import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Vehicle [] vehicles;
	private final String [] co2Class = {"0","1","2","3","4","5","6","7","8","9","10"};
	
	public ChangeCO2ClassDialog(JFrame parent,List<Vehicle> vehicles) {
		super(parent,true);
		setTitle("Change CO2 Class");
		
		if(vehicles.size() == 0) {
			//Excepcion
			this.vehicles = new Vehicle [10];
			
		}else {
			this.vehicles = new Vehicle [vehicles.size() - 1];
			
			for(int i = 0; i < vehicles.size() - 1; i++) {
				this.vehicles[i] = vehicles.get(i);
			}
			
		}
		

		initDialog();
	}
	
	private void initDialog() {
		
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 450, 160);
        
        //Se le añade un layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setBackground(Color.white);
        
        //Se le añade el texto
        JTextArea texto = new JTextArea("Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.");
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
        
        JTextArea tVehicle = new JTextArea(" Vehicle: " );
        tVehicle.setEditable(false);	
        panelCMain.add(tVehicle);        
        
        JComboBox<Vehicle> cbVehicles = new JComboBox<Vehicle> (vehicles);
        panelCMain.add(cbVehicles);
        
        JTextArea tCO2 = new JTextArea(" CO2 Class: " );
        tCO2.setEditable(false);
        panelCMain.add(tCO2);   
        
        JComboBox<String> cbCO2 = new JComboBox<String> (co2Class);
        panelCMain.add(cbCO2);
        
        JTextArea tTicks = new JTextArea(" Ticks: " );
        tTicks.setEditable(false);
        panelCMain.add(tTicks); 
        
        JSpinner spTicks = new JSpinner();
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
            	setVisible(false);
            }
		});
        
        JButton bOk = new JButton("OK");
        bOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	
            	setVisible(false);
            }
		});
        
        panelB.add(bCancel);
        panelB.add(bOk);
        
        setVisible(true);
	}
	
	
}
