package adamzerella.eBayExporter;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

	Window(){
		new Debug().Log("INSTANSIATE WINDOW");

		addWindowListener(this);
		addWindowFocusListener(this);
		addWindowStateListener(this);

		this.pf = new PropertiesFile(configPath);

		this.setTitle(this.pf.getProperty("title"));
		this.setResizable(Boolean.valueOf(this.pf.getProperty("resizeable")));
		this.setLocation((int) Double.parseDouble((pf.getProperty("windowX"))), (int) Double.parseDouble(pf.getProperty("windowY")));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(parseInteger(pf.getProperty("minWidth")), parseInteger(pf.getProperty("minHeight"))));
		this.setPreferredSize(new Dimension(parseInteger(pf.getProperty("width")), parseInteger(pf.getProperty("height"))));
		this.setExtendedState(0); //NORMAL
		this.pack();
		this.setVisible(true);

		this.ctrl = new Controller(); //Use controller to interact with domain

		this.dev = new Developer(
				pf.getProperty("authToken"),
				pf.getProperty("appID"),
				pf.getProperty("devID"),
				pf.getProperty("certID"),
				pf.getProperty("serverURL"));
		
		this.ctrl.setDeveloper(this.dev);

		//new Debug().Log(this.ctrl.getEbayOfficalTime());
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
}