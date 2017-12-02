package adamzerella.eBayExporter;

import adamzerella.eBayExporter.util.*;

public class Controller {
	private Developer dev = null;
	
	Controller(){
		this.dev = new Developer();
		
		new Debug().Log("CREATED CONTROLLER DOMAIN");
	}
			
	public void ExportQueryToCSV() { }
	
	public String getEbayOfficalTime() {
		return new EbayGetCall(this.dev).getEbayOfficalTime().toString();
	}
	
	public String getMyeBayBoughtTitles() {
		return new EbayGetCall(this.dev).getMyEbayBuyTitles();
	}
	
	public void setDeveloper(Developer d) {
		this.dev = d;
	}
}