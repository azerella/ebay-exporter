package adamzerella.eBayExporter;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;

import adamzerella.eBayExporter.util.Debug;

public class PropertiesFile extends File{

	private static final long serialVersionUID 	= -6603518301700635523L;
	private Properties prop 					= null;

	
    public PropertiesFile(String path) {
		super(path);

		setupFile();
	}

	/**
	 * Create or initialise the Properties file and object.
	 */
	private void setupFile() {
		this.prop = new Properties();

		if (!this.exists()) {
			try {
				this.createNewFile();

				this.prop.setProperty("minWidth", "640");
				this.prop.setProperty("minHeight", "480");
				this.prop.setProperty("width", "");
				this.prop.setProperty("height", "");
				this.prop.setProperty("resizeable", "true");
				this.prop.setProperty("visible", "true");
				this.prop.setProperty("extendedState", "0");
				this.prop.setProperty("windowX", new DecimalFormat("0.#").format(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2.5));
				this.prop.setProperty("windowY", new DecimalFormat("0.#").format(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2.5));
				this.prop.setProperty("title", "eBay Exporter");
				this.prop.setProperty("authToken", "");
				this.prop.setProperty("appID", "");
				this.prop.setProperty("devID", "");
				this.prop.setProperty("certID", "");
				this.prop.setProperty("serverURL", "");
				this.prop.setProperty("siteCode", "");
				this.prop.setProperty("WSDLVer", "");
			} catch (IOException ex) {
				new Debug().Err(ex.getMessage());
			}
		}

		readProperties();
	}

	/**
	 * Read and initalise Properties object with file input stream.
	 */
	private void readProperties() {
		FileInputStream in = null;

		try {
			in = new FileInputStream(this);
		} catch (FileNotFoundException e) {
			new Debug().Err(e.getMessage());
		}
		try {
			this.prop.load(in);
			in.close();
		} catch (IOException e) {
			new Debug().Err(e.getMessage());
		} 
	}

	/**
	 * Write the current state of the Properties object to file.
	 */
	public void WriteProperties() {
		FileOutputStream out = null;

		try {
			out = new FileOutputStream(this);
		} catch (FileNotFoundException e) {
			new Debug().Err(e.getMessage());
		}

		try {
			prop.store(out, "---No Comment---");
			out.close();
		} catch (IOException e) {
			new Debug().Err(e.getMessage());
		}
	}

	/**
	 * Return a property with the matching key
	 * @param key - object to match
	 * @return - key pair result
	 */
	public String getProperty(String key) {
		if (this.prop.containsKey(key)){
			return this.prop.getProperty(key);			
		}
		else {
			return "0";			
		}
	}
	
	/**
	 * Modify a key pair value with arguments specified.
	 * @param key - object to match
	 * @param value - pair value
	 */
	public void setProperty(String key, String value) {
		if (this.prop.containsKey(key)){
			this.prop.setProperty(key, value);
		}
		else {
			throw new NullPointerException("Property key: " + key + " wasn't found!");
		}
	}
}