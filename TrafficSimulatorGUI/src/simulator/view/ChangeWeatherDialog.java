package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {

	
	private final JPanel contentPanel = new JPanel();
	private Road [] roads;
	
	public ChangeWeatherDialog(JFrame parent,List<Road> roads) {
		super(parent,true);
		setTitle("Change CO2 Class");
		
		if(roads.size() == 0) {
			//Excepcion
			this.roads = new Road [10];
			
		}else {
			this.roads = new Road [roads.size() - 1];
			
			for(int i = 0; i < roads.size() - 1; i++) {
				this.roads[i] = roads.get(i);
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
        JTextArea texto = new JTextArea("Schedule an event to change the weather of a road after a given number of simulation ticks from now.");
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
        
        JTextArea tRoad = new JTextArea(" Road: " );
        tRoad.setEditable(false);	
        panelCMain.add(tRoad);        
        
        JComboBox<Road> cbRoads = new JComboBox<Road> (roads);
        panelCMain.add(cbRoads);
        
        JTextArea tWeather = new JTextArea(" Weather: " );
        tWeather.setEditable(false);
        panelCMain.add(tWeather);   
        
        JComboBox<Weather> cbWeather = new JComboBox<Weather> (Weather.values());
        panelCMain.add(cbWeather);
        
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
