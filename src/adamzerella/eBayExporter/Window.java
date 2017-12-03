package adamzerella.eBayExporter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.ebay.soap.eBLBaseComponents.SiteCodeType;

import adamzerella.eBayExporter.util.*;

public class Window extends JFrame implements WindowListener, 
							WindowFocusListener, WindowStateListener{

	private static final long serialVersionUID 	= -3302496335105363726L;
	protected short width 						= 0;
	protected short height 						= 0;
	protected String title 						= "";
	protected JPanel winPane					= null;

	private String configPath 					= "config.prop";
	private PropertiesFile	pf					= null;
	private Controller ctrl						= null;
	private Developer dev						= null;

	public Window(){
		new Debug().Log("INSTANSIATE WINDOW");

		this.ctrl = new Controller(); //Use controller to interact with domain
		this.pf = new PropertiesFile(configPath);
		
		addWindowListener(this);
		addWindowFocusListener(this);
		addWindowStateListener(this);

		this.setTitle(this.pf.getProperty("title"));
		this.setResizable(Boolean.valueOf(this.pf.getProperty("resizeable")));
		this.setLocation((int) Double.parseDouble((pf.getProperty("windowX"))), 
				(int) Double.parseDouble(pf.getProperty("windowY")));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(parseInteger(pf.getProperty("minWidth")), 
				parseInteger(pf.getProperty("minHeight"))));
		this.setPreferredSize(new Dimension(parseInteger(pf.getProperty("width")), 
				parseInteger(pf.getProperty("height"))));
		this.setExtendedState(0); //NORMAL
		this.pack();

		if (!isDeveloperPropertiesValid()) {
			this.add(createSetupPanel());			
		}else {
			this.dev = new Developer(
					pf.getProperty("authToken"),
					pf.getProperty("appID"),
					pf.getProperty("devID"),
					pf.getProperty("certID"),
					pf.getProperty("serverURL"));
			this.ctrl.setDeveloper(this.dev);
			this.add(createLandingPanel());
		}
		
		this.setVisible(true);
	}

	/**
	 * Check if 'config.prop' file successfully loaded previous credentials
	 * @return true if the Properties file was found and keys were 
	 * loaded successfully, false otherwise.
	 */
	public boolean isDeveloperPropertiesValid() {
		if (this.pf.getProperty("authToken").isEmpty() || 
				this.pf.getProperty("appID").isEmpty() ||
				this.pf.getProperty("devID").isEmpty() ||
				this.pf.getProperty("certID").isEmpty()) {

			return false;
		}
		return true;
	}

	private boolean isSetupPaneInputValid(String auth, String app, String dev, String cert) {
		if (auth.isEmpty() || app.isEmpty() || dev.isEmpty() || cert.isEmpty()) {
			return false;
		}
		return true;
	}
	
	private int parseInteger(String str) {
		if (str.equals(null) || str.equals("") || str.equals(" ")) {
			return 0;
		}
		else {
			return Integer.parseInt(str);
		}
	}
	
	/**
	 * Updates the Properties object with the latest values.
	 */
	public void RefreshProperties() {
		//Window Properties
		this.pf.setProperty("title", this.getTitle());
		this.pf.setProperty("resizeable", String.valueOf(this.isResizable()));
		this.pf.setProperty("windowX", String.valueOf(this.getLocation().getX()));
		this.pf.setProperty("windowY", String.valueOf(this.getLocation().getY()));
		this.pf.setProperty("width", String.valueOf(this.getSize().width));
		this.pf.setProperty("height", String.valueOf(this.getSize().height));
		this.pf.setProperty("visible", String.valueOf(this.isVisible()));
		this.pf.setProperty("extendedState", String.valueOf(this.getExtendedState()));

		//Developer Properties
		this.pf.setProperty("authToken", this.dev.getAUTH_TOKEN());
		this.pf.setProperty("appID", this.dev.getAPI_APP_ID());
		this.pf.setProperty("devID", this.dev.getAPI_DEV_ID());
		this.pf.setProperty("certID", this.dev.getAPI_CERT_ID());
		this.pf.setProperty("serverURL", this.dev.getAPI_SERVER_URL());

		new Debug().Log("UPDATED PROPERTY FILE VALUES");
	}

	@Override
	public void windowStateChanged(WindowEvent arg0) {
		new Debug().Log("WINDOW STATE CHANGED");
	}

	@Override
	public void windowGainedFocus(WindowEvent arg0) {
		new Debug().Log("WINDOW FOCUSED");
	}

	@Override
	public void windowLostFocus(WindowEvent arg0) {
		new Debug().Log("WINDOW LOST FOCUS");		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		new Debug().Log("WINDOW ACTIVATED");		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		new Debug().Log("WINDOW CLOSED");
	}

	/**
	 * Window Listener for a Window Closing event.
	 */
	@Override
	public void windowClosing(WindowEvent arg0) {
		new Debug().Log("WINDOW CLOSING");

		//Write latest config settings to file.
		RefreshProperties();
		this.pf.WriteProperties();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		new Debug().Log("WINDOW DEACTIVATED");
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		new Debug().Log("WINDOW DEICONIFIED");

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		new Debug().Log("WINDOW ICONIFIED");
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		new Debug().Log("WINDOW OPENED");
	}

	private JPanel createSetupPanel() {
		this.dev = new Developer(); //Invoke new developer instance

		JPanel pane = new JPanel();
		pane.setName("Setup");
		pane.setPreferredSize(this.getSize());
		pane.setLayout(new GridBagLayout());
		pane.setBorder(new EmptyBorder(20, 20, 20, 20));

		GridBagConstraints gbc = new GridBagConstraints();

		JLabel lblSetupInstructions = new JLabel();
		lblSetupInstructions.setText("Please enter your eBay Developer credentials: ");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 35, 0);
		pane.add(lblSetupInstructions, gbc);

		//***************BEGIN AUTH TOKEN********
		JLabel lblAuthToken = new JLabel();
		lblAuthToken.setText("Authentication Token: " );
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		pane.add(lblAuthToken, gbc);

		JTextField txtAuthInput = new JTextField();
		txtAuthInput.setPreferredSize(new Dimension(50,35));
		txtAuthInput.setEditable(true);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.weightx = 0.35;
		pane.add(txtAuthInput, gbc);
		//***************END AUTH TOKEN***********


		//***************BEGIN APP ID*************
		JLabel lblAppID = new JLabel();
		lblAppID.setText("App ID (Client ID): " );
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 4;
		pane.add(lblAppID, gbc);

		JTextField txtAppInput = new JTextField();
		txtAppInput.setPreferredSize(new Dimension(50,35));
		txtAppInput.setEditable(true);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.weightx = 0.35;
		pane.add(txtAppInput, gbc);
		//***************BEGIN APP ID*************


		//***************BEGIN DEV ID*************
		JLabel lblDevId = new JLabel();
		lblDevId.setText("Dev ID: " );
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 5;
		pane.add(lblDevId, gbc);

		JTextField txtDevInput = new JTextField();
		txtDevInput.setPreferredSize(new Dimension(50,35));
		txtDevInput.setEditable(true);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.weightx = 0.35;
		pane.add(txtDevInput, gbc);
		//***************END DEV ID***************


		//***************BEGIN CERT ID************
		JLabel lblCertId = new JLabel();
		lblCertId.setText("Cert ID (Client Secret): " );
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 6;
		pane.add(lblCertId, gbc);

		JTextField txtCertInput = new JTextField();
		txtCertInput.setPreferredSize(new Dimension(50,35));
		txtCertInput.setEditable(true);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.weightx = 0.35;
		pane.add(txtCertInput, gbc);
		//***************END DEV ID***************


		//***************BEGIN SITECODE***********
		JLabel lblSiteCode = new JLabel();
		lblSiteCode.setText("eBay Site Code: " );
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 7;
		pane.add(lblSiteCode, gbc);

		JComboBox<SiteCodeType> comboSiteInput = new JComboBox<SiteCodeType>();
		comboSiteInput.setPreferredSize(new Dimension(50,35));
		comboSiteInput.setEditable(false);
		for (SiteCodeType site : this.ctrl.getAllSiteCodes()) {
			comboSiteInput.addItem(site);
		}
		comboSiteInput.setSelectedItem(SiteCodeType.AUSTRALIA);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.weightx = 0.35;
		pane.add(comboSiteInput, gbc);
		//***************BEGIN SITECODE***********


		//***************BEGIN SERVICE ENDPOINT***
		JLabel lblService = new JLabel();
		lblService.setText("eBay Service Endpoint: " );
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 8;
		pane.add(lblService, gbc);

		JComboBox<String> comboServiceInput = new JComboBox<String>();
		comboServiceInput.setPreferredSize(new Dimension(50,35));
		comboServiceInput.setEditable(false);
		for (int i=0; i < this.ctrl.getAllServiceEndpoints().length; i++) {
			comboServiceInput.addItem(this.ctrl.getAllServiceEndpoints()[i]);
		}
		comboServiceInput.setSelectedItem(0);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.weightx = 0.35;
		pane.add(comboServiceInput, gbc);
		//***************END SERVICE ENDPOINT***


		JButton btnAuthenticate = new JButton();
		btnAuthenticate.setText("Validate");
		btnAuthenticate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Debug().Log("VALIDATE BUTTON PRESS DOWN");

				if (isSetupPaneInputValid(txtAuthInput.getText(), 
						txtAppInput.getText(), 
						txtDevInput.getText(), 
						txtCertInput.getText())) {
					
					dev.setAUTH_TOKEN(txtAuthInput.getText());
					dev.setAPI_APP_ID(txtAppInput.getText());
					dev.setAPI_DEV_ID(txtDevInput.getText());
					dev.setAPI_CERT_ID(txtCertInput.getText());
					dev.setAPI_SITECODE(comboSiteInput.getItemAt(comboSiteInput.getSelectedIndex()));	
					dev.setAPI_SERVER_URL(comboServiceInput.getItemAt(comboServiceInput.getSelectedIndex()));
					
					btnAuthenticate.setForeground(Color.GREEN);
					pane.setVisible(false);
					getFrameInstance().getContentPane().add(createLandingPanel());
					ctrl.setDeveloper(dev); 
					
					new Debug().Log(dev.toString());
				}
				else {
					btnAuthenticate.setForeground(Color.RED);
				}
			}
		});
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.insets = new Insets(15, 0, 0, 0);
		pane.add(btnAuthenticate, gbc);
		
		return pane;
	}
	
	private JPanel createLandingPanel() {
		JPanel pane = new JPanel();
		pane.setName("Landing");
		pane.setPreferredSize(this.getSize());
		pane.setLayout(new GridBagLayout());
		pane.setBorder(new EmptyBorder(20, 20, 20, 20));

		GridBagConstraints gbc = new GridBagConstraints();

		JLabel lbleBayTitle = new JLabel();
		lbleBayTitle.setText("eBay Exporter");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		pane.add(lbleBayTitle, gbc);
		
		JLabel lbleBayTime = new JLabel();
		lbleBayTime.setText("eBay Offical Time: " + "\t" + this.ctrl.getEbayOfficalTime());
		lbleBayTime.setForeground(Color.DARK_GRAY);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.5;
		pane.add(lbleBayTime, gbc);
		
		return pane;
	}
	
	public JFrame getFrameInstance() {
		return this;
	}
}