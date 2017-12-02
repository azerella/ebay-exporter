package adamzerella.eBayExporter.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Debug {
	private Calendar cal 			= null;
	private String className 		= "";
	private int lineNumber			= 0;
		
	public Debug(){
		this.cal = Calendar.getInstance();
		className = new Exception().getStackTrace()[1].getClassName().replaceAll(".+\\.", "");
		lineNumber = new Exception().getStackTrace()[1].getLineNumber();
	}

	public void Log(String error) { 
		System.out.println("LOG: " +"\t" + 
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS").format(cal.getTime()) + "\t" + "\t"+
				className + " : " + lineNumber + "\t"  + 
				" [" + error + "]");
	}
	
	public void Err(String error) { 
		System.err.println("ERROR: " +"\t" + 
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS").format(cal.getTime()) + "\t" + "\t"+
				className + " : " + lineNumber + "\t"  + 
				" [" + error + "]");
	}
}