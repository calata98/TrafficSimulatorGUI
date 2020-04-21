package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.*;

import simulator.control.Controller;

public class ControlPanel extends JPanel{

	private Controller _ctrl;
	private JButton bCargaFich,bChangeCO2,bChangeW,bPlay,bStop;
	
	public ControlPanel(Controller _ctrl) {
		this._ctrl = _ctrl;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(Color.white);
		initButtons();
		setName("ControlPanel");
	}
	
	private void initButtons() {
		
		
		
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
		
		add(bCargaFich);
		
		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		sep.setPreferredSize(new Dimension(2,50));
		sep.setForeground(Color.black);
		sep.setBackground(Color.black);
		add(sep);
		
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
            	ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog((JFrame) SwingUtilities.getWindowAncestor(bChangeCO2),_ctrl.getSimulator().getRoadMap().getVehicles());
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
            	ChangeWeatherDialog dialog = new ChangeWeatherDialog((JFrame) SwingUtilities.getWindowAncestor(bChangeCO2),_ctrl.getSimulator().getRoadMap().getRoads());
            }
		});
		pChange.add(bChangeW);
		add(pChange);
		
		JSeparator sep2 = new JSeparator(SwingConstants.VERTICAL);
		sep2.setPreferredSize(new Dimension(3,50));
		sep2.setForeground(Color.black);
		sep2.setBackground(Color.black);
		add(sep2);
		
		
		//Panel de botones play/stop
		JPanel pBPlayStop = new JPanel();
		pBPlayStop.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		pBPlayStop.setBackground(Color.white);
		
		
		//Spinner de ticks
		JTextArea tTicks = new JTextArea("Ticks: ");
		tTicks.setEditable(false);
		tTicks.setBackground(Color.white);
		
		JSpinner spTicks = new JSpinner();
		SpinnerModel model =   new SpinnerNumberModel(10, 1, 1000, 1);
		spTicks.setModel(model);
		spTicks.setPreferredSize(new Dimension(60,40));
		
		//Boton Play
		bPlay = new JButton();
		bPlay.setIcon(new ImageIcon("resources/icons/run.png"));
		bPlay.setToolTipText("Run the simulator");
		bPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	_ctrl.run((int)spTicks.getValue());
            }
		});
		
		//Boton Stop
		bStop = new JButton();
		bStop.setIcon(new ImageIcon("resources/icons/stop.png"));
		bStop.setToolTipText("Stop the simulator");
		bStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	//_ctrl.run();
            }
		});
				
		pBPlayStop.add(bPlay);
		pBPlayStop.add(bStop);
		add(pBPlayStop);

		add(tTicks);
		add(spTicks);
	}
	
	
	
	
}
