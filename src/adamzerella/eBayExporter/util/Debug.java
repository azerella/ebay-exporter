package adamzerella.eBayExporter.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Debug {
	Calendar cal = null;

	public Debug(){
		this.cal = Calendar.getInstance();
	}

	public void Log(String error) { 
		System.out.println("LOG: " +"\t" + 
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS").format(cal.getTime()) + "\t"
				+  "\t" + new Exception().getStackTrace()[1].getClassName() + "\t" 
				+ " [" + error + "]");
	}
	
	public void Err(String error) { 
		System.err.println("ERROR: " +"\t" + 
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(cal.getTime()) + "\t"
				+ " [" + error + "]");
	}
}